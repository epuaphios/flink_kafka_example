package org.example.parser
import org.apache.flink.types.Row
import org.example.packet.{JsonRoot, RADYOLOJIBILGISI}
import org.example.parser.Helper.{getDatetimeValue, getKabulZamaniValue, getStringCode, getStringValue}

import java.time
import scala.Int.MaxValue
import scala.util.hashing.MurmurHash3

object Parsers extends Helper {

   def parse201(radyolojiBilgisi: RADYOLOJIBILGISI, row: Row, index: Int) = {
          row.setField(2, index)
          row.setField(4,null)
          row.setField(5,getStringValue(radyolojiBilgisi.RADYOLOJI_LOINC))
          row.setField(6,getStringCode(radyolojiBilgisi.RADYOLOJI_LOINC))
          row.setField(7,getStringValue(radyolojiBilgisi.ISLEM_REFERANS_NUMARASI))
          row.setField(8,getDatetimeValue(radyolojiBilgisi.RAPOR_ONAYLANMA_ZAMANI))
          row.setField(9,time.LocalDateTime.now())
          row
    }

  def parseCenter(jsonRoot: JsonRoot): Row = {
    val row = new Row(10)
    row.setField(0, jsonRoot.key)
    row.setField(1, getKabulZamaniValue(jsonRoot.kabulzamani))
    row.setField(3, murmurHash(jsonRoot.idhash))
    row
  }

  def murmurHash(idhash: String): String = {
    (MurmurHash3.stringHash(idhash) + MaxValue).toString
  }

}
