package repository.people

import com.websudos.phantom.dsl._
import conf.connection.DataConnection
import domain.people.User

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/09/10.
 */
class UserRepository extends CassandraTable[UserRepository, User] {


  object email extends StringColumn(this) with PartitionKey[String]

  object screenName extends StringColumn(this)

  object firstname extends OptionalStringColumn(this)

  object lastname extends OptionalStringColumn(this)

  object password extends OptionalStringColumn(this)

  object status extends StringColumn(this)


  override def fromRow(row: Row): User = {
    User(
      email(row),
      screenName(row),
      firstname(row),
      lastname(row),
      password(row),
      status(row))
  }
}

object UserRepository extends UserRepository with  RootConnector {
  override lazy val tableName = "users"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(user: User): Future[ResultSet] = {
    insert
      .value(_.email, user.email)
      .value(_.firstname, user.firstname)
      .value(_.password, user.password)
      .value(_.screenName, user.screenName)
      .value(_.lastname, user.lastname)
      .value(_.status, user.status)
      .ifNotExists()
      .future()
  }

  def update(user: User): Future[ResultSet] = {
    insert
      .value(_.email, user.email)
      .value(_.firstname, user.firstname)
      .value(_.password, user.password)
      .value(_.screenName, user.screenName)
      .value(_.lastname, user.lastname)
      .value(_.status, user.status)
      .future()
  }

  def getUser(email: String): Future[Option[User]] = {
    select.where(_.email eqs email).one()
  }


}