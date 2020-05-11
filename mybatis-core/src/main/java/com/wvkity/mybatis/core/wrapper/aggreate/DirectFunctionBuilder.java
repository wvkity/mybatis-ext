package com.wvkity.mybatis.core.wrapper.aggreate;

import com.wvkity.mybatis.core.constant.AggregateType;
import com.wvkity.mybatis.core.constant.Comparator;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.wrapper.basic.Case;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.StringUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 聚合函数构建器
 * @author wvkity
 */
@Data
@Accessors(chain = true, fluent = true)
public final class DirectFunctionBuilder {

    /**
     * 构造方法
     */
    private DirectFunctionBuilder() {
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
     * 表别名
     */
    private String tableAlias;

    /**
     * 字段
     */
    private String column;

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
     * 创建COUNT聚合函数对象
     * @return {@link Count}
     */
    public AbstractDirectFunction count() {
        return build(AggregateType.COUNT);
    }

    /**
     * 创建SUM聚合函数对象
     * @return {@link Sum}
     */
    public AbstractDirectFunction sum() {
        return build(AggregateType.SUM);
    }

    /**
     * 创建AVG聚合函数对象
     * @return {@link Avg}
     */
    public AbstractDirectFunction avg() {
        return build(AggregateType.AVG);
    }

    /**
     * 创建MIN聚合函数对象
     * @return {@link Min}
     */
    public AbstractDirectFunction min() {
        return build(AggregateType.MIN);
    }

    /**
     * 创建MAX聚合函数对象
     * @return {@link Max}
     */
    public AbstractDirectFunction max() {
        return build(AggregateType.MAX);
    }

    /**
     * 构建聚合函数
     * @param type 聚合函数类型
     * @return 聚合函数对象
     */
    private AbstractDirectFunction build(AggregateType type) {
        if (this._case != null || hasText(this.column)) {
            switch (type) {
                case COUNT:
                    return new DirectCount(this.criteria, this.tableAlias, this.column, this.distinct,
                            this.scale, this.alias, this._case, this.value, this.minValue, this.maxValue,
                            this.comparator, this.logic);
                case SUM:
                    return new DirectSum(this.criteria, this.tableAlias, this.column, this.distinct,
                            this.scale, this.alias, this._case, this.value, this.minValue, this.maxValue,
                            this.comparator, this.logic);
                case AVG:
                    return new DirectAvg(this.criteria, this.tableAlias, this.column, this.distinct,
                            this.scale, this.alias, this._case, this.value, this.minValue, this.maxValue,
                            this.comparator, this.logic);
                case MIN:
                    return new DirectMin(this.criteria, this.tableAlias, this.column, this.distinct,
                            this.scale, this.alias, this._case, this.value, this.minValue, this.maxValue,
                            this.comparator, this.logic);
                case MAX:
                    return new DirectMax(this.criteria, this.tableAlias, this.column, this.distinct,
                            this.scale, this.alias, this._case, this.value, this.minValue, this.maxValue,
                            this.comparator, this.logic);
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
    public static DirectFunctionBuilder create() {
        return new DirectFunctionBuilder();
    }
}
