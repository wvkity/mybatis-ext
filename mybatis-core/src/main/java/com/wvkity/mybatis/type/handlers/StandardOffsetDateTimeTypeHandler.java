package com.wvkity.mybatis.type.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

/**
 * {@link OffsetDateTime}处理器
 * @author wvkity
 */
@MappedTypes(OffsetDateTime.class)
public class StandardOffsetDateTimeTypeHandler extends BaseTypeHandler<OffsetDateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OffsetDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, Optional.ofNullable(parameter).map(OffsetDateTime::toInstant).map(Timestamp::from).orElse(null));
    }

    @Override
    public OffsetDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return valueOf(rs.getTimestamp(columnName));
    }

    @Override
    public OffsetDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return valueOf(rs.getTimestamp(columnIndex));
    }

    @Override
    public OffsetDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return valueOf(cs.getTimestamp(columnIndex));
    }

    private OffsetDateTime valueOf(Timestamp timestamp) {
        return Optional.ofNullable(timestamp).map(time -> OffsetDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault())).orElse(null);
    }
}
