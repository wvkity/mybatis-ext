package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.core.SubCriteria;
import com.wkit.lost.mybatis.core.metadata.Column;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;

/**
 * 子查询条件
 * @param <T> 泛型类型
 * @author wvkity
 */
public class SubQuery<T> extends AbstractExpression<T> {

    private static final long serialVersionUID = -1824405347671875372L;

    /**
     * 子查询对象
     */
    @Getter
    protected SubCriteria<?> subCriteria;

    /**
     * 构造方法
     * @param subCriteria 子查询对象
     */
    public SubQuery( SubCriteria<?> subCriteria ) {
        this( null, subCriteria, Operator.EQ, Logic.AND );
    }

    /**
     * 构造方法
     * @param property    属性
     * @param subCriteria 子查询对象
     */
    public SubQuery( String property, SubCriteria<?> subCriteria ) {
        this( property, subCriteria, Operator.EQ, Logic.AND );
    }

    /**
     * 构造方法
     * @param subCriteria 子查询对象
     * @param logic       逻辑操作
     */
    public SubQuery( SubCriteria<?> subCriteria, Logic logic ) {
        this( null, subCriteria, Operator.EQ, logic );
    }

    /**
     * 构造方法
     * @param property    属性
     * @param subCriteria 子查询对象
     * @param logic       逻辑操作
     */
    public SubQuery( String property, SubCriteria<?> subCriteria, Logic logic ) {
        this( property, subCriteria, Operator.EQ, logic );
    }

    /**
     * 构造方法
     * @param property    属性
     * @param subCriteria 子查询对象
     * @param operator    操作类型
     */
    public SubQuery( String property, SubCriteria<?> subCriteria, Operator operator ) {
        this( property, subCriteria, operator, Logic.AND );
    }

    /**
     * 构造方法
     * @param property    属性
     * @param subCriteria 子查询对象
     * @param operator    操作类型
     * @param logic       逻辑操作
     */
    public SubQuery( String property, SubCriteria<?> subCriteria, Operator operator, Logic logic ) {
        this.operator = operator;
        this.logic = logic;
        this.property = property;
        this.subCriteria = subCriteria;
    }

    /**
     * 构造方法
     * @param criteria    查询对象
     * @param subCriteria 子查询对象
     */
    public SubQuery( Criteria<T> criteria, SubCriteria<?> subCriteria ) {
        this( criteria, null, subCriteria, Operator.EQ, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria    查询对象
     * @param property    属性
     * @param subCriteria 子查询对象
     */
    public SubQuery( Criteria<T> criteria, String property, SubCriteria<?> subCriteria ) {
        this( criteria, property, subCriteria, Operator.EQ, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria    查询对象
     * @param subCriteria 子查询对象
     * @param logic       逻辑操作
     */
    public SubQuery( Criteria<T> criteria, SubCriteria<?> subCriteria, Logic logic ) {
        this( criteria, null, subCriteria, Operator.EQ, logic );
    }

    /**
     * 构造方法
     * @param criteria    查询对象
     * @param property    属性
     * @param subCriteria 子查询对象
     * @param logic       逻辑操作
     */
    public SubQuery( Criteria<T> criteria, String property, SubCriteria<?> subCriteria, Logic logic ) {
        this( criteria, property, subCriteria, Operator.EQ, logic );
    }

    /**
     * 构造方法
     * @param criteria    查询对象
     * @param property    属性
     * @param subCriteria 子查询对象
     * @param operator    操作类型
     */
    public SubQuery( Criteria<T> criteria, String property, SubCriteria<?> subCriteria, Operator operator ) {
        this( criteria, property, subCriteria, operator, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria    查询对象
     * @param property    属性
     * @param subCriteria 子查询对象
     * @param operator    操作类型
     * @param logic       逻辑操作
     */
    public SubQuery( Criteria<T> criteria, String property, SubCriteria<?> subCriteria, Operator operator, Logic logic ) {
        this.operator = operator;
        this.logic = logic;
        this.property = property;
        this.subCriteria = subCriteria;
        this.setCriteria( criteria );
    }

    @Override
    public String getSqlSegment() {
        Column column;
        if ( StringUtil.isBlank( property ) ) {
            if ( operator != Operator.EXISTS && operator != Operator.NOT_EXISTS ) {
                column = EntityHandler.getTable( criteria.getEntityClass() ).getPrimaryKey();
            } else {
                column = null;
            }
        } else {
            column = getColumn();
        }
        if ( column != null || ( operator == Operator.EXISTS || operator == Operator.NOT_EXISTS ) ) {
            StringBuilder buffer = new StringBuilder( 100 );
            buffer.append( " " ).append( logic.getSqlSegment() );
            if ( column != null ) {
                if ( criteria.isEnableAlias() ) {
                    buffer.append( " " ).append( criteria.getAlias() ).append( "." ).append( column.getColumn() );
                } else {
                    buffer.append( " " ).append( column.getColumn() );
                }
            }
            buffer.append( " " ).append( operator.getSqlSegment() ).append( " " );
            buffer.append( subCriteria.getSqlSegmentForCondition() );
            return buffer.toString();
        }
        return "";
    }

    @Override
    protected String toJsonString() {
        return toJsonString( "subCriteria", getSubCriteria() );
    }
}
