package com.wkit.lost.mybatis.core.wrapper.basic;

import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.AbstractQueryCriteriaWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.core.wrapper.criteria.ForeignCriteria;
import com.wkit.lost.mybatis.core.wrapper.utils.CriteriaUtil;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 分组(字段包装对象)
 * @param <T> 泛型类型
 * @author wvkity
 * @see ColumnWrapper
 */
public class Group<T> extends AbstractGroupWrapper<T, ColumnWrapper> {

    private static final long serialVersionUID = -579446881939269926L;

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param columns  字段集合
     */
    public Group( Criteria<T> criteria, Collection<ColumnWrapper> columns ) {
        if ( criteria == null ) {
            throw new MyBatisException( "The Criteria object cannot be empty" );
        }
        this.criteria = criteria;
        if ( CollectionUtil.hasElement( columns ) ) {
            this.columns = new LinkedHashSet<>( this.columns );
            this.columns.addAll( columns.stream().filter( Objects::nonNull ).collect( Collectors.toList() ) );
        }
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性数组
     * @param <T>        泛型类型
     * @return 分组对象
     */
    public static <T> Group<T> group( Criteria<T> criteria, String... properties ) {
        return group( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性数组
     * @param <T>        泛型类型
     * @return 分组对象
     */
    @SafeVarargs
    public static <T> Group<T> group( Criteria<T> criteria, Property<T, ?>... properties ) {
        return new Group<>( criteria, CriteriaUtil.lambdaToColumn( criteria, ArrayUtil.toList( properties ) ) );
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性集合
     * @param <T>        泛型类型
     * @return 分组对象
     */
    public static <T> Group<T> group( Criteria<T> criteria, Collection<String> properties ) {
        return new Group<>( criteria, CriteriaUtil.propertyToColumn( criteria, distinct( properties ) ) );
    }

    /**
     * 分组
     * @param alias      联表对象别名
     * @param master     条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <E>        泛型类型
     * @return 分组对象
     */
    public static <T, E> Group<E> group( String alias, AbstractQueryCriteriaWrapper<T> master,
                                         String... properties ) {
        return group( alias, master, ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param alias      联表对象别名
     * @param master     条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <E>        泛型类型
     * @return 分组对象
     */
    @SuppressWarnings( { "unchecked" } )
    public static <T, E> Group<E> group( String alias, AbstractQueryCriteriaWrapper<T> master,
                                         Property<E, ?>... properties ) {
        ForeignCriteria<E> criteria = master.searchForeign( alias );
        return new Group<E>( criteria, CriteriaUtil.lambdaToColumn( criteria, ArrayUtil.toList( properties ) ) );
    }

    /**
     * 分组
     * @param alias      联表对象别名
     * @param master     条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <E>        泛型类型
     * @return 分组对象
     */
    @SuppressWarnings( { "unchecked" } )
    public static <T, E> Group<E> group( String alias, AbstractQueryCriteriaWrapper<T> master,
                                         Collection<String> properties ) {
        ForeignCriteria<?> criteria = master.searchForeign( alias );
        return new Group<E>( criteria, CriteriaUtil.propertyToColumn( criteria, distinct( properties ) ) );
    }

    @Override
    public String getSegment() {
        if ( notEmpty() ) {
            String alias = criteria.isEnableAlias() ? ( criteria.getAlias() + "." ) : "";
            return columns.stream().map( column -> alias + column.getColumn() ).collect( Collectors.joining( ", " ) );
        }
        return "";
    }
}
