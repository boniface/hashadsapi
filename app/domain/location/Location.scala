package domain.location

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/09/09.
 */
case class Location( zone:String,
                     locationTypeId:String,
                     id:String,
                     parentId:Option[String],
                     name:String,
                     latitude: Option[String],
                     longitude:Option[String]
                       )
object Location {
  implicit val locationFmt = Json.format[Location]
}

