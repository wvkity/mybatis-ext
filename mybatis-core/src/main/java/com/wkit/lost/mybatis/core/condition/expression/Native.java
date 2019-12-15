package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * 纯SQL条件
 * @param <T> 泛型类型
 * @author wvkity
 */
@Accessors( chain = true )
@NoArgsConstructor
public class Native<T> extends AbstractExpression<T> {

    private static final long serialVersionUID = 6499182721850306850L;

    /**
     * SQL片段
     */
    @Getter
    @Setter
    private String sql;

    /**
     * 构造方法
     * @param sql SQL片段
     */
    public Native( String sql ) {
        this.sql = sql;
    }

    @Override
    public String getSqlSegment() {
        return StringUtil.isBlank( this.sql ) ? "" : this.sql;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof Native ) ) return false;
        if ( !super.equals( o ) ) return false;
        Native<?> aNative = ( Native<?> ) o;
        return Objects.equals( sql, aNative.sql );
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), sql );
    }

    @Override
    protected String toJsonString() {
        return toJsonString( "sql", sql );
    }
}
