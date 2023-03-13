package org.example.packet

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty

  case class RADYOLOJILOINC(@JsonProperty("@version")`@version`: String,@JsonProperty("@codeSystemGuid") `@codeSystemGuid`: String,@JsonProperty("@code") `@code`: String,@JsonProperty("@value") `@value`: String)

  case class ISLEMREFERANSNUMARASI(@JsonProperty("@value")`@value`: String)

  case class RAPORONAYLANMAZAMANI(@JsonProperty("@value")`@value`: String)

  case class SONUCBASLIK(@JsonProperty("@value")`@value`: String)

  case class SONUCACIKLAMA(@JsonProperty("@value")`@value`: String)

  case class RAPORSONUCBILGISI(@JsonProperty("SONUC_BASLIK") SONUC_BASLIK: SONUCBASLIK, @JsonProperty("SONUC_ACIKLAMA") SONUC_ACIKLAMA: SONUCACIKLAMA)

  case class RADYOLOJIBILGISI(@JsonProperty("RADYOLOJI_LOINC") RADYOLOJI_LOINC: RADYOLOJILOINC, @JsonProperty("ISLEM_REFERANS_NUMARASI") ISLEM_REFERANS_NUMARASI: ISLEMREFERANSNUMARASI, @JsonProperty("RAPOR_ONAYLANMA_ZAMANI") RAPOR_ONAYLANMA_ZAMANI: RAPORONAYLANMAZAMANI, @JsonProperty("RAPOR_SONUC_BILGISI") RAPOR_SONUC_BILGISI: Vector[RAPORSONUCBILGISI])

  case class RADYOLOJISONUCKAYIT(@JsonProperty("RADYOLOJI_BILGISI") RADYOLOJI_BILGISI: Vector[RADYOLOJIBILGISI])



