package com.nflpickem.api.actors

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._

class PickemActor extends Actor with DefaultService {
  def actorRefFactory = context
  def receive = runRoute(defaultRoute)
}

trait DefaultService extends HttpService {
  val defaultRoute =
    path("/") {
      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to <i>Sam's</i> <strong>NFL</strong> pick'em on Heroku with <i>spray-can</i>!</h1>
              </body>
            </html>
          }
        }
      }
    }
}