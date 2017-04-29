package repository


import scalikejdbc._, async._,
FutureImplicits._
import scala.concurrent._
import scalikejdbc.async.ShortenedNames._


/**
  * Created by fcampos on 28/04/17.
  */
final case class UserData(id: Long, name: String, socialId: Option[String], socialToken: Option[String])
object UserData extends SQLSyntaxSupport[UserData] {
  override val tableName = "adapp.users"

  override val columnNames = Seq("id", "name","social_id", "social_token")

  def apply(c: SyntaxProvider[UserData])(rs: WrappedResultSet): UserData = apply(c.resultName)(rs)
  def apply(c: ResultName[UserData])(rs: WrappedResultSet): UserData = new UserData(
    id = rs.long(c.id),
    name = rs.string(c.name),
    socialId = rs.stringOpt(c.socialId)
    ,
    socialToken = rs.stringOpt(c.socialToken)
  )

  def apply(rs: WrappedResultSet): UserData = new UserData(
    rs.long("id"), rs.string("name"), rs.stringOpt("social_id"),rs.stringOpt("social_token"))
}

class UserRepository {
  AsyncConnectionPool.singleton("jdbc:postgresql://localhost:5432/ada", "adamaster", "ada123")

  implicit val session = AutoSession
   lazy val usr = UserData.syntax("usr")
  //private lazy val isNotDeleted = sqls.isNull(c.deletedAt)

  def listUsers:  List[UserData] = {
    val users: List[UserData] = {
      sql"select * from users".map(rs => UserData(rs)).list.apply()
    }
    return users
  }

  def findAll()(implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[List[UserData]] = withSQL {
    select.from(UserData as usr)
       .orderBy(usr.id)
  }.map(UserData(usr))

  def getUserBySocialNetwork (socialId:String, socialToken:String, socialType:String) = {
    val user: List[UserData] = {
      sql"select * from users where social_id = ${socialId} and social_token = ${socialToken} and social_type =  ${socialType}".map(rs => UserData(rs)).list.apply()
    }
  }
}
