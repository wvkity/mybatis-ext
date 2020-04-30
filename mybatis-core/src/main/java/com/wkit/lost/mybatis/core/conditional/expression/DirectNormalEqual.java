package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.PropertyMappingCache;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 字段相等条件
 * @author wvkity
 */
public class DirectNormalEqual extends DirectExpressionWrapper {

    private static final long serialVersionUID = 4647292574240862335L;

    /**
     * 其他条件包装对象
     */
    private final Criteria<?> otherCriteria;

    /**
     * 其他条件包装对象
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
     * @param criteria           条件包装对象
     * @param tableAlias         表别名
     * @param column             字段
     * @param otherCriteria      其他条件包装对象
     * @param otherTableAlias    其他表别名
     * @param otherColumnWrapper 其他字段包装对象
     * @param otherColumn        其他字段
     * @param logic              逻辑符号
     */
    <E> DirectNormalEqual(Criteria<?> criteria, String tableAlias, String column, Criteria<E> otherCriteria,
                          String otherTableAlias, ColumnWrapper otherColumnWrapper, String otherColumn, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.otherCriteria = otherCriteria;
        this.otherTableAlias = otherTableAlias;
        this.otherColumn = otherColumn;
        this.otherColumnWrapper = otherColumnWrapper;
        this.logic = logic;
        this.symbol = Symbol.EQ;
    }

    @Override
    public String getSegment() {
        StringBuilder builder = new StringBuilder();
        String alias = getAlias();
        String otherAlias = hasText(this.otherTableAlias) ? this.otherTableAlias :
                this.otherCriteria != null && this.otherCriteria.isEnableAlias() ? this.otherCriteria.as() : "";
        builder.append(this.logic.getSegment()).append(Constants.SPACE);
        if (StringUtil.hasText(alias)) {
            builder.append(alias.trim()).append(Constants.DOT).append(this.column);
        } else {
            builder.append(this.column);
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
     * 创建构建器
     * @return 构建器
     */
    public static DirectNormalEqual.Builder create() {
        return new DirectNormalEqual.Builder();
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
        protected Criteria<?> criteria;
        /**
         * 其他包装对象
         */
        private Criteria<?> otherCriteria;
        /**
         * 表别名
         */
        private String alias;
        /**
         * 字段名
         */
        private String column;
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
         * 其他属性
         * @param property 属性
         * @return {@link DirectNormalEqual.Builder}
         */
        public DirectNormalEqual.Builder otherProperty(String property) {
            this.otherProperty = property;
            return this;
        }

        /**
         * 其他属性
         * @param property 属性
         * @param <E>      实体类型
         * @param <V>      属性值类型
         * @return {@link DirectNormalEqual.Builder}
         */
        public <E, V> DirectNormalEqual.Builder otherProperty(Property<E, V> property) {
            this.otherLambdaProperty = property;
            return this;
        }

        /**
         * 其他表字段包装对象
         * @param column 表字段包装对象
         * @return {@link DirectNormalEqual.Builder}
         */
        public DirectNormalEqual.Builder otherColumn(ColumnWrapper column) {
            this.otherColumnWrapper = column;
            return this;
        }

        /**
         * 其他表字段
         * @param column 表字段
         * @return {@link DirectNormalEqual.Builder}
         */
        public DirectNormalEqual.Builder otherColumn(String column) {
            this.otherColumn = column;
            return this;
        }

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public DirectNormalEqual build() {
            if (hasText(this.column)) {
                if (this.otherColumnWrapper != null) {
                    return new DirectNormalEqual(this.criteria, this.alias, this.column, this.otherCriteria,
                            this.otherAlias, this.otherColumnWrapper, this.otherColumn, this.logic);
                } else {
                    if (this.otherCriteria != null) {
                        ColumnWrapper wrapper = null;
                        if (hasText(this.otherProperty)) {
                            wrapper = this.otherCriteria.searchColumn(this.otherProperty);
                        }
                        if (wrapper == null && this.otherLambdaProperty != null) {
                            wrapper = this.otherCriteria.searchColumn(
                                    PropertyMappingCache.lambdaToProperty(this.otherLambdaProperty));
                        }
                        if (wrapper != null) {
                            return new DirectNormalEqual(this.criteria, this.alias, this.column, this.otherCriteria,
                                    this.otherAlias, wrapper, this.otherColumn, this.logic);
                        }
                    }
                    if (hasText(this.otherColumn)) {
                        return new DirectNormalEqual(this.criteria, this.alias, this.column, this.otherCriteria,
                                this.otherAlias, this.otherColumnWrapper, this.otherColumn, this.logic);
                    }
                }
            }
            return null;
        }
    }
}
