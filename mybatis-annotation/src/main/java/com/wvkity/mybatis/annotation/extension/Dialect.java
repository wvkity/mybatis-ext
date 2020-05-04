package com.wvkity.mybatis.annotation.extension;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 主键生成方言
 * @author wvkity
 */
public enum Dialect {

    /**
     * ORACLE
     */
    ORACLE,

    /**
     * POSTGRESQL
     */
    POSTGRESQL,

    /**
     * H2
     */
    H2,

    /**
     * DB2
     */
    DB2,

    /**
     * MYSQL
     */
    MYSQL,

    /**
     * MARIADB
     */
    MARIADB,

    /**
     * SQL SERVER
     */
    SQLSERVER,

    /**
     * SQL SERVER 2012 OR LATER
     */
    SQLSERVERLATER,

    /**
     * SQLITE
     */
    SQLITE,

    /**
     * CLOUDSCAPE
     */
    CLOUDSCAPE,

    /**
     * DERBY
     */
    DERBY,

    /**
     * HSQLDB
     */
    HSQLDB,

    /**
     * SYBASE
     */
    SYBASE,

    /**
     * INFORMIX
     */
    INFORMIX,

    /**
     * INFORMIXSQLI
     */
    INFORMIXSQLI,

    /**
     * DM(达梦)
     */
    DM,

    /**
     * 未定义
     */
    UNDEFINED;

    Dialect() {
    }

    private static final Map<String, Dialect> DATABASE_JDBC_CACHE = new ConcurrentHashMap<>(20);

    static {
        DATABASE_JDBC_CACHE.put("jdbc:mysql:", MYSQL);
        DATABASE_JDBC_CACHE.put("jdbc:cobar:", MYSQL);
        DATABASE_JDBC_CACHE.put("jdbc:log4jdbc:mysql:", MYSQL);
        DATABASE_JDBC_CACHE.put("jdbc:mariadb:", MARIADB);
        DATABASE_JDBC_CACHE.put("jdbc:oracle:", ORACLE);
        DATABASE_JDBC_CACHE.put("jdbc:dm:", ORACLE);
        DATABASE_JDBC_CACHE.put("jdbc:log4jdbc:oracle:", ORACLE);
        DATABASE_JDBC_CACHE.put("jdbc:sqlserver:", SQLSERVER);
        DATABASE_JDBC_CACHE.put("jdbc:microsoft:", SQLSERVER);
        DATABASE_JDBC_CACHE.put("jdbc:sqlserver2012:", SQLSERVERLATER);
        DATABASE_JDBC_CACHE.put("jdbc:postgresql:", POSTGRESQL);
        DATABASE_JDBC_CACHE.put("jdbc:log4jdbc:postgresql:", POSTGRESQL);
        DATABASE_JDBC_CACHE.put("jdbc:hsqldb:", HSQLDB);
        DATABASE_JDBC_CACHE.put("jdbc:log4jdbc:hsqldb:", HSQLDB);
        DATABASE_JDBC_CACHE.put("jdbc:db2:", DB2);
        DATABASE_JDBC_CACHE.put("jdbc:sqlite:", SQLITE);
        DATABASE_JDBC_CACHE.put("jdbc:h2:", H2);
        DATABASE_JDBC_CACHE.put("jdbc:log4jdbc:h2:", H2);
        DATABASE_JDBC_CACHE.put("jdbc:derby:", DERBY);
        DATABASE_JDBC_CACHE.put("jdbc:sybase:", SYBASE);
        DATABASE_JDBC_CACHE.put("jdbc:informix-sqli:", INFORMIX);
        //DATABASE_JDBC_CACHE.put( "jdbc:informix-sqli:", INFORMIXSQLI );
        DATABASE_JDBC_CACHE.put("jdbc:cloudscape:", CLOUDSCAPE);
        DATABASE_JDBC_CACHE.put("jdbc:undefined:", UNDEFINED);
    }

    /**
     * 获取数据库主键生成方言
     * @param database 数据库
     * @return 方言
     */
    public static Dialect getDBDialect(final String database) {
        if (isBlank(database)) {
            return UNDEFINED;
        }
        switch (database.toUpperCase()) {
            case "ORACLE":
                return ORACLE;
            case "POSTGRESQL":
                return POSTGRESQL;
            case "H2":
                return H2;
            case "DB2":
                return DB2;
            case "SQLSERVERLATER":
                return SQLSERVERLATER;
            case "SQLSERVER":
                return SQLSERVER;
            case "CLOUDSCAPE":
                return CLOUDSCAPE;
            case "DERBY":
                return DERBY;
            case "HSQLDB":
                return HSQLDB;
            case "SYBASE":
                return SYBASE;
            case "INFORMIX":
                return INFORMIX;
            case "INFORMIXSQLI":
                return INFORMIXSQLI;
            case "MYSQL":
                return MYSQL;
            case "MARIADB":
                return MARIADB;
            case "SQLITE":
                return SQLITE;
            case "DM":
                return DM;
            case "UNDEFINED":
            default:
                return UNDEFINED;
        }
    }

    /**
     * 根据数据库驱动地址获取数据库方言
     * @param jdbcUrl JDBC连接地址
     * @return 数据库方言
     */
    public static Dialect getDialectFromJdbc(final String jdbcUrl) {
        if (isBlank(jdbcUrl)) {
            return UNDEFINED;
        }
        return DATABASE_JDBC_CACHE.keySet()
                .stream()
                .filter(jdbcUrl::startsWith)
                .findFirst()
                .map(DATABASE_JDBC_CACHE::get)
                .orElse(UNDEFINED);
    }

    /**
     * 获取主键生成方法
     * @return 字符串
     */
    public String getKeyGenerator() {
        return "";
    }

    /**
     * 检测字符串是否为空
     * @param source 待检测字符串
     * @return boolean
     */
    private static boolean isBlank(final String source) {
        return source == null || source.trim().isEmpty();
    }
}
