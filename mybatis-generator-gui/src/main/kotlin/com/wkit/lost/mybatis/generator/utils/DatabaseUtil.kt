package com.wkit.lost.mybatis.generator.utils

import com.wkit.lost.mybatis.generator.bean.Column
import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import com.wkit.lost.mybatis.generator.bean.Table
import com.wkit.lost.mybatis.generator.constants.DatabaseType
import com.wkit.lost.mybatis.generator.jdbc.JdbcTemplate
import org.apache.logging.log4j.LogManager
import java.sql.Connection
import java.sql.ResultSet
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class DatabaseUtil {

    companion object {

        private val LOG = LogManager.getLogger(DatabaseUtil)

        private const val IS_PREFIX = "IS_"
        private const val SQL_SERVER_COLUMN_COMMENT_SQL = "SELECT b.NAME, c.value AS REMARKS FROM sys.tables a" +
                " LEFT JOIN sys.columns b ON a.object_id=b.object_id LEFT JOIN sys.extended_properties c" +
                " ON a.object_id=c.major_id WHERE a.name = ? AND c.minor_id <> 0 AND b.column_id=c.minor_id" +
                " AND a.schema_id=(SELECT schema_id FROM sys.schemas WHERE name = ?)"
        private const val SQL_SERVER_COLUMN_INFO_SQL = "SELECT" +
                "  B.name AS TABLE_CAT," +
                "C.name AS COLUMN_NAME," +
                "T.name AS TYPE_NAME," +
                "E.VALUE AS REMARKS," +
                "COLUMNPROPERTY(C.id,C.name,'PRECISION') AS COLUMN_SIZE," +
                "C.colorder AS ORDINAL_POSITION" +
                " FROM" +
                " syscolumns C" +
                " LEFT JOIN systypes T ON C.xusertype= T.xusertype" +
                " INNER JOIN sysobjects B ON C.id= B.id " +
                " AND B.xtype= 'U' " +
                " AND B.name<> 'dtproperties'" +
                " LEFT JOIN sys.extended_properties E ON C.id= E.major_id" +
                " AND C.colid= E.minor_id WHERE B.name = ?" +
                " ORDER BY C.colorder"

        /**
         * 获取所有数据库
         * @param config 配置信息
         */
        fun getSchemaList(config: ConnectionConfig): List<String> {
            return getSchemaList(DataSourceUtil.getConnection(config))
        }

        /**
         * 获取所有数据库
         * @param connection 数据库连接对象
         */
        fun getSchemaList(connection: Connection): MutableList<String> {
            return connection.use {
                val schemaList = ArrayList<String>()
                val metadata = it.metaData
                // 获取所有数据库
                val schemaResultSet = metadata.catalogs
                while (schemaResultSet.next()) {
                    schemaList.add(schemaResultSet.getString("TABLE_CAT"))
                }
                schemaList
            }
        }

        fun getTables(config: ConnectionConfig, defaultSchema: String?, tablePrefix: String): MutableList<Table> {
            val connection = DataSourceUtil.getConnection(config, defaultSchema)
            return connection.use {
                val tables = ArrayList<Table>()
                val metadata = it.metaData
                val dbType = DatabaseType.valueOf(config.dbType!!)
                val rs: ResultSet
                if (dbType == DatabaseType.SQL_SERVER) {
                    val sql = "SELECT a.NAME, e.value REMARKS FROM sysobjects a LEFT JOIN sys.extended_properties e ON " +
                            "a.id= e.major_id AND e.minor_id= 0 WHERE a.xtype='u' or a.xtype='v' order by e.name"
                    rs = connection.createStatement().executeQuery(sql)
                    rs.use {
                        while (rs.next()) {
                            val comment = rs.getString("REMARKS").takeIf { value ->
                                !value.isNullOrBlank()
                            }?.run {
                                this
                            } ?: run {
                                ""
                            }
                            tables.add(Table(rs.getString("NAME"), comment, tablePrefix, ""))
                        }
                    }
                } else if (dbType == DatabaseType.SQLITE) {
                    val sql = "SELECT name from SQLITE_MASTER"
                    rs = connection.createStatement().executeQuery(sql)
                    rs.use {
                        while (rs.next()) {
                            tables.add(Table(rs.getString("name"), "", tablePrefix, ""))
                        }
                    }
                } else {
                    if (dbType == DatabaseType.ORACLE) {
                        rs = metadata.getTables(null, config.userName?.toUpperCase(),
                                null, arrayOf("TABLE", "VIEW"))
                    } else {
                        var realSchema: String? = null
                        config.schema.takeIf { str ->
                            str.isNullOrBlank()
                        }?.run {
                            realSchema = defaultSchema
                        } ?: run {
                            realSchema = config.schema
                        }
                        rs = metadata.getTables(realSchema, config.userName,
                                "%", arrayOf("TABLE", "VIEW"))
                        rs.use {
                            while (rs.next()) {
                                val comment = rs.getString("REMARKS").takeIf { remark ->
                                    !remark.isNullOrBlank()
                                }?.run {
                                    this
                                } ?: run {
                                    ""
                                }
                                val schema = rs.getString("TABLE_CAT").takeIf { sc ->
                                    !sc.isNullOrBlank()
                                }?.run {
                                    this
                                } ?: run {
                                    ""
                                }
                                val table = Table(rs.getString(3), comment, tablePrefix, schema)
                                tables.add(table)
                            }
                        }
                    }
                }
                tables.takeIf {
                    tables.isNotEmpty()
                }?.run {
                    tables.sortWith(Comparator { t1, t2 ->
                        t1.getName().compareTo(t2.getName())
                    })
                }
                tables
            }
        }

        fun getColumns(config: ConnectionConfig, defaultSchema: String?, tableName: String): ArrayList<Column> {
            val connection = DataSourceUtil.getConnection(config, defaultSchema)
            return connection.use {
                val columns = ArrayList<Column>()
                val metadata = it.metaData
                var realSchema: String? = null
                config.schema.takeIf { str ->
                    str.isNullOrBlank()
                }?.run {
                    realSchema = defaultSchema
                } ?: run {
                    realSchema = config.schema
                }
                // 先获取主键列
                val primaryKeys = HashSet<String>()
                val prs = metadata.getPrimaryKeys(realSchema, null, tableName)
                while (prs.next()) {
                    val columnName: String = prs.getString("COLUMN_NAME")
                    primaryKeys.add(columnName)
                }
                val isSqlServer = DatabaseType.SQL_SERVER == DatabaseType.valueOf(config.dbType!!)
                val rs = if (isSqlServer) {
                    // SQL SERVER采用以下查询获取列信息
                    JdbcTemplate.resultSet(DataSourceUtil.getConnection(config, defaultSchema),
                            SQL_SERVER_COLUMN_INFO_SQL, tableName)
                } else {
                    metadata.getColumns(realSchema, null, tableName, null)
                }
                rs.use {
                    while (rs.next()) {
                        // 获取表字段信息
                        val columnName = rs.getString("COLUMN_NAME")
                        val jdbcType = rs.getString("TYPE_NAME")
                        val comment = rs.getString("REMARKS")
                        val size = rs.getString("COLUMN_SIZE")
                        val index = rs.getString("ORDINAL_POSITION")
                        val realComment = comment.takeIf {
                            !comment.isNullOrBlank()
                        }?.run {
                            comment
                        } ?: run {
                            ""
                        }
                        // 封装
                        val column = Column(columnName, jdbcType, realComment)
                        column.setColumnLength(size)
                        column.setIndex(index)
                        column.setPrimary(primaryKeys.contains(columnName))
                        columns.add(column)
                    }
                }
                columns.takeIf { column ->
                    column.isNotEmpty()
                }?.run {
                    columns.sortWith(Comparator { o1: Column, o2: Column ->
                        if (o1.isPrimary().compareTo(o2.isPrimary()) == 0) {
                            o1.getColumnName().compareTo(o2.getColumnName())
                        }
                        o1.getIndex().toInt(10).compareTo(o2.getIndex().toInt(10))
                    })
                }
                columns
            }
        }

        private fun getComment(comments: MutableMap<String, String>, columnName: String): String {
            return if (comments.isEmpty() || columnName.isBlank()) {
                ""
            } else {
                comments[columnName].takeIf {
                    !it.isNullOrBlank()
                }?.run {
                    this
                } ?: run { "" }
            }
        }

        private fun querySqlServerColumnComment(isSqlServer: Boolean, connection: Connection,
                                                tableName: String): MutableMap<String, String> {
            val comments = HashMap<String, String>()
            if (isSqlServer) {
                val columnComments: MutableList<MutableMap<String, Any?>> = JdbcTemplate.queryList(connection,
                        SQL_SERVER_COLUMN_COMMENT_SQL, tableName, "dbo")
                if (columnComments.isNotEmpty()) {
                    for (item in columnComments) {
                        val name = item["NAME"]
                        val value = item["REMARKS"]
                        if (name is String && !name.isBlank()) {
                            comments[name] = value.takeIf {
                                it == null
                            }?.run {
                                ""
                            } ?: run {
                                value as String
                            }
                        }
                    }
                }
            }
            return comments
        }
    }

    private fun startWithIsPrefix(value: String): Boolean {
        return value.toUpperCase(Locale.ENGLISH).startsWith(IS_PREFIX)
    }

}