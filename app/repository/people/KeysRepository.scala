package repository.people


import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.people.Keys

import scala.concurrent.Future


sealed class KeysRepository extends CassandraTable[KeysRepository, Keys] {

  object id extends StringColumn(this) with PartitionKey[String]

  object value extends StringColumn(this)

  override def fromRow(row: Row): Keys = {
    Keys(
      id(row),
      value(row)
    )
  }
}

object KeysRepository extends KeysRepository with RootConnector {
  override lazy val tableName = "keys"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(key: Keys): Future[ResultSet] = {
    insert
      .value(_.id, key.id)
      .value(_.value, key.value)
      .future()
  }
  def getKeyById(id: String): Future[Option[Keys]] = {
    select.where(_.id eqs id).one()
  }
  def getAllkeys: Future[Seq[Keys]] = {
    select.fetchEnumerator() run Iteratee.collect()

  }

  def deleteKey(id:String) : Future[ResultSet]={
    delete.where(_.id eqs id).future()
  }
}