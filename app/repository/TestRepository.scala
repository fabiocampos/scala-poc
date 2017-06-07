package repository

import javax.inject._

import play.api.libs.json._
import play.modules.reactivemongo._
import reactivemongo.api.ReadPreference
import reactivemongo.play.json._
import reactivemongo.play.json.collection._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by fcampos on 28/04/17.
  */
final case class TestData( name: String)
object TestData {
  implicit val formatter = Json.format[TestData]
}

class TestRepository @Inject()(val reactiveMongoApi: ReactiveMongoApi)(implicit exec: ExecutionContext) {
  def citiesFuture: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("teste"))

  def listAll(): Future[List[TestData]] = {
    val futureCitiesList: Future[List[TestData]] = citiesFuture.flatMap {
      _.find(Json.obj("name" -> "teste")).
        cursor[TestData](ReadPreference.primary).
        collect[List]()
    }
    futureCitiesList
  }
}

