package org.example

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.connectors.kudu.connector.KuduTableInfo
import org.apache.flink.connectors.kudu.connector.writer.{AbstractSingleOperationMapper, KuduWriterConfig, RowOperationMapper}
import org.apache.flink.connectors.kudu.streaming.KuduSink
import org.apache.flink.streaming.api.scala._
import org.apache.flink.types.Row
import org.example.connection.AppParameters
import org.example.connection.scylla.ScyllaSessionBuild.getLastCommittedOffsets

object ReadFromKafka extends App{


  private val env = StreamExecutionEnvironment.getExecutionEnvironment

  AppParameters.TOPIC_NAME = "enabiz-mutation-201"
  val fromOffsets = getLastCommittedOffsets(AppParameters.TOPIC_NAME, "appName")

  private val kafkaSource = KafkaSource.builder()
    .setBootstrapServers(AppParameters.BOOTSTRAP_SERVERS)
    .setTopics("enabiz-mutation-201")
    .setGroupId("flink-consumer-group")
    .setStartFromSpecificOffsets(fromOffsets)
    .setValueOnlyDeserializer(new EventDeserializationSchema())
    .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.LATEST));
  .build()


  private val writerConfig = KuduWriterConfig.Builder.setMasters(AppParameters.KUDU_MASTERS).build

  private val sink:KuduSink[Row] = new KuduSink(
    writerConfig,
    KuduTableInfo.forTable("p201_hasta_patoloji_bilgileri"),
    new RowOperationMapper(Array[String]("idhash"), AbstractSingleOperationMapper.KuduOperation.INSERT)
  )


  private val lines = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source")
  val kuduRow = new Row(1)
  val a:DataStream[Row]  = lines.map(x => {
    kuduRow.setField(0, x.idhash)
    kuduRow
  })

  a.addSink(sink)



  env.execute("Read from Kafka")

}

