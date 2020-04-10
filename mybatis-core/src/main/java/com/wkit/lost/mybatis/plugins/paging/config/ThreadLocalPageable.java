package com.wkit.lost.mybatis.plugins.paging.config;

import com.wkit.lost.paging.Pageable;

/**
 * 本地分页对象缓存
 * @author wvkity
 */
public class ThreadLocalPageable {

    /**
     * 线程对象
     */
    protected static final ThreadLocal<Pageable> THREAD_LOCAL_PAGEABLE = new ThreadLocal<>();

    /**
     * 获取本地分页对象
     * @return 分页对象
     */
    public static Pageable getPageable() {
        return THREAD_LOCAL_PAGEABLE.get();
    }

    /**
     * 缓存分页对象
     * @param pageable 分页对象
     */
    public static void setPageable(Pageable pageable) {
        if (pageable == null) {
            remove();
        } else {
            THREAD_LOCAL_PAGEABLE.set(pageable);
        }
    }

    /**
     * 移除本地分页对象
     */
    public static void remove() {
        THREAD_LOCAL_PAGEABLE.remove();
    }
}
