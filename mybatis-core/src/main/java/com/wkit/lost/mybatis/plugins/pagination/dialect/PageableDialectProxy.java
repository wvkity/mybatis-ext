package com.wkit.lost.mybatis.plugins.pagination.dialect;

import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisConfiguration;
import com.wkit.lost.mybatis.plugins.pagination.dialect.exact.MySqlDialect;
import com.wkit.lost.mybatis.plugins.pagination.dialect.exact.OracleSqlDialect;
import com.wkit.lost.mybatis.plugins.pagination.exception.PageableException;
import org.apache.ibatis.mapping.MappedStatement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 分页方言代理类
 * @author DT
 */
public class PageableDialectProxy {

    /**
     * 注册分页方言
     */
    private static final Map<String, Class<? extends Dialect>> DIALECT_ALIAS_REGISTRY = new ConcurrentHashMap<>();

    /**
     * 分页方言对象缓存(从JDBC中读取)
     */
    private Map<String, AbstractPageableDialect> JDBC_PAGEABLE_DIALECT_CACHE = new ConcurrentHashMap<>();

    /**
     * 分页方言对象缓存(从指定方言读取)
     */
    private Map<String, AbstractPageableDialect> SPECIFIED_PAGEABLE_DIALECT_CACHE = new ConcurrentHashMap<>();

    /**
     * 分页方言本地线程对象
     */
    private ThreadLocal<AbstractPageableDialect> delegateThreadLocal = new ThreadLocal<>();

    /**
     * 属性
     */
    private Properties properties;

    /**
     * 数据库方言
     */
    private String dialect;

    /**
     * 是否自动获取数据库方言
     */
    protected boolean autoRuntimeDialect;

    /**
     * 所属
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 抽象分页方言
     */
    private AbstractPageableDialect delegate;

    static {
        // MYSQL
        register( "MYSQL", MySqlDialect.class );
        // MARIADB
        register( "MARIADB", MySqlDialect.class );
        // SQLITE
        register( "SQLITE", MySqlDialect.class );
        // ORACLE
        register( "ORACLE", OracleSqlDialect.class );
    }

    /**
     * 注册分页方言
     * @param alias        别名
     * @param dialectClass 数据库分页方言类
     */
    public static void register( final String alias, Class<? extends Dialect> dialectClass ) {
        DIALECT_ALIAS_REGISTRY.put( alias, dialectClass );
    }

    /**
     * 初始化数据库分页方言
     * @param statement {@link MappedStatement}
     */
    public void initDelegateDialect( MappedStatement statement ) {
        if ( this.delegate == null ) {
            if ( this.autoRuntimeDialect ) {
                this.delegate = getDialect( statement );
            } else {
                delegateThreadLocal.set( getDialect( statement ) );
            }
        }
    }

    /**
     * 获取分页方言对象(代理)
     * @return 分页方言对象
     */
    public AbstractPageableDialect getDelegate() {
        return Optional.ofNullable( this.delegate ).orElse( delegateThreadLocal.get() );
    }

    /**
     * 移除分页方言对象(代理)
     */
    public void clearDelegate() {
        delegateThreadLocal.remove();
    }

    /**
     * 根据映射对象获取数据库分页方言对象
     * <p>注: 多数据源请勿在全局配置中指定数据库方言</p>
     * @param statement {@link MappedStatement}
     * @return 分页方言对象
     */
    private AbstractPageableDialect getDialect( MappedStatement statement ) {
        if ( this.autoRuntimeDialect ) {
            return getDialectFromJdbcUrl( statement );
        } else {
            return getDialectFromSpecified( statement );
        }
    }

