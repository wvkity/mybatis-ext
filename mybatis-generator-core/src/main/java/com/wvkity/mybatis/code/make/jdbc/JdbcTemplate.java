package com.wvkity.mybatis.code.make.jdbc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC工具
 * @author wvkity
 */
@Log4j2
public final class JdbcTemplate {
    private JdbcTemplate() {
    }

    /**
     * 执行更新操作
     * @param connection {@link Connection}
     * @param sql        SQL语句
     * @param args       参数
     * @return 受影响行数
     */
    public static int update(final Connection connection, final String sql, final Object... args) throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            if (args != null && args.length > 0) {
                for (int i = 0, size = args.length; i < size; i++) {
                    statement.setObject((i + 1), args[i]);
                }
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("`{}`执行失败: {}", sql, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 查询列表
     * @param clazz      指定返回类型
     * @param connection {@link Connection}
     * @param sql        SQL
     * @param args       参数
     * @param <T>        泛型类型
     * @return 列表
     * @throws SQLException 异常信息
     */
    public static <T> List<T> list(final Class<T> clazz, final Connection connection,
                                   final String sql, final Object... args) throws SQLException {
        final List<Map<String, Object>> result = list(connection, sql, args);
        if (!result.isEmpty()) {
            return JSONArray.parseArray(JSON.toJSONString(result), clazz);
        }
        return new ArrayList<>(0);
    }

    /**
     * 查询列表
     * @param connection {@link Connection}
     * @param sql        SQL
     * @param args       参数
     * @return 列表
     * @throws SQLException 异常信息
     */
    public static List<Map<String, Object>> list(final Connection connection, final String sql,
                                                 final Object... args) throws SQLException {
        return select(connection, sql, args);
    }

    /**
     * 查询
     * @param connection {@link Connection}
     * @param sql        SQL
     * @param args       参数
     * @return 多个值
     * @throws SQLException 异常信息
     */
    private static List<Map<String, Object>> select(final Connection connection, final String sql,
                                                    final Object... args) throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            if (args != null && args.length > 0) {
                for (int i = 0, size = args.length; i < size; i++) {
                    statement.setObject((i + 1), args[i]);
                }
            }
            final List<Map<String, Object>> result = new ArrayList<>();
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    final Map<String, Object> rest = new HashMap<>();
                    final ResultSetMetaData metaData = rs.getMetaData();
                    for (int i = 1, size = metaData.getColumnCount(); i <= size; i++) {
                        final String key = metaData.getColumnName(i);
                        rest.put(key, rs.getObject(key));
                    }
                    result.add(rest);
                }
            }
            return result;
        } catch (SQLException e) {
            log.error("`{}`执行失败: {}", sql, e.getMessage(), e);
            throw e;
        }
    }

    public static List<String> schemas(final Connection connection) throws SQLException {
        final DatabaseMetaData it = connection.getMetaData();
        try (final ResultSet rt = it.getCatalogs()) {
            final List<String> schemas = new ArrayList<>();
            while (rt.next()) {
                schemas.add(rt.getString("TABLE_CAT"));
            }
            return schemas;
        } catch (SQLException e) {
            throw e;
        }
    }
}
