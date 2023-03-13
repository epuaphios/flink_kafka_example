package org.example.packet

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty


case class RecordData(
                       @JsonProperty("RADYOLOJI_SONUC_KAYIT") RADYOLOJI_SONUC_KAYIT: RADYOLOJISONUCKAYIT
                     )
