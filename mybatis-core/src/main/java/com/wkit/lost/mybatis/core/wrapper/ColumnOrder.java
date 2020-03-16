package com.wkit.lost.mybatis.core.wrapper;

import com.wkit.lost.mybatis.core.criteria.AbstractGeneralQueryCriteria;
import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.CriteriaUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 排序(字段包装对象)
 * @param <T> 实体类型
 * @author wvkity
 */
public class ColumnOrder<T> extends AbstractOrderWrapper<T, ColumnWrapper> {

    private static final long serialVersionUID = -7981752420346514008L;

    /**
     * 构造方法
     * @param criteria  查询对象
     * @param columns   字段集合
     * @param ascending 排序方式(是否为ASC排序)
     */
    private ColumnOrder( Criteria<T> criteria, boolean ascending, Collection<ColumnWrapper> columns ) {
        if ( criteria == null ) {
            throw new MyBatisException( "The Criteria object cannot be empty" );
        }
        this.criteria = criteria;
        this.ascending = ascending;
        if ( CollectionUtil.hasElement( columns ) ) {
            this.columns.addAll( columns.stream().filter( Objects::nonNull ).collect( Collectors.toList() ) );
        }
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <T> ColumnOrder<T> asc( Criteria<T> criteria, Property<T, ?>... properties ) {
        return new ColumnOrder<>( criteria, true, CriteriaUtil.lambdaToColumn( criteria, ArrayUtil.toList( properties ) ) );
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> ColumnOrder<T> asc( Criteria<T> criteria, String... properties ) {
        return asc( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> ColumnOrder<T> asc( Criteria<T> criteria, List<String> properties ) {
        return new ColumnOrder<>( criteria, true,
                CriteriaUtil.propertyToColumn( criteria, distinct( properties ) ) );
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T, C extends AbstractGeneralQueryCriteria<T, C>> ColumnOrder<T> asc( String alias,
                                                                                        AbstractGeneralQueryCriteria<T, C> master,
                                                                                        String... properties ) {
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
    public static <T, C extends AbstractGeneralQueryCriteria<T, C>, E> ColumnOrder<E> asc( String alias,
                                                                                           AbstractGeneralQueryCriteria<T, C> master,
                                                                                           Property<E, ?>... properties ) {
        Criteria<E> criteria = master.searchForeign( alias );
        return new ColumnOrder<>( criteria, true, CriteriaUtil.lambdaToColumn( criteria, ArrayUtil.toList( properties ) ) );
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T, C extends AbstractGeneralQueryCriteria<T, C>> ColumnOrder<T> asc( String alias,
                                                                                        AbstractGeneralQueryCriteria<T, C> master,
                                                                                        List<String> properties ) {
        return asc( master.searchForeign( alias ), properties );
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <T> ColumnOrder<T> desc( Criteria<T> criteria, Property<T, ?>... properties ) {
        return new ColumnOrder<>( criteria, false, CriteriaUtil.lambdaToColumn( criteria, ArrayUtil.toList( properties ) ) );
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> ColumnOrder<T> desc( Criteria<T> criteria, String... properties ) {
        return desc( criteria, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> ColumnOrder<T> desc( Criteria<T> criteria, List<String> properties ) {
        return new ColumnOrder<>( criteria, false,
                CriteriaUtil.propertyToColumn( criteria, distinct( properties ) ) );
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
    public static <T, C extends AbstractGeneralQueryCriteria<T, C>, E> ColumnOrder<E> desc( String alias,
                                                                                            AbstractGeneralQueryCriteria<T, C> master,
                                                                                            Property<E, ?>... properties ) {
        Criteria<E> criteria = master.searchForeign( alias );
        return new ColumnOrder<>( criteria, false, CriteriaUtil.lambdaToColumn( criteria, ArrayUtil.toList( properties ) ) );
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T, C extends AbstractGeneralQueryCriteria<T, C>> ColumnOrder<T> desc( String alias,
                                                                                         AbstractGeneralQueryCriteria<T, C> master,
                                                                                         String... properties ) {
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
    public static <T, C extends AbstractGeneralQueryCriteria<T, C>> ColumnOrder<T> desc( String alias,
                                                                                         AbstractGeneralQueryCriteria<T, C> master,
                                                                                         List<String> properties ) {
        return desc( master.searchForeign( alias ), properties );
    }

    @Override
    public String getSqlSegment() {
        if ( notEmpty() ) {
            String orderMode = ascending ? " ASC" : " DESC";
            String realAlias = this.criteria != null && criteria.isEnableAlias() ? ( criteria.getAlias() + "." ) : "";
            return this.columns.stream().map( it -> realAlias + it.getColumn() + orderMode )
                    .collect( Collectors.joining( ", " ) );
        }
        return "";
    }
}
