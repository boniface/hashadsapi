package domain.people

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/10.
 */
case class ContactType(id:String,name:String)

object ContactType{
  implicit val contactFmt = Json.format[ContactType]
}
