package com.wvkity.mybatis.code.make.datasource;

import com.wvkity.mybatis.code.make.config.ConnectConfiguration;
import com.wvkity.mybatis.code.make.constant.DatabaseType;
import com.wvkity.mybatis.code.make.utils.StringUtil;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源缓存
 */
@Log4j2
public final class DataSourceCache {

    private DataSourceCache() {
    }

    /**
     * 驱动缓存
     */
    private static final Map<DatabaseType, Driver> DRIVER_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取数据库连接
     * @param config 数据库连接配置
     * @return {@link Connection}
     * @throws SQLException 异常信息
     */
    public static Connection getConnection(final ConnectConfiguration config) throws SQLException {
        return getConnection(DatabaseType.valueOf(config.getDbType()), config.getSchema(), config.getHost(),
                config.getPort(), config.getUserName(), config.getPassword(), config.getEncoding());
    }

    /**
     * 获取数据库连接
     * @param config        数据库连接配置
     * @param defaultSchema 默认数据库
     * @return {@link Connection}
     * @throws SQLException 异常信息
     */
    public static Connection getConnection(final ConnectConfiguration config, final String defaultSchema) throws SQLException {
        return getConnection(DatabaseType.valueOf(config.getDbType()),
                Optional.ofNullable(config.getSchema()).filter(StringUtil::isNotEmpty).orElse(defaultSchema),
                config.getHost(), config.getPort(), config.getUserName(), config.getPassword(), config.getEncoding());
    }

    /**
     * 获取数据库连接
     * @param type     数据库类型
     * @param schema   数据库名称
     * @param host     主机
     * @param port     端口
     * @param userName 用户名
     * @param password 密码
     * @param encoding 编码
     * @return {@link Connection}
     * @throws SQLException 异常信息
     */
    public static Connection getConnection(final DatabaseType type, final String schema, final String host,
                                           final String port, final String userName, final String password,
                                           final String encoding) throws SQLException {
        if (!DRIVER_CACHE.containsKey(type)) {
            loadDriver(type);
        }
        final String template = type.connectTemplate();
        final List<String> args = new ArrayList<>(8);
        if (StringUtil.isNotNull(host)) {
            args.add(host);
        }
        if (StringUtil.isNotNull(port)) {
            args.add(port);
        }
        if (StringUtil.isNotNull(schema)) {
            args.add(schema);
        }
        if (StringUtil.isNotNull(encoding) && !"none".equals(encoding)) {
            args.add(encoding);
        }
        final String jdbcUrl = String.format(template, args.toArray());
        final Properties props = new Properties();
        if (StringUtil.isNotEmpty(userName)) {
            props.setProperty("user", userName);
        }
        if (StringUtil.isNotEmpty(password)) {
            props.setProperty("password", password);
        }
        DriverManager.setLoginTimeout(5);
        final Driver driver = DRIVER_CACHE.get(type);
        if (driver != null) {
            return driver.connect(jdbcUrl, props);
        }
        return null;
    }


    /**
     * 加载数据库驱动
     * @param type 数据库类型
     */
    private static void loadDriver(final DatabaseType type) {
        try {
            final Class<?> clazz = Class.forName(type.driverClass(), true,
                    Thread.currentThread().getContextClassLoader());
            DRIVER_CACHE.put(type, (Driver) clazz.newInstance());
        } catch (Exception e) {
            log.error("Database driven load failed: {}", e.getMessage(), e);
            throw new RuntimeException("Database driven load failed: " + type.driverClass(), e);
        }
    }
}
