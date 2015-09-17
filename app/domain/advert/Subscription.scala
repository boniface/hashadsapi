package domain.advert

import java.util.Date

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/11.
 */
case class Subscription(advertId: String,
                        subId: String,
                        subTypeId: String,
                        startDate: Date,
                        endDate: Date)

object Subscription {
  implicit val subsFmt = Json.format[Subscription]

}
