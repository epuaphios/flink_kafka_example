/*
package org.example;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.connectors.kudu.connector.KuduTableInfo;
import org.apache.flink.connectors.kudu.connector.writer.AbstractSingleOperationMapper;
import org.apache.flink.connectors.kudu.connector.writer.KuduWriterConfig;
import org.apache.flink.connectors.kudu.connector.writer.RowOperationMapper;
import org.apache.flink.connectors.kudu.streaming.KuduSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;
import org.example.connection.AppParameters;
import org.example.packet.JsonRoot;
import org.example.packet.KafkaClass;

public class main2 {



    public static void main(String[] args) throws Exception {

     final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    KafkaSource<KafkaClass> kafkaSource =
            KafkaSource.<KafkaClass>builder()
                    .setBootstrapServers(AppParameters.BOOTSTRAP_SERVERS)
                    .setTopics("enabiz-mutation-201")
                    .setStartingOffsets(OffsetsInitializer.earliest())
                    .setDeserializer((KafkaRecordDeserializationSchema<KafkaClass>) new MyDeserializationSchema())
                    .build();


        KuduWriterConfig writerConfig = KuduWriterConfig.Builder.setMasters(AppParameters.KUDU_MASTERS).build();

        KuduSink<Row> sink = new KuduSink<>(
                writerConfig,
                KuduTableInfo.forTable("p201_hasta_patoloji_bilgileri"),
                new RowOperationMapper(
                        new String[]{"systakipno"},
                        AbstractSingleOperationMapper.KuduOperation.UPSERT)
        );




        DataStreamSource<KafkaClass> lines = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source");

        DataStream<Row> row = lines.map(new MapFunction<KafkaClass, Row>() {
            @Override
            public Row map(KafkaClass e) throws Exception {
                Row kuduRow = new Row(1);
                kuduRow.setField(0, e.key());
                return kuduRow;
            }});

        row.addSink(sink);

        env.execute("Read from Kafka");

    }
}
*/
