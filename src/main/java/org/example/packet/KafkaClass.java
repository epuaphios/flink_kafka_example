package org.example.packet;

import java.io.Serializable;

public class KafkaClass implements Serializable {

    Long offset;
    Integer partition;
    String topic;
    JsonRoot value;



    public KafkaClass(Long offset, Integer partition, String topic, JsonRoot value) {
        this.offset = offset;
        this.partition = partition;
        this.topic = topic;
        this.value = value;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public JsonRoot getValue() {
        return value;
    }

    public void setValue(JsonRoot value) {
        this.value = value;
    }


}
