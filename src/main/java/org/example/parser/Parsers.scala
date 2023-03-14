package org.example.parser
import org.apache.flink.types.Row
import org.apache.kudu.client.Upsert
import org.example.packet.{JsonRoot, RADYOLOJIBILGISI}
import org.example.parser.Helper.{getDatetimeValue, getKabulZamaniValue, getStringCode, getStringValue}

import java.time
import scala.Int.MaxValue
import scala.util.hashing.MurmurHash3

object Parsers extends Helper {

   def parse201(radyolojiBilgisi: RADYOLOJIBILGISI, row: Row, index: Int) = {
       val operation = new Upsert()
       val row = operation.getRow

          row.addInt(2, index)
          row.addString(4,null)
          row.addString(5,getStringValue(radyolojiBilgisi.RADYOLOJI_LOINC))
          row.addString(6,getStringCode(radyolojiBilgisi.RADYOLOJI_LOINC))
          row.addString(7,getStringValue(radyolojiBilgisi.ISLEM_REFERANS_NUMARASI))
          row.addLong(8,getDatetimeValue(radyolojiBilgisi.RAPOR_ONAYLANMA_ZAMANI))
          row.addLong(9,time.LocalDateTime.now().toEpochSecond(time.ZoneOffset.UTC))
          row
    }

  def parseCenter(jsonRoot: JsonRoot): Row = {
    val operation = new Upsert()
    val row = operation.getRow
    row.setField(0, jsonRoot.key)
    row.setField(1, getKabulZamaniValue(jsonRoot.kabulzamani))
    row.setField(3, murmurHash(jsonRoot.idhash))
    row
  }

  def murmurHash(idhash: String): String = {
    (MurmurHash3.stringHash(idhash) + MaxValue).toString
  }

}
