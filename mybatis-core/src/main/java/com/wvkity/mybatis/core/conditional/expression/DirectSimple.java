package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;

/**
 * 简单条件
 * @author wvkity
 */
public class DirectSimple extends DirectExpressionWrapper {

    private static final long serialVersionUID = 5549482113181211099L;

    /**
     * 构造方法
     * @param criteria   条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param symbol     条件符号
     * @param logic      逻辑符号
     */
    DirectSimple(Criteria<?> criteria, String tableAlias, String column, Object value, Symbol symbol, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.value = value;
        this.symbol = symbol;
        this.logic = logic;
    }
}
