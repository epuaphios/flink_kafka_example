package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;

import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.packet.JsonRoot;
import org.example.packet.KafkaClass;


public class KafkaUsageRecordDeserializationSchema implements KafkaRecordDeserializationSchema<KafkaClass> {

    private static final long serialVersionUID = 1L;
    private transient ObjectMapper objectMapper;

    @Override
    public TypeInformation<KafkaClass> getProducedType() {
        return TypeInformation.of(KafkaClass.class);
    }

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        KafkaRecordDeserializationSchema.super.open(context);
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> consumerRecord, Collector<KafkaClass> collector) {
        try {
            KafkaClass kafkaClass = new KafkaClass(consumerRecord.offset(),consumerRecord.partition(),consumerRecord.topic(),consumerRecord.value());
            collector.collect(kafkaClass);
           } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
           }
    }

}
