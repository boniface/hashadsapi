package conf.connection

import com.mongodb.casbah.{MongoDB, MongoClientURI, MongoClient}
import com.typesafe.config.ConfigFactory
import com.websudos.phantom.connectors.{SimpleConnector, ContactPoints, KeySpace, ContactPoint}
import com.websudos.phantom.dsl.Session
import scala.collection.JavaConversions._

/**
 * Created by hashcode on 2015/09/09.
 */

object Config{
  val config = ConfigFactory.load()

}
trait DefaultsConnector extends SimpleConnector {
  val hosts: Seq[String] = Config.config.getStringList("cassandra.host").toList
  implicit val keySpace: KeySpace = KeySpace(Config.config.getString("cassandra.keyspace"))
  override implicit lazy val session: Session = ContactPoints(hosts).keySpace(keySpace.name).session
}

object DataConnection extends DefaultsConnector{

}

object HashMongoDB {
  def getConnection(): MongoDB={
    val hosts = Config.config.getString("mongodb.host")
    val database = Config.config.getString("mongodb.database")
    val uri = MongoClientURI(hosts)
    MongoClient(uri)(database)
  }
}
