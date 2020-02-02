package com.wkit.lost.mybatis.factory;

import com.wkit.lost.mybatis.core.criteria.CriteriaImpl;

/**
 * Criteria实例构建器
 * @author wvkity
 */
public class CriteriaInstanceBuilder {
    
    /**
     * 构建查询实例
     * @param entity 实体类
     * @param <T>    实体类型
     * @return 查询对象
     */
    public static <T> CriteriaImpl<T> build( final Class<T> entity ) {
        return new CriteriaImpl<>( entity );
    }
    
    /**
     * 构建查询实例
     * @param entity 实体类
     * @param alias  表别名
     * @param <T>    实体类型
     * @return 查询对象
     */
    public static <T> CriteriaImpl<T> build( final Class<T> entity, final String alias ) {
        return new CriteriaImpl<>( entity, alias );
    }
}
