package org.example;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.packet.JsonRoot;

import java.io.IOException;

public class EventDeserializationSchema implements KafkaDeserializationSchema<JsonRoot> {

    private static final long serialVersionUID = 1L;

    private transient ObjectMapper objectMapper;

    /**
     * For performance reasons it's better to create on ObjectMapper in this open method rather than
     * creating a new ObjectMapper for every record.
     */

    @Override
    public boolean isEndOfStream(JsonRoot jsonRoot) {
        return false;
    }

    /**
     * If our deserialize method needed access to the information in the Kafka headers of a
     * KafkaConsumerRecord, we would have implemented a KafkaRecordDeserializationSchema instead of
     * extending AbstractDeserializationSchema.
     */
    @Override
    public JsonRoot deserialize(ConsumerRecord<byte[], byte[]> record) throws IOException {


        savedOffset(AppParameters.APP_NAME, record.topic, record.partition, record.offset, sessionSylla)

        JsonRoot jsonRoot = deserializeValue(record.value());

        return jsonRoot;

    }


    private JsonRoot deserializeValue(byte[] value) {

        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        try {
            return objectMapper.readValue(value, JsonRoot.class);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public TypeInformation<JsonRoot> getProducedType() {
        return TypeInformation.of(JsonRoot.class);
    }
}