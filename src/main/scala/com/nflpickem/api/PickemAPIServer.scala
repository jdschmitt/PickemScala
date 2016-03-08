package com.nflpickem.api

import akka.actor._
import akka.io.IO
import akka.util.Timeout
import com.nflpickem.database.PickemMySqlConnectionPool
import scala.concurrent._
import scala.concurrent.duration._
import com.nflpickem.api.actors.PickemActor
import spray.can.Http

import scala.util.Properties

/**
  * Created by jason on 11/24/15.
  */
object PickemAPIServer extends App {

  Pickem.system.log.info("Starting Pickem server...")

  Pickem.initialize()

  Pickem.startHttpServer()

}

object Pickem {

  implicit val system = ActorSystem("PickemAPI")

  private def startDbConnectionPool(): Unit = {
    PickemMySqlConnectionPool.startup(MySqlConfig)

    system.registerOnTermination {
      system.log.info("Shutting down MySQL connection pool...")
      PickemMySqlConnectionPool.shutdown()
    }
  }

  def initialize(): Unit = {
    startDbConnectionPool()
  }

  def startHttpServer() = {
    implicit val timeout = Timeout(5.seconds)

    val service = Pickem.system.actorOf(Props[PickemActor], "pickem-service")

    val host = Config.getString("server.host")
    val port = Config.getString("server.port").toInt

    IO(Http) ! Http.Bind(service, interface = host, port = port)
  }

  def readVars(env: String, conf: String): String = {
    Properties.envOrElse(env, Config.getString(conf))
  }

}
