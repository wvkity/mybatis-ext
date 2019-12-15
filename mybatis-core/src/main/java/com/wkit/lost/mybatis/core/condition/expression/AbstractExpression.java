package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.ForeignSubCriteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.core.ParamValuePlaceholderConverter;
import com.wkit.lost.mybatis.core.condition.Range;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * 抽象条件类
 * @param <T> 泛型类型
 */
@Accessors( chain = true )
@SuppressWarnings( "serial" )
public abstract class AbstractExpression<T> implements Criterion<T>, ParamValuePlaceholderConverter {

    protected static final String COMMA = ", ";
    protected static final String COLON = ": ";

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

    /**
     * 获取别名
     * @return 别名
     */
    public String getAlias() {
        return StringUtil.nvl( getCriteria().isEnableAlias(), getCriteria().getAlias(), "" );
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
        if ( this.criteria instanceof ForeignSubCriteria ) {
            return ( ( ForeignSubCriteria<?> ) criteria ).getSubCriteria().searchColumn( this.property );
        }
        return this.criteria.searchColumn( this.property );
    }

    @Override
    public String getSqlSegment() {
        String placeholder = StringUtil.nvl( defaultPlaceholder( this.value ), "" );
        Column column = getColumn();
        if ( column == null ) {
            return ColumnConvert.convertToCustomArg( this.property, placeholder, getAlias(), this.operator, getLogic().getSqlSegment() );
        }
        return ColumnConvert.convertToCustomArg( getColumn(), placeholder, getAlias(), this.operator, getLogic().getSqlSegment() );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof AbstractExpression ) ) return false;
        AbstractExpression<?> that = ( AbstractExpression<?> ) o;
        return Objects.equals( criteria, that.criteria ) &&
                Objects.equals( column, that.column ) &&
                Objects.equals( property, that.property ) &&
                Objects.equals( value, that.value ) &&
                range == that.range &&
                operator == that.operator &&
                logic == that.logic;
    }

    @Override
    public int hashCode() {
        return Objects.hash( criteria, column, property, value, range, operator, logic );
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "{" );
        builder.append( toJsonString( "logic", logic.getSqlSegment(), null, COLON ) );
        builder.append( toJsonString( "criteria", getCriteria() ) );
        Column column = getColumn();
        if ( column != null ) {
            builder.append( toJsonString( "column", column.getColumn() ) );
            builder.append( toJsonString( "property", column.getProperty() ) );
        } else {
            builder.append( toJsonString( "column", property ) );
        }
        builder.append( toJsonString( "operator", operator.getSqlSegment() ) );
        builder.append( toJsonString( "value", value ) );
        builder.append( toJsonString( "range", range == null ? null : range.getSqlSegment() ) );
        String stringValue = toJsonString();
        if ( Ascii.hasText( stringValue ) ) {
            builder.append( stringValue );
        }
        builder.append( "}" );
        return builder.toString();
    }

    protected String toJsonString( String property, Object value ) {
        return toJsonString( property, value, COMMA, COLON );
    }

    protected String toJsonString( String property, Object value, String prefix, String suffix ) {
        return formatJson( property, prefix, suffix ) + formatJson( value, null, null );
    }

    protected String formatJson( Object value, String prefix, String suffix ) {
        if ( value != null ) {
            return ( Ascii.hasText( prefix ) ? prefix : "" )
                    + "\"" + value + "\""
                    + ( Ascii.hasText( suffix ) ? suffix : "" );
        }
        return null;
    }

    protected String toJsonString() {
        return "";
    }
}
