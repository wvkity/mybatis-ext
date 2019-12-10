package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.condition.AbstractConditionManager;
import com.wkit.lost.mybatis.core.condition.ConditionBuilder;
import com.wkit.lost.mybatis.core.condition.ConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;

import java.util.Collection;
import java.util.function.Function;

/**
 * 条件构建容器
 * @param <T> 泛型类型
 * @param <R> Lambda对象
 * @author wvkity
 */
@SuppressWarnings( "serial" )
public abstract class AbstractConditionExpressionWrapper<T, R> extends AbstractParamValueConverter
        implements ConditionBuilder<T, AbstractConditionManager<T>, R> {

    /**
     * 条件管理器
     */
    protected ConditionManager<T> conditionManager;


    @Override
    public AbstractConditionManager<T> idEqExp( Object value ) {
        return conditionManager.idEqExp( value );
    }

    @Override
    public AbstractConditionManager<T> orIdEqExp( Object value ) {
        return conditionManager.orIdEqExp( value );
    }

    @Override
    public AbstractConditionManager<T> eqExp( String property, Object value ) {
        return conditionManager.eqExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> orEqExp( String property, Object value ) {
        return conditionManager.orEqExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> neExp( String property, Object value ) {
        return conditionManager.neExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> orNeExp( String property, Object value ) {
        return conditionManager.orNeExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> ltExp( String property, Object value ) {
        return conditionManager.ltExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> orLtExp( String property, Object value ) {
        return conditionManager.orLtExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> leExp( String property, Object value ) {
        return conditionManager.leExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> orLeExp( String property, Object value ) {
        return conditionManager.orLeExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> gtExp( String property, Object value ) {
        return conditionManager.gtExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> orGtExp( String property, Object value ) {
        return conditionManager.orGtExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> geExp( String property, Object value ) {
        return conditionManager.geExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> orGeExp( String property, Object value ) {
        return conditionManager.orGeExp( property, value );
    }

    @Override
    public AbstractConditionManager<T> isNullExp( String property ) {
        return conditionManager.isNullExp( property );
    }

    @Override
    public AbstractConditionManager<T> orIsNullExp( String property ) {
        return conditionManager.orIsNullExp( property );
    }

    @Override
    public AbstractConditionManager<T> notNullExp( String property ) {
        return conditionManager.notNullExp( property );
    }

    @Override
    public AbstractConditionManager<T> orNotNullExp( String property ) {
        return conditionManager.orNotNullExp( property );
    }

    @Override
    public AbstractConditionManager<T> inExp( String property, Collection<Object> values ) {
        return conditionManager.inExp( property, values );
    }

    @Override
    public AbstractConditionManager<T> orInExp( String property, Collection<Object> values ) {
        return conditionManager.orInExp( property, values );
    }

    @Override
    public AbstractConditionManager<T> notInExp( String property, Collection<Object> values ) {
        return conditionManager.notInExp( property, values );
    }

    @Override
    public AbstractConditionManager<T> orNotInExp( String property, Collection<Object> values ) {
        return conditionManager.orNotInExp( property, values );
    }

    @Override
    public AbstractConditionManager<T> likeExp( String property, String value, MatchMode matchMode, Character escape ) {
        return conditionManager.likeExp( property, value, matchMode, escape );
    }

    @Override
    public AbstractConditionManager<T> orLikeExp( String property, String value, MatchMode matchMode, Character escape ) {
        return conditionManager.orLikeExp( property, value, matchMode, escape );
    }

    @Override
    public AbstractConditionManager<T> betweenExp( String property, Object begin, Object end ) {
        return conditionManager.betweenExp( property, begin, end );
    }

    @Override
    public AbstractConditionManager<T> orBetweenExp( String property, Object begin, Object end ) {
        return conditionManager.orBetweenExp( property, begin, end );
    }

    @Override
    public AbstractConditionManager<T> notBetweenExp( String property, Object begin, Object end ) {
        return conditionManager.notBetweenExp( property, begin, end );
    }

    @Override
    public AbstractConditionManager<T> orNotBetweenExp( String property, Object begin, Object end ) {
        return conditionManager.orNotBetweenExp( property, begin, end );
    }

    @Override
    public AbstractConditionManager<T> templateExp( String template, String property, Object value ) {
        return conditionManager.templateExp( template, property, value );
    }

    @Override
    public AbstractConditionManager<T> templateExp( String template, String property, Collection<Object> values ) {
        return conditionManager.templateExp( template, property, values );
    }

    @Override
    public AbstractConditionManager<T> orTemplateExp( String template, String property, Object value ) {
        return conditionManager.orTemplateExp( template, property, value );
    }

    @Override
    public AbstractConditionManager<T> orTemplateExp( String template, String property, Collection<Object> values ) {
        return conditionManager.orTemplateExp( template, property, values );
    }

    @Override
    public AbstractConditionManager<T> exactTemplateExp( String template, String property, Object value ) {
        return conditionManager.exactTemplateExp( template, property, value );
    }

    @Override
    public AbstractConditionManager<T> exactTemplateExp( String template, String property, Collection<Object> values ) {
        return conditionManager.exactTemplateExp( template, property, values );
    }

    @Override
    public AbstractConditionManager<T> nesting( Logic logic, Collection<Criterion<?>> conditions ) {
        return conditionManager.nesting( logic, conditions );
    }

    @Override
    public AbstractConditionManager<T> nesting( Criteria<T> criteria, Logic logic, Collection<Criterion<?>> conditions ) {
        return conditionManager.nesting( criteria, logic, conditions );
    }

    @Override
    public AbstractConditionManager<T> nesting( Function<AbstractConditionManager<T>, AbstractConditionManager<T>> function ) {
        return this.conditionManager.nesting( function );
    }

    @Override
    public AbstractConditionManager<T> orNesting( Function<AbstractConditionManager<T>, AbstractConditionManager<T>> function ) {
        return this.conditionManager.orNesting( function );
    }
}
