package com.wkit.lost.mybatis.service;

/**
 * 实体、返回值一样Service通用接口
 * @param <T>  实体类型、返回值类型
 * @param <PK> 主键类型
 * @author wvkity
 */
public interface UniformServiceExecutor<T, PK> extends BaseServiceExecutor<T, T, PK> {
}
