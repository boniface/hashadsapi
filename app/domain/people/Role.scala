package domain.people

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/10.
 */
case class Role(id:String,name:String,description:String)

object Role {
  implicit val roleFmt = Json.format[Role]
}