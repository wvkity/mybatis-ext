package com.wkit.lost.mybatis.generator.constants

/**
 * 树图标枚举
 * @author wvkity
 */
enum class TreeItemIcon(val icon: String) {
    MYSQL("icons/mysql.png"),
    MYSQL_CLOSE("icons/mysql_close.png"),
    ORACLE("icons/oracle.png"),
    ORACLE_CLOSE("icons/oracle_close.png"),
    SQLSERVER("icons/sqlserver.png"),
    SQLSERVER_CLOSE("icons/sqlserver_close.png"),
    POSTGRESQL("icons/postgresql.png"),
    POSTGRESQL_CLOSE("icons/postgresql_close.png"),
    SQLITE("icons/sqlite.png"),
    SQLITE_CLOSE("icons/sqlite_close.png"),
    DB("icons/db.png"),
    DB_CLOSE("icons/db_close.png"),
    TABLE("icons/table.png"),
    COLUMN("icons/column.png"),
    PRIMARY("icons/primary.png")
}