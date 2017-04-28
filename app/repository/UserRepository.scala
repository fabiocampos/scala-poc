package repository

import javax.inject.Singleton

import scalikejdbc.WrappedResultSet
import scalikejdbc._


/**
  * Created by fcampos on 28/04/17.
  */
final case class UserData(id: Long, name: String, socialId: Option[String], socialToken: Option[String])
object UserData extends SQLSyntaxSupport[UserData] {
  override val tableName = "users"
  def apply(rs: WrappedResultSet): UserData = new UserData(
    rs.long("id"), rs.string("name"), rs.stringOpt("social_id"),rs.stringOpt("social_token"))
}

@Singleton
class UserRepository {
  implicit val session = AutoSession

  def listUsers:  List[UserData] = {
    val users: List[UserData] = {
      sql"select * from users".map(rs => UserData(rs)).list.apply()
    }
    return users
  }

  def getUserBySocialNetwork (socialId:String, socialToken:String, socialType:String) = {
    val user: List[UserData] = {
      sql"select * from users where social_id = ${socialId} and social_token = ${socialToken} and social_type =  ${socialType}".map(rs => UserData(rs)).list.apply()
    }
  }
}
