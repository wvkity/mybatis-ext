package com.wkit.lost.mybatis.core.condition;

import com.wkit.lost.mybatis.core.AbstractCriteria;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;

import java.util.List;

/**
 * 条件接口
 * @param <T> 泛型类型
 * @author DT
 */
public interface Condition<T> {
    
    /**
     * 设置查询对象
     * @param criteria 查询对象
     * @return 当前对象
     */
    Condition<T> setCriteria( AbstractCriteria<T> criteria );
    
    /**
     * 检查是否存在条件
     * @return true: 是 false: 否
     */
    boolean hasCondition();
    
    /**
     * 获取所有的条件
     * @return 条件对象集合
     */
    List<Criterion<?>> all();
    
    /**
     * 清空条件
     * @return 当前对象
     */
    Condition<T> clear();
}
