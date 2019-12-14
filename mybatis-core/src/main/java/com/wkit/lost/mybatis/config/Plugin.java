package com.wkit.lost.mybatis.config;

/**
 * 内置插件
 * <p>枚举类对应的拦截器如下: </p>
 * <ul>
 *     <li>{@link Plugin#PAGEABLE}: {@link com.wkit.lost.mybatis.plugins.interceptor.PageableInterceptor}</li>
 *     <li>{@link Plugin#LIMIT}: {@link com.wkit.lost.mybatis.plugins.interceptor.LimitInterceptor}</li>
 *     <li>{@link Plugin#META_OBJECT_FILLING}: {@link com.wkit.lost.mybatis.plugins.interceptor.MetaObjectFillingInterceptor}</li>
 *     <li>{@link Plugin#OPTIMISTIC_LOCKER}: {@link com.wkit.lost.mybatis.plugins.interceptor.OptimisticLockerInterceptor}</li>
 * </ul>
 * <p>建议注册顺序：{@link Plugin#META_OBJECT_FILLING} > {@link Plugin#OPTIMISTIC_LOCKER} >
 * {@link Plugin#PAGEABLE} > {@link Plugin#LIMIT}</p>
 * @author wvkity
 */
public enum Plugin {

    /**
     * 常规分页插件
     */
    PAGEABLE,

    /**
     * 范围分页插件
     */
    LIMIT,

    /**
     * 自动填充值插件
     */
    META_OBJECT_FILLING,

    /**
     * 乐观锁插件
     */
    OPTIMISTIC_LOCKER
}
