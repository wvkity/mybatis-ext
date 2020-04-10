package com.wkit.lost.mybatis.type.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * {@link ZonedDateTime}处理器
 * @author wvkity
 */
@MappedTypes(ZonedDateTime.class)
public class StandardZonedDateTimeTypeHandler extends BaseTypeHandler<ZonedDateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ZonedDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, Optional.ofNullable(parameter).map(ZonedDateTime::toInstant).map(Timestamp::from).orElse(null));
    }

    @Override
    public ZonedDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return valueOf(rs.getTimestamp(columnName));
    }

    @Override
    public ZonedDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return valueOf(rs.getTimestamp(columnIndex));
    }

    @Override
    public ZonedDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return valueOf(cs.getTimestamp(columnIndex));
    }

    private ZonedDateTime valueOf(Timestamp timestamp) {
        return Optional.ofNullable(timestamp).map(time -> ZonedDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault())).orElse(null);
    }
}
