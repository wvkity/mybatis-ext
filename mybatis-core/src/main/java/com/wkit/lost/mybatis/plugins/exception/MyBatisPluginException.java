package com.wkit.lost.mybatis.plugins.exception;

/**
 * 分页异常
 * @author wvkity
 */
public class MyBatisPluginException extends RuntimeException {

    private static final long serialVersionUID = 8722811709208826971L;

    public MyBatisPluginException() {
        super();
    }

    public MyBatisPluginException( String message ) {
        super( message );
    }

    public MyBatisPluginException( Throwable cause ) {
        super( cause );
    }

    public MyBatisPluginException( String message, Throwable cause ) {
        super( message, cause );
    }

    public MyBatisPluginException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
