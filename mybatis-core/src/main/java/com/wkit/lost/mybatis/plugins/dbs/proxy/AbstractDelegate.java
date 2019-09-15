package com.wkit.lost.mybatis.plugins.dbs.proxy;

import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.plugins.dbs.dialect.AbstractDialect;
import com.wkit.lost.mybatis.plugins.dbs.dialect.Dialect;
import com.wkit.lost.mybatis.plugins.dbs.dialect.exact.MySqlDialect;
import com.wkit.lost.mybatis.plugins.dbs.dialect.exact.OracleDialect;
import com.wkit.lost.mybatis.plugins.exception.MyBatisPluginException;
import com.wkit.lost.mybatis.utils.Ascii;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
public abstract class AbstractDelegate {

    /**
     * 数据库方言
     */
    protected static final Map<String, Class<? extends Dialect>> DIALECT_ALIAS_REGISTRY = new ConcurrentHashMap<>();

    /**
     * 分页方言对象缓存(从JDBC中读取)
     */
    protected Map<String, AbstractDialect> JDBC_DIALECT_CACHE = new ConcurrentHashMap<>();

    /**
     * 分页方言对象缓存(从指定方言读取)
     */
    protected Map<String, AbstractDialect> SPECIFIED_DIALECT_CACHE = new ConcurrentHashMap<>();

    /**
     * 分页方言本地线程对象
     */
    protected ThreadLocal<AbstractDialect> delegateThreadLocal = new ThreadLocal<>();

    /**
     * 方言对象
     */
    protected AbstractDialect delegate;

    /**
     * 属性
     */
    protected Properties properties;

    /**
     * 数据库方言
     */
    protected String dbDialect;

    /**
     * 是否自动获取数据库方言
     */
    protected boolean autoRuntimeDialect;

    /**
     * 锁
     */
    private ReentrantLock lock = new ReentrantLock();

    static {
        // MYSQL
        register( "MYSQL", MySqlDialect.class );
        // MARIADB
        register( "MARIADB", MySqlDialect.class );
        // SQLITE
        register( "SQLITE", MySqlDialect.class );
        // ORACLE
        register( "ORACLE", OracleDialect.class );
    }

    /**
     * 数据库方言
     * @param alias        别名
     * @param dialectClass 数据库分页方言类
     */
    public static void register( final String alias, Class<? extends Dialect> dialectClass ) {
        DIALECT_ALIAS_REGISTRY.put( alias, dialectClass );
    }

    /**
     * 初始化方言
     * @param prefix    前缀
     * @param statement {@link MappedStatement}
     */
    public void initDialect( MappedStatement statement, String prefix ) {
        if ( this.delegate == null ) {
            if ( this.autoRuntimeDialect ) {
                getDialect( statement, prefix );
            } else {
                delegateThreadLocal.set( getDialect( statement, prefix ) );
            }
        }
    }

    protected AbstractDialect getDialect( MappedStatement statement, String prefix ) {
        if ( this.autoRuntimeDialect ) {
            return getDialectFromJdbcUrl( statement, prefix );
        }
        return getDialectFromSpecified( statement, prefix );
    }

    /**
     * 初始化数据库方言实例
     * @param dialectClazz 方言别名或全类名
     * @return 方言对象
     */
    protected AbstractDialect initDialect( final String dialectClazz ) {
        if ( Ascii.isNullOrEmpty( dialectClazz ) ) {
            throw new MyBatisPluginException( "The corresponding database dialect identifier must be specified." );
        }
        try {
            Class<?> clazz = transform( dialectClazz );
            if ( !AbstractDialect.class.isAssignableFrom( clazz ) ) {
                throw new MyBatisPluginException( "The current plug-in must inherit the `" + AbstractDialect.class.getName() + "` abstract class and implement the corresponding methods" );
            }
            return ( AbstractDialect ) clazz.getDeclaredConstructor().newInstance();
        } catch ( Exception e ) {
            throw new MyBatisPluginException( "Failed to initialize database dialect object: " + e.getMessage(), e );
        }
    }

