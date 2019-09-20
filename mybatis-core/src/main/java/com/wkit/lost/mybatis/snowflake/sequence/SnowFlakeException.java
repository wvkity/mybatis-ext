package com.wkit.lost.mybatis.snowflake.sequence;

/**
 * MyBatis异常
 * @author DT
 */
public class SnowFlakeException extends RuntimeException {

    private static final long serialVersionUID = 8783484017295545253L;

    public SnowFlakeException() {
        super();
    }

    public SnowFlakeException( String message ) {
        super( message );
    }

    public SnowFlakeException( String message, Object... args ) {
        super( String.format( message, args ) );
    }

    public SnowFlakeException( Throwable cause ) {
        super( cause );
    }

    public SnowFlakeException( String message, Throwable cause ) {
        super( message, cause );
    }

    public SnowFlakeException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}