package com.wkit.lost.mybatis.core.wrapper;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 排序(字符串字段)
 * @param <T> 实体类型
 * @author wvkity
 */
public class StringOrder<T> extends AbstractOrderWrapper<T, String> {

    private static final long serialVersionUID = 7837358423348936221L;

    /**
     * 表别名
     */
    @Getter
    private final String alias;

    /**
     * 构造方法
     * @param alias     表别名
     * @param ascending 排序方式(是否为ASC排序)
     * @param columns   字段集合
     */
    public StringOrder( String alias, boolean ascending, Collection<String> columns ) {
        this.alias = alias;
        this.ascending = ascending;
        this.columns = distinct( columns );
    }

    /**
     * ASC排序
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> StringOrder<T> asc( String... properties ) {
        return new StringOrder<>( null, true, ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param alias      表别名
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> StringOrder<T> ascWithAlias( String alias, String... properties ) {
        return new StringOrder<>( alias, true, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> StringOrder<T> desc( String... properties ) {
        return new StringOrder<>( null, false, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param alias      表别名
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> StringOrder<T> descWithAlias( String alias, String... properties ) {
        return new StringOrder<>( alias, false, ArrayUtil.toList( properties ) );
    }

    @Override
    public String getSqlSegment() {
        if ( notEmpty() ) {
            String orderMode = ascending ? " ASC" : " DESC";
            String realAlias = StringUtil.hasText( this.alias ) ? ( this.alias + "." ) : "";
            return this.columns.stream().map( it -> realAlias + it + orderMode )
                    .collect( Collectors.joining( ", " ) );
        }
        return "";
    }
}
