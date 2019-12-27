package com.wkit.lost.mybatis.generator.utils

import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import com.wkit.lost.mybatis.generator.constants.DatabaseType
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.lang.Exception
import java.lang.RuntimeException
import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.sql.DataSource
import kotlin.collections.ArrayList

/**
 * 数据源工具类
 * @author wvkity
 */
class DataSourceUtil {

    companion object {
        private val LOG: Logger = LogManager.getLogger(DataSourceUtil)
        private val DATA_SOURCE_CACHE: MutableMap<String, DataSource> = ConcurrentHashMap()
        private val DRIVER_CACHE: MutableMap<DatabaseType, Driver> = ConcurrentHashMap()
        
        fun getConnection(config: ConnectionConfig): Connection {
            return getConnection(DatabaseType.valueOf(config.dbType!!), config.schema, config.host, config.port,
                    config.userName, config.password, config.encoding)
        }

        fun getConnection(config: ConnectionConfig, defaultSchema: String?): Connection {
            var realSchema: String? = null
            config.schema.takeIf { str ->
                str.isNullOrBlank()
            }?.run {
                realSchema = defaultSchema
            } ?: run {
                realSchema = config.schema
            }
            return getConnection(DatabaseType.valueOf(config.dbType!!), realSchema, config.host, config.port,
                    config.userName, config.password, config.encoding)
        }

        fun getConnection(databaseType: DatabaseType, schema: String?, host: String?, port: String?,
                          userName: String?, password: String?, encoding: String?): Connection {
            DRIVER_CACHE[databaseType] ?: run {
                loadDriver(databaseType)
            }
            val args = ArrayList<String>()
            var jdbcUrl = databaseType.connectionUrlPattern
            host?.run {
                args.add(this)
            }
            port?.run {
                args.add(this)
            }
            schema?.run {
                args.add(this)
            }
            encoding?.run {
                args.add(this)
            }
            jdbcUrl = jdbcUrl.format(*args.toArray())
            val props = Properties()
            userName?.run { props.setProperty("user", userName) }
            password?.run { props.setProperty("password", password) }
            DriverManager.setLoginTimeout(5)
            return DRIVER_CACHE[databaseType]!!.connect(jdbcUrl, props)
        }

        private fun loadDriver(dbType: DatabaseType) {
            try {
                val clazz = Class.forName(dbType.driverClass, true,
                        Thread.currentThread().contextClassLoader)
                val driver = clazz.newInstance() as Driver
                DRIVER_CACHE.putIfAbsent(dbType, driver)
            } catch (e: Exception) {
                LOG.error("Database driven load failed: ", e.message, e)
                throw RuntimeException("Database driven load failed: ${dbType.driverClass}")
            }
        }

        fun getDataSource(config: ConnectionConfig): DataSource? {
            return config.dbType?.run {
                getDataSource(DatabaseType.valueOf(config.dbType!!), config.schema, config.host, config.port,
                        config.userName, config.password, config.encoding)
            } ?: run {
                null
            }
        }

        fun getDataSource(databaseType: DatabaseType, schema: String?, host: String?, port: String?,
                          userName: String?, password: String?, encoding: String?): DataSource {
            var key: String = ""
            if (!schema.isNullOrEmpty()) {
                key += schema
            }
            val list = ArrayList<String>()
            var jdbcUrl = databaseType.connectionUrlPattern
            host?.run {
                key += this
                list.add(this)
            }
            port?.run {
                key += "_$this"
                list.add(this)
            }
            schema?.run {
                list.add(this)
            }
            encoding?.run {
                key += "_$this"
                list.add(this)
            }
            jdbcUrl = jdbcUrl.format(*list.toArray())
            val dataSource = getDataSource(key)
            dataSource?.run {
                return this
            } ?: run {
                return initDataSource(databaseType, jdbcUrl, key, userName, password)
            }
        }

        fun initDataSource(databaseType: DatabaseType, jdbcUrl: String, key: String,
                           userName: String?, password: String?): DataSource {
            val config = HikariConfig()
            config.jdbcUrl = jdbcUrl
            config.driverClassName = databaseType.driverClass
            userName?.run { config.username = this }
            password?.run { config.password = this }
            config.addDataSourceProperty("cachePrepStmts", "true") //是否自定义配置，为true时下面两个参数才生效
            config.addDataSourceProperty("prepStmtCacheSize", "25") //连接池大小默认25，官方推荐250-500
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048") //单条语句最大长度默认256，官方推荐2048
            config.addDataSourceProperty("useServerPrepStmts", "true") //新版本MySQL支持服务器端准备，开启能够得到显著性能提升
            config.addDataSourceProperty("useLocalSessionState", "true")
            config.addDataSourceProperty("useLocalTransactionState", "true")
            config.addDataSourceProperty("rewriteBatchedStatements", "true")
            config.addDataSourceProperty("cacheResultSetMetadata", "true")
            config.addDataSourceProperty("cacheServerConfiguration", "true")
            config.addDataSourceProperty("elideSetAutoCommits", "true")
            config.addDataSourceProperty("maintainTimeStats", "false")
            getDataSource(key)?.run {
                return this
            } ?: run {
                val dataSource = HikariDataSource(config)
                DATA_SOURCE_CACHE.putIfAbsent(key, dataSource)
                return dataSource
            }
        }

        fun getDataSource(key: String): DataSource? {
            val dataSource: DataSource? = DATA_SOURCE_CACHE[key]
            dataSource?.run {
                var connection: Connection? = null
                try {
                    // 测试获取连接
                    connection = this.connection
                    return dataSource
                } catch (e: Exception) {
                    LOG.error("JDBC数据源获取失败: ", e)
                } finally {
                    try {
                        connection?.run {
                            connection.close()
                        }
                    } catch (e: Exception) {
                        // ignore
                    }
                }
            }
            return null
        }
    }
}