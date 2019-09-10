package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 嵌套条件
 * @param <T> 泛型类型
 */
public class NestedExpression<T> extends AbstractExpression<T> {

    private static final long serialVersionUID = -6089868985393017077L;

    /**
     * 条件集合
     */
    private Set<Criterion<?>> conditions = new LinkedHashSet<>();

    /**
     * 构造方法
     * @param conditions 条件集合
     */
    public NestedExpression( Collection<Criterion<?>> conditions ) {
        this( Logic.AND, conditions );
    }

    /**
     * 构造方法
     * @param conditions 条件集合
     * @param logic      逻辑操作
     */
    public NestedExpression( Logic logic, Collection<Criterion<?>> conditions ) {
        this.logic = logic;
        this.add( conditions );
    }

    /**
     * 构造方法
     * @param criteria   查询对象
     * @param conditions 条件
     */
    public NestedExpression( Criteria<T> criteria, Collection<Criterion<?>> conditions ) {
        this( criteria, Logic.AND, conditions );
    }

    /**
     * 构造方法
     * @param criteria   查询对象
     * @param logic      逻辑操作
     * @param conditions 条件集合
     */
    public NestedExpression( Criteria<T> criteria, Logic logic, Collection<Criterion<?>> conditions ) {
        this.logic = logic;
        this.setCriteria( criteria );
        this.add( conditions );
    }


    /**
     * 添加多个条件
     * @param conditions 条件对象数组
     * @return 当前对象
     */
    public NestedExpression<T> add( Criterion<?>... conditions ) {
        return add( ArrayUtil.toList( conditions ) );
    }

    /**
     * 添加多个条件
     * @param conditions 条件对象集合
     * @return 当前对象
     */
    public NestedExpression<T> add( Collection<Criterion<?>> conditions ) {
        return add( this.criteria, conditions );
    }

    /**
     * 添加多个条件
     * @param criteria   查询对象
     * @param conditions 条件对象数组
     * @return 当前对象
     */
    public NestedExpression<T> add( Criteria<T> criteria, Criterion<?>... conditions ) {
        return add( criteria, Arrays.asList( conditions ) );
    }

    /**
     * 添加多个条件
     * @param criteria   查询对象
     * @param conditions 条件对象集合
     * @return 当前对象
     */
    public NestedExpression<T> add( Criteria<T> criteria, Collection<Criterion<?>> conditions ) {
        Criteria<T> realCriteria = criteria == null ? getCriteria() : criteria;
        if ( CollectionUtil.hasElement( conditions ) ) {
            this.conditions.addAll(
                    conditions.stream()
                            .filter( Objects::nonNull )
                            .peek( criterion -> {
                                if ( criterion.getCriteria() == null ) {
                                    criterion.setCriteria( realCriteria );
                                }
                            } ).collect( Collectors.toList() ) );
        }
        return this;
    }

    @Override
    public String getSqlSegment() {
        if ( CollectionUtil.hasElement( conditions ) ) {
            StringBuffer buffer = new StringBuffer( 80 );
            buffer.append( this.logic.getSqlSegment() ).append( " (" );
            boolean isItemFirst = true;
            for ( Criterion criterion : this.conditions ) {
                if ( isItemFirst ) {
                    // 截取AND/OR
                    String sqlSegment = criterion.getSqlSegment();
                    if ( StringUtil.hasText( sqlSegment ) ) {
                        // 移除AND|OR关键字
                        removeStartWithOfInvalidKeyWord( buffer, sqlSegment );
                        isItemFirst = false;
                    }
                } else {
                    buffer.append( criterion.getSqlSegment() ).append( " " );
                }
            }
            // 删除最后一个空格
            buffer.deleteCharAt( buffer.length() - 1 );
            buffer.append( ")" );
            return buffer.toString();
        }
        return "";
    }

    /**
     * 移除无效关键字(AND|OR)
     * @param buffer     字符串拼接对象
     * @param sqlSegment SQL片段对象
     */
    private void removeStartWithOfInvalidKeyWord( StringBuffer buffer, String sqlSegment ) {
        if ( StringUtil.hasText( sqlSegment ) ) {
            if ( sqlSegment.startsWith( "AND" ) ) {
                buffer.append( sqlSegment.substring( 4 ) );
            } else if ( sqlSegment.startsWith( "OR" ) ) {
                buffer.append( sqlSegment.substring( 3 ) );
            } else {
                buffer.append( sqlSegment );
            }
            buffer.append( " " );
        }
    }
}
