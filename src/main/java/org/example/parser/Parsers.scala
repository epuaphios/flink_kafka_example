package org.example.parser
import org.apache.flink.types.Row
import org.example.packet.JsonRoot
object Parsers {

   def parse201(jsonRoot: JsonRoot) = {
      val row = new Row(3)
      row.setField(0, jsonRoot.key)
      jsonRoot.content.HASTA_PATOLOJI_BILGILERI.PATOLOJI_BILGISI.forEach(
        x => {
          if (x.ISTEM_ZAMANI == null) {
            row.setField(1, null)
          }
          else {
            row.setField(1, x.ISTEM_ZAMANI.`@value`)
          }
          if (x.TETKIK_SONUCU == null) {
            row.setField(2, null)
          }
          else {
            row.setField(2, x.TETKIK_SONUCU.`@value`)
          }
        }
      )
      row
    }

}
