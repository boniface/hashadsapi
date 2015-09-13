package repository.advert

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.column.PrimitiveColumn
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.advert.Category
import domain.location.LocationType
import repository.locations.LocationTypeRepository
import repository.locations.LocationTypeRepository._

/**
 * Created by hashcode on 2015/09/12.
 */


class CategoryRepository extends CassandraTable[CategoryRepository, Category] {
  object id extends StringColumn(this) with PartitionKey[String]

  object name extends StringColumn(this)

  object parentId extends StringColumn(this)

  override def fromRow(row: Row): Category = {
    Category(
      id(row),name(row),code(row)
    )
  }
}

object CategoryRepository extends CategoryRepository with RootConnector{
  override lazy val tableName = "cats"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(category:Category) ={
    insert
      .value(_.id,category.id)
      .value(_.parentId,category.parentId)
      .value(_.name,category.name)
      .future()
  }

  def findById(id:String)={
    select.where(_.id eqs id).one()

  }

  def findAll()={
    select.fetchEnumerator() run Iteratee.collect()
  }

}
