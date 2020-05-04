package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * BETWEEN范围条件
 * @author wvkity
 */
public class DirectNotBetween extends AbstractDirectBetweenExpression {

    private static final long serialVersionUID = -4154660572376109929L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param logic      逻辑符号
     */
    DirectNotBetween(Criteria<?> criteria, String tableAlias, String column, Object begin, Object end, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.begin = begin;
        this.end = end;
        this.logic = logic;
        this.symbol = Symbol.NOT_BETWEEN;
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static DirectNotBetween.Builder create() {
        return new DirectNotBetween.Builder();
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
         * 开始值
         */
        private Object begin;
        /**
         * 结束值
         */
        private Object end;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public DirectNotBetween build() {
            if (isEmpty(this.column) || this.begin == null || this.end == null) {
                return null;
            }
            return new DirectNotBetween(this.criteria, this.alias, this.column, this.begin, this.end, this.logic);
        }
    }
}
