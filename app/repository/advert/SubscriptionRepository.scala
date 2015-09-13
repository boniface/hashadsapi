package repository.advert

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.column.PrimitiveColumn
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.advert.Subscription
import domain.location.LocationType
import repository.locations.LocationTypeRepository
import repository.locations.LocationTypeRepository._

/**
 * Created by hashcode on 2015/09/12.
 */

class SubscriptionRepository extends CassandraTable[SubscriptionRepository, Subscription] {
  object advertId extends StringColumn(this) with PartitionKey[String]

  object subId extends StringColumn(this)

  object subTypeId extends StringColumn(this)

  object startDate extends DateColumn(this)

  object endDate extends DateColumn(this)

  override def fromRow(row: Row): Subscription = {
    Subscription(
      advertId(row),subId(row),subTypeId(row),startDate(row),endDate(row)
    )
  }
}

object SubscriptionRepository extends SubscriptionRepository with RootConnector{
  override lazy val tableName = "subs"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(subs:Subscription) ={
    insert
      .value(_.advertId,subs.advertId)
      .value(_.subId,subs.subId)
      .value(_.subTypeId,subs.subTypeId)
      .value(_.startDate,subs.startDate)
      .value(_.endDate,subs.endDate)
      .future()
  }

  def findById(id:String)={
    select.where(_.advertId eqs id).one()

  }

  def findAll()={
    select.fetchEnumerator() run Iteratee.collect()
  }

}
