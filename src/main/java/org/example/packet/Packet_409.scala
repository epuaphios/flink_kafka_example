package org.example.packet

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.{JsonInclude, JsonProperty}


@JsonInclude(JsonInclude.Include.NON_NULL)
  case class RADYOLOJILOINC(@JsonProperty("@version")`@version`: String,@JsonProperty("@codeSystemGuid") `@codeSystemGuid`: String,@JsonProperty("@code") `@code`: String,@JsonProperty("@value") `@value`: String)
@JsonInclude(JsonInclude.Include.NON_NULL)
  case class ISLEMREFERANSNUMARASI(@JsonProperty("@value")`@value`: String)
@JsonInclude(JsonInclude.Include.NON_NULL)
  case class RAPORONAYLANMAZAMANI(@JsonProperty("@value")`@value`: String)
@JsonInclude(JsonInclude.Include.NON_NULL)
  case class SONUCBASLIK(@JsonProperty("@value")`@value`: String)
@JsonInclude(JsonInclude.Include.NON_NULL)
  case class SONUCACIKLAMA(@JsonProperty("@value")`@value`: String)

@JsonInclude(JsonInclude.Include.NON_NULL)
case class RAPORSONUCBILGISI(@JsonProperty("SONUC_BASLIK") SONUC_BASLIK: SONUCBASLIK, @JsonProperty("SONUC_ACIKLAMA") SONUC_ACIKLAMA: SONUCACIKLAMA)

@JsonInclude(JsonInclude.Include.NON_NULL)
 case class RADYOLOJIBILGISI(@JsonProperty("RADYOLOJI_LOINC") RADYOLOJI_LOINC: RADYOLOJILOINC, @JsonProperty("ISLEM_REFERANS_NUMARASI") ISLEM_REFERANS_NUMARASI: ISLEMREFERANSNUMARASI, @JsonProperty("RAPOR_ONAYLANMA_ZAMANI") RAPOR_ONAYLANMA_ZAMANI: RAPORONAYLANMAZAMANI, @JsonProperty("RAPOR_SONUC_BILGISI") RAPOR_SONUC_BILGISI: java.util.List[RAPORSONUCBILGISI])

@JsonInclude(JsonInclude.Include.NON_NULL)
  case class RADYOLOJISONUCKAYIT(@JsonProperty("RADYOLOJI_BILGISI") RADYOLOJI_BILGISI: java.util.List[RADYOLOJIBILGISI])



