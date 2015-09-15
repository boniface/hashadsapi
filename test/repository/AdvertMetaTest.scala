package repository

import domain.advert.AdvertMeta
import org.junit.Test
import repository.advert.AdvertMetaRepository

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by hashcode on 2015/09/15.
 */
class AdvertMetaTest extends TestApi{

  @Test
  def createMetaAdvert():Unit ={
    val meta = Map("Bedrooms"-> "2","Bathrooms"->"5","Garages"->"7")
    val advertMeta = AdvertMeta("1",meta)
    val repo = AdvertMetaRepository

    val res = Await.result(repo.save(advertMeta), 5000.millis)
    println(" THE RESULTS IS ", res)

  }
}
