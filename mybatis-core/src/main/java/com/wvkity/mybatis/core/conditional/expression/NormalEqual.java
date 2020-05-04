package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.metadata.PropertyMappingCache;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.Constants;
import com.wvkity.mybatis.utils.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 字段相等条件
 * @author wvkity
 */
public class NormalEqual extends ColumnExpressionWrapper {

    private static final long serialVersionUID = -2314270986996270968L;

    /**
     * 其他条件包装对象
     */
    private final Criteria<?> otherCriteria;

    /**
     * 其他字段包装对象
     */
    private final ColumnWrapper otherColumnWrapper;

    /**
     * 其他表别名
     */
    private final String otherTableAlias;

    /**
     * 其他表字段
     */
    private final String otherColumn;

    /**
     * 构造方法
     * @param criteria        条件包装对象
     * @param column          字段包装对象
     * @param otherCriteria   其他条件包装对象
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他字段
     * @param logic           逻辑符号
     * @param <E>             实体类型
     */
    <E> NormalEqual(Criteria<?> criteria, ColumnWrapper column, Criteria<E> otherCriteria,
                    String otherTableAlias, ColumnWrapper otherColumnWrapper, String otherColumn, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.otherCriteria = otherCriteria;
        this.otherTableAlias = otherTableAlias;
        this.otherColumnWrapper = otherColumnWrapper;
        this.otherColumn = otherColumn;
        this.symbol = Symbol.EQ;
        this.logic = logic;
    }

    @Override
    public String getSegment() {
        StringBuilder builder = new StringBuilder(60);
        String alias = getAlias();
        String otherAlias = hasText(this.otherTableAlias) ? this.otherTableAlias :
                this.otherCriteria != null && this.otherCriteria.isEnableAlias() ? this.otherCriteria.as() : "";
        builder.append(this.logic.getSegment()).append(Constants.SPACE);
        if (StringUtil.hasText(alias)) {
            builder.append(alias.trim()).append(Constants.DOT).append(this.column.getColumn());
        } else {
            builder.append(this.column.getColumn());
        }
        builder.append(Constants.SPACE).append(this.symbol.getSegment()).append(Constants.SPACE);
        if (hasText(otherAlias)) {
            builder.append(otherAlias.trim()).append(Constants.DOT);
        }
        if (this.otherColumnWrapper != null) {
            builder.append(this.otherColumnWrapper.getColumn());
        } else if (hasText(this.otherColumn)) {
            builder.append(this.otherColumn);
        }
        return builder.toString();
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static NormalEqual.Builder create() {
        return new NormalEqual.Builder();
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
         * 其他条件包装对象
         */
        private Criteria<?> otherCriteria;
        /**
         * 其他字段包装对象
         */
        @Setter(AccessLevel.NONE)
        private ColumnWrapper otherColumnWrapper;
        /**
         * 其他属性
         */
        @Setter(AccessLevel.NONE)
        private String otherProperty;
        /**
         * 其他属性
         */
        @Setter(AccessLevel.NONE)
        private Property<?, ?> otherLambdaProperty;
        /**
         * 其他表别名
         */
        private String otherAlias;
        /**
         * 其他表字段
         */
        @Setter(AccessLevel.NONE)
        private String otherColumn;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 属性
         * @param property 属性
         * @return {@link NormalEqual.Builder}
         */
        public NormalEqual.Builder property(String property) {
            this.property = property;
            return this;
        }

        /**
         * 属性
         * @param property 属性
         * @param <T>      实体类型
         * @param <V>      属性值类型
         * @return {@link NormalEqual.Builder}
         */
        public <T, V> NormalEqual.Builder property(Property<T, V> property) {
            this.lambdaProperty = property;
            return this;
        }

        /**
         * 其他属性
         * @param property 属性
         * @return {@link NormalEqual.Builder}
         */
        public NormalEqual.Builder otherProperty(String property) {
            this.otherProperty = property;
            return this;
        }

        /**
         * 其他属性
         * @param property 属性
         * @param <E>      实体类型
         * @param <V>      属性值类型
         * @return {@link NormalEqual.Builder}
         */
        public <E, V> NormalEqual.Builder otherProperty(Property<E, V> property) {
            this.otherLambdaProperty = property;
            return this;
        }

        /**
         * 其他表字段包装对象
         * @param column 表字段包装对象
         * @return {@link NormalEqual.Builder}
         */
        public NormalEqual.Builder otherColumn(ColumnWrapper column) {
            this.otherColumnWrapper = column;
            return this;
        }

        /**
         * 其他表字段
         * @param column 表字段
         * @return {@link NormalEqual.Builder}
         */
        public NormalEqual.Builder otherColumn(String column) {
            this.otherColumn = column;
            return this;
        }

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public NormalEqual build() {
            if (this.column != null) {
                if (this.otherColumnWrapper != null) {
                    return new NormalEqual(this.criteria, this.column, this.otherCriteria,
                            this.otherAlias, this.otherColumnWrapper, this.otherColumn, this.logic);
                }
            }
            if (this.criteria == null) {
                return null;
            }
            ColumnWrapper wrapper = null;
            if (this.column != null) {
                wrapper = this.column;
            } else {
                if (hasText(this.property)) {
                    wrapper = this.criteria.searchColumn(this.property);
                }
                if (wrapper == null && lambdaProperty != null) {
                    wrapper = this.criteria.searchColumn(lambdaProperty);
                }
            }
            if (wrapper != null) {
                if (this.otherColumnWrapper != null) {
                    return new NormalEqual(this.criteria, wrapper, this.otherCriteria,
                            this.otherAlias, this.otherColumnWrapper, this.otherColumn, this.logic);
                } else {
                    ColumnWrapper otherWrapper = null;
                    if (this.otherCriteria != null) {
                        if (hasText(this.otherProperty)) {
                            otherWrapper = this.otherCriteria.searchColumn(this.otherProperty);
                        }
                        if (otherWrapper == null && this.otherLambdaProperty != null) {
                            otherWrapper = this.otherCriteria.searchColumn(
                                    PropertyMappingCache.lambdaToProperty(this.otherLambdaProperty));
                        }
                    }
                    if (otherWrapper != null) {
                        return new NormalEqual(this.criteria, wrapper, this.otherCriteria,
                                this.otherAlias, otherWrapper, this.otherColumn, this.logic);
                    } else if (hasText(this.otherColumn)) {
                        return new NormalEqual(this.criteria, wrapper, this.otherCriteria,
                                this.otherAlias, this.otherColumnWrapper, this.otherColumn, this.logic);
                    }
                }
            }
            return null;
        }
    }
}
