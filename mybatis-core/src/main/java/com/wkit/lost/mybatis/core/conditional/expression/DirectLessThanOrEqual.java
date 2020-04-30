package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 小于或等于条件(字符串字段)
 * @author wvkity
 */
public class DirectLessThanOrEqual extends DirectSimple {

    private static final long serialVersionUID = 5960872630102917706L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param logic      逻辑符号
     */
    DirectLessThanOrEqual(Criteria<?> criteria, String tableAlias, String column, Object value, Logic logic) {
        super(criteria, tableAlias, column, value, Symbol.LE, logic);
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static DirectLessThanOrEqual.Builder create() {
        return new DirectLessThanOrEqual.Builder();
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
        private Object value;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public DirectLessThanOrEqual build() {
            if (isEmpty(this.column)) {
                return null;
            }
            return new DirectLessThanOrEqual(this.criteria, this.alias, this.column, this.value, this.logic);
        }
    }
}
