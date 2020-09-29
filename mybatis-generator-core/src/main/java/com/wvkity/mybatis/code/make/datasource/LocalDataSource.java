package com.wvkity.mybatis.code.make.datasource;

import com.alibaba.fastjson.JSON;
import com.wvkity.mybatis.code.make.bean.ConnectConfig;
import com.wvkity.mybatis.code.make.config.ConnectConfiguration;
import com.wvkity.mybatis.code.make.config.SystemConfiguration;
import com.wvkity.mybatis.code.make.utils.KeyUtil;
import com.wvkity.mybatis.code.make.constant.DatabaseType;
import com.wvkity.mybatis.code.make.encrypt.AESEncrypt;
import com.wvkity.mybatis.code.make.jdbc.JdbcTemplate;
import com.wvkity.mybatis.code.make.utils.StringUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 本地数据源
 * @author wvkity
 */
public final class LocalDataSource {
    private LocalDataSource() {
    }

    private static final String CONNECT_CONFIG_SELECT_SQL =
            "SELECT ID, NAME, CONFIG, GMT_CREATED, GMT_LAST_MODIFIED FROM CONNECT_CONFIG";
    private static final String CONNECT_CONFIG_INSERT_SQL =
            "INSERT INTO CONNECT_CONFIG(NAME, CONFIG, GMT_CREATED, GMT_LAST_MODIFIED) VALUES(?, ?, ?, ?)";
    private static final String CONNECT_CONFIG_UPDATE_SQL =
            "UPDATE CONNECT_CONFIG SET NAME = ?, CONFIG = ?, GMT_LAST_MODIFIED = ? WHERE ID = ?";

    /**
     * 获取数据库连接
     * @return {@link Connection}
     * @throws SQLException 异常信息
     */
    private static Connection getConnection() throws SQLException {
        return DataSourceCache.getConnection(DatabaseType.SQLITE, SystemConfiguration.getLocalDatabaseUrl(),
                null, null, null, null, null);
    }

    /**
     * 获取数据库连接
     * @param config {@link ConnectConfiguration}
     * @return {@link Connection}
     * @throws SQLException 异常信息
     */
    private static Connection getConnection(final ConnectConfiguration config) throws SQLException {
        return DataSourceCache.getConnection(config);
    }

    /**
     * 保存配置
     * @param config {@link ConnectConfig}
     * @return 受影响行数
     * @throws SQLException 异常信息
     */
    public static int save(final ConnectConfig config) throws SQLException {
        try (final Connection connection = getConnection()) {
            return JdbcTemplate.update(getConnection(), CONNECT_CONFIG_INSERT_SQL, config.getName(),
                    config.getConfig(), config.getGmtCreated(), config.getGmtLastModified());
        }
    }

    /**
     * 加载数据库连接配置列表
     * @return {@link ConnectConfig}列表
     * @throws SQLException 异常信息
     */
    public static List<ConnectConfiguration> loadConnections() throws SQLException {
        try (final Connection connection = getConnection()) {
            final List<ConnectConfig> configs = JdbcTemplate.list(ConnectConfig.class, connection,
                    CONNECT_CONFIG_SELECT_SQL);
            if (configs.isEmpty()) {
                return new ArrayList<>(0);
            }
            return configs.stream().filter(Objects::nonNull).map(LocalDataSource::convert).filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }

    private static ConnectConfiguration convert(final ConnectConfig config) {
        try {
            final String key = KeyUtil.generated(config.getGmtLastModified());
            final ConnectConfiguration it = JSON.parseObject(config.getConfig(), ConnectConfiguration.class);
            if (StringUtil.isNotEmpty(it.getUserName())) {
                it.setUserName(AESEncrypt.decrypt(key, it.getUserName()));
            }
            if (StringUtil.isNotEmpty(it.getPassword())) {
                it.setPassword(AESEncrypt.decrypt(key, it.getPassword()));
            }
            return it;
        } catch (Exception ignore) {
            // ignore
        }
        return null;
    }

    /**
     * 加载数据库列表
     * @param configuration {@link ConnectConfiguration}
     * @return 数据库列表
     * @throws SQLException 异常信息
     */
    public static List<String> loadSchemas(final ConnectConfiguration configuration) throws SQLException {
        final DatabaseType db = DatabaseType.valueOf(configuration.getDbType());
        if (db == DatabaseType.SQLITE) {
            return new ArrayList<>(Collections.singletonList("sqlite_master"));
        }
        try (final Connection connection = getConnection(configuration)) {
            return JdbcTemplate.schemas(connection);
        }
    }

}