    /**
     * 从JDBC连接获取数据库分页方言对象
     * @param statement {@link MappedStatement}
     * @param prefix    key前缀
     * @return 分页方言对象
     */
    protected AbstractDialect getDialectFromJdbcUrl( MappedStatement statement, String prefix ) {
        String jdbcUrl = getJdbcUrlFromDataSource( statement.getConfiguration().getEnvironment().getDataSource() );
        String key = prefix.toUpperCase( Locale.ENGLISH ) + jdbcUrl.toUpperCase( Locale.ENGLISH );
        if ( JDBC_DIALECT_CACHE.containsKey( key ) ) {
            return JDBC_DIALECT_CACHE.get( key );
        }
        try {
            lock.lock();
            if ( JDBC_DIALECT_CACHE.containsKey( key ) ) {
                return JDBC_DIALECT_CACHE.get( key );
            }
            String dialectAlias = getDialectAliasFromJdbc( jdbcUrl.toUpperCase( Locale.ENGLISH ) );
            if ( dialectAlias == null ) {
                throw new MyBatisPluginException( "The plug-in does not currently support the current database or cannot recognize the current database type." );
            }
            AbstractDialect instance = initDialect( dialectAlias );
            JDBC_DIALECT_CACHE.put( key, instance );
            return instance;
        } catch ( Exception e ) {
            throw new MyBatisPluginException( e );
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从指定数据库方言获取方言对象
     * @param statement {@link MappedStatement}
     * @param prefix    key前缀
     * @return 方言对象
     */
    public AbstractDialect getDialectFromSpecified( MappedStatement statement, String prefix ) {
        String dialectAlias = Optional.ofNullable( this.dbDialect )
                .orElse( Optional.ofNullable( MyBatisConfigCache.getCustomConfiguration( statement.getConfiguration() ) )
                        .map( MyBatisCustomConfiguration::getDialect )
                        .map( Enum::name )
                        .orElse( "UNDEFINED" ) );
        if ( !"UNDEFINED".equalsIgnoreCase( dialectAlias ) ) {
            String key = prefix.toUpperCase( Locale.ENGLISH ) + dialectAlias.toUpperCase( Locale.ENGLISH );
            if ( SPECIFIED_DIALECT_CACHE.containsKey( key ) ) {
                return SPECIFIED_DIALECT_CACHE.get( key );
            }
            try {
                lock.lock();
                if ( SPECIFIED_DIALECT_CACHE.containsKey( key ) ) {
                    return SPECIFIED_DIALECT_CACHE.get( key );
                }
                AbstractDialect instance = initDialect( dialectAlias );
                SPECIFIED_DIALECT_CACHE.put( key, instance );
                return instance;
            } catch ( Exception e ) {
                throw new MyBatisPluginException( e );
            } finally {
                lock.unlock();
            }
        } else {
            return getDialectFromJdbcUrl( statement, prefix );
        }
    }

    /**
     * 根据数据源获取JDBC路径
     * @param dataSource 数据源
     * @return JDBC URL
     */
    protected String getJdbcUrlFromDataSource( DataSource dataSource ) {
        try ( Connection connection = dataSource.getConnection() ) {
            return connection.getMetaData().getURL();
        } catch ( SQLException e ) {
            throw new MyBatisPluginException( e );
        }
    }

    /**
     * 根据JDBC URL获取数据库方言
     * @param jdbcUrl JDBC URL
     * @return 数据库方言
     */
    protected String getDialectAliasFromJdbc( String jdbcUrl ) {
        return DIALECT_ALIAS_REGISTRY.keySet()
                .parallelStream()
                .map( value -> value.toUpperCase( Locale.ENGLISH ) )
                .filter( value -> jdbcUrl.contains( "JDBC:" + value + ":" ) )
                .findAny()
                .orElse( null );
    }

    /**
     * 将类名转换成对应的类
     * @param dialectClassName 类名
     * @return 类对象
     * @throws ClassNotFoundException \n
     */
    protected Class<?> transform( final String dialectClassName ) throws ClassNotFoundException {
        if ( DIALECT_ALIAS_REGISTRY.containsKey( dialectClassName.toUpperCase( Locale.ENGLISH ) ) ) {
            return DIALECT_ALIAS_REGISTRY.get( dialectClassName.toUpperCase( Locale.ENGLISH ) );
        }
        return Class.forName( dialectClassName );
    }

    /**
     * 设置属性
     * @param props {@link Properties}
     */
    public void setProperties( Properties props ) {
        this.properties = props;
        this.dbDialect = Optional.ofNullable( props.getProperty( "dialect" ) ).filter( Ascii::hasText ).orElse( null );
        this.autoRuntimeDialect = Optional.ofNullable( props.getProperty( "autoRuntimeDialect" ) ).map( Ascii::toBool ).orElse( false );
    }

    /**
     * 清除线程方言对象
     */
    public void clearDelegateOfThread() {
        this.delegateThreadLocal.remove();
    }

    /**
     * 获取方言对象
     * @return 方言对象
     */
    public abstract AbstractDialect getDelegate();

}
