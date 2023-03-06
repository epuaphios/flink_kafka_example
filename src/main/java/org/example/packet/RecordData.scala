package org.example.packet

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty


case class RecordData(@JsonProperty("HASTA_PATOLOJI_BILGILERI") HASTA_PATOLOJI_BILGILERI: HASTAPATOLOJIBILGILERI)
