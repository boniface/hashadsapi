package domain.advert

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/11.
 */
case class AdvertUploads(advertId: String,
                         uploadId: String,
                         uploadTypeId: String,
                         url: String)

object AdvertUploads {
  implicit val advertupFmt = Json.format[AdvertUploads]
}
