package controllers

import java.text.SimpleDateFormat

import play.api.libs.iteratee.Enumerator
import play.api.mvc._
import services.FileService
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2015/09/28.
 */
class ImageController extends Controller {

  def getPhoto(id: String) = Action {

    FileService.getImage(id) match {
      case Some(file) => Result(
        ResponseHeader(OK, Map(
          CONTENT_LENGTH -> file.length.toString,
          CONTENT_TYPE -> file.contentType.getOrElse(BINARY),
          DATE -> new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", java.util.Locale.US).format(file.uploadDate)
        )),
        Enumerator.fromStream(file.inputStream)
      )
      case None => NotFound
    }


    //    Ok(views.html.index("Your new application is ready."))

  }
}
