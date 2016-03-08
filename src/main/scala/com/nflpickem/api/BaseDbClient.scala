package com.nflpickem.api

import akka.actor.ActorSystem

/**
  * Created by jason on 2/28/16.
  */
class BaseDbClient(implicit system:ActorSystem) {

  implicit val ec = system.dispatchers.lookup("pickem.dispatchers.default")

}
