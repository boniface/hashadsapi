package domain.people

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/10.
 */
case class Token(id: String, tokenValue: String)

object Token {
  implicit val tokenFmt = Json.format[Token]
}