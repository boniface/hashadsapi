package domain.advert

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/11.
 */
case class UploadType(id: String,
                      name: String)

object UploadType {
  implicit val uploadTypeFmt = Json.format[UploadType]

}
