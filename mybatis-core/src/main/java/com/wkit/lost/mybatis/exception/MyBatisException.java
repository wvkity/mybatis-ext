package com.wkit.lost.mybatis.exception;

/**
 * MyBatis异常
 * @author wvkity
 */
public class MyBatisException extends RuntimeException {

    private static final long serialVersionUID = -6444616123358626573L;

    public MyBatisException() {
        super();
    }

    public MyBatisException(String message) {
        super(message);
    }

    public MyBatisException(Throwable cause) {
        super(cause);
    }

    public MyBatisException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyBatisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}