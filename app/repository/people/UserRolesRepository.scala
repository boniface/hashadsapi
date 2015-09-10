package repository.people

import com.websudos.phantom.CassandraTable
import com.websudos.phantom.connectors.RootConnector
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.people.UserRole

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/09/10.
 */
class UserRolesRepository extends CassandraTable[UserRolesRepository, UserRole] {

  //  roleId:String, email:String
  object roleId extends StringColumn(this) with PartitionKey[String]

  object email extends StringColumn(this) with PrimaryKey[String]


  override def fromRow(row: Row): UserRole = {
    UserRole(roleId(row),
      email(row))
  }
}

object UserRolesRepository extends UserRolesRepository with RootConnector {
  override lazy val tableName = "uroles"

  override implicit def space: KeySpace = DataConnection.keySpace

  override implicit def session: Session = DataConnection.session

  def save(role: UserRole): Future[ResultSet] = {
    insert
      .value(_.email, role.email)
      .value(_.roleId, role.roleId)
      .future()
  }

  def getUserRole(email: String, roleId: String): Future[Option[UserRole]] = {
    select.where(_.email eqs email).and(_.roleId eqs roleId).one()
  }

  def getUserRoles(email: String): Future[Seq[UserRole]] = {
    select.where(_.email eqs email).fetchEnumerator() run Iteratee.collect()
  }
}

