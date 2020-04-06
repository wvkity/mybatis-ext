package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.lambda.LambdaConverter;
import com.wkit.lost.mybatis.core.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 查询列接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 */
public interface Query<T, Chain extends Query<T, Chain>> extends CriteriaSearch, LambdaConverter<Property<T, ?>> {


    /**
     * 添加查询列
     * @param property 属性
     * @return {@code this}
     */
    default Chain query( Property<T, ?> property ) {
        return query( lambdaToProperty( property ) );
    }

    /**
     * 添加查询列
     * @param property 属性
     * @return {@code this}
     */
    Chain query( String property );

    /**
     * 添加查询列
     * @param property    属性
     * @param columnAlias 列别名
     * @return {@code this}
     */
    default Chain query( Property<T, ?> property, String columnAlias ) {
        return query( lambdaToProperty( property ), columnAlias );
    }

    /**
     * 添加查询列
     * @param property    属性
     * @param columnAlias 列别名
     * @return {@code this}
     */
    Chain query( String property, String columnAlias );

    /**
     * 添加查询列
     * @param column 列名
     * @return {@code this}
     */
    Chain immediateQuery( String column );

    /**
     * 添加查询列
     * @param column      列名
     * @param columnAlias 列别名
     * @return {@code this}
     */
    Chain immediateQuery( String column, String columnAlias );

    /**
     * 添加查询列
     * @param tableAlias  表别名
     * @param column      列名
     * @param columnAlias 列别名
     * @return {@code this}
     */
    Chain immediateQuery( String tableAlias, String column, String columnAlias );

    /**
     * 添加查询列
     * @param criteriaAlias 子查询对象别名
     * @param property      属性
     * @return {@code this}
     */
    default <E> Chain subQuery( String criteriaAlias, String property ) {
        return subQuery( searchSubCriteria( criteriaAlias ), property );
    }


    /**
     * 添加查询列
     * @param criteria 子查询对象
     * @param property 属性
     * @return {@code this}
     */
    default <E> Chain subQuery( SubCriteria<E> criteria, Property<E, ?> property ) {
        return subQuery( criteria, criteria.lambdaToProperty( property ) );
    }

    /**
     * 添加查询列
     * @param criteria 子查询对象
     * @param property 属性
     * @return {@code this}
     */
    <E> Chain subQuery( SubCriteria<E> criteria, String property );

    /**
     * 添加查询列
     * @param criteria    子查询对象
     * @param property    属性
     * @param columnAlias 列别名
     * @return {@code this}
     */
    default <E> Chain subQuery( SubCriteria<E> criteria, Property<E, ?> property, String columnAlias ) {
        return subQuery( criteria, criteria.lambdaToProperty( property ), columnAlias );
    }


    /**
     * 添加查询列
     * @param criteria    子查询对象
     * @param property    属性
     * @param columnAlias 列别名
     * @return {@code this}
     */
    <E> Chain subQuery( SubCriteria<E> criteria, String property, String columnAlias );

    /**
     * 添加查询列
     * @param properties 属性数组
     * @return {@code this}
     */
    @SuppressWarnings( { "unchecked" } )
    default Chain queries( Property<T, ?>... properties ) {
        return queries( lambdaToProperties( properties ) );
    }

    /**
     * 添加查询列
     * @param properties 属性数组
     * @return {@code this}
     */
    default Chain queries( String... properties ) {
        return queries( ArrayUtil.toList( properties ) );
    }

    /**
     * 添加查询列
     * @param properties 属性集合
     * @return {@code this}
     */
    Chain queries( Collection<String> properties );

    /**
     * 添加查询列
     * @param properties 列别名-属性集合
     * @return {@code this}
     */
    Chain queries( Map<String, String> properties );

    /**
     * 添加查询列
     * @param columns 列名数组
     * @return {@code this}
     */
    default Chain immediateQueries( String... columns ) {
        return immediateQueries( ArrayUtil.toList( columns ) );
    }

    /**
     * 添加查询列
     * @param columns 列名集合
     * @return {@code this}
     */
    Chain immediateQueries( Collection<String> columns );

