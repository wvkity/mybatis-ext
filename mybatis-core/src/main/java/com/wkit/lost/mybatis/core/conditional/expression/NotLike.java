package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Match;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * NOT LIKE模糊匹配
 * @author wvkity
 */
public class NotLike extends AbstractFuzzyExpression {

    private static final long serialVersionUID = 6927519566015548400L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     */
    NotLike(Criteria<?> criteria, ColumnWrapper column, String value, Match match, Character escape, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.match = match;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static NotLike.Builder create() {
        return new NotLike.Builder();
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
         * 值
         */
        private String value;
        /**
         * 匹配模式
         */
        private Match match = Match.ANYWHERE;
        /**
         * 转义字符
         */
        private Character escape;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 属性
         * @param property 属性
         * @return {@link NotLike.Builder}
         */
        public NotLike.Builder property(String property) {
            this.property = property;
            return this;
        }

        /**
         * 属性
         * @param property 属性
         * @param <T>      实体类型
         * @param <V>      属性值类型
         * @return {@link NotLike.Builder}
         */
        public <T, V> NotLike.Builder property(Property<T, V> property) {
            this.lambdaProperty = property;
            return this;
        }

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public NotLike build() {
            if (this.value == null) {
                return null;
            }
            if (this.column != null) {
                return new NotLike(this.criteria, this.column, this.value, this.match, this.escape, this.logic);
            }
            if (this.criteria == null) {
                return null;
            }
            if (hasText(this.property)) {
                ColumnWrapper wrapper = this.criteria.searchColumn(this.property);
                if (wrapper != null) {
                    return new NotLike(this.criteria, wrapper, this.value, this.match, this.escape, this.logic);
                }
            }
            if (lambdaProperty != null) {
                ColumnWrapper wrapper = this.criteria.searchColumn(lambdaProperty);
                if (wrapper != null) {
                    return new NotLike(this.criteria, wrapper, this.value, this.match, this.escape, this.logic);
                }
            }
            return null;
        }
    }
}
