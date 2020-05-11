package com.wvkity.mybatis.core.wrapper.aggreate;

import com.wvkity.mybatis.core.conditional.utils.PlaceholderParser;
import com.wvkity.mybatis.core.constant.AggregateType;
import com.wvkity.mybatis.core.constant.Comparator;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.wrapper.basic.Case;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.exception.MyBatisException;
import com.wvkity.mybatis.utils.Constants;
import com.wvkity.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@SuppressWarnings("serial")
public abstract class AbstractFunction<E> implements Function {

    /**
     * 条件包装对象
     */
    @Getter
    protected Criteria<?> criteria;

    /**
     * 聚合函数类型
     */
    protected AggregateType type;

    /**
     * 字段
     */
    protected E column;

    /**
     * 是否去重
     */
    protected boolean distinct = false;

    /**
     * 保留小数位数
     */
    protected Integer scale;

    /**
     * 表别名
     */
    protected String tableAlias;

    /**
     * 别名
     */
    protected String alias;

    /**
     * 值
     */
    protected Object value;

    /**
     * 最小值
     */
    protected Object minValue;

    /**
     * 最大值
     */
    protected Object maxValue;

    /**
     * 比较运算符
     */
    protected Comparator comparator = Comparator.EQ;

    /**
     * 逻辑符号
     */
    protected Logic logic = Logic.AND;

    /**
     * CASE选择
     */
    protected Case _case;

    /**
     * 是否存在保留小数位数
     * @return boolean
     */
    protected boolean hasScale() {
        return this.scale != null && this.scale > -1;
    }

    /**
     * 表别名
     * @return 表别名
     */
    protected String as() {
        if (StringUtil.hasText(this.tableAlias)) {
            return this.tableAlias.trim() + Constants.DOT;
        } else {
            if (this.criteria != null && this.criteria.isEnableAlias()) {
                return criteria.as().trim() + Constants.DOT;
            }
        }
        return Constants.EMPTY;
    }

    /**
     * 设置聚合函数别名
     * @param alias 聚合函数别名
     * @return {@code this}
     */
    public AbstractFunction<E> as(final String alias) {
        this.alias = alias;
        return this;
    }

    /**
     * 设置比较运算符
     * @param comparator 运算符
     * @return {@code this}
     */
    public AbstractFunction<E> comparator(Comparator comparator) {
        this.comparator = comparator;
        return this;
    }

    /**
     * 设置小数点位数
     * @param scale 小数点位数
     * @return {@code this}
     */
    public AbstractFunction<E> scale(Integer scale) {
        this.scale = scale;
        return this;
    }

    /**
     * 设置值
     * @param value 值
     * @return {@code this}
     */
    public AbstractFunction<E> value(Object value) {
        this.value = value;
        return this;
    }

    /**
     * 设置最小值
     * @param value 值
     * @return {@code this}
     */
    public AbstractFunction<E> min(Object value) {
        this.minValue = value;
        return this;
    }

    /**
     * 设置最大值
     * @param value 值
     * @return {@code this}
     */
    public AbstractFunction<E> max(Object value) {
        this.maxValue = value;
        return this;
    }

    /**
     * 设置是否去重
     * @param distinct 是否去重
     * @return {@code this}
     */
    public AbstractFunction<E> distinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    /**
     * CASE选择
     * @param _case {@link Case}
     * @return {@code this}
     */
    public AbstractFunction<E> _case(Case _case) {
        this._case = _case;
        return this;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getSegment() {
        return getSegment(false);
    }

    @Override
    public String getQuerySegment() {
        return getSegment(true);
    }

    @Override
    public String getOrderSegment() {
        return getFunctionBody();
    }

    /**
     * 获取SQL片段
     * @param isQuery 是否应用于查询
     * @return SQL片段
     */
    protected String getSegment(boolean isQuery) {
        StringBuilder builder = new StringBuilder();
        if (!isQuery) {
            if (this.logic == null) {
                builder.append(Logic.AND.getSegment());
            } else {
                builder.append(this.logic.getSegment());
            }
            builder.append(Constants.SPACE);
        }
        String functionBody = getFunctionBody();
        if (isQuery) {
            builder.append(functionBody);
            if (StringUtil.hasText(this.alias)) {
                builder.append(Constants.SPACE);
                if (this.alias.contains(Constants.DOT)) {
                    builder.append(" AS ").append(Constants.CHAR_QUOTE).append(this.alias).append(Constants.CHAR_QUOTE);
                } else {
                    builder.append(" AS ").append(this.alias);
                }
            }
        } else {
            // HAVING条件
            if (this.criteria == null) {
                throw new MyBatisException("The criteria conditional wrapper attribute cannot be null.");
            }
            if (this.comparator == null) {
                throw new MyBatisException("The comparison operator property cannot be null.");
            }
            if (this.comparator.isSimple()) {
                if (this.value == null) {
                    throw new MyBatisException("The value attribute cannot be null.");
                }
                builder.append(functionBody).append(Constants.SPACE).append(this.comparator.getSegment());
                builder.append(Constants.SPACE).append("#{").append(this.criteria.defaultPlaceholder(this.value));
                builder.append("}");
            } else {
                if (this.minValue == null || this.maxValue == null) {
                    throw new MyBatisException("The value of the minValue attribute and the value of the " +
                            "maxValue attribute cannot be null.");
                }
                String script = this.comparator.getSegment().replaceAll(":@", functionBody);
                String firstArg = "#{" + this.criteria.defaultPlaceholder(this.minValue) + "}";
                String lastArg = "#{" + this.criteria.defaultPlaceholder(this.maxValue) + "}";
                builder.append(Constants.SPACE).append(PlaceholderParser.format(script, firstArg, lastArg));
            }
        }
        return builder.toString();
    }

    /**
     * 获取聚合函数主要部分
     * @return 聚合函数主要部分
     */
    protected abstract String getFunctionBody();
}
