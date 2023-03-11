package org.example

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.configuration.{ConfigConstants, Configuration}
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.connectors.kudu.connector.KuduTableInfo
import org.apache.flink.connectors.kudu.connector.writer.{AbstractSingleOperationMapper, KuduWriterConfig, RowOperationMapper}
import org.apache.flink.connectors.kudu.streaming.KuduSink
import org.apache.flink.streaming.api.scala._
import org.apache.flink.types.Row
import org.example.connection.AppParameters
import org.example.connection.scylla.ScyllaSessionBuild
import org.example.connection.scylla.ScyllaSessionBuild.getLastCommittedOffsets
import org.example.parser.Parsers.parse201

object main extends App{

  val conf = new Configuration();
  conf.setBoolean(ConfigConstants.LOCAL_START_WEBSERVER, true)
  val env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf)

  AppParameters.TOPIC_NAME = "enabiz-mutation-201"
  new ScyllaSessionBuild()
  val fromOffsets = getLastCommittedOffsets(AppParameters.TOPIC_NAME, "appname")
//  closeScyllaSession()

  private val kafkaSource = KafkaSource.builder()
    .setBootstrapServers(AppParameters.BOOTSTRAP_SERVERS)
    .setTopics("enabiz-mutation-201")
    //.setGroupId("flink-consumer-group")
    .setStartingOffsets(OffsetsInitializer.offsets(fromOffsets))
//    .setStartingOffsets(OffsetsInitializer.earliest())
    .setValueOnlyDeserializer(new EventDeserializationSchema())
    .build()


  private val writerConfig = KuduWriterConfig.Builder.setMasters(AppParameters.KUDU_MASTERS).build

  private val sink:KuduSink[Row] = new KuduSink(
    writerConfig,
    KuduTableInfo.forTable("p201_hasta_patoloji_bilgileri"),
    new RowOperationMapper(Array[String]("systakipno","istem_zamani","tetkik_sonucu"), AbstractSingleOperationMapper.KuduOperation.UPSERT)
  )


  private val lines = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source")


  private val a:DataStream[Row]  = lines.map(x => {
    if (x.content.HASTA_PATOLOJI_BILGILERI.PATOLOJI_BILGISI != null) {
      parse201(x)
    }
    else {
      null
    }
  })
//  try{
    a.addSink(sink)

    env.execute("Read from Kafka")





}

