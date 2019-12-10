package com.wkit.lost.mybatis.exception;

/**
 * Mapper解析异常
 * @author wvkity
 */
public class MapperResolverException extends RuntimeException {

    private static final long serialVersionUID = -6444616123358626573L;

    public MapperResolverException() {
        super();
    }

    public MapperResolverException( String message ) {
        super( message );
    }

    public MapperResolverException( Throwable cause ) {
        super( cause );
    }

    public MapperResolverException( String message, Throwable cause ) {
        super( message, cause );
    }

    public MapperResolverException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}