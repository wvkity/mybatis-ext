package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.core.ParamValuePlaceholderConverter;
import com.wkit.lost.mybatis.core.condition.Range;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 抽象条件类
 * @param <T> 泛型类型
 */
@Accessors( chain = true )
@SuppressWarnings( "serial" )
public abstract class AbstractExpression<T> implements Criterion<T>, ParamValuePlaceholderConverter {

    /**
     * Criteria对象
     */
    @Getter
    protected Criteria<T> criteria;

    /**
     * 字段映射对象
     */
    protected Column column;

    /**
     * 属性
     */
    @Getter
    protected String property;

    /**
     * 值
     */
    @Getter
    @Setter
    protected Object value;

    /**
     * 范围
     */
    @Getter
    protected Range range;

    /**
     * 操作符
     */
    @Getter
    protected Operator operator;

    /**
     * 逻辑操作
     */
    @Getter
    @Setter
    protected Logic logic;

    /**
     * 是否为NOT
     */
    @Getter
    protected boolean not = false;

    @SuppressWarnings( "unchecked" )
    @Override
    public Criterion<T> setCriteria( Criteria<?> criteria ) {
        this.criteria = ( Criteria<T> ) criteria;
        return this;
    }

    @Override
    public ArrayList<String> placeholders( String template, Collection<Object> values ) {
        return getCriteria().placeholders( template, values );
    }

    @Override
    public String placeholder( boolean need, String template, Object... values ) {
        return getCriteria().placeholder( need, template, values );
    }

    @Override
    public Column getColumn() {
        return this.criteria.searchColumn( this.property );
    }

    @Override
    public String getSqlSegment() {
        return getColumn().convertToCustomArg( StringUtil.nvl( defaultPlaceholder( this.value ), "" ), StringUtil.nvl( this.criteria.isEnableAlias(), this.criteria.getAlias(), "" ), this.operator, getLogic().getSqlSegment() );
    }

}
