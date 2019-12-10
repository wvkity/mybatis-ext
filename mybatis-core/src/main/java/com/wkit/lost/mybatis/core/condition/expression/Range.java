package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.utils.ArgumentUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 范围条件
 * @param <T> 泛型类型
 * @author wvkity
 */
public class Range<T> extends AbstractExpression<T> {

    private static final long serialVersionUID = 6335118821506506434L;

    /**
     * 值
     */
    @Getter
    @Setter
    protected Collection<Object> values;

    /**
     * 构造方法
     * @param range    范围类型
     * @param property 属性
     * @param values   值
     */
    public Range( com.wkit.lost.mybatis.core.condition.Range range, String property, Collection<Object> values ) {
        this( range, property, values, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param range    范围类型
     * @param property 属性
     * @param values   值
     */
    public Range( Criteria<T> criteria, com.wkit.lost.mybatis.core.condition.Range range, String property, Collection<Object> values ) {
        this( criteria, range, property, values, Logic.AND );
    }

    /**
     * 构造方法
     * @param range    范围类型
     * @param property 属性
     * @param values   值
     * @param logic    逻辑操作
     */
    public Range( com.wkit.lost.mybatis.core.condition.Range range, String property, Collection<Object> values, Logic logic ) {
        this.range = range;
        this.property = property;
        this.values = values;
        this.logic = logic;
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param range    范围类型
     * @param property 属性
     * @param values   值
     * @param logic    逻辑操作
     */
    public Range( Criteria<T> criteria, com.wkit.lost.mybatis.core.condition.Range range, String property, Collection<Object> values, Logic logic ) {
        this.criteria = criteria;
        this.range = range;
        this.property = property;
        this.values = values;
        this.logic = logic;
    }

    @Override
    public String getSqlSegment() {
        if ( CollectionUtil.hasElement( this.values ) ) {
            StringBuilder buffer = new StringBuilder( 100 );
            Column column = getColumn();
            String alias = criteria.getAlias();
            boolean isEnable = criteria.isEnableAlias();
            buffer.append( logic.getSqlSegment() ).append( " " );
            if ( isEnable ) {
                buffer.append( alias ).append( "." );
            }
            buffer.append( Optional.ofNullable( column ).map( Column::getColumn ).orElse( this.property ) )
                    .append( " " ).append( range.getSqlSegment() ).append( " " );
            String valuePlaceholder = values.stream().filter( Objects::nonNull )
                    .map( value -> ArgumentUtil.fill( column, defaultPlaceholder( value ) ) )
                    .collect( Collectors.joining( ", ", "(", ")" ) );
            buffer.append( valuePlaceholder );
            return buffer.toString();
        }
        return "";
    }
}
