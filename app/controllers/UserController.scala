package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.libs.json.Writes
import repository.{UserData, UserRepository}


/**
  * Created by fcampos on 21/04/17.
  */
@Singleton
class UserController @Inject()(userRepository: UserRepository) extends Controller {


  def index = Action { implicit request =>
    val users: List[UserData] = userRepository.listUsers

    implicit val subWrites:Writes[UserData] = Json.writes[UserData]

    Ok(Json.toJson(users))
  }
}