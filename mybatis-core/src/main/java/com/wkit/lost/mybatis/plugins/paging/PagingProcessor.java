package com.wkit.lost.mybatis.plugins.paging;

import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.plugins.paging.dbs.dialect.Dialect;
import com.wkit.lost.mybatis.plugins.processor.QueryProcessorSupport;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.ClassUtil;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.paging.Pageable;
import lombok.Getter;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class PagingProcessor extends QueryProcessorSupport {

    /**
     * 锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * 代理工厂对象
     */
    protected volatile Dialect factory;

    /**
     * 数据库方言类
     */
    @Getter
    protected String dialectClass;

    @Override
    public void setProperties( Properties properties ) {
        switch ( getMode() ) {
            case RANGE:
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

    @Override
    public boolean filter( MappedStatement ms, Object parameter ) {
        Criteria<?> criteria = getCriteria( parameter );
        Pageable pageable = getPageable( parameter );
        return pageable == null && Optional.ofNullable( criteria ).map( Criteria::isRange ).orElse( false );
    }

    /**
     * 获取条件对象
     * @param parameter 参数
     * @return {@link Criteria}
     */
    protected Criteria<?> getCriteria( Object parameter ) {
        return getParameter( parameter, Constants.PARAM_CRITERIA, Criteria.class );
    }

    /**
     * 获取分页对象
     * @param parameter 参数
     * @return 「{@link Pageable}
     */
    protected Pageable getPageable( Object parameter ) {
        return getParameter( parameter, Constants.PARAM_PAGEABLE, Pageable.class );
    }

    /**
     * 获取指定类型参数
     * @param parameter 参数对象
     * @param key       键
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 指定类型参数
     */
    @SuppressWarnings( { "unchecked" } )
    protected <T> T getParameter( Object parameter, String key, Class<T> clazz ) {
        if ( parameter != null && clazz != null && Ascii.hasText( key ) ) {
            if ( clazz.isAssignableFrom( parameter.getClass() ) ) {
                return ( T ) parameter;
            }
            if ( parameter instanceof Map ) {
                Map<String, Object> paramMap = ( Map<String, Object> ) parameter;
                if ( !paramMap.isEmpty() ) {
                    return ( T ) Optional.ofNullable( paramMap.getOrDefault( key, null ) )
                            .filter( it -> clazz.isAssignableFrom( it.getClass() ) ).orElse( null );
                }
            }
        }
        return null;
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
    protected abstract PageMode getMode();

    /**
     * 获取默认方言
     * @return 默认方言类路径
     */
    protected abstract String getDefaultDialect();
}
