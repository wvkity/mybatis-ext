package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.Logic;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.ArgumentUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 自定义模板条件
 * @param <T> 泛型类型
 * @author wvkity
 */
@Accessors( chain = true )
public class Template<T> extends AbstractExpression<T> {

    private static final long serialVersionUID = 178077267075555472L;

    /**
     * 模板
     */
    @Getter
    @Setter
    private String template;

    /**
     * 值
     */
    @Getter
    @Setter
    private Collection<Object> values;

    /**
     * 构造方法
     * @param template 模板
     * @param property 属性
     * @param value    值
     */
    public Template( String template, String property, Object value ) {
        this( template, Logic.AND, property, value );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param template 模板
     * @param property 属性
     * @param value    值
     */
    public Template( Criteria<T> criteria, String template, String property, Object value ) {
        this( criteria, template, Logic.AND, property, value );
    }

    /**
     * 构造方法
     * @param template 模板
     * @param property 属性
     * @param values   值
     */
    public Template( String template, String property, Collection<Object> values ) {
        this( template, Logic.AND, property, values );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param template 模板
     * @param property 属性
     * @param values   值
     */
    public Template( Criteria<T> criteria, String template, String property, Collection<Object> values ) {
        this( criteria, template, Logic.AND, property, values );
    }

    /**
     * 构造方法
     * @param template 模板
     * @param logic    逻辑操作
     * @param property 属性
     * @param value    值
     */
    public Template( String template, Logic logic, String property, Object value ) {
        this.property = property;
        this.value = value;
        this.template = template;
        this.logic = logic;
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param template 模板
     * @param logic    逻辑操作
     * @param property 属性
     * @param value    值
     */
    public Template( Criteria<T> criteria, String template, Logic logic, String property, Object value ) {
        this.criteria = criteria;
        this.property = property;
        this.value = value;
        this.template = template;
        this.logic = logic;
    }

    /**
     * 构造方法
     * @param template 模板
     * @param logic    连接类型
     * @param property 属性
     * @param values   值
     */
    public Template( String template, Logic logic, String property, Collection<Object> values ) {
        this.property = property;
        this.values = values;
        this.template = template;
        this.logic = logic;
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param template 模板
     * @param logic    连接类型
     * @param property 属性
     * @param values   值
     */
    public Template( Criteria<T> criteria, String template, Logic logic, String property, Collection<Object> values ) {
        this.criteria = criteria;
        this.property = property;
        this.values = values;
        this.template = template;
        this.logic = logic;
    }

    @Override
    public String getSqlSegment() {
        if ( StringUtil.hasText( template ) ) {
            StringBuilder buffer = new StringBuilder( 100 );
            ColumnWrapper column = getColumn();
            String realColumnName = Optional.ofNullable( column ).map( ColumnWrapper::getColumn )
                    .orElse( this.property );
            String alias = criteria.getAlias();
            boolean isEnable = criteria.isEnableAlias();
            String columnArg = isEnable ? ( alias + "." + realColumnName ) : realColumnName;
            String valueArg;
            if ( CollectionUtil.hasElement( this.values ) ) {
                valueArg = values.stream()
                        .filter( Objects::nonNull )
                        .map( value -> ArgumentUtil.fill( column, defaultPlaceholder( value ) ) )
                        .collect( Collectors.joining( ", " ) );
            } else {
                valueArg = ArgumentUtil.fill( column, defaultPlaceholder( value ) );
            }
            if ( logic != null && logic != Logic.NORMAL ) {
                buffer.append( logic.getSqlSegment() ).append( " " );
            }
            buffer.append( StringUtil.format( template, columnArg, valueArg ) );
            return buffer.toString();
        }
        return "";
    }

    @Override
    public Template<T> setLogic( Logic logic ) {
        // 针对exactTemplate方法
        if ( this.logic != Logic.NORMAL ) {
            this.logic = logic;
        }
        return this;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof Template ) ) return false;
        if ( !super.equals( o ) ) return false;
        Template<?> template1 = ( Template<?> ) o;
        return Objects.equals( template, template1.template );
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), template );
    }

    @Override
    protected String toJsonString() {
        return toJsonString( "template", template );
    }
}
