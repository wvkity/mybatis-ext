package com.wvkity.mybatis.core.wrapper.aggreate;

import com.wvkity.mybatis.core.constant.AggregateType;
import com.wvkity.mybatis.core.constant.Comparator;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.wrapper.basic.Case;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;

/**
 * COUNT聚合函数
 * @author wvkity
 */
public class Count extends AbstractColumnFunction {

    private static final long serialVersionUID = -7666419553760165173L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param column     字段包装对象
     * @param distinct   去重
     * @param scale      小数位数
     * @param alias      别名
     * @param _case      {@link Case}
     * @param value      值
     * @param minValue   最小值
     * @param maxValue   最大值
     * @param comparator 比较运算符
     * @param logic      逻辑符号
     */
    public Count(Criteria<?> criteria, ColumnWrapper column, boolean distinct, Integer scale, String alias,
                 Case _case, Object value, Object minValue, Object maxValue, Comparator comparator, Logic logic) {
        super.criteria = criteria;
        super.column = column;
        super.distinct = distinct;
        super.scale = scale;
        super.alias = alias;
        super._case = _case;
        super.value = value;
        super.minValue = minValue;
        super.maxValue = maxValue;
        super.comparator = comparator;
        super.type = AggregateType.COUNT;
        super.logic = logic;
    }
}
