package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.core.wrapper.ColumnGroup;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
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
 * @see ColumnWrapper
 * @see ColumnGroup
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
    private Set<ColumnWrapper> columns = new LinkedHashSet<>();

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param columns  字段
     */
    private Group( Criteria<T> criteria, Collection<ColumnWrapper> columns ) {
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
        return new Group<>( criteria, CriteriaUtil.lambdaToColumn( criteria, ArrayUtil.toList( properties ) ) );
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 分组对象
     */
    public static <T> Group<T> group( Criteria<T> criteria, Collection<String> properties ) {
        return new Group<>( criteria, CriteriaUtil.propertyToColumn( criteria, properties ) );
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
    public static <T, C extends AbstractGeneralQueryCriteria<T, C>, E> Group<E> group( String alias,
                                                                                       AbstractGeneralQueryCriteria<T, C> master,
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
    @SafeVarargs
    public static <T, C extends AbstractGeneralQueryCriteria<T, C>, E> Group<E> group( String alias,
                                                                                       AbstractGeneralQueryCriteria<T, C> master,
                                                                                       Property<E, ?>... properties ) {
        Criteria<E> criteria = master.searchForeign( alias );
        return new Group<>( criteria, CriteriaUtil.lambdaToColumn( criteria, ArrayUtil.toList( properties ) ) );
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
    public static <T, C extends AbstractGeneralQueryCriteria<T, C>, E> Group<E> group( String alias,
                                                                                       AbstractGeneralQueryCriteria<T, C> master,
                                                                                       Collection<String> properties ) {
        Criteria<E> criteria = master.searchForeign( alias );
        return new Group<>( criteria, CriteriaUtil.propertyToColumn( criteria, properties ) );
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
