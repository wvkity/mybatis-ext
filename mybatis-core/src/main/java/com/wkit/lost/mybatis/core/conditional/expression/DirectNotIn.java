package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * IN范围条件
 * @author wvkity
 */
public class DirectNotIn extends AbstractDirectRangeExpression {

    private static final long serialVersionUID = 2503342275351623888L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param values     值
     * @param logic      逻辑符号
     */
    DirectNotIn(Criteria<?> criteria, String tableAlias, String column, Collection<Object> values, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.values = values;
        this.logic = logic;
        this.symbol = Symbol.NOT_IN;
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static DirectNotIn.Builder create() {
        return new DirectNotIn.Builder();
    }

    /**
     * 条件构建器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        /**
         * 条件包装对象
         */
        private Criteria<?> criteria;
        /**
         * 表别名
         */
        private String alias;
        /**
         * 条件包装对象
         */
        private String column;
        /**
         * 值
         */
        private Collection<Object> values;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public DirectNotIn build() {
            if (isEmpty(this.column) || CollectionUtil.isEmpty(this.values)) {
                return null;
            }
            return new DirectNotIn(this.criteria, this.alias, this.column, this.values, this.logic);
        }
    }
}
