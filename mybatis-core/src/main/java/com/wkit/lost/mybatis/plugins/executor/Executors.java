package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.plugins.paging.dbs.dialect.Dialect;
import com.wkit.lost.mybatis.plugins.exception.MyBatisPluginException;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 执行工具类
 * @author wvkity
 */
public abstract class Executors {

    private static Field additionalParametersField;

    static {
        try {
            additionalParametersField = BoundSql.class.getDeclaredField( "additionalParameters" );
            additionalParametersField.setAccessible( true );
        } catch ( Exception e ) {
            throw new MyBatisPluginException( "Failure to obtain BoundSql attribute additionalParameters：" + e, e );
        }
    }

    /**
     * 执行存在的总记录数查询
     * @param executor      执行器
     * @param statement     映射对象
     * @param parameter     接口参数
     * @param boundSql      SQL绑定对象
     * @param resultHandler 返回值处理类
     * @return 总记录数
     * @throws SQLException \n
     */
    public static Long executeQueryRecordOfExists( Executor executor, MappedStatement statement, Object parameter, BoundSql boundSql, ResultHandler resultHandler ) throws SQLException {
        CacheKey cacheKey = executor.createCacheKey( statement, parameter, RowBounds.DEFAULT, boundSql );
        BoundSql recordBoundSql = statement.getBoundSql( parameter );
        List<Object> resultList = executor.query( statement, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, recordBoundSql );
        return Optional.ofNullable( resultList )
                .map( result -> ( ( Number ) ( result ).get( 0 ) ).longValue() )
                .orElse( 0L );
    }

    /**
     * 执行自定义生成的总记录数查询
     * @param dialect       分页方言
     * @param executor      执行器
     * @param statement     映射对象
     * @param parameter     接口参数
     * @param boundSql      SQL绑定对象
     * @param rowBounds     分页参数
     * @param resultHandler 返回值处理类
     * @return 总记录数
     * @throws SQLException \n
     */
    public static Long executeQueryRecordOfCustom( Dialect dialect, Executor executor, MappedStatement statement, Object parameter, BoundSql boundSql, RowBounds rowBounds, ResultHandler resultHandler ) throws SQLException {
        Map<String, Object> additionalParameters = getAdditionalParameter( boundSql );
        // 创建record查询缓存key
        CacheKey cacheKey = executor.createCacheKey( statement, parameter, RowBounds.DEFAULT, boundSql );
        // 调用方言生成对应总记录数SQL语句
        String recordSql = dialect.generateQueryRecordSql( statement, boundSql, parameter, rowBounds, cacheKey );
        // 更新SQL绑定对象
        BoundSql recordBoundSql = new BoundSql( statement.getConfiguration(), recordSql, boundSql.getParameterMappings(), parameter );
        // 重新设置参数
        for ( Map.Entry<String, Object> entry : additionalParameters.entrySet() ) {
            recordBoundSql.setAdditionalParameter( entry.getKey(), entry.getValue() );
        }
        // 执行查询
        List<Object> resultList = executor.query( statement, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, recordBoundSql );
        return Optional.ofNullable( resultList )
                .map( result -> ( ( Number ) result.get( 0 ) ).longValue() )
                .orElse( 0L );
    }

    /**
     * 执行分页查询
     * @param dialect       分页方言
     * @param executor      执行器
     * @param statement     映射对象
     * @param parameter     参数
     * @param boundSql      SQL绑定对象
     * @param rowBounds     分页参数
     * @param resultHandler 返回值处理类
     * @param cacheKey      缓存Key
     * @param <E>           泛型类型
     * @return 多条记录
     * @throws SQLException SQL异常
     */
    public static <E> List<E> executeQueryPageableOfCustom( Dialect dialect, Executor executor, MappedStatement statement, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql, CacheKey cacheKey ) throws SQLException {
        // 检查是否需要进行分页查询
        if ( dialect.executePagingOnBefore( statement, parameter, rowBounds ) ) {
            // 设置分页参数
            parameter = dialect.processParameter( statement, boundSql, parameter, cacheKey );
            String pageableSql = dialect.generatePageableSql( statement, boundSql, parameter, rowBounds, cacheKey );
            BoundSql pageableBoundSql = new BoundSql( statement.getConfiguration(), pageableSql, boundSql.getParameterMappings(), parameter );
            Map<String, Object> additionalParameters = getAdditionalParameter( boundSql );
            // 设置动态参数
            for ( Map.Entry<String, Object> entry : additionalParameters.entrySet() ) {
                pageableBoundSql.setAdditionalParameter( entry.getKey(), entry.getValue() );
            }
            // 执行分页查询
            return executor.query( statement, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, pageableBoundSql );
        } else {
            // 执行原查询
            return executor.query( statement, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, boundSql );
        }
    }

    /**
     * 获取{@link BoundSql}对象的additionalParameters属性值
     * @param boundSql {@link BoundSql}对象
     * @return 属性值
     */
    @SuppressWarnings( "unchecked" )
    public static Map<String, Object> getAdditionalParameter( final BoundSql boundSql ) {
        try {
            return ( Map<String, Object> ) additionalParametersField.get( boundSql );
        } catch ( Exception e ) {
            throw new MyBatisPluginException( "Failure to obtain BoundSql attribute value additionalParameters: " + e, e );
        }
    }
}
