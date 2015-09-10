package repository

import conf.connection.DataConnection
import domain.location.{Location, LocationType}
import org.junit.Test
import repository.locations.{LocationRepository, LocationTypeRepository}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by hashcode on 2015/09/10.
 */
class LocationsTest {
  implicit val keyspace = DataConnection.keySpace
  implicit val session = DataConnection.session
  @Test
  def testLocationType():Unit ={
    val repo = LocationTypeRepository
    val lt = LocationType("zone","ZAMBIA","CT")
    val res = Await.result(repo.save(lt), 5000.millis)
    println(" WHAT YOU GOT IN RETURN !!!!! ",res.wasApplied())
  }
  @Test
  def testLocationTypeUpdate():Unit ={
    val repo = LocationTypeRepository
    val lt = Await.result(repo.findById("1"), 5000.millis)
    println(" The Result from Create is ",lt)
  }

  @Test
  def testCreateLocation():Unit ={
    val repo = LocationRepository
    val location = Location("2","1","2",Some("1"),"Zambia",Some("123121"),None)
    Await.result(repo.save(location), 5000.millis)
  }

  @Test
  def testfindLocation():Unit ={
    val repo = LocationRepository
    val lt = Await.result(repo.findById("2","1","2"), 5000.millis)
    println( " The Result from Create is ",lt)
  }

}