    /**
     * 添加查询列
     * @param columns 列别名-列名集合
     * @return {@code this}
     */
    Chain immediateQueries( Map<String, String> columns );

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param columns    列别名-列名集合
     * @return {@code this}
     */
    Chain immediateQueries( String tableAlias, Map<String, String> columns );

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param columns    列名数组
     * @return {@code this}
     */
    default Chain immediateQueriesWithAlias( String tableAlias, String... columns ) {
        return immediateQueries( tableAlias, ArrayUtil.toList( columns ) );
    }

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param columns    列名集合
     * @return {@code this}
     */
    Chain immediateQueries( String tableAlias, Collection<String> columns );

    /**
     * 添加子查询列
     * @param criteriaAlias 子查询条件对象别名
     * @param properties    属性数组
     * @param <E>           实体类型
     * @return {@code this}
     */
    default <E> Chain subQueries( String criteriaAlias, String... properties ) {
        return subQueries( criteriaAlias, ArrayUtil.toList( properties ) );
    }

    /**
     * 添加子查询列
     * @param criteriaAlias 子查询条件对象别名
     * @param properties    属性集合
     * @param <E>           实体类型
     * @return {@code this}
     */
    default <E> Chain subQueries( String criteriaAlias, Collection<String> properties ) {
        return subQueries( searchSubCriteria( criteriaAlias ), properties );
    }

    /**
     * 添加子查询列
     * @param criteria   子查询条件对象
     * @param properties 属性数组
     * @param <E>        实体类型
     * @return {@code this}
     */
    @SuppressWarnings( { "unchecked" } )
    default <E> Chain subQueries( SubCriteria<E> criteria, Property<E, ?>... properties ) {
        return subQueries( criteria, criteria.lambdaToProperties( properties ) );
    }

    /**
     * 添加子查询列
     * @param criteria   子查询条件对象
     * @param properties 属性数组
     * @param <E>        实体类型
     * @return {@code this}
     */
    default <E> Chain subQueries( SubCriteria<E> criteria, String... properties ) {
        return subQueries( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * 添加子查询列
     * @param criteria   子查询条件对象
     * @param properties 属性集合
     * @param <E>        实体类型
     * @return {@code this}
     */
    <E> Chain subQueries( SubCriteria<E> criteria, Collection<String> properties );

    /**
     * 添加子查询列
     * @param criteriaAlias 子查询条件对象别名
     * @param properties    列别名-属性集合
     * @param <E>           实体类型
     * @return {@code this}
     */
    default <E> Chain subQueries( String criteriaAlias, Map<String, String> properties ) {
        return subQueries( searchSubCriteria( criteriaAlias ), properties );
    }


    /**
     * 添加子查询列
     * @param criteria   子查询条件对象
     * @param properties 列别名-属性集合
     * @param <E>        实体类型
     * @return {@code this}
     */
    <E> Chain subQueries( SubCriteria<E> criteria, Map<String, String> properties );

    /**
     * 排除查询列
     * @param property 属性
     * @return {@code this}
     */
    default Chain exclude( Property<T, ?> property ) {
        return exclude( lambdaToProperty( property ) );
    }

    /**
     * 排除查询列
     * @param property 属性
     * @return {@code this}
     */
    default Chain exclude( String property ) {
        return excludes( property );
    }

    /**
     * 排除查询列
     * @param properties 属性数组
     * @return {@code this}
     */
    @SuppressWarnings( { "unchecked" } )
    default Chain excludes( Property<T, ?>... properties ) {
        return excludes( lambdaToProperties( properties ) );
    }

    /**
     * 排除查询列
     * @param properties 属性数组
     * @return {@code this}
     */
    default Chain excludes( String... properties ) {
        return excludes( ArrayUtil.toList( properties ) );
    }

    /**
     * 排除查询列
     * @param properties 属性集合
     * @return {@code this}
     */
    Chain excludes( Collection<String> properties );

    /**
     * 排除查询列
     * @param columns 列名数组
     * @return {@code this}
     */
    default Chain immediateExcludes( String... columns ) {
        return immediateExcludes( ArrayUtil.toList( columns ) );
    }

    /**
     * 排除查询列
     * @param columns 列名集合
     * @return {@code this}
     */
    Chain immediateExcludes( Collection<String> columns );

    /**
     * 获取查询字段片段
     * @return SQL字符串
     */
    String getQuerySegment();

}
