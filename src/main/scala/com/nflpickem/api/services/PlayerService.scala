package com.nflpickem.api.services

import com.nflpickem.api.model.Player
import com.nflpickem.database.PlayersDbClient

import scala.util.Success

/**
  * Created by jason on 2/29/16.
  */
trait PlayerService { this:PickemApiService =>

  val playersDao = new PlayersDbClient()(context.system)

  import context.dispatcher

  val playerRoute =
    path("players" / Rest) { username =>
      get { ctx =>
        playersDao.getByUsername(username).onComplete {
          case Success(Some(p: Player)) => ctx.complete(s"Hello ${p.firstName} ${p.lastName}")
          case Success(None) => ctx.complete(s"Did not find username: $username")
          case _ => ctx.complete("Could not read from DB")
        }
      }
    } ~
      (path("players") & get) { ctx =>
        playersDao.getAllPlayers.onComplete {
          case Success(ps) => ctx.complete(s"Found ${ps.length} players")
          case _ => ctx.complete("Could not read from DB")
        }
      }

}
