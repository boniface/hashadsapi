package conf.connection

import com.typesafe.config.ConfigFactory
import com.websudos.phantom.connectors.{SimpleConnector, ContactPoints, KeySpace, ContactPoint}
import com.websudos.phantom.dsl.Session
import scala.collection.JavaConversions._

/**
 * Created by hashcode on 2015/09/09.
 */
trait DefaultsConnector extends SimpleConnector {
  val config = ConfigFactory.load()
  val hosts: Seq[String] = config.getStringList("cassandra.host").toList
  println("THE VALUE FROM THE APP FILE IS ",hosts)
  implicit val keySpace: KeySpace = KeySpace(config.getString("cassandra.keyspace"))
  override implicit lazy val session: Session = ContactPoints(hosts).keySpace(keySpace.name).session
}

object DataConnection extends DefaultsConnector{

}
