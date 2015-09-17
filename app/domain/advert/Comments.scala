package domain.advert

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/11.
 */
case class Comments(advertId: String,
                    commentId: String,
                    comment: String)

object Comments {
  implicit val commentFmt = Json.format[Comments]

}
