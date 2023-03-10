package org.example.parser
import org.apache.flink.types.Row
import org.example.packet.JsonRoot
import org.example.parser.Helper.{getDatetimeValue, getStringValue}

object Parsers extends Helper {

   def parse201(jsonRoot: JsonRoot) = {
      val row = new Row(3)
      row.setField(0, jsonRoot.key)
      jsonRoot.content.HASTA_PATOLOJI_BILGILERI.PATOLOJI_BILGISI.forEach(
        x => {
          row.setField(1,getDatetimeValue(x.ISTEM_ZAMANI))
          row.setField(2,getStringValue(x.TETKIK_SONUCU))
        }
      )
      row
    }

}
