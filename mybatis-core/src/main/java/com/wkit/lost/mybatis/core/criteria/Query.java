package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.lambda.LambdaConverter;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 查询列接口
 * @param <T>       泛型类型
 * @param <Context> 当前对象
 * @author wvkity
 */
public interface Query<T, Context> extends CriteriaSearch, LambdaConverter<Property<T, ?>> {

    /**
     * 添加查询列
     * @param property 属性
     * @return 当前对象
     */
    default Context query( Property<T, ?> property ) {
        return query( lambdaToProperty( property ) );
    }

    /**
     * 添加查询列
     * @param property 属性
     * @return 当前对象
     */
    Context query( String property );

    /**
     * 添加查询列
     * @param property    属性
     * @param columnAlias 列别名
     * @return 当前对象
     */
    default Context query( Property<T, ?> property, String columnAlias ) {
        return query( lambdaToProperty( property ), columnAlias );
    }

    /**
     * 添加查询列
     * @param property    属性
     * @param columnAlias 列别名
     * @return 当前对象
     */
    Context query( String property, String columnAlias );

    /**
     * 添加查询列
     * @param column 列名
     * @return 当前对象
     */
    Context immediateQuery( String column );

    /**
     * 添加查询列
     * @param column      列名
     * @param columnAlias 列别名
     * @return 当前对象
     */
    Context immediateQuery( String column, String columnAlias );

    /**
     * 添加查询列
     * @param tableAlias  表别名
     * @param column      列名
     * @param columnAlias 列别名
     * @return 当前对象
     */
    Context immediateQuery( String tableAlias, String column, String columnAlias );

    /**
     * 添加查询列
     * @param criteriaAlias 子查询对象别名
     * @param property      属性
     * @return 当前对象
     */
    default <E> Context subQuery( String criteriaAlias, String property ) {
        return subQuery( searchSubCriteria( criteriaAlias ), property );
    }


    /**
     * 添加查询列
     * @param criteria 子查询对象
     * @param property 属性
     * @return 当前对象
     */
    default <E> Context subQuery( SubCriteria<E> criteria, Property<E, ?> property ) {
        return subQuery( criteria, criteria.lambdaToProperty( property ) );
    }

    /**
     * 添加查询列
     * @param criteria 子查询对象
     * @param property 属性
     * @return 当前对象
     */
    <E> Context subQuery( SubCriteria<E> criteria, String property );

    /**
     * 添加查询列
     * @param criteria    子查询对象
     * @param property    属性
     * @param columnAlias 列别名
     * @return 当前对象
     */
    default <E> Context subQuery( SubCriteria<E> criteria, Property<E, ?> property, String columnAlias ) {
        return subQuery( criteria, criteria.lambdaToProperty( property ), columnAlias );
    }


    /**
     * 添加查询列
     * @param criteria    子查询对象
     * @param property    属性
     * @param columnAlias 列别名
     * @return 当前对象
     */
    <E> Context subQuery( SubCriteria<E> criteria, String property, String columnAlias );
    
    /**
     * 添加查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    default Context queries( Property<T, ?>... properties ) {
        return queries( lambdaToProperties( properties ) );
    }

    /**
     * 添加查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    default Context queries( String... properties ) {
        return queries( ArrayUtil.toList( properties ) );
    }

    /**
     * 添加查询列
     * @param properties 属性集合
     * @return 当前对象
     */
    Context queries( Collection<String> properties );

    /**
     * 添加查询列
     * @param properties 列别名-属性集合
     * @return 当前对象
     */
    Context queries( Map<String, String> properties );

    /**
     * 添加查询列
     * @param columns 列名数组
     * @return 当前对象
     */
    default Context immediateQueries( String... columns ) {
        return immediateQueries( ArrayUtil.toList( columns ) );
    }

    /**
     * 添加查询列
     * @param columns 列名集合
     * @return 当前对象
     */
    Context immediateQueries( Collection<String> columns );

