package org.example.packet

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
  case class RADYOLOJILOINC(@JsonProperty("@version")`@version`: String,@JsonProperty("@codeSystemGuid") `@codeSystemGuid`: String,@JsonProperty("@code") `@code`: String,@JsonProperty("@value") `@value`: String)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
  case class ISLEMREFERANSNUMARASI(@JsonProperty("@value")`@value`: String)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
  case class RAPORONAYLANMAZAMANI(@JsonProperty("@value")`@value`: String)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
  case class SONUCBASLIK(@JsonProperty("@value")`@value`: String)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
  case class SONUCACIKLAMA(@JsonProperty("@value")`@value`: String)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
case class RAPORSONUCBILGISI(@JsonProperty("SONUC_BASLIK") SONUC_BASLIK: SONUCBASLIK, @JsonProperty("SONUC_ACIKLAMA") SONUC_ACIKLAMA: SONUCACIKLAMA)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
 case class RADYOLOJIBILGISI(@JsonProperty("RADYOLOJI_LOINC") RADYOLOJI_LOINC: RADYOLOJILOINC, @JsonProperty("ISLEM_REFERANS_NUMARASI") ISLEM_REFERANS_NUMARASI: ISLEMREFERANSNUMARASI, @JsonProperty("RAPOR_ONAYLANMA_ZAMANI") RAPOR_ONAYLANMA_ZAMANI: RAPORONAYLANMAZAMANI, @JsonProperty("RAPOR_SONUC_BILGISI") RAPOR_SONUC_BILGISI: Seq[RAPORSONUCBILGISI])

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
  case class RADYOLOJISONUCKAYIT(@JsonProperty("RADYOLOJI_BILGISI") RADYOLOJI_BILGISI: Seq[RADYOLOJIBILGISI])



