package domain.people

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/10.
 */
case class UserRole(roleId:String, email:String)

object UserRole {
  implicit val userroleFmt = Json.format[UserRole]
}
