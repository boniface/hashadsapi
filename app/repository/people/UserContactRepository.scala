package repository.people

import com.websudos.phantom.CassandraTable
import com.websudos.phantom.connectors.RootConnector
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.people.UserContact

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/09/10.
 */
class UserContactRepository extends CassandraTable[UserContactRepository, UserContact] {

  object email extends StringColumn(this) with PartitionKey[String]

  object id extends StringColumn(this) with PrimaryKey[String]

  object locationId extends StringColumn(this)

  object contactTypeId extends StringColumn(this)

  object address extends OptionalStringColumn(this)

  object postalCode extends OptionalStringColumn(this)

  object contacts extends MapColumn[UserContactRepository, UserContact, String, String](this)


  override def fromRow(row: Row): UserContact = {
    UserContact(
      email(row),
      id(row),
      locationId(row),
      contactTypeId(row),
      address(row),
      postalCode(row),
      contacts(row)
    )
  }
}

object UserContactRepository extends UserContactRepository with RootConnector {
  override lazy val tableName = "contacts"

  override implicit def space: KeySpace = DataConnection.keySpace

  override implicit def session: Session = DataConnection.session

  def save(userContact: UserContact): Future[ResultSet] = {
    insert
      .value(_.id, userContact.id)
      .value(_.email, userContact.email)
      .value(_.locationId, userContact.locationId)
      .value(_.contactTypeId, userContact.contactTypeId)
      .value(_.address, userContact.address)
      .value(_.postalCode, userContact.postalCode)
      .value(_.contacts, userContact.contacts)
      .future()
  }

  def getContacts(email: String) = {
    select.where(_.email eqs email).fetchEnumerator() run Iteratee.collect()
  }

  def getContact(email: String, id:String): Future[Option[UserContact]] = {
    select.where(_.email eqs email).and(_.id eqs id).one()
  }

}

