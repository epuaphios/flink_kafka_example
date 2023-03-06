package org.example.packet

import java.util

case class TETKIKSONUCU(`@value`: String, `@self-closing`: String)
case class ISLEMREFERANSNUMARASI(`@value`: String, `@self-closing`: String)
case class ISTEMZAMANI(`@value`: String, `@self-closing`: String)
case class ISTEMYAPILANKLINIK(`@version`: String, `@codeSystemGuid`: String, `@code`: String, `@value`: String, `@self-closing`: String)
case class ISTEMYAPANHEKIMKIMLIKNUMARASI(`@value`: String, `@self-closing`: String)
case class RAPORLAMAZAMANI(`@value`: String, `@self-closing`: String)
case class RAPORLAYANHEKIMKIMLIKNUMARASI(`@value`: String, `@self-closing`: String)
case class NUMUNENINALINDIGIDOKUNUNTEMELOZELLIGI(`@version`: String, `@codeSystemGuid`: String, `@code`: String, `@value`: String, `@self-closing`: String)
case class NUMUNEALINMASEKLI(`@version`: String, `@codeSystemGuid`: String, `@code`: String, `@value`: String, `@self-closing`: String)
case class PREPARATDURUMU(`@version`: String, `@codeSystemGuid`: String, `@code`: String, `@value`: String, `@self-closing`: String)
case class YERLESIMYERI(`@version`: String, `@codeSystemGuid`: String, `@code`: String, `@value`: String, `@self-closing`: String)
case class MORFOLOJIKODU(`@version`: String, `@codeSystemGuid`: String, `@code`: String, `@value`: String, `@self-closing`: String)
case class PATOLOJIBASE64PDFBILGISI(`@value`: String, `@self-closing`: String)
case class PATOLOJIBILGISI(TETKIK_SONUCU: TETKIKSONUCU, ISLEM_REFERANS_NUMARASI: ISLEMREFERANSNUMARASI, ISTEM_ZAMANI: ISTEMZAMANI, ISTEM_YAPILAN_KLINIK: ISTEMYAPILANKLINIK, ISTEM_YAPAN_HEKIM_KIMLIK_NUMARASI: ISTEMYAPANHEKIMKIMLIKNUMARASI, RAPORLAMA_ZAMANI: RAPORLAMAZAMANI, RAPORLAYAN_HEKIM_KIMLIK_NUMARASI: RAPORLAYANHEKIMKIMLIKNUMARASI, NUMUNENIN_ALINDIGI_DOKUNUN_TEMEL_OZELLIGI: NUMUNENINALINDIGIDOKUNUNTEMELOZELLIGI, NUMUNE_ALINMA_SEKLI: NUMUNEALINMASEKLI, PREPARAT_DURUMU: PREPARATDURUMU, YERLESIM_YERI: YERLESIMYERI, MORFOLOJI_KODU: MORFOLOJIKODU, PATOLOJI_BASE_64_PDF_BILGISI: PATOLOJIBASE64PDFBILGISI)
case class HASTAPATOLOJIBILGILERI(PATOLOJI_BILGISI: util.Vector[PATOLOJIBILGISI])