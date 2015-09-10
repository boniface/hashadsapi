package repository.people

import com.websudos.phantom.CassandraTable
import com.websudos.phantom.connectors.RootConnector
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.people.ContactType

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/09/10.
 */
class ContactTypeRepository  extends CassandraTable[ContactTypeRepository, ContactType] {

  object id extends StringColumn(this) with PartitionKey[String]

  object name extends StringColumn(this)

  override def fromRow(row: Row): ContactType = {
    ContactType(id(row),
      name(row))
  }
}

object ContactTypeRepository extends ContactTypeRepository with  RootConnector {
  override lazy val tableName = "contypes"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(contactType: ContactType): Future[ResultSet] = {
    insert
      .value(_.id, contactType.id)
      .value(_.name, contactType.name)
      .future()
  }

  def findContactTypeById(id: String): Future[Option[ContactType]] = {
    select.where(_.id eqs id).one()
  }

  def findAll(): Future[Seq[ContactType]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }
}

