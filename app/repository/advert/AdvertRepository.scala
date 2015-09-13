package repository.advert

import java.util.Date

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.column.PrimitiveColumn
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.advert.Advert
import domain.location.LocationType
import repository.locations.LocationTypeRepository
import repository.locations.LocationTypeRepository._

/**
 * Created by hashcode on 2015/09/12.
 */


class AdvertRepository extends CassandraTable[AdvertRepository, Advert] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object categoryId extends StringColumn(this) with PrimaryKey[String]

  object id extends StringColumn(this) with PrimaryKey[String]

  object datePosted extends DateColumn(this) with PrimaryKey[Date] with ClusteringOrder[Date] with Descending

  object userId extends StringColumn(this)

  object description extends StringColumn(this)

  override def fromRow(row: Row): Advert = {
    Advert(
      zone(row), categoryId(row), id(row), datePosted(row), userId(row), description(row)
    )
  }
}

object AdvertRepository extends AdvertRepository with RootConnector {
  override lazy val tableName = "adverts"

  override implicit def space: KeySpace = DataConnection.keySpace

  override implicit def session: Session = DataConnection.session

  def save(advert: Advert) = {
    insert
      .value(_.zone, advert.zone)
      .value(_.categoryId, advert.categoryId)
      .value(_.id, advert.id)
      .value(_.datePosted, advert.datePosted)
      .value(_.userId, advert.userId)
      .value(_.description, advert.description)
      .future() flatMap {
      _ => {
        SingleAdvertRepository.insert
          .value(_.zone, advert.zone)
          .value(_.categoryId, advert.categoryId)
          .value(_.id, advert.id)
          .value(_.datePosted, advert.datePosted)
          .value(_.userId, advert.userId)
          .value(_.description, advert.description)
          .future()
      }
    }
  }

  def findByZones(zone: String) = {
    select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.collect()
  }

  def findCategoryByZone(zone: String, catId: String) = {
    select.where(_.zone eqs zone).and(_.categoryId eqs catId).fetchEnumerator() run Iteratee.collect()
  }
}

class SingleAdvertRepository extends CassandraTable[SingleAdvertRepository, Advert] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object categoryId extends StringColumn(this) with PrimaryKey[String]

  object id extends StringColumn(this) with PrimaryKey[String]

  object datePosted extends DateColumn(this) with PrimaryKey[Date] with ClusteringOrder[Date] with Descending

  object userId extends StringColumn(this)

  object description extends StringColumn(this)

  override def fromRow(row: Row): Advert = {
    Advert(
      zone(row), categoryId(row), id(row), datePosted(row), userId(row), description(row)
    )
  }
}

object SingleAdvertRepository extends SingleAdvertRepository with RootConnector {
  override lazy val tableName = "sadverts"

  override implicit def space: KeySpace = DataConnection.keySpace

  override implicit def session: Session = DataConnection.session

  def findById(id: String) = {
    select.where(_.id eqs id).one()
  }

}
