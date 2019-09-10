package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 纯SQL条件
 * @param <T> 泛型类型
 * @author DT
 */
@Accessors( chain = true )
@NoArgsConstructor
public class NativeExpression<T> extends AbstractExpression<T> {

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
    public NativeExpression( String sql ) {
        this.sql = sql;
    }

    @Override
    public String getSqlSegment() {
        return StringUtil.isBlank( this.sql ) ? "" : this.sql;
    }
}
