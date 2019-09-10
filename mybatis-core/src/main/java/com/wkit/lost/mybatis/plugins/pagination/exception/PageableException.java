package com.wkit.lost.mybatis.plugins.pagination.exception;

/**
 * 分页异常
 * @author DT
 */
public class PageableException extends RuntimeException {
    
    public PageableException() {
        super();
    }

    public PageableException( String message ) {
        super( message );
    }

    public PageableException( Throwable cause ) {
        super( cause );
    }

    public PageableException( String message, Throwable cause ) {
        super( message, cause );
    }

    public PageableException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
