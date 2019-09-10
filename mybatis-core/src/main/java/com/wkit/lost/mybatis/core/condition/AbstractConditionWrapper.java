package com.wkit.lost.mybatis.core.condition;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.core.AbstractCriteria;
import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.MatchMode;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.condition.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 条件容器
 * @param <T>       泛型类型
 * @param <R>       Lambda类型
 * @param <Context> 子类型
 */
@SuppressWarnings( value = { "serial", "unchecked" } )
public abstract class AbstractConditionWrapper<T, R, Context extends AbstractConditionWrapper<T, R, Context>>
        implements ConditionWrapper<T, R, Context> {

    /**
     * 条件对象集合
     */
    protected Set<Criterion<?>> conditions = Collections.synchronizedSet( new LinkedHashSet<>() );

    /**
     * 查询对象
     */
    protected AbstractCriteria<T> criteria;

    /**
     * 当前对象
     */
    final Context context = ( Context ) this;

    @Override
    public Context idEqExp( Object value ) {
        return addCondition( Restrictions.idEq( this.criteria, value ) );
    }

    @Override
    public Context orIdEqExp( Object value ) {
        return addCondition( Restrictions.idEq( this.criteria, value, Logic.OR ) );
    }

    @Override
    public Context eqExp( String property, Object value ) {
        return addCondition( Restrictions.eq( this.criteria, property, value ) );
    }

    @Override
    public Context orEqExp( String property, Object value ) {
        return addCondition( Restrictions.eq( this.criteria, property, value, Logic.OR ) );
    }

    @Override
    public Context neExp( String property, Object value ) {
        return addCondition( Restrictions.ne( this.criteria, property, value ) );
    }

    @Override
    public Context orNeExp( String property, Object value ) {
        return addCondition( Restrictions.ne( this.criteria, property, value, Logic.OR ) );
    }

    @Override
    public Context ltExp( String property, Object value ) {
        return addCondition( Restrictions.lt( this.criteria, property, value ) );
    }

    @Override
    public Context orLtExp( String property, Object value ) {
        return addCondition( Restrictions.lt( property, value, Logic.OR ) );
    }

    @Override
    public Context leExp( String property, Object value ) {
        return addConditions( Restrictions.le( this.criteria, property, value ) );
    }

    @Override
    public Context orLeExp( String property, Object value ) {
        return addCondition( Restrictions.le( this.criteria, property, value, Logic.OR ) );
    }

    @Override
    public Context gtExp( String property, Object value ) {
        return addCondition( Restrictions.gt( this.criteria, property, value ) );
    }

    @Override
    public Context orGtExp( String property, Object value ) {
        return addCondition( Restrictions.gt( this.criteria, property, value, Logic.OR ) );
    }

    @Override
    public Context geExp( String property, Object value ) {
        return addCondition( Restrictions.ge( this.criteria, property, value ) );
    }

    @Override
    public Context orGeExp( String property, Object value ) {
        return addCondition( Restrictions.ge( this.criteria, property, value, Logic.OR ) );
    }

    @Override
    public Context isNullExp( String property ) {
        return addCondition( Restrictions.isNull( this.criteria, property ) );
    }

    @Override
    public Context orIsNullExp( String property ) {
        return addCondition( Restrictions.isNull( this.criteria, property, Logic.OR ) );
    }

    @Override
    public Context notNullExp( String property ) {
        return addCondition( Restrictions.notNull( this.criteria, property ) );
    }

    @Override
    public Context orNotNullExp( String property ) {
        return addCondition( Restrictions.notNull( this.criteria, property, Logic.OR ) );
    }

    @Override
    public Context inExp( String property, Collection<Object> values ) {
        return addCondition( Restrictions.in( this.criteria, property, values ) );
    }

    @Override
    public Context orInExp( String property, Collection<Object> values ) {
        return addCondition( Restrictions.in( this.criteria, property, Logic.OR, values ) );
    }

    @Override
    public Context notInExp( String property, Collection<Object> values ) {
        return addCondition( Restrictions.notIn( this.criteria, property, values ) );
    }

    @Override
    public Context orNotInExp( String property, Collection<Object> values ) {
        return addCondition( Restrictions.notIn( this.criteria, property, Logic.OR, values ) );
    }

    @Override
    public Context likeExp( String property, String value, MatchMode matchMode, Character escape ) {
        return addCondition( Restrictions.like( this.criteria, property, value, matchMode, escape ) );
    }

    @Override
    public Context orLikeExp( String property, String value, MatchMode matchMode, Character escape ) {
        return addCondition( Restrictions.like( this.criteria, property, value, matchMode, escape, Logic.OR ) );
    }

    @Override
    public Context betweenExp( String property, Object begin, Object end ) {
        return addCondition( Restrictions.between( this.criteria, property, begin, end ) );
    }

    @Override
    public Context orBetweenExp( String property, Object begin, Object end ) {
        return addCondition( Restrictions.between( this.criteria, property, begin, end, Logic.OR ) );
    }

    @Override
    public Context notBetweenExp( String property, Object begin, Object end ) {
        return addCondition( Restrictions.notBetween( this.criteria, property, begin, end ) );
    }

    @Override
    public Context orNotBetweenExp( String property, Object begin, Object end ) {
        return addCondition( Restrictions.notBetween( this.criteria, property, begin, end, Logic.OR ) );
    }

    @Override
    public Context templateExp( String template, String property, Object value ) {
        return addCondition( Restrictions.template( this.criteria, template, property, value ) );
    }

    @Override
    public Context templateExp( String template, String property, Collection<Object> values ) {
        return addCondition( Restrictions.template( this.criteria, template, property, values ) );
    }

    @Override
    public Context orTemplateExp( String template, String property, Object value ) {
        return addCondition( Restrictions.template( this.criteria, template, Logic.OR, property, value ) );
    }

    @Override
    public Context orTemplateExp( String template, String property, Collection<Object> values ) {
        return addCondition( Restrictions.template( this.criteria, template, Logic.OR, property, values ) );
    }

    @Override
    public Context exactTemplateExp( String template, String property, Object value ) {
        return addCondition( Restrictions.template( this.criteria, template, Logic.NORMAL, property, value ) );
    }

    @Override
    public Context exactTemplateExp( String template, String property, Collection<Object> values ) {
        return addCondition( Restrictions.template( this.criteria, template, Logic.NORMAL, property, values ) );
    }

    @Override
    public Context nesting( Logic logic, Collection<Criterion<?>> conditions ) {
        return addCondition( Restrictions.nested( this.criteria, logic, conditions ) );
    }

    @Override
    public Context nesting( Criteria<T> criteria, Logic logic, Collection<Criterion<?>> conditions ) {
        return addCondition( Restrictions.nested( criteria, logic, conditions ) );
    }

    @Override
    public Context nesting( Function<Context, Context> function ) {
        return doIt( function, true );
    }

    @Override
    public Context orNesting( Function<Context, Context> function ) {
        return doIt( function, false );
    }

    protected Context doIt( Function<Context, Context> function, boolean isAnd ) {
        Context instance = instance( this.criteria );
        Context applyInstance = function.apply( instance );
        Set<Criterion<?>> applyConditions = applyInstance.conditions;
        if ( CollectionUtil.hasElement( applyConditions ) ) {
            if ( isAnd ) {
                this.nesting( applyConditions );
            } else {
                this.nesting( Logic.OR, applyConditions );
            }
        }
        return context;
    }

    /**
     * 添加条件
     * @param condition 条件对象
     * @return 当前对象
     */
    protected Context addCondition( Criterion<?> condition ) {
        if ( condition != null ) {
            this.conditions.add( condition );
        }
        return this.context;
    }

    /**
     * 添加多个条件
     * @param conditions 条件数组
     * @return 当前对象
     */
    protected Context addConditions( Criterion<?>... conditions ) {
        return addConditions( ArrayUtil.toList( conditions ) );
    }

    /**
     * 添加多个条件
     * @param conditions 条件集合
     * @return 当前对象
     */
    protected Context addConditions( Collection<Criterion<?>> conditions ) {
        if ( CollectionUtil.hasElement( conditions ) ) {
            List<Criterion<?>> list = conditions.stream().filter( Objects::nonNull ).collect( Collectors.toList() );
            if ( CollectionUtil.hasElement( list ) ) {
                this.conditions.addAll( conditions );
            }
        }
        return this.context;
    }

    /**
     * 将所有条件添加查询对象中(自动清空当前对象中保存的条件对象)
     * @return 查询对象
     */
    public AbstractCriteria<T> flush() {
        if ( this.hasCondition() ) {
            this.criteria.add( this.all() );
            this.clear();
        }
        return this.criteria;
    }

    @Override
    public List<Criterion<?>> all() {
        return this.hasCondition() ? new ArrayList<>( this.conditions ) : new ArrayList<>( 0 );
    }

    @Override
    public boolean hasCondition() {
        return CollectionUtil.hasElement( this.conditions );
    }

    @Override
    public Context clear() {
        if ( this.hasCondition() ) {
            this.conditions.clear();
        }
        return this.context;
    }

    @Override
    public Context setCriteria( AbstractCriteria<T> criteria ) {
        this.criteria = criteria;
        return this.context;
    }

    /**
     * 创建实例
     * @param criteria 查询对象
     * @return 实例对象
     */
    protected abstract Context instance( AbstractCriteria<T> criteria );
}
