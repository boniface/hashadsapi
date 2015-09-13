package repository.advert

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.column.PrimitiveColumn
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.advert.AdvertUploads
import domain.location.LocationType
import repository.locations.LocationTypeRepository
import repository.locations.LocationTypeRepository._

/**
 * Created by hashcode on 2015/09/12.
 */


sealed class AdvertUploadsRepository extends CassandraTable[AdvertUploadsRepository, AdvertUploads] {
  object advertId extends StringColumn(this) with PartitionKey[String]

  object uploadId extends StringColumn(this) with PrimaryKey[String]

  object uploadTypeId extends StringColumn(this)

  object url extends StringColumn(this)

  override def fromRow(row: Row): AdvertUploads = {
    AdvertUploads(
      advertId(row),
      uploadId(row),
      uploadTypeId(row),
      url(row)
    )
  }
}

object AdvertUploadsRepository extends AdvertUploadsRepository with RootConnector{
  override lazy val tableName = "auploads"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(uploads:AdvertUploads) ={
    insert
      .value(_.advertId,uploads.advertId)
      .value(_.uploadId,uploads.uploadId)
      .value(_.uploadTypeId,uploads.uploadTypeId)
      .value(_.url,uploads.url)
      .future()
  }

  def findById(advertId:String, uploadId:String)={
    select.where(_.advertId eqs advertId).and(_.uploadId eqs uploadId).one()

  }

  def findAllUploads(advertId:String)={
    select.where(_.advertId eqs advertId).fetchEnumerator() run Iteratee.collect()
  }

}
