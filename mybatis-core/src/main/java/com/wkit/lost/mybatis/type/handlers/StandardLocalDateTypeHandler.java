package com.wkit.lost.mybatis.type.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * {@link LocalDate}处理器
 * @author DT
 */
@MappedTypes( LocalDate.class )
public class StandardLocalDateTypeHandler extends BaseTypeHandler<LocalDate> {
    
    @Override
    public void setNonNullParameter( PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType ) throws SQLException {
        ps.setDate( i, Optional.ofNullable( parameter ).map( Date::valueOf ).orElse( null ) );
    }

    @Override
    public LocalDate getNullableResult( ResultSet rs, String columnName ) throws SQLException {
        return valueOf( rs.getDate( columnName ) );
    }

    @Override
    public LocalDate getNullableResult( ResultSet rs, int columnIndex ) throws SQLException {
        return valueOf( rs.getDate( columnIndex ) );
    }

    @Override
    public LocalDate getNullableResult( CallableStatement cs, int columnIndex ) throws SQLException {
        return valueOf( cs.getDate( columnIndex ) );
    }
    
    private LocalDate valueOf( Date date ) {
        return Optional.ofNullable( date ).map( Date::toLocalDate ).orElse( null );
    }
}
