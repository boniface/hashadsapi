package domain.people

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/10.
 */
case class User(email: String,
                screenName: String,
                firstname: Option[String],
                lastname: Option[String],
                password: Option[String],
                status: String)

object User {
  implicit val userFmt = Json.format[User]
}