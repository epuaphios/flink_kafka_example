/*
package org.example

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.connectors.kudu.connector.KuduTableInfo
import org.apache.flink.connectors.kudu.connector.writer.{AbstractSingleOperationMapper, KuduWriterConfig, PojoOperationMapper, RowOperationMapper}
import org.apache.flink.connectors.kudu.streaming.KuduSink
import org.apache.flink.streaming.api.scala._
import org.apache.flink.types.Row
import org.example.connection.AppParameters
import org.example.packet.JsonRoot

object ReadFromKafka extends App{


  private val env = StreamExecutionEnvironment.getExecutionEnvironment

  private val kafkaSource = KafkaSource.builder()
    .setBootstrapServers(AppParameters.BOOTSTRAP_SERVERS)
    .setTopics("enabiz-mutation-201")
    .setGroupId("flink-consumer-group")
    .setStartingOffsets(OffsetsInitializer.latest())
    .setValueOnlyDeserializer(new EventDeserializationSchema())
    .build()


  private val writerConfig = KuduWriterConfig.Builder.setMasters(AppParameters.KUDU_MASTERS).build

  private val sink:KuduSink[Row] = new KuduSink(
    writerConfig,
    KuduTableInfo.forTable("p201_hasta_patoloji_bilgileri"),
    new RowOperationMapper(Array[String]("idhash"), AbstractSingleOperationMapper.KuduOperation.INSERT)
  )


  private val lines = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source")

  val a:DataStream[String]  = lines.map(x => x.idhash)

  a.addSink(sink)

  // sleep to allow eventual consistency to finish
  Thread.sleep(1000)

  sink.close

  env.execute("Read from Kafka")

}
*/
