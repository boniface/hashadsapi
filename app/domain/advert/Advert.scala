package domain.advert

import java.util.Date

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/11.
 */
case class Advert(zone: String,
                  categoryId: String,
                  id: String,
                  datePosted: Date,
                  userId: String,
                  description: String)

object Advert {
  implicit val advertFmt = Json.format[Advert]

}
