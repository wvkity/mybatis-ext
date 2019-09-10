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
import java.time.chrono.JapaneseDate;
import java.util.Optional;

/**
 * {@link JapaneseDate}处理器
 * @author DT
 */
@MappedTypes( JapaneseDate.class )
public class StandardJapaneseDateTypeHandler extends BaseTypeHandler<JapaneseDate> {

    @Override
    public void setNonNullParameter( PreparedStatement ps, int i, JapaneseDate parameter, JdbcType jdbcType ) throws SQLException {
        ps.setDate( i, Optional.ofNullable( parameter ).map( time -> Date.valueOf( LocalDate.ofEpochDay( time.toEpochDay() ) ) ).orElse( null ) );
    }

    @Override
    public JapaneseDate getNullableResult( ResultSet rs, String columnName ) throws SQLException {
        return valueOf( rs.getDate( columnName ) );
    }

    @Override
    public JapaneseDate getNullableResult( ResultSet rs, int columnIndex ) throws SQLException {
        return valueOf( rs.getDate( columnIndex ) );
    }

    @Override
    public JapaneseDate getNullableResult( CallableStatement cs, int columnIndex ) throws SQLException {
        return valueOf( cs.getDate( columnIndex ) );
    }

    private JapaneseDate valueOf( Date date ) {
        return Optional.ofNullable( date ).map( time -> JapaneseDate.from( date.toLocalDate() ) ).orElse( null );
    }
}
