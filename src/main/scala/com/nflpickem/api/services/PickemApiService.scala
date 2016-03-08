package com.nflpickem.api.services

import akka.actor.ActorContext
import com.nflpickem.api.PickemAuthenticator
import com.nflpickem.api.model.Player
import com.nflpickem.database.PlayersDbClient
import spray.http.MediaTypes._
import spray.routing.AuthenticationFailedRejection.{CredentialsRejected, CredentialsMissing}
import spray.routing.{AuthenticationFailedRejection, authentication, RequestContext, HttpService}
import spray.routing.authentication._
import spray.routing.directives.AuthMagnet._

import scala.concurrent.Future
import scala.util.Success

/**
  * Created by jason on 2/29/16.
  */
trait PickemApiService extends HttpService
    with PlayerService {

  val context:ActorContext
/*
  def myUserPassAuthenticator(userPass: Option[UserPass]): Future[Option[String]] =
    Future {
      if (userPass.exists(up => up.user == "John" && up.pass == "p4ssw0rd")) Some("John")
      else None
    }


  private def extractCredentials(ctx: RequestContext): Option[Credentials] = {
    val queryParams = ctx.request.uri.query.toMap
    for {
      id <- queryParams.get("client_id")
      secret <- queryParams.get("client_secret")
    } yield Credentials(id, secret)
  }

  val authenticator: ContextAuthenticator[Unit] = { ctx =>
    Future {
      val maybeCredentials = extractCredentials(ctx)
      maybeCredentials.fold[authentication.Authentication[Unit]](
        Left(AuthenticationFailedRejection(CredentialsMissing, List()))
      )( credentials =>
        credentials match {
          case Credentials("my-client-id", "my-client-super-secret") => Right()
          case _ => Left(AuthenticationFailedRejection(CredentialsRejected, List()))
        }
      )
    }
  }
*/
  val route =
    // TODO Why doesn't implicit remove the need to call fromContextAuthenticator?
//    authenticate(fromContextAuthenticator(authenticator))
      playerRoute ~
        path("") {
          get {
            respondWithMediaType(`text/html`) {
              // XML is marshalled to `text/xml` by default, so we simply override here
              complete {
                <html>
                  <body>
                    <h1>Say hello to
                      <i>Sam's</i> <strong>NFL</strong>
                      pick'em on Heroku with
                      <i>spray-can</i>
                      !</h1>
                  </body>
                </html>
              }
            }
          }
        }

}

case class Credentials(id: String, secret: String)