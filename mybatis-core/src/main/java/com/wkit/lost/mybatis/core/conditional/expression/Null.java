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
 * IS NULL条件
 * @author wvkity
 */
public class Null extends AbstractEmptyExpression {

    private static final long serialVersionUID = 1553843773012605762L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param logic    逻辑符号
     */
    Null(Criteria<?> criteria, ColumnWrapper column, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.logic = logic;
        this.symbol = Symbol.NULL;
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static Null.Builder create() {
        return new Null.Builder();
    }

    /**
     * 条件对象构建器
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
        private Property<?, ?> lambdaProperty;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 属性
         * @param property 属性
         * @return {@link Null.Builder}
         */
        public Null.Builder property(String property) {
            this.property = property;
            return this;
        }

        /**
         * 属性
         * @param property 属性
         * @param <T>      实体类型
         * @param <V>      属性值类型
         * @return {@link Null.Builder}
         */
        public <T, V> Null.Builder property(Property<T, V> property) {
            this.lambdaProperty = property;
            return this;
        }

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public Null build() {
            if (this.column != null) {
                return new Null(this.criteria, this.column, this.logic);
            }
            if (this.criteria == null) {
                return null;
            }
            if (hasText(this.property)) {
                ColumnWrapper wrapper = this.criteria.searchColumn(this.property);
                if (wrapper != null) {
                    return new Null(this.criteria, wrapper, this.logic);
                }
            }
            if (lambdaProperty != null) {
                ColumnWrapper wrapper = this.criteria.searchColumn(lambdaProperty);
                if (wrapper != null) {
                    return new Null(this.criteria, wrapper, this.logic);
                }
            }
            return null;
        }
    }

}
