package com.wvkity.mybatis.code.make.constant;

/**
 * 图标
 */
public enum Icon {

    MYSQL("icons/mysql.open.png", "icons/mysql.close.png"),
    MYSQL8("icons/mysql.open.png", "icons/mysql.close.png"),
    ORACLE("icons/oracle.open.png", "icons/oracle.close.png"),
    MSSQL("icons/mssql.open.png", "icons/mssql.close.png"),
    SQLITE("icons/sqlite.open.png", "icons/sqlite.close.png"),
    POSTGRE("icons/postgre.open.png", "icons/postgre.close.png"),
    OTHER("icons/other.open.png", "icons/other.close.png"),
    DATABASE("icons/db.open.png", "icons/db.close.png");

    private final String open;
    private final String close;

    Icon(String open, String close) {
        this.open = open;
        this.close = close;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }

    public static Icon convert(final String unique) {
        try {
            return Icon.valueOf(unique);
        } catch (Exception ignore) {
            return Icon.OTHER;
        }
    }
}
