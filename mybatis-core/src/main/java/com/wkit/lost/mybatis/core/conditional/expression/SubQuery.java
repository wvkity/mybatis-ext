package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.core.wrapper.criteria.SubCriteria;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 子查询条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class SubQuery<T> extends ColumnExpressionWrapper<T> {

    private static final long serialVersionUID = 6237749348714845224L;

    /**
     * 子查询条件包装对象
     */
    private final SubCriteria<?> subCriteria;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     */
    SubQuery(Criteria<T> criteria, ColumnWrapper column, SubCriteria<?> sc, Symbol symbol, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.subCriteria = sc;
        this.symbol = symbol;
        this.logic = logic;
    }

    @Override
    public String getSegment() {
        if (this.symbol != Symbol.NULL && this.symbol != Symbol.NOT_NULL && this.symbol != Symbol.BETWEEN
                && this.symbol != Symbol.NOT_BETWEEN) {
            StringBuilder builder = new StringBuilder(60);
            if (this.logic != null) {
                builder.append(this.logic.getSegment()).append(Constants.SPACE);
            }
            String realSegment = this.subCriteria.getSegmentForCondition();
            if (this.column != null) {
                String realAlias = getAlias();
                if (StringUtil.hasText(realAlias)) {
                    builder.append(realAlias).append(Constants.DOT);
                }
                builder.append(this.column.getColumn());
            }
            builder.append(Constants.SPACE).append(this.symbol.getSegment()).append(Constants.SPACE);
            builder.append(realSegment);
            return builder.toString();
        }
        return "";
    }

    /**
     * 创建条件构建器
     * @param <T> 实体类型
     * @return 构建器
     */
    public static <T> SubQuery.Builder<T> create() {
        return new SubQuery.Builder<>();
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
         * 子查询条件包装对象
         */
        private SubCriteria<?> sc;
        /**
         * 条件符号
         */
        private Symbol symbol = Symbol.EQ;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 属性
         * @param property 属性
         * @return {@link SubQuery.Builder}
         */
        public SubQuery.Builder<T> property(String property) {
            this.property = property;
            return this;
        }

        /**
         * 属性
         * @param property 属性
         * @param <V>      属性值类型
         * @return {@link SubQuery.Builder}
         */
        public <V> SubQuery.Builder<T> property(Property<T, V> property) {
            this.lambdaProperty = property;
            return this;
        }

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public SubQuery<T> build() {
            if (this.sc == null) {
                return null;
            }
            if (this.column != null || this.symbol == Symbol.EXISTS || this.symbol == Symbol.NOT_EXISTS) {
                return new SubQuery<>(this.criteria, this.column, this.sc, this.symbol, this.logic);
            }
            if (this.criteria == null) {
                return null;
            }
            if (hasText(this.property)) {
                ColumnWrapper wrapper = this.criteria.searchColumn(this.property);
                if (wrapper != null) {
                    return new SubQuery<>(this.criteria, wrapper, this.sc, this.symbol, this.logic);
                }
            }
            if (lambdaProperty != null) {
                ColumnWrapper wrapper = this.criteria.searchColumn(lambdaProperty);
                if (wrapper != null) {
                    return new SubQuery<>(this.criteria, wrapper, this.sc, this.symbol, this.logic);
                }
            }
            return null;
        }
    }
}
