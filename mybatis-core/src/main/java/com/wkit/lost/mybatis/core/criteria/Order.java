package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 排序类
 * @author wvkity
 */
public class Order<T> implements Segment {

    private static final long serialVersionUID = 8376229254028005315L;

    /**
     * 查询对象
     */
    private Criteria<T> criteria;

    /**
     * 是否为聚合函数
     */
    @Getter
    private boolean func;

    /**
     * 表别名
     */
    private String alias;

    /**
     * 排序字段
     */
    private Set<ColumnWrapper> columns = new LinkedHashSet<>( 8 );

    /**
     * 排序属性(字段)
     */
    private Set<String> properties = new LinkedHashSet<>( 8 );

    /**
     * 排序聚合函数
     */
    private Set<Aggregation> aggregations = new LinkedHashSet<>( 8 );

    /**
     * 排序方式
     */
    @Getter
    private boolean ascending;

    /**
     * 构造方法
     * @param ascending 排序方式
     */
    private Order( boolean ascending, boolean func ) {
        this.ascending = ascending;
        this.func = func;
    }

    /**
     * 构造方法
     * @param ascending  排序方式
     * @param properties 属性集合
     */
    private Order( boolean ascending, List<String> properties ) {
        this( ascending, false );
        if ( CollectionUtil.hasElement( properties ) ) {
            this.properties.addAll( properties.stream().filter( StringUtil::hasText ).collect( Collectors.toList() ) );
        }
    }

    /**
     * 构造方法
     * @param alias      表别名
     * @param ascending  排序方式
     * @param properties 属性集合
     */
    private Order( String alias, boolean ascending, List<String> properties ) {
        this( ascending, false );
        this.alias = alias;
        if ( CollectionUtil.hasElement( properties ) ) {
            this.properties.addAll( properties.stream().filter( StringUtil::hasText ).collect( Collectors.toList() ) );
        }
    }

    /**
     * 构造方法
     * @param criteria  查询对象
     * @param columns   列
     * @param ascending 排序方式
     */
    private Order( Criteria<T> criteria, boolean ascending, Collection<ColumnWrapper> columns ) {
        this( ascending, false );
        if ( criteria == null ) {
            throw new MyBatisException( "The Criteria object cannot be empty" );
        }
        this.criteria = criteria;
        if ( CollectionUtil.hasElement( columns ) ) {
            this.columns.addAll( columns.stream().filter( Objects::nonNull ).collect( Collectors.toList() ) );
        }
    }

    /**
     * 构造方法
     * @param ascending    排序方式
     * @param aggregations 聚合函数
     */
    private Order( boolean ascending, Collection<Aggregation> aggregations ) {
        this( ascending, true );
        if ( CollectionUtil.hasElement( aggregations ) ) {
            this.aggregations.addAll( aggregations.stream().filter( Objects::nonNull ).collect( Collectors.toList() ) );
        }
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> ascFunc( Criteria<T> criteria, String... aliases ) {
        return ascFunc( criteria, ArrayUtil.toList( aliases ) );
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> ascFunc( Criteria<T> criteria, List<String> aliases ) {
        if ( CollectionUtil.hasElement( aliases ) ) {
            return asc( aliases.stream().map( criteria::getFunction ).collect( Collectors.toList() ) );
        }
        return null;
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> asc( Aggregation... aggregations ) {
        return asc( ArrayUtil.toList( aggregations ) );
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> asc( List<Aggregation> aggregations ) {
        return new Order<>( true, aggregations );
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <T> Order<T> asc( Criteria<T> criteria, Property<T, ?>... properties ) {
        return new Order<>( criteria, true, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> asc( Criteria<T> criteria, String... properties ) {
        return asc( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> asc( Criteria<T> criteria, List<String> properties ) {
        return new Order<>( criteria, true, CriteriaUtil.transform( criteria, properties ) );
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> descFunc( Criteria<T> criteria, String... aliases ) {
        return descFunc( criteria, ArrayUtil.toList( aliases ) );
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> descFunc( Criteria<T> criteria, List<String> aliases ) {
        if ( CollectionUtil.hasElement( aliases ) ) {
            return desc( aliases.stream().map( criteria::getFunction ).collect( Collectors.toList() ) );
        }
        return null;
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> desc( Aggregation... aggregations ) {
        return desc( ArrayUtil.toList( aggregations ) );
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> desc( List<Aggregation> aggregations ) {
        return new Order<>( false, aggregations );
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <T> Order<T> desc( Criteria<T> criteria, Property<T, ?>... properties ) {
        return new Order<>( criteria, false, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> desc( Criteria<T> criteria, String... properties ) {
        return desc( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> desc( Criteria<T> criteria, List<String> properties ) {
        return new Order<>( criteria, false, CriteriaUtil.transform( criteria, properties ) );
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> asc( String alias, Criteria<T> master, String... properties ) {
        return asc( master.searchForeign( alias ), ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <E>        泛型类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <T, E> Order<E> asc( String alias, Criteria<T> master, Property<E, ?>... properties ) {
        Criteria<E> criteria = master.searchForeign( alias );
        return new Order<>( criteria, true, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> asc( String alias, Criteria<T> master, List<String> properties ) {
        return asc( master.searchForeign( alias ), properties );
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <E>        泛型类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <T, E> Order<E> desc( String alias, Criteria<T> master, Property<E, ?>... properties ) {
        Criteria<E> criteria = master.searchForeign( alias );
        return new Order<>( criteria, false, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> desc( String alias, Criteria<T> master, String... properties ) {
        return desc( master.searchForeign( alias ), properties );
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> desc( String alias, Criteria<T> master, List<String> properties ) {
        return desc( master.searchForeign( alias ), properties );
    }

    /**
     * ASC排序
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> asc( String... properties ) {
        return new Order<>( true, ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param alias      表别名
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> aliasAsc( String alias, String... properties ) {
        return new Order<>( alias, true, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> desc( String... properties ) {
        return new Order<>( false, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param alias      表别名
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> Order<T> aliasDesc( String alias, String... properties ) {
        return new Order<>( alias, false, ArrayUtil.toList( properties ) );
    }

    @Override
    public String getSqlSegment() {
        String orderMode = ascending ? " ASC" : " DESC";
        if ( this.isFunc() && CollectionUtil.hasElement( this.aggregations ) ) {
            return this.aggregations.stream().map( function -> function.toOrderSqlSegment() + orderMode )
                    .collect( Collectors.joining( ", " ) );
        } else if ( CollectionUtil.hasElement( this.columns ) ) {
            String realAlias = this.criteria != null && criteria.isEnableAlias() ? ( criteria.getAlias() + "." ) : "";
            return this.columns.stream().map( column -> realAlias + column.getColumn() + orderMode )
                    .collect( Collectors.joining( ", " ) );
        } else if ( CollectionUtil.hasElement( this.properties ) ) {
            String realAlias = StringUtil.hasText( this.alias ) ? ( this.alias + "." ) : "";
            return this.properties.stream().map( column -> realAlias + column + orderMode )
                    .collect( Collectors.joining( ", " ) );
        }
        return "";
    }
}
