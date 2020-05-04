package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * BETWEEN范围条件
 * @author wvkity
 */
public class NotBetween extends AbstractBetweenExpression {

    private static final long serialVersionUID = 1476232138618457610L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     */
    NotBetween(Criteria<?> criteria, ColumnWrapper column, Object begin, Object end, Logic logic) {
        this.criteria = criteria;
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
    public static NotBetween.Builder create() {
        return new NotBetween.Builder();
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
         * 属性
         * @param property 属性
         * @return {@link NotBetween.Builder}
         */
        public NotBetween.Builder property(String property) {
            this.property = property;
            return this;
        }

        /**
         * 属性
         * @param property 属性
         * @param <T>      实体类型
         * @param <V>      属性值类型
         * @return {@link NotBetween.Builder}
         */
        public <T, V> NotBetween.Builder property(Property<T, V> property) {
            this.lambdaProperty = property;
            return this;
        }

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public NotBetween build() {
            if (this.begin == null && this.end == null) {
                return null;
            }
            if (this.column != null) {
                return new NotBetween(this.criteria, this.column, this.begin, this.end, this.logic);
            }
            if (this.criteria == null) {
                return null;
            }
            if (hasText(this.property)) {
                ColumnWrapper wrapper = this.criteria.searchColumn(this.property);
                if (wrapper != null) {
                    return new NotBetween(this.criteria, wrapper, this.begin, this.end, this.logic);
                }
            }
            if (lambdaProperty != null) {
                ColumnWrapper wrapper = this.criteria.searchColumn(lambdaProperty);
                if (wrapper != null) {
                    return new NotBetween(this.criteria, wrapper, this.begin, this.end, this.logic);
                }
            }
            return null;
        }
    }
}
