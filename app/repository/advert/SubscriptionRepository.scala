package repository.advert

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.column.PrimitiveColumn
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.location.LocationType
import repository.locations.LocationTypeRepository
import repository.locations.LocationTypeRepository._

/**
 * Created by hashcode on 2015/09/12.
 */
class SubscriptionRepository extends CassandraTable[LocationTypeRepository, LocationType] {
  object id extends StringColumn(this) with PartitionKey[String]

  object name extends StringColumn(this)

  object code extends StringColumn(this)

  override def fromRow(row: Row): LocationType = {
    LocationType(
      id(row),name(row),code(row)
    )
  }
}

object LocationTypeRepository extends LocationTypeRepository with RootConnector{
  override lazy val tableName = "ltypes"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(ltype:LocationType) ={
    insert
      .value(_.id,ltype.id)
      .value(_.code,ltype.code)
      .value(_.name,ltype.name)
      .future()
  }

  def findById(id:String)={
    select.where(_.id eqs id).one()

  }

  def findAll()={
    select.fetchEnumerator() run Iteratee.collect()
  }

}
