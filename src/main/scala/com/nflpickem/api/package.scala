package com.nflpickem

import com.typesafe.config.ConfigFactory
import com.nflpickem.database._

package object api {
  def Config = ConfigFactory.load()
  def PickemConfig = Config.getConfig("pickem")
  val MySqlConfig = PickemMySqlConfig(PickemConfig.getConfig("mysql"))
}