package com.wkit.lost.mybatis.core.wrapper.basic;

import com.wkit.lost.mybatis.core.lambda.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.core.wrapper.utils.CriteriaUtil;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 查询字段(字段包装对象)
 * @param <T> 实体类型
 * @author wvkity
 */
public class Query<T> extends AbstractQueryWrapper<T, ColumnWrapper> {

    private static final long serialVersionUID = 9171953090394342598L;

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param column   字段对象
     */
    private Query( Criteria<T> criteria, ColumnWrapper column ) {
        this.criteria = criteria;
        this.column = column;
    }

    /**
     * 构造方法
     * @param criteria    条件对象
     * @param column      字段对象
     * @param columnAlias 字段别名
     */
    private Query( Criteria<T> criteria, ColumnWrapper column, String columnAlias ) {
        this.criteria = criteria;
        this.column = column;
        this.columnAlias = columnAlias;
    }

    /**
     * 获取属性
     * @return 属性
     */
    public String getProperty() {
        return this.column.getProperty();
    }

    @Override
    public AbstractQueryWrapper<?, ?> transform( Criteria<?> criteria ) {
        return Optional.ofNullable( criteria ).map( it -> {
            String columnName = StringUtil.hasText( this.columnAlias ) ? this.columnAlias :
                    ( this.criteria != null && this.criteria.isColumnAliasAutoMapping() ?
                            this.column.getProperty() : this.column.getColumn() );
            return ImmediateQuery.Single.query( it, columnName, null );
        } ).orElse( null );
    }

    @Override
    public String getSegment() {
        return getSegment( true );
    }

    @Override
    public String getSegment( boolean applyQuery ) {
        String tableAlias = this.criteria.isEnableAlias() ? this.criteria.getAlias() : null;
        if ( StringUtil.hasText( this.columnAlias ) ) {
            return ColumnConvert.convertToQueryArg( this.column.getColumn(), this.columnAlias, tableAlias );
        } else {
            return ColumnConvert.convertToQueryArg( this.column, tableAlias, criteria.getReference(),
                    applyQuery && criteria.isColumnAliasAutoMapping() );
        }
    }

    /**
     * 单个查询列
     */
    public static final class Single {
        private Single() {
        }

        /**
         * 查询字段
         * @param criteria 条件对象
         * @param property 属性Lambda对象
         * @param <T>      实体类型
         * @return 查询列对象
         */
        public static <T> Query<T> query( Criteria<T> criteria, Property<T, ?> property ) {
            return query( criteria, criteria.searchColumn( property ) );
        }

        /**
         * 查询字段
         * @param criteria 条件对象
         * @param property 属性
         * @param <T>      实体类型
         * @return 查询列对象
         */
        public static <T> Query<T> query( Criteria<T> criteria, String property ) {
            return query( criteria, criteria.searchColumn( property ) );
        }

        /**
         * 查询字段
         * @param criteria    条件对象
         * @param property    属性Lambda对象
         * @param columnAlias 字段别名
         * @param <T>         实体类型
         * @return 查询列对象
         */
        public static <T> Query<T> query( Criteria<T> criteria, Property<T, ?> property, String columnAlias ) {
            return query( criteria, criteria.searchColumn( property ), columnAlias );
        }

        /**
         * 查询字段
         * @param criteria    条件对象
         * @param property    属性
         * @param columnAlias 字段别名
         * @param <T>         实体类型
         * @return 查询列对象
         */
        public static <T> Query<T> query( Criteria<T> criteria, String property, String columnAlias ) {
            return query( criteria, criteria.searchColumn( property ), columnAlias );
        }

        /**
         * 查询字段
         * @param criteria 条件对象
         * @param column   字段包装对象
         * @param <T>      实体类型
         * @return 查询列对象
         */
        public static <T> Query<T> query( Criteria<T> criteria, ColumnWrapper column ) {
            return column != null ? new Query<>( criteria, column ) : null;
        }

        /**
         * 查询字段
         * @param criteria    条件对象
         * @param column      字段包装对象
         * @param columnAlias 字段别名
         * @param <T>         实体类型
         * @return 查询列对象
         */
        public static <T> Query<T> query( Criteria<T> criteria, ColumnWrapper column, String columnAlias ) {
            return column != null ? new Query<>( criteria, column, columnAlias ) : null;
        }
    }

    /**
     * 多个查询列
     */
    public static final class Multi {
        private Multi() {
        }

        /**
         * 查询字段
         * @param criteria   条件对象
         * @param properties 属性数组
         * @param <T>        实体类型
         * @return 查询对象集合
         */
        @SafeVarargs
        public static <T> ArrayList<Query<T>> query( Criteria<T> criteria, Property<T, ?>... properties ) {
            return query( criteria, CriteriaUtil.lambdaToColumn( criteria, ArrayUtil.toList( properties ) ) );
        }

        /**
         * 查询字段
         * @param criteria   条件对象
         * @param properties 属性数组
         * @param <T>        实体类型
         * @return 查询对象集合
         */
        public static <T> ArrayList<Query<T>> query( Criteria<T> criteria, String... properties ) {
            return query( criteria, ArrayUtil.toList( properties ) );
        }

        /**
         * 查询字段
         * @param criteria   条件对象
         * @param properties 属性集合
         * @param <T>        实体类型
         * @return 查询对象集合
         */
        public static <T> ArrayList<Query<T>> query( Criteria<T> criteria, Collection<String> properties ) {
            Set<String> props = distinct( properties );
            return props.isEmpty() ? new ArrayList<>( 0 ) :
                    props.stream().map( it -> Single.query( criteria, it ) )
                            .collect( Collectors.toCollection( ArrayList::new ) );
        }

        /**
         * 查询字段
         * @param criteria 条件对象
         * @param columns  字段包装对象集合
         * @param <T>      实体类型
         * @return 查询对象集合
         */
        public static <T> ArrayList<Query<T>> query( Criteria<T> criteria, List<ColumnWrapper> columns ) {
            if ( CollectionUtil.hasElement( columns ) ) {
                return columns.stream().filter( Objects::nonNull ).map( it -> Single.query( criteria, it ) )
                        .collect( Collectors.toCollection( ArrayList::new ) );
            }
            return new ArrayList<>( 0 );
        }

        /**
         * 查询字段
         * @param criteria   条件对象
         * @param properties 字段别名-属性集合
         * @param <T>        实体类型
         * @return 字符串查询列对象集合
         */
        public static <T> ArrayList<Query<T>> query( Criteria<T> criteria, Map<String, String> properties ) {
            Map<String, String> its = filterNullValue( properties );
            if ( CollectionUtil.hasElement( its ) ) {
                ArrayList<Query<T>> list = new ArrayList<>( its.size() );
                for ( Map.Entry<String, String> entry : its.entrySet() ) {
                    list.add( Single.query( criteria, entry.getValue(), entry.getKey() ) );
                }
                return list;
            }
            return new ArrayList<>( 0 );
        }
    }

}
