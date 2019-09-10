package com.wkit.lost.mybatis.exception;

public class SerializationException extends RuntimeException {

    private static final long serialVersionUID = 9041591661037837108L;

    public SerializationException() {
        super();
    }

    public SerializationException( String message ) {
        super( message );
    }

    public SerializationException( Throwable cause ) {
        super( cause );
    }

    public SerializationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public SerializationException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
