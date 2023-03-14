package org.example.parser
import org.apache.flink.types.Row
import org.example.packet.JsonRoot
import org.example.parser.Helper.{getDatetimeValue, getKabulZamaniValue, getStringCode, getStringValue}

import java.time
import scala.Int.MaxValue
import scala.collection.JavaConverters._
import scala.util.hashing.MurmurHash3

object Parsers extends Helper {

   def parse201(jsonRoot: JsonRoot) = {
     val rows  = new java.util.ArrayList[Row]()

       jsonRoot.content.RADYOLOJI_SONUC_KAYIT.RADYOLOJI_BILGISI.zipWithIndex.foreach { case (radyolojiBilgisi, index) =>
         val row = new Row(11)
         row.setField(0, jsonRoot.key)
         row.setField(1, getKabulZamaniValue(jsonRoot.kabulzamani))
         row.setField(2, index)
         row.setField(3, murmurHash(jsonRoot.idhash))
         row.setField(4, null)
         row.setField(5, getStringValue(radyolojiBilgisi.RADYOLOJI_LOINC))
         row.setField(6, getStringCode(radyolojiBilgisi.RADYOLOJI_LOINC))
         row.setField(7, getStringValue(radyolojiBilgisi.ISLEM_REFERANS_NUMARASI))
         row.setField(8, getDatetimeValue(radyolojiBilgisi.RAPOR_ONAYLANMA_ZAMANI))
         row.setField(9, time.LocalDateTime.now().toEpochSecond(time.ZoneOffset.UTC))
         rows.add(row)
       }
     rows
   }

  def murmurHash(idhash: String): String = {
    (MurmurHash3.stringHash(idhash) + MaxValue).toString
  }

}
