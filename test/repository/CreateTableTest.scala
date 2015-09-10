package repository

import conf.connection.DataConnection
import org.junit.Test
import repository.locations.{LocationRepository, LocationTypeRepository}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by hashcode on 2015/09/10.
 */
class CreateTableTest {
  implicit val keyspace = DataConnection.keySpace
  implicit val session = DataConnection.session
  @Test
  def testCreateLocationType():Unit ={
    val repo = LocationTypeRepository
    Await.result(repo.create.ifNotExists().future(), 5000.millis)
  }
  @Test
  def testCreateLocation():Unit ={
    val repo = LocationRepository
    Await.result(repo.create.ifNotExists().future(), 5000.millis)
  }

}
