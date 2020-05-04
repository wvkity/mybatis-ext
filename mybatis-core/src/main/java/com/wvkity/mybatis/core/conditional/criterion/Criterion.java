package com.wvkity.mybatis.core.conditional.criterion;

import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;

/**
 * 条件接口
 * @author wvkity
 */
public interface Criterion extends Segment {

    /**
     * {@link Criteria}对象
     * @return {@link Criteria}对象
     */
    Criteria<?> getCriteria();

    /**
     * 设置{@link Criteria}对象
     * @param criteria {@link Criteria}对象
     * @return {@code this}
     */
    Criterion criteria(Criteria<?> criteria);

    /**
     * 获取属性值
     * @return 属性值
     */
    Object getValue();

    /**
     * 设置连接类型
     * @param logic 操作连接类型
     * @return {@code this}
     */
    Criterion logic(Logic logic);
    
}
