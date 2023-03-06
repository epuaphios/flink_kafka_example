package org.example

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.streaming.api.scala._
/*
kafka/bin/kafka-topics.sh --bootstrap-server kafka2:9092 --create --topic flink-example --partitions 3 --replication-factor 3
 */
object ReadFromKafka extends App{
  private val env = StreamExecutionEnvironment.getExecutionEnvironment
  private val kafkaSource = KafkaSource.builder()
    .setBootstrapServers("172.18.241.143:31064")
    .setTopics("enabiz-mutation-223")
    .setGroupId("flink-consumer-group")
    .setStartingOffsets(OffsetsInitializer.latest())
    .setValueOnlyDeserializer(new SimpleStringSchema())
    .build()

  private val lines = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source")

  lines.print()

  env.execute("Read from Kafka")



}