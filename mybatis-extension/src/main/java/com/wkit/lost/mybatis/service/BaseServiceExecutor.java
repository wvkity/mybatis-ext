package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.BaseMapperExecutor;

/**
 * 通用Service接口
 * @param <T>  泛型类型
 * @param <V>  返回值类型
 * @param <PK> 主键类型
 * @author wvkity
 */
public interface BaseServiceExecutor<T, V, PK> extends SaveService<T>, UpdateService<T>, DeleteService<T>,
        QueryService<T, V, PK>, CriteriaService<T, V> {

    /**
     * 获取Mapper泛型接口
     * @return Mapper泛型接口
     */
    BaseMapperExecutor<T, V, PK> getExecutor();

}
