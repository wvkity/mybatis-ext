package com.wvkity.mybatis.core.wrapper.aggreate;

import com.wvkity.mybatis.core.constant.AggregateType;
import com.wvkity.mybatis.core.constant.Comparator;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.wrapper.basic.Case;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.StringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 聚合函数构建器
 * @author wvkity
 */
@Data
@Accessors(chain = true, fluent = true)
public final class FunctionBuilder {

    private FunctionBuilder() {
    }

    /**
     * 条件包装对象
     */
    private Criteria<?> criteria;

    /**
     * 聚合函数类型
     */
    private AggregateType type;

    /**
     * 字段
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
     * 是否去重
     */
    private boolean distinct = false;

    /**
     * 保留小数位数
     */
    private Integer scale;

    /**
     * 别名
     */
    private String alias;

    /**
     * 值
     */
    private Object value;

    /**
     * 最小值
     */
    private Object minValue;

    /**
     * 最大值
     */
    private Object maxValue;

    /**
     * CASE选择
     */
    private Case _case;

    /**
     * 比较运算符
     */
    private Comparator comparator = Comparator.EQ;

    /**
     * 逻辑符号
     */
    protected Logic logic = Logic.AND;

    /**
     * 设置属性
     * @param property 属性
     * @return {@code this}
     */
    public FunctionBuilder property(final String property) {
        this.property = property;
        return this;
    }

    /**
     * 设置属性
     * @param property 属性
     * @return {@code this}
     */
    public <T, V> FunctionBuilder property(final Property<T, V> property) {
        this.lambdaProperty = property;
        return this;
    }

    /**
     * 创建COUNT聚合函数对象
     * @return {@link Count}
     */
    public AbstractColumnFunction count() {
        return build(AggregateType.COUNT);
    }

    /**
     * 创建SUM聚合函数对象
     * @return {@link Sum}
     */
    public AbstractColumnFunction sum() {
        return build(AggregateType.SUM);
    }

    /**
     * 创建AVG聚合函数对象
     * @return {@link Avg}
     */
    public AbstractColumnFunction avg() {
        return build(AggregateType.AVG);
    }

    /**
     * 创建MIN聚合函数对象
     * @return {@link Min}
     */
    public AbstractColumnFunction min() {
        return build(AggregateType.MIN);
    }

    /**
     * 创建MAX聚合函数对象
     * @return {@link Max}
     */
    public AbstractColumnFunction max() {
        return build(AggregateType.MAX);
    }

    /**
     * 构建聚合函数
     * @param type 聚合函数类型
     * @return 聚合函数对象
     */
    private AbstractColumnFunction build(AggregateType type) {
        if (this._case == null && this.column == null) {
            if (hasText(this.property)) {
                ColumnWrapper wrapper = this.criteria.searchColumn(this.property);
                if (wrapper != null) {
                    this.column = wrapper;
                }
            }
            if (this.column == null && this.lambdaProperty != null) {
                ColumnWrapper wrapper = this.criteria.searchColumn(this.lambdaProperty);
                if (wrapper != null) {
                    this.column = wrapper;
                }
            }
        }

        if (this._case != null || this.column != null) {
            switch (type) {
                case COUNT:
                    return new Count(this.criteria, this.column, this.distinct, this.scale, this.alias,
                            this._case, this.value, this.minValue, this.maxValue, this.comparator, this.logic);
                case SUM:
                    return new Sum(this.criteria, this.column, this.distinct, this.scale, this.alias,
                            this._case, this.value, this.minValue, this.maxValue, this.comparator, this.logic);
                case AVG:
                    return new Avg(this.criteria, this.column, this.distinct, this.scale, this.alias,
                            this._case, this.value, this.minValue, this.maxValue, this.comparator, this.logic);
                case MIN:
                    return new Min(this.criteria, this.column, this.distinct, this.scale, this.alias,
                            this._case, this.value, this.minValue, this.maxValue, this.comparator, this.logic);
                case MAX:
                    return new Max(this.criteria, this.column, this.distinct, this.scale, this.alias,
                            this._case, this.value, this.minValue, this.maxValue, this.comparator, this.logic);
                default:
                    return null;
            }
        }
        return null;
    }

    /**
     * 检查字符串是否不为空
     * @param value 待检查字符串
     * @return boolean
     */
    public static boolean hasText(final String value) {
        return StringUtil.hasText(value);
    }

    /**
     * 创建构建器
     * @return 构建器
     */
    public static FunctionBuilder create() {
        return new FunctionBuilder();
    }
}
