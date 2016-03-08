package com.nflpickem.api

import spray.http.StatusCode
import spray.http.StatusCodes._
import spray.json._

/**
  * Created by jason on 2/28/16.
  */
trait PickemResponse {
  val statusCode:StatusCode
  val exception:Option[Throwable]
  val errorMessage:Option[String]

  val offset:Option[Int] = None
  val limit:Option[Int] = None
  val total:Option[Long] = None

  val dataJson: JsValue

  lazy val meta = {
    val c = Seq("code" -> JsNumber(statusCode.intValue))

    //if there's an explicit error message, use that,
    // else fall back to the exception's message
    val errorMessageOpt:Option[JsString] =
      errorMessage.map(JsString(_)).orElse {
        exception.map { t =>
          JsString(t.getMessage)
        }
      }

    val e = errorMessageOpt.map(m => Seq("error" -> m)).getOrElse(Seq.empty)
    val o = if(offset.isDefined) Seq("offset" -> JsNumber(offset.get)) else Seq()
    val l = if(limit.isDefined) Seq("limit" -> JsNumber(limit.get)) else Seq()
    val t = if(total.isDefined) Seq("total" -> JsNumber(total.get)) else Seq()

    JsObject((c ++ e ++ o ++ l ++ t).toMap)
  }
}

/**
  * Returns a JSON response consisting of a single object in the 'data'
  * field.
  * @param data
  * @param statusCode
  * @tparam T
  */
case class PickemObjectResponse[T : JsonWriter](data:T, statusCode:StatusCode = OK)
  extends PickemResponse {

  lazy val dataJson = data.toJson
  val exception = None
  val errorMessage = None

}