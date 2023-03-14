package org.example.packet

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
case class RecordData(
                       @JsonProperty("RADYOLOJI_SONUC_KAYIT") RADYOLOJI_SONUC_KAYIT: RADYOLOJISONUCKAYIT
                     )
