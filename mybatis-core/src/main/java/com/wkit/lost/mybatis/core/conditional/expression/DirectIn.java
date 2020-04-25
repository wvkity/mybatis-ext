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
 * @param <T> 实体类型
 * @author wvkity
 */
public class DirectIn<T> extends AbstractDirectRangeExpression<T> {

    private static final long serialVersionUID = -610559071573172529L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param values     值
     * @param logic      逻辑符号
     */
    DirectIn(Criteria<T> criteria, String tableAlias, String column, Collection<Object> values, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.values = values;
        this.logic = logic;
        this.symbol = Symbol.IN;
    }

    /**
     * 创建条件构建器
     * @param <T> 实体类型
     * @return 构建器
     */
    public static <T> DirectIn.Builder<T> create() {
        return new DirectIn.Builder<>();
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
        private Collection<Object> values;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public DirectIn<T> build() {
            if (isEmpty(this.column) || CollectionUtil.isEmpty(this.values)) {
                return null;
            }
            return new DirectIn<>(this.criteria, this.alias, this.column, this.values, this.logic);
        }
    }
}
