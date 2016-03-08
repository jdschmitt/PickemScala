package com.nflpickem.api.model

/**
  * Created by jason on 2/29/16.
  */
class Player(val id:Long, val firstName: String, val lastName: String, val userName: String) {

}

object Player {

  def apply(id: Long, firstName: String, lastName: String, userName: String) =
    new Player(id, firstName, lastName, userName)

}
