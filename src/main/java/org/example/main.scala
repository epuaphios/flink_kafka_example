package org.example

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.streaming.api.scala._
import org.example.connection.AppParameters

object ReadFromKafka extends App{
  private val env = StreamExecutionEnvironment.getExecutionEnvironment
  private val kafkaSource = KafkaSource.builder()
    .setBootstrapServers(AppParameters.BOOTSTRAP_SERVERS)
    .setTopics("enabiz-mutation-201")
    .setGroupId("flink-consumer-group")
    .setStartingOffsets(OffsetsInitializer.latest())
    .setValueOnlyDeserializer(new EventDeserializationSchema())
    .build()

  private val lines = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source")

  lines.print()

  env.execute("Read from Kafka")

//  val writerConfig = KuduWriterConfig.Builder.setMasters(AppParameters.KUDU_MASTERS).build
//
//  val sink = new Nothing(writerConfig, KuduTableInfo.forTable("AlreadyExistingTable"), new Nothing(Array[String]("col1", "col2", "col3"), AbstractSingleOperationMapper.KuduOperation.UPSERT))

}