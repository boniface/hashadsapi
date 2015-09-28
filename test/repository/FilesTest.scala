package repository

import java.io.{File, FileInputStream}
import java.net.URL

import com.mongodb.casbah.gridfs.GridFS
import conf.connection.HashMongoDB
import org.bson.types.ObjectId
import org.junit.Test

import scalaz.stream.nio.file

/**
 * Created by hashcode on 2015/09/28.
 */
class FilesTest extends TestApi {
  @Test
  def testLocationType(): Unit = {

    val gridfs = GridFS(HashMongoDB.getConnection())
    val  url = getClass().getResource("image.jpg")
    val  file = new File(url.getPath())

    val logo = new FileInputStream(file)

    val id = gridfs(logo) { f =>
      f.filename = "simpsons.jpg"
      f.contentType = "image/jpeg"
    }

    val ido = new ObjectId("56094b34be24189b30b10411")

    println(" The ID OBJECT IS ",id)

    val my1File = gridfs.findOne(ido)

    val myFile = gridfs.findOne("simpsons.jpg")

    println("The File is ", my1File)

    // Print all filenames stored in GridFS
    for (f <- gridfs) println(f)





  }

}
