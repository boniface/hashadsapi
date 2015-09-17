package domain.advert

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/11.
 */
case class SubscriptionTypes(zone: String,
                             id: String,
                             name: String,
                             currency: String,
                             value: BigDecimal)

object SubscriptionTypes {
  implicit val subtypeFmt = Json.format[SubscriptionTypes]

}
