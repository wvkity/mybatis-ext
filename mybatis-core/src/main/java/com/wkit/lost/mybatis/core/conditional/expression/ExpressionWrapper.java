package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.PlaceholderConverter;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"serial"})
public abstract class ExpressionWrapper<E> implements Criterion, PlaceholderConverter {

    /**
     * Criteria对象
     */
    protected Criteria<?> criteria;

    /**
     * 表别名
     */
    @Getter
    protected String tableAlias;

    /**
     * 字段映射对象
     */
    @Getter
    protected E column;

    /**
     * 值
     */
    @Getter
    protected Object value;

    /**
     * 操作符
     */
    @Getter
    protected Symbol symbol = Symbol.EQ;

    /**
     * 逻辑操作
     */
    @Getter
    protected Logic logic = Logic.AND;

    @Override
    public ArrayList<String> placeholders(String template, Collection<Object> values) {
        return getCriteria().placeholders(template, values);
    }

    @Override
    public String placeholder(boolean format, String template, Object... values) {
        return getCriteria().placeholder(format, template, values);
    }

    @Override
    public Criteria<?> getCriteria() {
        return this.criteria;
    }

    @Override
    public ExpressionWrapper<E> criteria(Criteria<?> criteria) {
        this.criteria = criteria;
        return this;
    }

    @Override
    public ExpressionWrapper<?> logic(Logic logic) {
        this.logic = logic;
        return this;
    }

    /**
     * 获取表别名
     * @return 表别名
     */
    public String getAlias() {
        return StringUtil.hasText(this.tableAlias) ? this.tableAlias :
                (this.criteria != null ? this.criteria.as() : "");
    }

    /**
     * 检查字符串是否为空
     * @param value 待检查字符串
     * @return boolean
     */
    public static boolean isEmpty(String value) {
        return StringUtil.isBlank(value);
    }

    /**
     * 检查字符串是否不为空
     * @param value 待检查字符串
     * @return boolean
     */
    public static boolean hasText(String value) {
        return StringUtil.hasText(value);
    }

    /**
     * 获取主键字段包装对象
     * @param klass 实体类
     * @return 主键字段包装对象
     */
    static ColumnWrapper loadIdColumn(Class<?> klass) {
        return TableHandler.getPrimaryKey(klass);
    }
}
