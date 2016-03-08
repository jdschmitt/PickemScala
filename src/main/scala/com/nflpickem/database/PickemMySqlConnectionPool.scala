package com.nflpickem.database

import com.nflpickem.api._
import com.mchange.v2.c3p0.ComboPooledDataSource
import com.zaxxer.hikari.{HikariDataSource, HikariConfig}
import scalikejdbc.{DataSourceConnectionPool, ConnectionPool}
import scala.concurrent._

object PickemMySqlConnectionPool {
  private var mysqlConfig:Option[PickemMySqlConfig] = None

  lazy val datasource = {
    mysqlConfig match {
      case Some(config) => {
        val hikariConfig = new HikariConfig()
        Class.forName(PickemMySqlConfig.DriverClass)
        hikariConfig.setJdbcUrl(config.jdbcUrl)
        hikariConfig.setUsername(config.user)
        hikariConfig.setPassword(config.password)
        hikariConfig.setMaximumPoolSize(config.poolSize)
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true")
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
//        hikariConfig.setMetricRegistry(co.zing.k2.MetricsRegistry)
        hikariConfig.setPoolName("default-mysql-pool")
        new HikariDataSource(hikariConfig)
      }

      case None =>
        throw new IllegalStateException("Attempted to start datasource without MySQL configuration set, please call startup()")
    }
  }

  /**
    * Must be called before any actors are created.
    *
    * @param config
    */
  def startup(config:PickemMySqlConfig):Unit = this.synchronized {
    mysqlConfig = Some(MySqlConfig)
    ConnectionPool.singleton(new DataSourceConnectionPool(PickemMySqlConnectionPool.datasource))
  }

  def shutdown():Unit = this.synchronized {
    mysqlConfig = None
    datasource.close()
  }
}