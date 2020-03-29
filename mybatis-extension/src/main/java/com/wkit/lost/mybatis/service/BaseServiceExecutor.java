package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.BaseMapperExecutor;

/**
 * 业务泛型接口
 * @param <T> 泛型类
 * @param <V> 返回值类型
 */
public interface BaseServiceExecutor<T, V> extends SaveService<T>, UpdateService<T>, DeleteService<T>,
        QueryService<T, V>, CriteriaService<T, V> {

    /**
     * 获取Mapper泛型接口
     * @return Mapper泛型接口
     */
    BaseMapperExecutor<T, V> getExecutor();

}
