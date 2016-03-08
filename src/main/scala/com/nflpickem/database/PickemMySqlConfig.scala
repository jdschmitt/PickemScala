package com.nflpickem.database
import com.typesafe.config.Config

/**
  * Reusable class for MySQL database configuration
  *
  * @param host
  * @param port
  * @param database
  * @param user
  * @param password
  */
case class PickemMySqlConfig(host:String, port:Int = 3306, database:String,
                         user:String, password:String, poolSize:Int) {

  val jdbcUrl = s"jdbc:mysql://$host:$port/$database"
}

object PickemMySqlConfig {
  val DriverClass = "com.mysql.jdbc.Driver"

  private val DEFAULT_POOL_SIZE = 36

  def apply(config:Config):PickemMySqlConfig = PickemMySqlConfig(
    host = config.getString("host"),
    port = if(config.hasPath("port")) config.getInt("port") else 3306,
    database = config.getString("database"),
    user = config.getString("user"),
    password = config.getString("password"),
    poolSize = if(config.hasPath("pool-size")) config.getInt("pool-size") else DEFAULT_POOL_SIZE
  )
}