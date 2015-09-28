package services

import com.mongodb.casbah.gridfs.GridFSDBFile
import repository.files.FilesRepository

/**
 * Created by hashcode on 2015/09/28.
 */
object FileService {

  def getImage(id: String): Option[GridFSDBFile]= {
    FilesRepository.getFileById(id)
  }
}
