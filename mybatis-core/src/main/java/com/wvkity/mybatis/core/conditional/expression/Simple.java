package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;

/**
 * 简单条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class Simple extends ColumnExpressionWrapper {

    private static final long serialVersionUID = 431175395571986016L;

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param column   列包装对象
     * @param value    值
     * @param symbol   条件符号
     * @param logic    逻辑符号
     */
    Simple(Criteria<?> criteria, ColumnWrapper column, Object value, Symbol symbol, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.symbol = symbol;
        this.logic = logic;
    }
}
