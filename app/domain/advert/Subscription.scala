package domain.advert

import java.util.Date

/**
 * Created by hashcode on 2015/09/11.
 */
case class Subscription(advertId:String,
                         subId:String,
                         subTypeid:String,
                         startDate:Date,
                         endDAte:String) {

}
