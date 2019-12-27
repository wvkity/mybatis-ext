package com.wkit.lost.mybatis.generator.jdbc

import com.alibaba.fastjson.JSON
import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import com.wkit.lost.mybatis.generator.bean.ConnectionConfigInfo
import com.wkit.lost.mybatis.generator.constants.DatabaseType
import com.wkit.lost.mybatis.generator.utils.DataSourceUtil
import com.wkit.lost.mybatis.generator.utils.FileUtil
import com.wkit.lost.mybatis.generator.utils.SystemUtil
import java.sql.Connection
import javax.sql.DataSource

/**
 * 本地数据源
 * @author wvkity
 */
class LocalDataSource {

    companion object {
        private val JDBC_URL = SystemUtil.userHome() + FileUtil.SLASH + ".MybatisGenerator" +
                FileUtil.SLASH + FileUtil.SLASH + "config" + FileUtil.SLASH + "configuration.db"

        private const val CONNECT_CONFIG_SELECT_SQL = "SELECT ID, CONNECT_NAME, CONNECT_VALUE, GMT_CREATE, GMT_MODIFY FROM CONNECT_CONFIG_INFO"
        private const val CONNECT_CONFIG_INSERT_SQL = "INSERT INTO CONNECT_CONFIG_INFO(CONNECT_NAME, CONNECT_VALUE, GMT_CREATE) VALUES(?, ?, ?)"
        private const val CONNECT_CONFIG_UPDATE_SQL = "UPDATE CONNECT_CONFIG_INFO SET CONNECT_NAME = ?, CONNECT_VALUE = ?, GMT_MODIFY = ? WHERE ID = ?"
        private const val CONNECT_CONFIG_DELETE_SQL = "DELETE FROM CONNECT_CONFIG_INFO WHERE id = ?"

        /**
         * 获取数据源对象
         * @return 数据源对象
         */
        private fun getDataSource(): DataSource {
            return DataSourceUtil.getDataSource(DatabaseType.SQLITE, JDBC_URL, null, null, null, null, null)
        }

        /**
         * 获取数据库连接对象
         * @return 数据库连接对象
         */
        private fun getConnection(): Connection {
            //return getDataSource().connection
            return DataSourceUtil.getConnection(DatabaseType.SQLITE, JDBC_URL, null, null, null, null, null)
        }

        fun loadConnectConfig(name: String): ConnectionConfig? {
            val result = JdbcTemplate.query(ConnectionConfigInfo::class.java, getConnection(),
                    "$CONNECT_CONFIG_SELECT_SQL WHERE CONNECT_NAME = ?", name)
            result?.run {
                return transform(this)
            }
            return null
        }

        fun loadConnectConfigList(): MutableList<ConnectionConfig> {
            val list = JdbcTemplate.queryList(ConnectionConfigInfo::class.java, getConnection(),
                    CONNECT_CONFIG_SELECT_SQL)
            val result = ArrayList<ConnectionConfig>()
            if (list != null && list.isNotEmpty()) {
                list.forEach {
                    it?.run {
                        result.add(transform(this))
                    }
                }
            }
            return result
        }

        fun save(connect: ConnectionConfigInfo): Int {
            return JdbcTemplate.update(getConnection(), CONNECT_CONFIG_INSERT_SQL, connect.connectName,
                    connect.connectValue, connect.gmtCreate)
        }

        fun update(connect: ConnectionConfigInfo): Int {
            return JdbcTemplate.update(getConnection(), CONNECT_CONFIG_UPDATE_SQL, connect.connectName, connect.connectValue,
                    connect.gmtModify, connect.id)
        }

        fun delete(connect: ConnectionConfig): Int {
            return JdbcTemplate.update(getConnection(), CONNECT_CONFIG_DELETE_SQL, connect.id)
        }

        private fun transform(connect: ConnectionConfigInfo): ConnectionConfig {
            return connect.connectValue.run {
                val config = JSON.parseObject(this, ConnectionConfig::class.java)
                config.id = connect.id
                return config
            }
        }
    }
}