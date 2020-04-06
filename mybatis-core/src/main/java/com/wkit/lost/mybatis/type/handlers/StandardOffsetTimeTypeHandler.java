package com.wkit.lost.mybatis.type.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.OffsetTime;
import java.util.Optional;

/**
 * {@link OffsetTime}处理器
 * @author wvkity
 */
public class StandardOffsetTimeTypeHandler extends BaseTypeHandler<OffsetTime> {

    @Override
    public void setNonNullParameter( PreparedStatement ps, int i, OffsetTime parameter, JdbcType jdbcType ) throws SQLException {
        ps.setTime( i, Optional.ofNullable( parameter ).map( time -> Time.valueOf( time.toLocalTime() ) ).orElse( null ) );
    }

    @Override
    public OffsetTime getNullableResult( ResultSet rs, String columnName ) throws SQLException {
        return valueOf( rs.getTime( columnName ) );
    }

    @Override
    public OffsetTime getNullableResult( ResultSet rs, int columnIndex ) throws SQLException {
        return valueOf( rs.getTime( columnIndex ) );
    }

    @Override
    public OffsetTime getNullableResult( CallableStatement cs, int columnIndex ) throws SQLException {
        return valueOf( cs.getTime( columnIndex ) );
    }

    public OffsetTime valueOf( Time time ) {
        return Optional.ofNullable( time ).map( value -> value.toLocalTime().atOffset( OffsetTime.now().getOffset() ) ).orElse( null );
    }
}
