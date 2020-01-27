package com.wkit.lost.mybatis.plugins.paging.config;

/**
 * 本地Limit对象缓存
 */
public class ThreadLocalRangePageable {

    private static final ThreadLocal<RangePageable> RANGE_PAGE_THREAD_LOCAL = new ThreadLocal<>();

    public static RangePageable getRange() {
        return RANGE_PAGE_THREAD_LOCAL.get();
    }

    public static void set( RangePageable value ) {
        if ( value == null ) {
            remove();
        } else {
            RANGE_PAGE_THREAD_LOCAL.set( value );
        }
    }

    /**
     * 清除本地对象
     */
    public static void remove() {
        RANGE_PAGE_THREAD_LOCAL.remove();
    }
}
