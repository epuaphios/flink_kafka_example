package org.example

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}
import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.connectors.kudu.connector.KuduTableInfo
import org.apache.flink.connectors.kudu.connector.writer.{AbstractSingleOperationMapper, KuduWriterConfig, RowOperationMapper}
import org.apache.flink.connectors.kudu.streaming.KuduSink
import org.apache.flink.streaming.api.scala._
import org.apache.flink.types.Row
import org.apache.flink.util.Collector
import org.example.connection.AppParameters
import org.example.connection.scylla.ScyllaQuery.savedOffset
import org.example.connection.scylla.ScyllaSessionBuild
import org.example.connection.scylla.ScyllaSessionBuild.getLastCommittedOffsets
import org.example.packet.JsonRoot
import org.example.parser.Parsers.parse201

import java.util

object main extends App {

  val env = StreamExecutionEnvironment.getExecutionEnvironment
//  val conf =  new Configuration()
//  val env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf)
//  env.setRuntimeMode(RuntimeExecutionMode.BATCH)


  AppParameters.TOPIC_NAME = "enabiz-mutation-409"
  AppParameters.APP_NAME = "flink-test"
  new ScyllaSessionBuild()
  val fromOffsets = getLastCommittedOffsets(AppParameters.TOPIC_NAME, AppParameters.APP_NAME)
  //closeScyllaSession()

  private val kafkaSource = KafkaSource.builder()
    .setBootstrapServers(AppParameters.BOOTSTRAP_SERVERS)
    .setTopics("enabiz-mutation-409")
    .setStartingOffsets(OffsetsInitializer.offsets(fromOffsets))
//    .setGroupId("appname")
    .setDeserializer(new KafkaUsageRecordDeserializationSchema())
    .build()


  private val writerConfig = KuduWriterConfig.Builder.setMasters(AppParameters.KUDU_MASTERS).build


  private val sink:KuduSink[Row] = new KuduSink(
    writerConfig,
    KuduTableInfo.forTable("p409_radyoloji_sonuc_kayit"),
    new RowOperationMapper(
      Array[String]( "systakipno" ,"kabul_zamani" ,"id" ,"idhash" ,"hizmet_sunucu" ,"radyoloji_loinc" ,"radyoloji_loinc_code" ,"islem_referans_numarasi" ,"rapor_onaylanma_zamani" ,"last_updated"),
      AbstractSingleOperationMapper.KuduOperation.UPSERT)
  )


  val lines = env.fromSource(kafkaSource, WatermarkStrategy.forMonotonousTimestamps(), "Kafka Source").name("Kafka Source")


  val  rows:DataStream[util.ArrayList[Row]]  = lines.map(x => {
    //new ScyllaSessionBuild()
//    val sessionSylla = ScyllaSessionBuild.getSession()
//    savedOffset(AppParameters.APP_NAME, x.getTopic, x.getPartition, x.getOffset, sessionSylla)
    if (x.getOffset % 1000 == 0) {
      println(x.getTopic+"->"+x.getPartition+"->"+x.getOffset)
    }
    val jsonRoot = mapperScala(x.getValue)
    if (jsonRoot.content.RADYOLOJI_SONUC_KAYIT != null) {
      parse201(jsonRoot)
      }
    else {
      null
    }
  }).name("Map")

  val row: DataStream[Row] = rows.flatMap(new FlatMapFunction[util.ArrayList[Row], Row] {
      override def flatMap(arrayList: util.ArrayList[Row], collector: Collector[Row]): Unit = {
        val iterator = arrayList.iterator()
        while (iterator.hasNext) {
          collector.collect(iterator.next())
        }
      }
  }).uid("FlatMap").name("FlatMap")


  row.addSink(sink).name("Kudu Sink")


  env.execute("kafka to kudu")

  def mapperScala(x:Array[Byte])= {
    try {
      val mapper = new ObjectMapper with ScalaObjectMapper
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
      mapper.registerModule(DefaultScalaModule)
      mapper.readValue(x, classOf[JsonRoot])
    } catch {
      case e: Exception => println(e)
        null
    }
  }
}

