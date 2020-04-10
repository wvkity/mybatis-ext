package com.wkit.lost.mybatis.core.snowflake.sequence;

/**
 * MyBatis异常
 * @author wvkity
 */
public class SnowflakeException extends RuntimeException {

    private static final long serialVersionUID = 8783484017295545253L;

    public SnowflakeException() {
        super();
    }

    public SnowflakeException(String message) {
        super(message);
    }

    public SnowflakeException(String message, Object... args) {
        super(String.format(message, args));
    }

    public SnowflakeException(Throwable cause) {
        super(cause);
    }

    public SnowflakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnowflakeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}