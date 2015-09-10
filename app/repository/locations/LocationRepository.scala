package repository.locations

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.connectors.RootConnector
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.location.Location

/**
 * Created by hashcode on 2015/09/10.
 */
class LocationRepository extends CassandraTable[LocationRepository, Location] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object locationTypeId extends StringColumn(this) with PrimaryKey[String]

  object id extends StringColumn(this) with PrimaryKey[String]

  object parentId extends OptionalStringColumn(this)

  object name extends StringColumn(this)

  object latitude extends OptionalStringColumn(this)

  object longitude extends OptionalStringColumn(this)

  override def fromRow(r: Row): Location = {

    Location(zone(r),locationTypeId(r),id(r),parentId(r),name(r),latitude(r),longitude(r))
  }
}

object LocationRepository extends LocationRepository with RootConnector {
  override lazy val tableName = "locations"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(location:Location) = {
    insert
      .value(_.id,location.id)
      .value(_.zone,location.zone)
      .value(_.locationTypeId,location.locationTypeId)
      .value(_.name,location.name)
      .value(_.latitude,location.latitude)
      .value(_.longitude,location.longitude)
      .value(_.parentId,location.parentId)
    .future()

  }

  def findById(zone:String,locationTypeId:String,id:String) = {
    select
      .where(_.zone eqs zone)
      .and(_.locationTypeId eqs locationTypeId)
      .and(_.id eqs id).one()
  }

  def findbyZones(zone:String) ={
    select
      .where(_.zone eqs zone)
      .fetchEnumerator() run Iteratee.collect()
  }

  def findByLocationType(zone:String,locationTypeId:String) ={
    select
      .where(_.zone eqs zone)
      .and(_.locationTypeId eqs locationTypeId)
      .fetchEnumerator() run Iteratee.collect()
  }

}
