package org.example.packet

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty

import java.util

case class TETKIKSONUCU(@JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class ISLEMREFERANSNUMARASI(@JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class ISTEMZAMANI(@JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class ISTEMYAPILANKLINIK(@JsonProperty("@version")  `@version`: String, @JsonProperty("@codeSystemGuid")`@codeSystemGuid`: String, @JsonProperty("@code") `@code`: String, @JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class ISTEMYAPANHEKIMKIMLIKNUMARASI(@JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class RAPORLAMAZAMANI(@JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class RAPORLAYANHEKIMKIMLIKNUMARASI(@JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class NUMUNENINALINDIGIDOKUNUNTEMELOZELLIGI(@JsonProperty("@version") `@version`: String, @JsonProperty("@codeSystemGuid") `@codeSystemGuid`: String, @JsonProperty("@code") `@code`: String, @JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class NUMUNEALINMASEKLI(@JsonProperty("@version") `@version`: String,@JsonProperty("@codeSystemGuid") `@codeSystemGuid`: String, @JsonProperty("@code") `@code`: String, @JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class PREPARATDURUMU(@JsonProperty("@version") `@version`: String, @JsonProperty("@codeSystemGuid") `@codeSystemGuid`: String, @JsonProperty("@code") `@code`: String, @JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class YERLESIMYERI(@JsonProperty("@version") `@version`: String,@JsonProperty("@codeSystemGuid")  `@codeSystemGuid`: String, @JsonProperty("@code") `@code`: String, @JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class MORFOLOJIKODU(@JsonProperty("@version") `@version`: String,@JsonProperty("@codeSystemGuid") `@codeSystemGuid`: String, @JsonProperty("@code") `@code`: String, @JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class PATOLOJIBASE64PDFBILGISI(@JsonProperty("@value") `@value`: String, @JsonProperty("@self-closing") `@self-closing`: String)
case class PATOLOJIBILGISI(@JsonProperty("TETKIK_SONUCU") TETKIK_SONUCU: TETKIKSONUCU, @JsonProperty("ISLEM_REFERANS_NUMARASI") ISLEM_REFERANS_NUMARASI: ISLEMREFERANSNUMARASI, @JsonProperty("ISTEM_ZAMANI") ISTEM_ZAMANI: ISTEMZAMANI,@JsonProperty("ISTEM_YAPILAN_KLINIK") ISTEM_YAPILAN_KLINIK: ISTEMYAPILANKLINIK, @JsonProperty("ISTEM_YAPAN_HEKIM_KIMLIK_NUMARASI") ISTEM_YAPAN_HEKIM_KIMLIK_NUMARASI: ISTEMYAPANHEKIMKIMLIKNUMARASI, @JsonProperty("RAPORLAMA_ZAMANI") RAPORLAMA_ZAMANI: RAPORLAMAZAMANI,@JsonProperty("RAPORLAYAN_HEKIM_KIMLIK_NUMARASI") RAPORLAYAN_HEKIM_KIMLIK_NUMARASI: RAPORLAYANHEKIMKIMLIKNUMARASI, @JsonProperty("NUMUNENIN_ALINDIGI_DOKUNUN_TEMEL_OZELLIGI") NUMUNENIN_ALINDIGI_DOKUNUN_TEMEL_OZELLIGI: NUMUNENINALINDIGIDOKUNUNTEMELOZELLIGI, @JsonProperty("NUMUNE_ALINMA_SEKLI") NUMUNE_ALINMA_SEKLI: NUMUNEALINMASEKLI, @JsonProperty("PREPARAT_DURUMU") PREPARAT_DURUMU: PREPARATDURUMU,@JsonProperty("YERLESIM_YERI")  YERLESIM_YERI: YERLESIMYERI, @JsonProperty("MORFOLOJI_KODU") MORFOLOJI_KODU: MORFOLOJIKODU,@JsonProperty("PATOLOJI_BASE_64_PDF_BILGISI") PATOLOJI_BASE_64_PDF_BILGISI: PATOLOJIBASE64PDFBILGISI)
case class HASTAPATOLOJIBILGILERI(@JsonProperty("PATOLOJI_BILGISI") PATOLOJI_BILGISI: util.Vector[PATOLOJIBILGISI])