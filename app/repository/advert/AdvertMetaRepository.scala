package repository.advert

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.dsl._
import conf.connection.DataConnection
import domain.advert.AdvertMeta

/**
 * Created by hashcode on 2015/09/12.
 */

sealed class AdvertMetaRepository extends CassandraTable[AdvertMetaRepository, AdvertMeta] {
  object advertId extends StringColumn(this) with PartitionKey[String]
  object meta extends MapColumn[AdvertMetaRepository, AdvertMeta, String, String](this)
  override def fromRow(row: Row): AdvertMeta = {
    AdvertMeta(
      advertId(row),meta(row)
    )
  }
}

object AdvertMetaRepository extends AdvertMetaRepository with RootConnector{
  override lazy val tableName = "ameta"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(meta:AdvertMeta) ={
    insert
      .value(_.advertId,meta.advertId)
      .value(_.meta,meta.meta)
      .future()
  }

  def findById(id:String)={
    select.where(_.advertId eqs id).one()

  }

}
