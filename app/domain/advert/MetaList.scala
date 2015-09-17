package domain.advert

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/11.
 */
case class MetaList(id: String, name: String)

object MetaList {
  implicit val metalisFmt = Json.format[MetaList]

}
