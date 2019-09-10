package com.wkit.lost.mybatis.type.handlers;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义枚举属性处理器
 * @param <E> 枚举类
 * @author DT
 */
@Log4j2
public class EnumTypeHandler<E extends Enum<E> & EnumSupport> extends BaseTypeHandler<EnumSupport> {

    private Class<E> type;

    private E[] enums;

    public EnumTypeHandler( Class<E> type ) {
        if ( type == null ) {
            throw new IllegalArgumentException( "Type argument cannot be null" );
        }
        this.type = type;
        this.enums = this.type.getEnumConstants();
        if ( this.enums == null ) {
            throw new IllegalArgumentException( this.type.getSimpleName() + "does not represent an enum type." );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter( PreparedStatement ps, int i, EnumSupport parameter, JdbcType jdbcType ) throws SQLException {
        log.debug( "index : {}, parameter : {},jdbcType : {} ", i, parameter.getValue(), jdbcType );
        if ( jdbcType == null ) {
            ps.setObject( i, parameter.getValue() );
        } else {
            ps.setObject( i, parameter.getValue(), jdbcType.TYPE_CODE );
        }
    }

    @Override
    public EnumSupport getNullableResult( ResultSet rs, String columnName ) throws SQLException {
        if ( rs.getString( columnName ) == null && rs.wasNull() ) {
            return null;
        }
        return valueOf( rs.getObject( columnName ) );
    }

    @Override
    public EnumSupport getNullableResult( ResultSet rs, int columnIndex ) throws SQLException {
        if ( rs.getString( columnIndex ) == null && rs.wasNull() ) {
            return null;
        }
        return valueOf( rs.getObject( columnIndex ) );
    }

    @Override
    public EnumSupport getNullableResult( CallableStatement cs, int columnIndex ) throws SQLException {
        if ( cs.getString( columnIndex ) == null && cs.wasNull() ) {
            return null;
        }
        return valueOf( cs.getObject( columnIndex ) );
    }

    /**
     * 将值映射为枚举
     * @param value 枚举值
     * @return 枚举类
     */
    protected E valueOf( Object value ) {
        for ( E e : enums ) {
            if ( e.getValue() == value ) {
                return e;
            } else if ( value instanceof Number ) {
                if ( e.getValue() instanceof Number &&
                        ( ( Number ) value ).doubleValue() == ( ( Number ) e.getValue() ).doubleValue() ) {
                    return e;
                }
            } else if ( String.valueOf( value ).equals( String.valueOf( e.getValue() ) ) ) {
                return e;
            }
        }
        return null;
    }

    public Class<E> getType() {
        return this.type;
    }
}
