package com.wkit.lost.mybatis.factory;

import com.wkit.lost.mybatis.core.CriteriaImpl;

/**
 * 查询对象构建工厂接口
 * @param <T> 类型
 * @author DT
 */
public interface CriteriaBuilderFactory<T> {
    
    /**
     * 获取查询对象实例
     * @return 查询对象
     */
    CriteriaImpl<T> getCriteria();
    
    /**
     * 获取查询对象实例
     * @param alias 表别名
     * @return 查询对象
     */
    CriteriaImpl<T> getCriteria( String alias );
}
