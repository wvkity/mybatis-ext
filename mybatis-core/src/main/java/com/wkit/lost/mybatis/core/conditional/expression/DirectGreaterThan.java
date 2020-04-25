package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 大于条件(字符串字段)
 * @param <T> 泛型类型
 * @author wvkity
 */
public class DirectGreaterThan<T> extends DirectSimple<T> {

    private static final long serialVersionUID = 181907352196018634L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param logic      逻辑符号
     */
    DirectGreaterThan(Criteria<T> criteria, String tableAlias, String column, Object value, Logic logic) {
        super(criteria, column, value, Symbol.GT, logic);
        super.tableAlias = tableAlias;
    }

    /**
     * 创建条件构建器
     * @param <T> 实体类型
     * @return 构建器
     */
    public static <T> DirectGreaterThan.Builder<T> create() {
        return new DirectGreaterThan.Builder<>();
    }

    /**
     * 条件构建器
     * @param <T> 实体类型
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder<T> {
        /**
         * 条件包装对象
         */
        private Criteria<T> criteria;
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
        public DirectGreaterThan<T> build() {
            if (isEmpty(this.column)) {
                return null;
            }
            return new DirectGreaterThan<>(this.criteria, this.alias, this.column, this.value, this.logic);
        }
    }
}
