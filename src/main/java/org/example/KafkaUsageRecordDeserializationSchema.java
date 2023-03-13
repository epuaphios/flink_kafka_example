package org.example;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
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
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> consumerRecord, Collector<KafkaClass> collector) {
        JsonRoot jsonRoot;
        try {
            jsonRoot = objectMapper.readValue(consumerRecord.value(), JsonRoot.class);
            KafkaClass kafkaClass = new KafkaClass(consumerRecord.offset(),consumerRecord.partition(),consumerRecord.topic(),jsonRoot);
            collector.collect(kafkaClass);
           } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

           }


    }

}
