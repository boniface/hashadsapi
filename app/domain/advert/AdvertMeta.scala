package domain.advert

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/11.
 */
case class AdvertMeta(advertId: String,
                      meta: Map[String, String])

object AdvertMeta {
  implicit val advertMetaFmt = Json.format[AdvertMeta]

}
