package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.plugins.dbs.dialect.Dialect;
import com.wkit.lost.mybatis.plugins.filter.Filter;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.ClassUtil;
import lombok.Getter;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 抽象查询执行器
 * @author wvkity
 */
public abstract class AbstractQueryExecutor implements Filter {

    /**
     * 锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * 代理工厂对象
     */
    protected volatile Dialect factory;

    /**
     * 属性
     */
    @Getter
    protected Properties properties;

    /**
     * 数据库方言类
     */
    @Getter
    protected String dialectClass;

    /**
     * 拦截
     * @param invocation 参数信息
     * @return 结果
     * @throws Throwable 异常信息
     */
    public Object intercept( Invocation invocation ) throws Throwable {
        // 参数
        Object[] args = invocation.getArgs();
        int size = args.length;
        MappedStatement statement = ( MappedStatement ) args[ 0 ];
        Object parameter = args[ 1 ];
        RowBounds rowBounds = ( RowBounds ) args[ 2 ];
        ResultHandler<?> resultHandler = ( ResultHandler<?> ) args[ 3 ];
        Executor executor = ( Executor ) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        if ( size == 4 ) {
            boundSql = statement.getBoundSql( parameter );
            cacheKey = executor.createCacheKey( statement, parameter, rowBounds, boundSql );
        } else {
            cacheKey = ( CacheKey ) args[ 4 ];
            boundSql = ( BoundSql ) args[ 5 ];
        }
        // 参数对象
        Argument argument = new Argument( args, statement, executor, rowBounds, resultHandler, cacheKey, boundSql, parameter, size );
        if ( filter( argument ) ) {
            return doIntercept( executor, statement, parameter, rowBounds, resultHandler, cacheKey, boundSql );
        } else {
            return executor.query( statement, parameter, rowBounds, resultHandler, cacheKey, boundSql );
        }
    }

    /**
     * 设置属性
     * @param properties 属性信息
     */
    public void setProperties( Properties properties ) {
        switch ( getTarget() ) {
            case LIMIT:
                this.dialectClass = properties.getProperty( "limitDelegate" );
                break;
            case PAGEABLE:
                this.dialectClass = properties.getProperty( "pageableDelegate" );
                break;
            default:
        }
        this.properties = properties;
        if ( Ascii.isNullOrEmpty( this.dialectClass ) ) {
            this.dialectClass = getDefaultDialect();
        }
        this.factory = ( Dialect ) ClassUtil.newInstance( this.dialectClass );
        if ( factory == null ) {
            throw new IllegalArgumentException( "Failed to initialize database dialect based on the specified class name: `" + this.dialectClass + "`" );
        }
        this.factory.setProperties( properties );
    }

    /**
     * 检查数据库方言是否创建
     */
    protected void validateDialectExists() {
        if ( this.factory == null ) {
            if ( this.lock.tryLock() ) {
                try {
                    if ( this.factory == null ) {
                        this.setProperties( new Properties() );
                    }
                } finally {
                    this.lock.unlock();
                }
            }
        }
    }

    /**
     * 获取拦截器类型
     * @return 模式
     */
    protected abstract QueryMode getTarget();

    /**
     * 获取默认方言
     * @return 默认方言类路径
     */
    protected abstract String getDefaultDialect();

    /**
     * 执行拦截
     * @param executor      执行对象
     * @param statement     {@link MappedStatement}
     * @param parameter     接口参数
     * @param rowBounds     分页参数
     * @param resultHandler 结果处理对象
     * @param cacheKey      缓存key
     * @param boundSql      绑定SQL对象
     * @return 结果
     * @throws Exception 异常信息
     */
    protected abstract Object doIntercept( Executor executor, MappedStatement statement, Object parameter, 
                                           RowBounds rowBounds, ResultHandler<?> resultHandler, 
                                           CacheKey cacheKey, BoundSql boundSql ) throws Exception;

}
