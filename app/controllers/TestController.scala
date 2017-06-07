package controllers

import javax.inject._

import repository.{TestData, TestRepository}
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo._
import reactivemongo.api.ReadPreference
import reactivemongo.play.json._
import reactivemongo.play.json.collection._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by fcampos on 27/05/17.
  */
@Singleton
class TestController @Inject()(testRepository: TestRepository)(implicit exec: ExecutionContext) extends Controller {

//  }
 def index = Action.async {

     val testList: Future[List[TestData]] =  testRepository.listAll()

        testList.map { tests =>
          Ok(Json.toJson(tests))
        }
  }
}
