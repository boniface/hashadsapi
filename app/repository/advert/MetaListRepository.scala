package repository.advert

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.column.PrimitiveColumn
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.advert.MetaList
import domain.location.LocationType
import repository.locations.LocationTypeRepository
import repository.locations.LocationTypeRepository._

/**
 * Created by hashcode on 2015/09/12.
 */
class MetaListRepository extends CassandraTable[MetaListRepository, MetaList] {
  object id extends StringColumn(this) with PartitionKey[String]

  object name extends StringColumn(this)

  override def fromRow(row: Row): MetaList = {
    MetaList(
      id(row),name(row)
    )
  }
}

object MetaListRepository extends MetaListRepository with RootConnector{
  override lazy val tableName = "metalist"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(mlist:MetaList) ={
    insert
      .value(_.id,mlist.id)
      .value(_.name,mlist.name)
      .future()
  }

  def findById(id:String)={
    select.where(_.id eqs id).one()

  }

  def findAll()={
    select.fetchEnumerator() run Iteratee.collect()
  }

}
