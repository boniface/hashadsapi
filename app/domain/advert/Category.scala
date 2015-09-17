package domain.advert

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/11.
 */
case class Category(id: String,
                    name: String,
                    parentId: String)

object Category {
  implicit val catFmt = Json.format[Category]

}