    /**
     * 从JDBC连接获取数据库分页方言对象
     * @param statement {@link MappedStatement}
     * @return 分页方言对象
     */
    private AbstractPageableDialect getDialectFromJdbcUrl( MappedStatement statement ) {
        String jdbcUrl = getJdbcUrlFromDataSource( statement.getConfiguration().getEnvironment().getDataSource() );
        if ( JDBC_PAGEABLE_DIALECT_CACHE.containsKey( jdbcUrl ) ) {
            return JDBC_PAGEABLE_DIALECT_CACHE.get( jdbcUrl );
        }
        try {
            lock.lock();
            if ( JDBC_PAGEABLE_DIALECT_CACHE.containsKey( jdbcUrl ) ) {
                return JDBC_PAGEABLE_DIALECT_CACHE.get( jdbcUrl );
            }
            String dialectAlias = getDialectAliasNameFromJdbc( jdbcUrl.toUpperCase( Locale.ROOT ) );
            if ( dialectAlias == null ) {
                throw new PageableException( "The plugin does not currently support the database or cannot identify the current database." );
            }
            AbstractPageableDialect instance = initDialect( dialectAlias, this.properties );
            JDBC_PAGEABLE_DIALECT_CACHE.put( jdbcUrl, instance );
            return instance;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从指定数据库方言获取分页方言对象
     * @param statement {@link MappedStatement}
     * @return 分页方言对象
     */
    private AbstractPageableDialect getDialectFromSpecified( MappedStatement statement ) {
        String dialectAlias = Optional.ofNullable( this.dialect )
                .orElse( Optional.ofNullable( MyBatisConfigCache.getCustomConfiguration( statement.getConfiguration() ) )
                        .map( MyBatisConfiguration::getDialect )
                        .map( Enum::name )
                        .orElse( "UNDEFINED" ) );
        if ( !"UNDEFINED".equalsIgnoreCase( dialectAlias ) ) {
            if ( SPECIFIED_PAGEABLE_DIALECT_CACHE.containsKey( dialectAlias ) ) {
                return SPECIFIED_PAGEABLE_DIALECT_CACHE.get( dialectAlias );
            }
            try {
                lock.lock();
                if ( SPECIFIED_PAGEABLE_DIALECT_CACHE.containsKey( dialectAlias ) ) {
                    return SPECIFIED_PAGEABLE_DIALECT_CACHE.get( dialectAlias );
                }
                AbstractPageableDialect instance = initDialect( dialectAlias, this.properties );
                SPECIFIED_PAGEABLE_DIALECT_CACHE.put( dialectAlias, instance );
                return instance;
            } finally {
                lock.unlock();
            }
        } else {
            return getDialectFromJdbcUrl( statement );
        }
    }

    /**
     * 初始化数据库分页方言
     * @param dialectClassName 分页方言类名
     * @param props            属性
     * @return 数据库分页方言
     */
    private AbstractPageableDialect initDialect( final String dialectClassName, Properties props ) {
        if ( StringUtil.isBlank( dialectClassName ) ) {
            throw new PageableException( "The corresponding database paging dialect identifier must be specified" );
        }
        try {
            Class<?> clazz = resolve( dialectClassName );
            if ( AbstractPageableDialect.class.isAssignableFrom( clazz ) ) {
                return ( AbstractPageableDialect ) clazz.getDeclaredConstructor().newInstance();
            }
            throw new PageableException( "The database paging dialect must implement the `" + AbstractPageableDialect.class.getCanonicalName() + "` interface" );
        } catch ( Exception e ) {
            throw new PageableException( "Database paging dialect initialization failed: " + e.getMessage(), e );
        }
    }

    /**
     * 将字符串转换成类
     * @param dialectClassName 分页方言类名
     * @return 分页方言类
     * @throws ClassNotFoundException \n
     */
    private Class<?> resolve( String dialectClassName ) throws ClassNotFoundException {
        if ( DIALECT_ALIAS_REGISTRY.containsKey( dialectClassName.toUpperCase( Locale.ROOT ) ) ) {
            return DIALECT_ALIAS_REGISTRY.get( dialectClassName.toUpperCase( Locale.ROOT ) );
        }
        return Class.forName( dialectClassName );
    }

    /**
     * 从数据源获取数据库连接地址
     * @param dataSource 数据源
     * @return 数据库连接地址
     */
    private String getJdbcUrlFromDataSource( DataSource dataSource ) {
        try ( Connection connection = dataSource.getConnection() ) {
            return connection.getMetaData().getURL();
        } catch ( SQLException e ) {
            throw new PageableException( e );
        }
    }

    /**
     * 从JDBC连接地址获取分页方言别名
     * @param jdbcUrl JDBC连接地址
     * @return 分页方言别名
     */
    private String getDialectAliasNameFromJdbc( String jdbcUrl ) {
        return DIALECT_ALIAS_REGISTRY.keySet()
                .parallelStream()
                .map( String::toUpperCase )
                .filter( value -> jdbcUrl.contains( "JDBC:" + value + ":" ) )
                .findFirst()
                .orElse( null );
    }

    /**
     * 设置属性
     * @param props {@link Properties}
     */
    public void setProperties( Properties props ) {
        this.properties = props;
        this.dialect = Optional.ofNullable( props.getProperty( "dialect" ) )
                .filter( StringUtil::hasText )
                .orElse( null );
        this.autoRuntimeDialect = Optional.ofNullable( props.getProperty( "autoRuntimeDialect" ) )
                .map( StringUtil::toBoolean )
                .orElse( false );
    }
}
