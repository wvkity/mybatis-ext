package com.wkit.lost.mybatis.type.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

/**
 * {@link Instant}处理器
 * @author wvkity
 */
@MappedTypes( Instant.class )
public class StandardInstantTypeHandler extends BaseTypeHandler<Instant> {

    @Override
    public void setNonNullParameter( PreparedStatement ps, int i, Instant parameter, JdbcType jdbcType ) throws SQLException {
        ps.setTimestamp( i, Optional.ofNullable( parameter ).map( Timestamp::from ).orElse( null ) );
    }

    @Override
    public Instant getNullableResult( ResultSet rs, String columnName ) throws SQLException {
        return valueOf( rs.getTimestamp( columnName ) );
    }

    @Override
    public Instant getNullableResult( ResultSet rs, int columnIndex ) throws SQLException {
        return valueOf( rs.getTimestamp( columnIndex ) );
    }

    @Override
    public Instant getNullableResult( CallableStatement cs, int columnIndex ) throws SQLException {
        return valueOf( cs.getTimestamp( columnIndex ) );
    }

    private Instant valueOf( Timestamp timestamp ) {
        return Optional.ofNullable( timestamp ).map( Timestamp::toInstant ).orElse( null );
    }
}
