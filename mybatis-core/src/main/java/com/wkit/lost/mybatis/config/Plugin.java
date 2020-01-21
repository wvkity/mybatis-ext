package com.wkit.lost.mybatis.config;

import com.wkit.lost.mybatis.plugins.data.auditing.MetadataAuditingInterceptor;
import com.wkit.lost.mybatis.plugins.locking.OptimisticLockingInterceptor;
import com.wkit.lost.mybatis.plugins.paging.LimitInterceptor;
import com.wkit.lost.mybatis.plugins.paging.PageableInterceptor;

/**
 * 内置插件
 * <p>枚举类对应的拦截器如下: </p>
 * <ul>
 *     <li>{@link Plugin#PAGEABLE}: {@link PageableInterceptor}</li>
 *     <li>{@link Plugin#LIMIT}: {@link LimitInterceptor}</li>
 *     <li>{@link Plugin#META_DATA_AUDIT}: {@link MetadataAuditingInterceptor}</li>
 *     <li>{@link Plugin#OPTIMISTIC_LOCKING}: {@link OptimisticLockingInterceptor}</li>
 * </ul>
 * <p>建议注册顺序：{@link Plugin#META_DATA_AUDIT} > {@link Plugin#OPTIMISTIC_LOCKING} >
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
     * 元数据审计
     */
    META_DATA_AUDIT,

    /**
     * 乐观锁插件
     */
    OPTIMISTIC_LOCKING
}
