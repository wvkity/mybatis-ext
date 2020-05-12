package com.wkit.lost.mybatis.generator.constants

/**
 * 数据库类型枚举类
 * @author wvkity
 */
enum class DatabaseType(val driverClass: String, val connectionUrlPattern: String) {
    /**
     * MySQL
     */
    MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://%s:%s/%s?useUnicode=true&useSSL=false&characterEncoding=%s"),

    /**
     * MySQL8+
     */
    MYSQL_8("com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=%s"),

    /**
     * Oracle
     */
    ORACLE("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//%s:%s/%s"),

    /**
     * PostgreSQL
     */
    POSTGRESQL("org.postgresql.Driver", "jdbc:postgresql://%s:%s/%s"),

    /**
     * SQL SERVER
     */
    SQL_SERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://%s:%s;databaseName=%s"),

    /**
     * Sqlite
     */
    SQLITE("org.sqlite.JDBC", "jdbc:sqlite:%s")
}