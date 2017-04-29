package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.libs.json.Writes
import repository.{UserData, UserRepository}
import com.github.tototoshi.play2.json4s.native._
import org.json4s.{DefaultFormats, Extraction}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by fcampos on 21/04/17.
  */
@Singleton
class UserController @Inject()(userRepository: UserRepository) extends Controller{
  implicit val subWrites:Writes[UserData] = Json.writes[UserData]

  def index = Action.async {
    userRepository.findAll.map(users =>
      Ok(Json.toJson(users))
    )
  }

}