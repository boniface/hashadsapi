package repository

import conf.connection.DataConnection
import org.junit.Test
import repository.advert._
import repository.locations.{LocationRepository, LocationTypeRepository}
import repository.people._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by hashcode on 2015/09/10.
 */
class CreateTableTest extends TestApi{


  @Test
  def testCreateLocationType(): Unit = {
    val repo = LocationTypeRepository
    Await.result(AdvertMetaRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(AdvertRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(SingleAdvertRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(AdvertUploadsRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(CategoryRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(CommentsRespository.create.ifNotExists().future(), 5000.millis)
    Await.result(MetaListRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(SubscriptionRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(SubscriptionTypesRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(UploadTypeRespository.create.ifNotExists().future(), 5000.millis)
    Await.result(ContactTypeRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(KeysRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(TokenRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(UserContactRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(UserRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(UserRolesRepository.create.ifNotExists().future(), 5000.millis)
    Await.result(repo.create.ifNotExists().future(), 5000.millis)

  }

  @Test
  def testCreateLocation(): Unit = {
    val repo = LocationRepository
    Await.result(repo.create.ifNotExists().future(), 5000.millis)
  }

}
