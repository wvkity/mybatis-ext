package com.wkit.lost.mybatis.plugins.pagination.executor;

import com.wkit.lost.mybatis.utils.ClassUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.plugins.cache.Cache;
import com.wkit.lost.mybatis.plugins.cache.CacheFactory;
import com.wkit.lost.mybatis.plugins.pagination.dialect.Dialect;
import com.wkit.lost.mybatis.plugins.pagination.exception.PageableException;
import com.wkit.lost.mybatis.plugins.utils.ExecutorUtil;
import com.wkit.lost.mybatis.utils.MappedStatementUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 分页执行器
 * @author DT
 */
@Log4j2
public class PageableExecutor extends AbstractPageableExecutor {

    /**
     * 锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * 查询总记录{@link MappedStatement}对象缓存
     */
    protected Cache<String, MappedStatement> recordMsCache = null;

    /**
     * 分页方言代理工厂对象
     */
    protected volatile Dialect proxyFactory;

    /**
     * 构造方法
     */
    public PageableExecutor() {
        super();
    }

    /**
     * 拦截处理
     * @param invocation 调用对象
     * @return 处理结果
     * @throws Throwable ""
     */
    public Object intercept( Invocation invocation ) throws Throwable {
        try {
            // 参数
            Object[] args = invocation.getArgs();
            MappedStatement statement = ( MappedStatement ) args[ 0 ];
            Object parameter = args[ 1 ];
            RowBounds rowBounds = ( RowBounds ) args[ 2 ];
            ResultHandler resultHandler = ( ResultHandler ) args[ 3 ];
            Executor executor = ( Executor ) invocation.getTarget();
            CacheKey cacheKey;
            BoundSql boundSql;
            if ( args.length == 4 ) {
                boundSql = statement.getBoundSql( parameter );
                cacheKey = executor.createCacheKey( statement, parameter, rowBounds, boundSql );
            } else {
                cacheKey = ( CacheKey ) args[ 4 ];
                boundSql = ( BoundSql ) args[ 5 ];
            }
            // 检测分页方言
            validatePageableDialectExists();
            // 检查是否需要分页
            List resultList;
            if ( !this.proxyFactory.filter( statement, parameter, rowBounds ) ) {
                // 检查是否需要查询总记录
                if ( this.proxyFactory.beforeOfQueryRecord( statement, parameter, rowBounds ) ) {
                    // 查询总记录数
                    Long records = executeQueryRecord( executor, statement, parameter, rowBounds, boundSql, null );
                    // 处理总记录数
                    if ( !this.proxyFactory.afterOfQueryRecord( records, parameter, rowBounds ) ) {
                        // 总记录数为0，返回空集合
                        return this.proxyFactory.executePagingOnAfter( new ArrayList<>(), parameter, rowBounds );
                    }
                }
                resultList = ExecutorUtil.executeQueryPageableOfCustom( this.proxyFactory, executor, statement, parameter,
                        rowBounds, resultHandler, boundSql, cacheKey );
            } else {
                //resultList = ExecutorUtil.executeQueryPageableOfCustom( this.proxyFactory, executor, statement, parameter,
                //        rowBounds, resultHandler, boundSql, cacheKey );
                // 默认查询
                resultList = executor.query( statement, parameter, rowBounds, resultHandler, cacheKey, boundSql );
            }
            return this.proxyFactory.executePagingOnAfter( resultList, parameter, rowBounds );
        } finally {
            Optional.ofNullable( proxyFactory ).ifPresent( Dialect::completed );
        }
    }

    /**
     * 检测数据库分页方言是否已初始化
     */
    private void validatePageableDialectExists() {
        if ( this.proxyFactory == null ) {
            if ( lock.tryLock() ) {
                try {
                    if ( this.proxyFactory == null ) {
                        this.setProperties( new Properties() );
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 执行总记录查询
     * @param executor      执行器
     * @param statement     {@link MappedStatement}对象
     * @param parameter     接口参数
     * @param rowBounds     分页参数
     * @param boundSql      绑定SQL
     * @param resultHandler 返回值处理对象
     * @return 总记录数
     * @throws SQLException \n
     */
    private Long executeQueryRecord( Executor executor, MappedStatement statement, Object parameter, RowBounds rowBounds, BoundSql boundSql, ResultHandler resultHandler ) throws SQLException {
        String msId = statement.getId() + Dialect.PAGEABLE_RECORD_SUFFIX;
        // 检查是否存在总记录查询(MappedStatement对象)
        MappedStatement recordMs = MappedStatementUtil.getExistsMappedStatement( statement.getConfiguration(), msId );
        if ( recordMs != null ) {
            // 执行查询
            return ExecutorUtil.executeQueryRecordOfExists( executor, recordMs, parameter, boundSql, resultHandler );
        } else {
            // 从缓存中读取查询记录MappedStatement对象
            recordMs = recordMsCache.get( msId );
            if ( recordMs == null ) {
                // 创建
                recordMs = MappedStatementUtil.newQueryRecordMappedStatement( statement, msId );
                recordMsCache.put( msId, recordMs );
            }
            return ExecutorUtil.executeQueryRecordOfCustom( this.proxyFactory, executor, recordMs, parameter, boundSql, rowBounds, resultHandler );
        }
    }

    @Override
    public void setProperties( Properties properties ) {
        super.setProperties( properties );
        this.recordMsCache = CacheFactory.createCache( properties.getProperty( "recordMsCache" ), "recordMs", properties );
        if ( StringUtil.isBlank( this.dialectClass ) ) {
            this.dialectClass = "com.wkit.lost.mybatis.plugins.pagination.dialect.PageableDialectProxyFactory";
        }
        this.proxyFactory = ( Dialect ) ClassUtil.newInstance( this.dialectClass );
        if ( this.proxyFactory == null ) {
            throw new PageableException( "Database paging dialect initialization failed: `" + this.dialectClass + "`" );
        }
        // 设置属性
        this.proxyFactory.setProperties( properties );
    }
}
