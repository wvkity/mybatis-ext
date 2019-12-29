package com.wkit.lost.mybatis.generator.utils

import com.wkit.lost.mybatis.generator.bean.Column
import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import com.wkit.lost.mybatis.generator.constants.DatabaseType
import com.wkit.lost.mybatis.generator.mapping.JdbcJavaTypeRegistrar
import com.wvkit.lost.mybatis.generator.utils.CaseFormat
import java.sql.Connection
import java.sql.ResultSet
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class DatabaseUtil {

    companion object {
        
        private const val IS_PREFIX = "IS_"

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

        fun getTables(config: ConnectionConfig, defaultSchema: String?): MutableList<String> {
            val connection = DataSourceUtil.getConnection(config, defaultSchema)
            return connection.use {
                val tables = ArrayList<String>()
                val metadata = it.metaData
                val dbType = DatabaseType.valueOf(config.dbType!!)
                val rs: ResultSet
                if (dbType == DatabaseType.SQL_SERVER) {
                    val sql = "SELECT NAME FROM sysobjects WHERE xtype='u' or xtype='v' order by name"
                    rs = connection.createStatement().executeQuery(sql)
                    rs.use {
                        while (rs.next()) {
                            tables.add(rs.getString("name"))
                        }
                    }
                } else if (dbType == DatabaseType.SQLITE) {
                    val sql = "SELECT name from SQLITE_MASTER"
                    rs = connection.createStatement().executeQuery(sql)
                    rs.use {
                        while (rs.next()) {
                            tables.add(rs.getString("name"))
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
                                tables.add(rs.getString(3))
                            }
                        }
                    }
                }
                tables.takeIf {
                    tables.isNotEmpty()
                }?.run {
                    tables.sort()
                }
                tables
            }
        }

        fun getColumns(config: ConnectionConfig, defaultSchema: String?, tableName: String): MutableList<Column> {
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
                val rs = metadata.getColumns(realSchema, null, tableName, null)
                while (rs.next()) {
                    // 获取表字段信息
                    val columnName = rs.getString("COLUMN_NAME")
                    val jdbcType = rs.getString("TYPE_NAME")
                    val comment = rs.getString("REMARKS")
                    val size = rs.getString("COLUMN_SIZE")
                    val index = rs.getString("ORDINAL_POSITION")
                    val javaType = JdbcJavaTypeRegistrar.javaTypeString(jdbcType.toLowerCase(Locale.ENGLISH), "")
                    // 封装
                    val column = Column()
                    column.setColumnName(columnName)
                    column.setJdbcType(jdbcType)
                    column.setColumnLength(size)
                    comment.takeIf { 
                        !comment.isNullOrBlank()
                    } ?.run {
                        column.setComment(comment)
                    }
                    column.setIndex(index)
                    column.setPrimary(primaryKeys.contains(columnName))
                    column.setPropertyName(transformProperty(columnName))
                    column.setJavaType(javaType)
                    column.setImportJavaType(JdbcJavaTypeRegistrar.javaType(javaType, ""))
                    columns.add(column)
                }
                columns.takeIf { column ->
                    column.isNotEmpty()
                }?.run {
                    columns.sortWith(Comparator {
                        o1: Column, o2: Column ->  
                        if (o1.isPrimary().compareTo(o2.isPrimary()) == 0) {
                            o1.getColumnName().compareTo(o2.getColumnName())
                        } 
                        o1.getIndex().compareTo(o2.getIndex())
                    })
                }
                columns
            }
        }
        
        private fun startWithIsPrefix(value: String): Boolean {
            return value.toUpperCase(Locale.ENGLISH).startsWith(IS_PREFIX)
        }
        
        private fun transformProperty(columnName: String): String {
            return columnName.takeIf { 
                startWithIsPrefix(it)
            } ?.run { 
                val newColumnName = columnName.substring(3).toUpperCase(Locale.ENGLISH)
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, newColumnName)
            } ?: run {
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName)
            }
        }
    }

}