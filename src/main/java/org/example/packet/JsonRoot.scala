package org.example.packet

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty

case class JsonRoot(
                        @JsonProperty("event") event: String
                        , @JsonProperty("key") key: String
                        , @JsonProperty("cas") cas: String
                        , @JsonProperty("IDHash") idhash: String
                        , @JsonProperty("KABUL_ZAMANI") kabulzamani: String
                        , @JsonProperty("content") content: RecordData
                      )
