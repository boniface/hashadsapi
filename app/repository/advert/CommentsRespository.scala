package repository.advert

import com.datastax.driver.core.Row
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.column.PrimitiveColumn
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.advert.Comments
import domain.location.LocationType
import repository.locations.LocationTypeRepository
import repository.locations.LocationTypeRepository._

/**
 * Created by hashcode on 2015/09/12.
 */

class CommentsRespository extends CassandraTable[CommentsRespository, Comments] {
  object advertId extends StringColumn(this) with PartitionKey[String]

  object commentId extends StringColumn(this) with PrimaryKey[String]

  object comment extends StringColumn(this)

  override def fromRow(row: Row): Comments = {
    Comments(
      advertId(row),commentId(row),comment(row)
    )
  }
}

object CommentsRespository extends CommentsRespository with RootConnector{
  override lazy val tableName = "comments"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(comment:Comments) ={
    insert
      .value(_.advertId,comment.advertId)
      .value(_.commentId,comment.commentId)
      .value(_.comment,comment.comment)
      .future()
  }

  def findById(advertId:String,commentId:String)={
    select.where(_.advertId eqs advertId).and(_.commentId eqs commentId).one()

  }

  def findAll(advertId:String)={
    select.where(_.advertId eqs advertId).fetchEnumerator() run Iteratee.collect()
  }

}
