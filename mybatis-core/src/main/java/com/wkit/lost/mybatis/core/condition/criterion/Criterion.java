package com.wkit.lost.mybatis.core.condition.criterion;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 条件接口
 * @author DT
 */
public interface Criterion<T> extends Segment {
    
    /**
     * {@link Criteria}对象
     * @return {@link Criteria}对象
     */
    Criteria<T> getCriteria();
    
    /**
     * 设置{@link Criteria}对象
     * @param criteria {@link Criteria}对象
     * @return {@code this}
     */
    Criterion<T> setCriteria( Criteria<?> criteria );
    
    /**
     * 获取字段映射信息
     * @return {@link Column}
     */
    Column getColumn();
    
    /**
     * 设置连接类型
     * @param logic 操作连接类型
     * @return {@code this}
     */
    Criterion<T> setLogic( Logic logic );
    
    /**
     * 设置值
     * @param value 值
     * @return {@code this}
     */
    Criterion<T> setValue( Object value );
}
