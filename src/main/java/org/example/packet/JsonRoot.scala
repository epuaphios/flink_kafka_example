package org.example.packet

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
case class JsonRoot(
                          @JsonProperty("event") event: String
                        , @JsonProperty("key") key: String
                        , @JsonProperty("cas") cas: String
                        , @JsonProperty("IDHash") idhash: String
                        , @JsonProperty("KABUL_ZAMANI") kabulzamani: String
                        , @JsonProperty("content") content: RecordData
                      )
