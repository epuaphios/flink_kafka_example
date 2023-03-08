package org.example;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connectors.kudu.connector.KuduTableInfo;
import org.apache.flink.connectors.kudu.connector.writer.AbstractSingleOperationMapper;
import org.apache.flink.connectors.kudu.connector.writer.KuduWriterConfig;
import org.apache.flink.connectors.kudu.connector.writer.RowOperationMapper;
import org.apache.flink.connectors.kudu.streaming.KuduSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;
import org.example.connection.AppParameters;
import org.example.packet.JsonRoot;
public class main2 {



    public static void main(String[] args) throws Exception {

     final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    KafkaSource<JsonRoot> kafkaSource =
            KafkaSource.<JsonRoot>builder()
                    .setBootstrapServers(AppParameters.BOOTSTRAP_SERVERS)
                    .setTopics("enabiz-mutation-201")
                    .setStartingOffsets(OffsetsInitializer.earliest())
                    .setValueOnlyDeserializer(new EventDeserializationSchema())
                    .build();


        KuduWriterConfig writerConfig = KuduWriterConfig.Builder.setMasters(AppParameters.KUDU_MASTERS).build();

        KuduSink<Row> sink = new KuduSink<>(
                writerConfig,
                KuduTableInfo.forTable("AlreadyExistingTable"),
                new RowOperationMapper(
                        new String[]{"idhash"},
                        AbstractSingleOperationMapper.KuduOperation.UPSERT)
        );




        DataStream<JsonRoot> lines = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source");

        DataStream<Row> row = lines.map(new MapFunction<JsonRoot, Row>() {
            @Override
            public Row map(JsonRoot e) throws Exception {
                Row kuduRow = new Row(2);
                kuduRow.setField(0, e.idhash());
                return kuduRow;
            }});

        row.addSink(sink);

        env.execute("Read from Kafka");

    }
}
