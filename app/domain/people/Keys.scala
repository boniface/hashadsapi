package domain.people

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/10.
 */
case class Keys (id:String,value:String)

object Keys{
  implicit val keysFmt = Json.format[Keys]
}