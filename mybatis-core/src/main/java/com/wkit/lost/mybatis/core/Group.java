package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.lambda.Property;
import lombok.Getter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分组
 * @param <T> 泛型类型
 * @author wvkity
 */
public class Group<T> implements Segment {

    private static final long serialVersionUID = 1194673118767609594L;

    /**
     * 条件对象
     */
    @Getter
    private Criteria<T> criteria;

    /**
     * 分组字段
     */
    @Getter
    private Set<Column> columns = new LinkedHashSet<>();

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param columns  字段
     */
    private Group( Criteria<T> criteria, Collection<Column> columns ) {
        if ( criteria == null ) {
            throw new MyBatisException( "The Criteria object cannot be empty" );
        }
        this.criteria = criteria;
        if ( CollectionUtil.hasElement( columns ) ) {
            this.columns.addAll( columns.stream().filter( Objects::nonNull ).collect( Collectors.toList() ) );
        }
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 分组对象
     */
    public static <T> Group<T> group( Criteria<T> criteria, String... properties ) {
        return group( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 分组对象
     */
    @SafeVarargs
    public static <T> Group<T> group( Criteria<T> criteria, Property<T, ?>... properties ) {
        return new Group<>( criteria, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 分组对象
     */
    public static <T> Group<T> group( Criteria<T> criteria, Collection<String> properties ) {
        return new Group<>( criteria, CriteriaUtil.transform( criteria, properties ) );
    }

    /**
     * 分组
     * @param alias      联表对象别名
     * @param master     条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 分组对象
     */
    public static <T, E> Group<E> group( String alias, Criteria<T> master, String... properties ) {
        return group( alias, master, ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param alias      联表对象别名
     * @param master     条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 分组对象
     */
    @SafeVarargs
    public static <T, E> Group<E> group( String alias, Criteria<T> master, Property<E, ?>... properties ) {
        Criteria<E> criteria = master.searchForeign( alias );
        return new Group<>( criteria, CriteriaUtil.transform( ArrayUtil.toList( properties ), criteria ) );
    }

    /**
     * 分组
     * @param alias      联表对象别名
     * @param master     条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 分组对象
     */
    public static <T, E> Group<E> group( String alias, Criteria<T> master, Collection<String> properties ) {
        Criteria<E> criteria = master.searchForeign( alias );
        return new Group<>( criteria, CriteriaUtil.transform( criteria, properties ) );
    }

    @Override
    public String getSqlSegment() {
        if ( CollectionUtil.hasElement( columns ) ) {
            String alias = criteria.isEnableAlias() ? ( criteria.getAlias() + "." ) : "";
            return columns.stream().map( column -> alias + column.getColumn() ).collect( Collectors.joining( ", " ) );
        }
        return "";
    }
}
