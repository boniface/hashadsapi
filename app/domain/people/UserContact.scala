package domain.people

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/10.
 */
case class UserContact(
                      email:String,
                      id:String,
                      locationId:String,
                      contactTypeId:String,
                      address:Option[String],
                      postalCode:Option[String],
                      contacts:Map[String,String]
                        )

object UserContact {
  implicit val usercontctFmt = Json.format[UserContact]
}
