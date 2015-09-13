package repository.advert

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.advert.SubscriptionTypes

/**
 * Created by hashcode on 2015/09/12.
 */


class SubscriptionTypesRepository extends CassandraTable[SubscriptionTypesRepository, SubscriptionTypes] {
  object zone extends StringColumn(this) with PartitionKey[String]
  object id extends StringColumn(this) with PrimaryKey[String]

  object name extends StringColumn(this)

  object currency extends StringColumn(this)

  object value extends BigDecimalColumn(this)

  override def fromRow(row: Row): SubscriptionTypes = {
    SubscriptionTypes(
      zone(row),id(row),name(row),currency(row),value(row)
    )
  }
}

object SubscriptionTypesRepository extends SubscriptionTypesRepository with RootConnector{
  override lazy val tableName = "subtypes"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(subs:SubscriptionTypes) ={
    insert
      .value(_.zone,subs.zone)
      .value(_.id,subs.id)
      .value(_.name,subs.name)
      .value(_.currency,subs.currency)
      .value(_.value,subs.value)
      .future()
  }

  def findById(zone:String,id:String)={
    select.where(_.zone eqs zone).and(_.id eqs id).one()

  }

  def findAll(zone:String)={
    select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.collect()
  }

}
