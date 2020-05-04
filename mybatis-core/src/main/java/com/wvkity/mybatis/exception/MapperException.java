package com.wvkity.mybatis.exception;

/**
 * Mapper异常
 * @author wvkity
 */
public class MapperException extends RuntimeException {

    private static final long serialVersionUID = -6444616123358626573L;

    public MapperException() {
        super();
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}