package com.nflpickem.api

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import scala.concurrent.duration._
import com.nflpickem.api.actors.PickemActor
import com.typesafe.config.ConfigFactory
import spray.can.Http

import scala.util.Properties

/**
  * Created by jason on 11/24/15.
  */
object PickemAPIServer extends App {

  implicit val system = ActorSystem("PickemAPI")

  val config = ConfigFactory.load()

  val service = system.actorOf(Props[PickemActor], "pickem-service")

  val host = config.getString("server.host")
  val defaultPort = config.getString("server.port")
  // start a new HTTP server on port 8080 with our service actor as the handler
  val port = Properties.envOrElse("PORT", defaultPort).toInt
  println(s"Attempting to start on port: $port")

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ! Http.Bind(service, interface = host, port = port)

}

// TODO Connect to ClearDB