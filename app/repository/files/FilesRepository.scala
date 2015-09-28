package repository.files

import java.io.FileInputStream
import java.net.URLConnection

import com.mongodb.casbah.gridfs.{GridFSDBFile, GridFS}
import conf.connection.HashMongoDB
import org.bson.types.ObjectId

/**
 * Created by hashcode on 2015/09/28.
 */
object FilesRepository {
  val gridfs = GridFS(HashMongoDB.getConnection())

 def getFileType(name:String) = {
   URLConnection.guessContentTypeFromName(name)
 }

  def save(file:FileInputStream)={

  }

  def getFileById(id:String):Option[GridFSDBFile]= {
    val ido = new ObjectId(id)
     gridfs.findOne(ido)
  }

  def getFilesByName()={

  }

  def deleteFileById()={}

  def deleteFilesByName() ={}


}
