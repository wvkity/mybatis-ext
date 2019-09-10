package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.plugins.filter.Filter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.Properties;

/**
 * 抽象查询执行器
 * @author DT
 */
public abstract class AbstractQueryExecutor implements Filter {

    /**
     * 参数信息
     */
    @AllArgsConstructor
    public static class Argument {

        /**
         * 所有参数
         */
        @Getter
        private Object[] args;

        /**
         * 映射信息
         */
        @Getter
        private MappedStatement statement;

        /**
         * 执行器
         */
        @Getter
        private Executor executor;

        /**
         * 分页参数
         */
        @Getter
        private RowBounds rowBounds;

        /**
         * 结果处理器
         */
        @Getter
        private ResultHandler resultHandler;

        /**
         * 缓存key
         */
        @Getter
        private CacheKey cacheKey;

        /**
         * 绑定SQL
         */
        @Getter
        private BoundSql boundSql;

        /**
         * 接口参数
         */
        @Getter
        private Object parameter;

        /**
         * 参数个数
         */
        @Getter
        private int size;

        /**
         * 执行原查询
         * @return 结果
         * @throws SQLException 异常信息
         */
        public Object query() throws SQLException {
            return executor.query( statement, parameter, rowBounds, resultHandler, cacheKey, boundSql );
        }

        /**
         * 获取执行的目标方法名
         * @return 方法名
         */
        public String targetMethodName() {
            String msId = this.statement.getId();
            return msId.substring( msId.lastIndexOf( "." ) + 1 );
        }
    }

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
        ResultHandler resultHandler = ( ResultHandler ) args[ 3 ];
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
            return doIntercept( argument );
        } else {
            return executeOriginalQuery( argument );
        }
    }

    /**
     * 设置属性
     * @param properties 属性信息
     */
    public void setProperties( Properties properties ) {
    }

    /**
     * 执行原查询
     * @param argument 参数
     * @return 结果
     * @throws Exception 异常信息
     */
    protected Object executeOriginalQuery( Argument argument ) throws Exception {
        return argument.query();
    }

    /**
     * 执行拦截
     * @param argument 参数信息
     * @return 结果
     * @throws Exception 异常信息
     */
    protected abstract Object doIntercept( Argument argument ) throws Exception;

}
