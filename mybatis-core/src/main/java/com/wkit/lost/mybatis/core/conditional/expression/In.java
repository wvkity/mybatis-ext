package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.ArrayUtil;
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
public class In<T> extends AbstractRangeExpression<T> {

    private static final long serialVersionUID = 1408575628333900120L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param logic    逻辑符号
     */
    In(Criteria<T> criteria, ColumnWrapper column, Collection<Object> values, Logic logic) {
        this.criteria = criteria;
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
    public static <T> In.Builder<T> create() {
        return new In.Builder<>();
    }

    /**
     * 条件对象构建器
     * @param <T> 实体类
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
         * 条件包装对象
         */
        private ColumnWrapper column;
        /**
         * 属性
         */
        @Setter(AccessLevel.NONE)
        private String property;
        /**
         * 属性
         */
        @Setter(AccessLevel.NONE)
        private Property<T, ?> lambdaProperty;
        /**
         * 值
         */
        @Setter(AccessLevel.NONE)
        private Collection<Object> values;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 属性
         * @param property 属性
         * @return {@link In.Builder}
         */
        public In.Builder<T> property(String property) {
            this.property = property;
            return this;
        }

        /**
         * 属性
         * @param property 属性
         * @param <V>      属性值类型
         * @return {@link In.Builder}
         */
        public <V> In.Builder<T> property(Property<T, V> property) {
            this.lambdaProperty = property;
            return this;
        }

        /**
         * 值
         * @param values 值
         * @return {@link In.Builder}
         */
        public In.Builder<T> values(Object... values) {
            return this.values(ArrayUtil.toList(values));
        }

        /**
         * 值
         * @param values 值
         * @return {@link In.Builder}
         */
        public In.Builder<T> values(Collection<Object> values) {
            this.values = values;
            return this;
        }

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public In<T> build() {
            if (this.column != null) {
                return new In<>(this.criteria, this.column, this.values, this.logic);
            }
            if (this.criteria == null || CollectionUtil.isEmpty(this.values)) {
                return null;
            }
            if (hasText(this.property)) {
                ColumnWrapper wrapper = this.criteria.searchColumn(this.property);
                if (wrapper != null) {
                    return new In<>(this.criteria, wrapper, this.values, this.logic);
                }
            }
            if (lambdaProperty != null) {
                ColumnWrapper wrapper = this.criteria.searchColumn(lambdaProperty);
                if (wrapper != null) {
                    return new In<>(this.criteria, wrapper, this.values, this.logic);
                }
            }
            return null;
        }
    }
}
