package domain.location

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/09.
 */
case class LocationType (id:String,name:String,code:String)

object LocationType {
  implicit val locationTypeFmt = Json.format[LocationType]
}
