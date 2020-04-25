package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 不等于条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class NotEqual<T> extends Simple<T> {

    private static final long serialVersionUID = -5367724512624788968L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     */
    NotEqual(Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic) {
        super(criteria, column, value, Symbol.NE, logic);
    }


    /**
     * 创建条件构建器
     * @param <T> 实体类型
     * @return 构建器
     */
    public static <T> NotEqual.Builder<T> create() {
        return new NotEqual.Builder<>();
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
        private Object value;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 属性
         * @param property 属性
         * @return {@link NotEqual.Builder}
         */
        public NotEqual.Builder<T> property(String property) {
            this.property = property;
            return this;
        }

        /**
         * 属性
         * @param property 属性
         * @param <V>      属性值类型
         * @return {@link NotEqual.Builder}
         */
        public <V> NotEqual.Builder<T> property(Property<T, V> property) {
            this.lambdaProperty = property;
            return this;
        }

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public NotEqual<T> build() {
            if (this.column != null) {
                return new NotEqual<>(this.criteria, this.column, this.value, this.logic);
            }
            if (this.criteria == null) {
                return null;
            }
            if (hasText(this.property)) {
                ColumnWrapper wrapper = this.criteria.searchColumn(this.property);
                if (wrapper != null) {
                    return new NotEqual<>(this.criteria, wrapper, this.value, this.logic);
                }
            }
            if (lambdaProperty != null) {
                ColumnWrapper wrapper = this.criteria.searchColumn(lambdaProperty);
                if (wrapper != null) {
                    return new NotEqual<>(this.criteria, wrapper, this.value, this.logic);
                }
            }
            return null;
        }
    }
}
