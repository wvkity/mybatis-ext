package com.wkit.lost.mybatis.generator.jdbc

import com.wkit.lost.mybatis.generator.constants.DatabaseType
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.lang.Exception
import java.sql.Connection
import java.util.concurrent.ConcurrentHashMap
import javax.sql.DataSource

/**
 * 数据源工具类
 * @author wvkity
 */
class DataSourceUtil {
    
    companion object {
        private val LOG: Logger = LogManager.getLogger(DataSourceUtil)
        private val DATA_SOURCE_CACHE: MutableMap<String, DataSource> = ConcurrentHashMap()
        
        fun getDataSource(databaseType: DatabaseType, databaseName: String, host: String?, port: String?, 
                          userName: String?, password: String?, encoding: String?): DataSource {
            var key: String = databaseName
            var jdbcUrl = databaseType.connectionUrlPattern
            host?.run { 
                key += this
                jdbcUrl = jdbcUrl.format(this)
            }
            port?.run { 
                key += "_$this"
                jdbcUrl = jdbcUrl.format(this)
            }
            jdbcUrl = jdbcUrl.format(databaseName)
            encoding?.run { 
                key += "_$this"
                jdbcUrl = jdbcUrl.format(this)
            }
            val dataSource = getDataSource(key)
            dataSource?.run { 
                return this
            } ?:run {
                return initDataSource(databaseType, jdbcUrl, key, userName, password)
            }
        }
        
        private fun initDataSource(databaseType: DatabaseType, jdbcUrl: String, key: String, 
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
            } ?:run {
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