package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.factory.CriteriaBuilderFactory;
import com.wkit.lost.mybatis.mapper.MapperExecutor;

/**
 * 业务泛型接口
 * @param <T>  泛型类
 * @param <PK> 主键类型
 * @param <R>  返回值类
 */
public interface ServiceExecutor<T, PK, R> extends ReaderService<T, PK, R>, WriterService<T, PK>, CriteriaBuilderFactory<T> {

    /**
     * 获取Mapper泛型接口
     * @return Mapper泛型接口
     */
    MapperExecutor<T, PK, R> getExecutor();
    
}
