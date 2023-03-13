package org.example.parser
import org.apache.flink.types.Row
import org.example.packet.JsonRoot
import org.example.parser.Helper.{getDatetimeValue, getStringCode, getStringValue}

import scala.util.hashing.MurmurHash3

object Parsers extends Helper {

   def parse201(jsonRoot: JsonRoot) = {
      val row = new Row(13)
      row.setField(0, jsonRoot.key)
      row.setField(1,jsonRoot.kabulzamani)
      row.setField(5,MurmurHash3.stringHash(jsonRoot.idhash))

     jsonRoot.content.RADYOLOJI_SONUC_KAYIT.RADYOLOJI_BILGISI.foreach {
        case (value, index) =>
          row.setField(2,index)
          row.setField(6,null)
          row.setField(7,getStringValue(value.RADYOLOJI_LOINC))
          row.setField(8,getStringCode(value.RADYOLOJI_LOINC))
          row.setField(9,getStringValue(value.ISLEM_REFERANS_NUMARASI))
          row.setField(10,getDatetimeValue(value.RAPOR_ONAYLANMA_ZAMANI))
          row.setField(13,null)
          if (value.RAPOR_SONUC_BILGISI != null) {
            value.RAPOR_SONUC_BILGISI.zipWithIndex.foreach {
              case (value, index) =>
                row.setField(3,index)
                row.setField(11,getStringValue(value.SONUC_BASLIK))
                row.setField(12,getStringValue(value.SONUC_ACIKLAMA))
            }
          }

        }

      row
    }

}