    /**
     * 添加查询列
     * @param columns 列别名-列名集合
     * @return 当前对象
     */
    Context immediateQueries( Map<String, String> columns );

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param columns    列别名-列名集合
     * @return 当前对象
     */
    Context immediateQueries( String tableAlias, Map<String, String> columns );

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param columns    列名数组
     * @return 当前对象
     */
    default Context immediateQueriesWithAlias( String tableAlias, String... columns ) {
        return immediateQueries( tableAlias, ArrayUtil.toList( columns ) );
    }

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param columns    列名集合
     * @return 当前对象
     */
    Context immediateQueries( String tableAlias, Collection<String> columns );

    /**
     * 添加子查询列
     * @param criteriaAlias 子查询条件对象别名
     * @param properties    属性数组
     * @param <E>           实体类型
     * @return 当前对象
     */
    default <E> Context subQueries( String criteriaAlias, String... properties ) {
        return subQueries( criteriaAlias, ArrayUtil.toList( properties ) );
    }

    /**
     * 添加子查询列
     * @param criteriaAlias 子查询条件对象别名
     * @param properties    属性集合
     * @param <E>           实体类型
     * @return 当前对象
     */
    default <E> Context subQueries( String criteriaAlias, Collection<String> properties ) {
        return subQueries( searchSubCriteria( criteriaAlias ), properties );
    }

    /**
     * 添加子查询列
     * @param criteria   子查询条件对象
     * @param properties 属性数组
     * @param <E>        实体类型
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    default <E> Context subQueries( SubCriteria<E> criteria, Property<E, ?>... properties ) {
        return subQueries( criteria, criteria.lambdaToProperties( properties ) );
    }

    /**
     * 添加子查询列
     * @param criteria   子查询条件对象
     * @param properties 属性数组
     * @param <E>        实体类型
     * @return 当前对象
     */
    default <E> Context subQueries( SubCriteria<E> criteria, String... properties ) {
        return subQueries( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * 添加子查询列
     * @param criteria   子查询条件对象
     * @param properties 属性集合
     * @param <E>        实体类型
     * @return 当前对象
     */
    <E> Context subQueries( SubCriteria<E> criteria, Collection<String> properties );

    /**
     * 添加子查询列
     * @param criteriaAlias 子查询条件对象别名
     * @param properties    列别名-属性集合
     * @param <E>           实体类型
     * @return 当前对象
     */
    default <E> Context subQueries( String criteriaAlias, Map<String, String> properties ) {
        return subQueries( searchSubCriteria( criteriaAlias ), properties );
    }


    /**
     * 添加子查询列
     * @param criteria   子查询条件对象
     * @param properties 列别名-属性集合
     * @param <E>        实体类型
     * @return 当前对象
     */
    <E> Context subQueries( SubCriteria<E> criteria, Map<String, String> properties );
    
    /**
     * 排除查询列
     * @param property 属性
     * @return 当前对象
     */
    default Context exclude( Property<T, ?> property ) {
        return exclude( lambdaToProperty( property ) );
    }

    /**
     * 排除查询列
     * @param property 属性
     * @return 当前对象
     */
    default Context exclude( String property ) {
        return excludes( property );
    }

    /**
     * 排除查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    default Context excludes( Property<T, ?>... properties ) {
        return excludes( lambdaToProperties( properties ) );
    }

    /**
     * 排除查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    default Context excludes( String... properties ) {
        return excludes( ArrayUtil.toList( properties ) );
    }

    /**
     * 排除查询列
     * @param properties 属性集合
     * @return 当前对象
     */
    Context excludes( Collection<String> properties );

    /**
     * 排除查询列
     * @param columns 列名数组
     * @return 当前对象
     */
    default Context immediateExcludes( String... columns ) {
        return immediateExcludes( ArrayUtil.toList( columns ) );
    }

    /**
     * 排除查询列
     * @param columns 列名集合
     * @return 当前对象
     */
    Context immediateExcludes( Collection<String> columns );

    /**
     * 获取查询字段片段
     * @return SQL字符串
     */
    String getQuerySegment();
}
