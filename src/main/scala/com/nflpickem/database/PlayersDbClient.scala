package com.nflpickem.database

import akka.actor._
import com.nflpickem.api.model.Player
import scalikejdbc._

import scala.concurrent._

/**
  * Created by jason on 2/28/16.
  */
class PlayersDbClient(implicit system:ActorSystem) {

  implicit val ec = system.dispatchers.lookup("pickem.dispatchers.default")

  def getAllPlayers:Future[List[Player]] = Future {
    blocking {
      DB readOnly { implicit session =>
        val q = sql"SELECT id, first_name, last_name, username FROM player"

        q.map(asPlayer)
          .list.apply()
      }
    }
  }

  def getById(id: Long):Future[Option[Player]] = Future {
    blocking {
      DB readOnly { implicit session =>
        val q = sql"SELECT id, first_name, last_name, username FROM player WHERE id = ?".bind(id)

        q.map(asPlayer)
          .single.apply()
      }
    }
  }

  def getByUsername(userName: String):Future[Option[Player]] = Future {
    blocking {
      DB readOnly { implicit session =>
        val q = sql"SELECT id, first_name, last_name, username FROM player WHERE username = ?".bind(userName)

        q.map(asPlayer)
          .single.apply()
      }
    }
  }

  def asPlayer(r: WrappedResultSet) =
    Player(r.long("id"), r.string("first_name"), r.string("last_name"), r.string("username"))

}
