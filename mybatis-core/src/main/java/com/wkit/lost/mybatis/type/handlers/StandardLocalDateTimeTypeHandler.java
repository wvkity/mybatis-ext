package com.wkit.lost.mybatis.type.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * {@link LocalDateTime}处理器
 * @author wvkity
 */
@MappedTypes( LocalDateTime.class )
public class StandardLocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    @Override
    public void setNonNullParameter( PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType ) throws SQLException {
        ps.setTimestamp( i, Optional.ofNullable( parameter ).map( Timestamp::valueOf ).orElse( null ) );
    }

    @Override
    public LocalDateTime getNullableResult( ResultSet rs, String columnName ) throws SQLException {
        return valueOf( rs.getTimestamp( columnName ) );
    }

    @Override
    public LocalDateTime getNullableResult( ResultSet rs, int columnIndex ) throws SQLException {
        return valueOf( rs.getTimestamp( columnIndex ) );
    }

    @Override
    public LocalDateTime getNullableResult( CallableStatement cs, int columnIndex ) throws SQLException {
        return valueOf( cs.getTimestamp( columnIndex ) );
    }

    public LocalDateTime valueOf( Timestamp timestamp ) {
        return Optional.ofNullable( timestamp ).map( Timestamp::toLocalDateTime ).orElse( null );
    }
}
