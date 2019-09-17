package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.utils.Ascii;
import lombok.Getter;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 参数
 * @author DT
 */
public class Argument {

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
     * 参数
     */
    private Map<String, Object> paramMap;

    /**
     * 参数个数
     */
    @Getter
    private int size;

    public Argument( Object[] args, MappedStatement statement, Executor executor, RowBounds rowBounds,
                     ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql, Object parameter, int size ) {
        this.args = args;
        this.statement = statement;
        this.executor = executor;
        this.rowBounds = rowBounds;
        this.resultHandler = resultHandler;
        this.cacheKey = cacheKey;
        this.boundSql = boundSql;
        this.parameter = parameter;
        this.size = size;
        init();
    }

    @SuppressWarnings( "unchecked" )
    private void init() {
        paramMap = Optional.ofNullable( parameter ).map( value -> ( Map<String, Object> ) value ).orElse( new HashMap<>( 0 ) );
    }

    /**
     * 获取参数值
     * @param key key
     * @param <T> 参数类型
     * @return 参数
     */
    @SuppressWarnings( "unchecked" )
    public <T> T getParameter( String key ) {
        if ( Ascii.hasText( key ) && paramMap.containsKey( key ) ) {
            Object value = paramMap.get( key );
            if ( value != null ) {
                return ( T ) value;
            }
        }
        return null;
    }

    /**
     * 执行原查询
     * @return 结果
     * @throws SQLException 异常信息
     */
    public <E> List<E> query() throws SQLException {
        return executor.query( statement, parameter, rowBounds, resultHandler, cacheKey, boundSql );
    }

    /**
     * 获取目标方法
     * @return 方法全路径
     */
    public String target() {
        return this.statement.getId();
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
