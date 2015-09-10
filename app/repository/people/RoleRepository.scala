package repository.people

import com.websudos.phantom.connectors.RootConnector
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import conf.connection.DataConnection
import domain.people.Role

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/09/10.
 */
//id:String,name:String,description:String
class RoleRepository extends CassandraTable[RoleRepository, Role] {

  object id extends StringColumn(this) with PartitionKey[String]

  object name extends StringColumn(this)

  object description extends StringColumn(this)

  override def fromRow(row: Row): Role = {
    Role(id(row),
      name(row),
      description(row))
  }
}

object RoleRepository extends RoleRepository with  RootConnector {
  override lazy val tableName = "roles"
  override implicit def space: KeySpace = DataConnection.keySpace
  override implicit def session: Session = DataConnection.session

  def save(role: Role): Future[ResultSet] = {
    insert
      .value(_.id, role.id)
      .value(_.name, role.name)
      .value(_.description, role.description)
      .future()
  }

  def getRoleById(id: String): Future[Option[Role]] = {
    select.where(_.id eqs id).one()
  }

  def getRoles(): Future[Seq[Role]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }
}

