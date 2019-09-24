package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.lambda.Property;
import lombok.Getter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 排序类
 * @author DT
 */
public class Order<T> implements Segment {

    private static final long serialVersionUID = 8376229254028005315L;

    /**
     * 查询对象
     */
    @Getter
    private Criteria<T> criteria;

    /**
     * 是否为聚合函数
     */
    @Getter
    private boolean func;

    /**
     * 排序字段
     */
    @Getter
    private Set<Column> columns = new LinkedHashSet<>( 8 );

    /**
     * 排序聚合函数
     */
    @Getter
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
     * @param criteria  查询对象
     * @param columns   列
     * @param ascending 排序方式
     */
    private Order( Criteria<T> criteria, boolean ascending, Collection<Column> columns ) {
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
    public static <T> Order ascFunc( Criteria<T> criteria, String... aliases ) {
        return ascFunc( criteria, ArrayUtil.toList( aliases ) );
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> Order ascFunc( Criteria<T> criteria, List<String> aliases ) {
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
    public static <T> Order asc( Aggregation... aggregations ) {
        return asc( ArrayUtil.toList( aggregations ) );
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> Order asc( List<Aggregation> aggregations ) {
        return new Order<>( true, aggregations );
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    @SafeVarargs
    public static <T> Order asc( Criteria<T> criteria, Property<T, ?>... properties ) {
        return new Order<>( criteria, true, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static <T> Order asc( Criteria<T> criteria, String... properties ) {
        return asc( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static <T> Order asc( Criteria<T> criteria, List<String> properties ) {
        return new Order<>( criteria, true, CriteriaUtil.transform( criteria, properties ) );
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> Order descFunc( Criteria<T> criteria, String... aliases ) {
        return descFunc( criteria, ArrayUtil.toList( aliases ) );
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> Order descFunc( Criteria<T> criteria, List<String> aliases ) {
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
    public static <T> Order desc( Aggregation... aggregations ) {
        return asc( ArrayUtil.toList( aggregations ) );
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> Order desc( List<Aggregation> aggregations ) {
        return new Order<>( false, aggregations );
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    @SafeVarargs
    public static <T> Order desc( Criteria<T> criteria, Property<T, ?>... properties ) {
        return new Order<>( criteria, false, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static <T> Order desc( Criteria<T> criteria, String... properties ) {
        return desc( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static <T> Order desc( Criteria<T> criteria, List<String> properties ) {
        return new Order<>( criteria, false, CriteriaUtil.transform( criteria, properties ) );
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static <T> Order asc( String alias, Criteria<T> master, String... properties ) {
        return asc( master.search( alias ), ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    @SafeVarargs
    public static <T, E> Order asc( String alias, Criteria<T> master, Property<E, ?>... properties ) {
        Criteria<E> criteria = master.search( alias );
        return new Order<>( criteria, true, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static <T> Order asc( String alias, Criteria<T> master, List<String> properties ) {
        return asc( master.search( alias ), properties );
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    @SafeVarargs
    public static <T, E> Order desc( String alias, Criteria<T> master, Property<E, ?>... properties ) {
        Criteria<E> criteria = master.search( alias );
        return new Order<>( criteria, false, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static <T> Order desc( String alias, Criteria<T> master, String... properties ) {
        return desc( master.search( alias ), properties );
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static <T> Order desc( String alias, Criteria<T> master, List<String> properties ) {
        return desc( master.search( alias ), properties );
    }

    @Override
    public String getSqlSegment() {
        return "";
    }
}
