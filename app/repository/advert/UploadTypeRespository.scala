package repository.advert

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.column.PrimitiveColumn
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.advert.UploadType
import domain.location.LocationType
import repository.locations.LocationTypeRepository
import repository.locations.LocationTypeRepository._

/**
 * Created by hashcode on 2015/09/12.
 */
class UploadTypeRespository extends CassandraTable[UploadTypeRespository, UploadType] {

  object id extends StringColumn(this) with PartitionKey[String]

  object name extends StringColumn(this)

  override def fromRow(row: Row): UploadType = {
    UploadType(
      id(row), name(row)
    )
  }
}

object UploadTypeRespository extends UploadTypeRespository with RootConnector {
  override lazy val tableName = "uptypes"

  override implicit def space: KeySpace = DataConnection.keySpace

  override implicit def session: Session = DataConnection.session

  def save(uptypes: UploadType) = {
    insert
      .value(_.id, uptypes.id)
      .value(_.name, uptypes.name)
      .future()
  }

  def findById(id: String) = {
    select.where(_.id eqs id).one()

  }

  def findAll() = {
    select.fetchEnumerator() run Iteratee.collect()
  }

}
