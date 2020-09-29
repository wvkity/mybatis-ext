package com.wvkity.mybatis.code.make.constant;

/**
 * 数据库类型枚举
 * @author wvkity
 */
public enum DatabaseType {

    /**
     * MYSQL
     */
    MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://%s:%s/%s?useUnicode=true&useSSL=false&characterEncoding=%s"),
    /**
     * MYSQL8
     */
    MYSQL8("com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&useSSL=false" +
            "&allowPublicKeyRetrieval=true&characterEncoding=%s"),
    /**
     * ORACLE
     */
    ORACLE("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//%s:%s/%s"),
    /**
     * SQLITE
     */
    SQLITE("org.sqlite.JDBC", "jdbc:sqlite:%s"),
    /**
     * SQLSERVER
     */
    MSSQL("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://%s:%s;databaseName=%s"),
    /**
     * POSTGRESQL
     */
    POSTGRESQL("org.postgresql.Driver", "jdbc:postgresql://%s:%s/%s");

    /**
     * 驱动类
     */
    private final String driver;

    /**
     * 数据库连接地址
     */
    private final String url;

    DatabaseType(String driver, String url) {
        this.driver = driver;
        this.url = url;
    }

    /**
     * 获取数据库驱动类
     * @return 驱动类
     */
    public String driverClass() {
        return this.driver;
    }

    /**
     * 获取数据连接地址模板
     * @return 模板
     */
    public String connectTemplate() {
        return this.url;
    }
}
