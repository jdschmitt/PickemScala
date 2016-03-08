package com.nflpickem.api.actors

import akka.actor.{ActorContext, Actor}
import com.nflpickem.api._
import com.nflpickem.api.model.Player
import com.nflpickem.api.services.PickemApiService
import spray.httpx.SprayJsonSupport
import spray.json._
import spray.routing._
import spray.http._
import MediaTypes._

import scala.util.{Failure, Success}

import scala.concurrent.ExecutionContext.Implicits.global

class PickemActor extends HttpServiceActor with PickemApiService {

  def receive = runRoute(route)

}


trait PickemResponseSupport extends SprayJsonSupport with DefaultJsonProtocol {

  private def writePickemResponse(o:PickemResponse):JsValue = {
    val meta = Seq("meta" -> o.meta.asInstanceOf[JsValue])
    val data = o.dataJson match {
      case o:JsObject => Seq("data" -> o)
      case a:JsArray  => Seq("data" -> a)
      case _          => Seq()
    }

    JsObject((meta ++ data).toMap)
  }

  implicit object K2ResponseJsonFormat extends RootJsonWriter[PickemResponse] {
    def write(o:PickemResponse) = writePickemResponse(o)
  }

}