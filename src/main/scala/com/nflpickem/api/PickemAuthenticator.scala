package com.nflpickem.api

import spray.routing.authentication.{UserPass, UserPassAuthenticator}

import scala.concurrent.Future

/**
  * Created by jason on 2/29/16.
  */
object PickemAuthenticator extends UserPassAuthenticator[Unit] {

  val AccessGranted = Some(())
  val AccessDenied = None

  override def apply(v1: Option[UserPass]): Future[Option[Unit]] = Future.successful {
    // TODO Lookup user in DB
    AccessGranted
  }
}
