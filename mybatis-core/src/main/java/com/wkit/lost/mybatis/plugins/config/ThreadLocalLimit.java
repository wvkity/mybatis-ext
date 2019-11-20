package com.wkit.lost.mybatis.plugins.config;

/**
 * 本地Limit对象缓存
 */
public class ThreadLocalLimit {

    private static final ThreadLocal<Limit> LIMIT_THREAD_LOCAL = new ThreadLocal<>();

    public static Limit getLimit() {
        return LIMIT_THREAD_LOCAL.get();
    }

    public static void set( Limit value ) {
        if ( value == null ) {
            remove();
        } else {
            LIMIT_THREAD_LOCAL.set( value );
        }
    }

    /**
     * 清除本地对象
     */
    public static void remove() {
        LIMIT_THREAD_LOCAL.remove();
    }
}
