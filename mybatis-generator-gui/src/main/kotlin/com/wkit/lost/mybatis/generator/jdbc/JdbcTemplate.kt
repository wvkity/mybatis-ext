package com.wkit.lost.mybatis.generator.jdbc

import com.alibaba.fastjson.JSON
import org.apache.logging.log4j.LogManager
import java.lang.Exception
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.HashMap

/**
 * JDBC工具类
 * @author wvkity
 */
class JdbcTemplate {

    companion object {
        private val LOG = LogManager.getLogger(JdbcTemplate)

        /**
         * 查询操作
         * @param connection 数据库连接对象
         * @param sql SQL语句
         * @param args 参数
         */
        fun query(connection: Connection, sql: String, vararg args: Any?): MutableMap<String, Any?>? {
            val result = select(connection, sql, args)
            return if (result.isEmpty()) {
                null
            } else {
                result[0]
            }
        }

        /**
         * 查询操作
         * @param clazz 指定返回类型
         * @param connection 数据库连接对象
         * @param sql SQL语句
         * @param args 参数
         * @param <T> 泛型类型
         */
        fun <T> query(clazz: Class<T>, connection: Connection, sql: String, vararg args: Any?): T? {
            val result = query(connection, sql, *args)
            result?.run {
                if (this.isNotEmpty()) {
                    return JSON.parseObject(JSON.toJSONString(this), clazz)
                }
            }
            return null
        }

        /**
         * 查询操作
         * @param clazz 指定返回类型
         * @param connection 数据库连接对象
         * @param sql SQL语句
         * @param args 参数
         * @param <T> 泛型类型
         */
        fun <T> queryList(clazz: Class<T>, connection: Connection, sql: String, vararg args: Any): MutableList<T?>? {
            val result = queryList(connection, sql, *args)
            if (result.isNotEmpty()) {
                return JSON.parseArray(JSON.toJSONString(result), clazz)
            }
            return null
        }

        /**
         * 查询操作
         * @param connection 数据库连接对象
         * @param sql SQL语句
         * @param args 参数
         */
        fun queryList(connection: Connection, sql: String, vararg args: Any?): MutableList<MutableMap<String, Any?>> {
            return select(connection, sql, *args)
        }

        private fun select(connection: Connection, sql: String, vararg args: Any?): MutableList<MutableMap<String, Any?>> {
            var statement: PreparedStatement? = null
            var resultSet: ResultSet? = null
            val result = ArrayList<MutableMap<String, Any?>>()
            LOG.info("The SQL statement executed: $sql")
            try {
                statement = connection.prepareStatement(sql)
                if (args.isNotEmpty()) {
                    for ((i, v) in args.withIndex()) {
                        statement.setObject((i + 1), v)
                    }
                }
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    val map = HashMap<String, Any?>()
                    val metadata = resultSet.metaData
                    for (i in 1..metadata.columnCount) {
                        val columnName = metadata.getColumnName(i)
                        map[columnName] = resultSet.getObject(columnName)
                    }
                    result.add(map)
                }
            } catch (e: Exception) {
                LOG.error("`{}`执行失败: {}", sql, e.message, e)
            } finally {
                close(resultSet)
                close(statement)
                close(connection)
            }
            return result
        }


        /**
         * 更新操作
         * @param connection 数据库连接对象
         * @param sql SQL语句
         * @param args 参数
         */
        fun update(connection: Connection, sql: String, vararg args: Any?): Int {
            var statement: PreparedStatement? = null
            LOG.info("The SQL statement executed: $sql")
            try {
                statement = connection.prepareStatement(sql)
                return if (args.isEmpty()) {
                    statement.executeUpdate()
                } else {
                    for ((i, v) in args.withIndex()) {
                        statement.setObject((i + 1), v)
                    }
                    statement.executeUpdate()
                }
            } catch (e: Exception) {
                LOG.error("`{}`执行失败: {}", sql, e.message, e)
                return 0
            } finally {
                close(statement)
                close(connection)
            }
        }

        fun close(resultSet: ResultSet?) {
            try {
                resultSet?.run { this.close() }
            } catch (e: Exception) {
                // ignore
            }
        }

        fun close(statement: PreparedStatement?) {
            try {
                statement?.run { this.close() }
            } catch (e: Exception) {
                // ignore
            }
        }

        fun close(connection: Connection?) {
            try {
                connection?.run { this.close() }
            } catch (e: Exception) {
                // ignore
            }
        }
    }
}