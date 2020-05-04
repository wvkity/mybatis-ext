package com.wvkity.mybatis.exception;

/**
 * Mapper解析异常
 * @author wvkity
 */
public class MapperParserException extends RuntimeException {

    private static final long serialVersionUID = -6444616123358626573L;

    public MapperParserException() {
        super();
    }

    public MapperParserException(String message) {
        super(message);
    }

    public MapperParserException(Throwable cause) {
        super(cause);
    }

    public MapperParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}