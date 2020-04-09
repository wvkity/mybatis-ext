package com.wkit.lost.mybatis.core.injector.method;

import com.wkit.lost.mybatis.core.mapping.sql.Provider;
import com.wkit.lost.mybatis.core.mapping.sql.ProviderBuilder;
import com.wkit.lost.mybatis.core.mapping.sql.ProviderCache;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 抽象通用更新方法映射注入
 * @param <T> SQL提供类
 * @author wvkity
 */
public abstract class AbstractGeneralUpdateMethod<T extends Provider> extends AbstractUpdateMethod
        implements ProviderBuilder<T> {

    @Override
    public MappedStatement injectMappedStatement( TableWrapper table, Class<?> mapperInterface, Class<?> resultType ) {
        return addUpdateMappedStatement( mapperInterface, resultType, table, target() );
    }

    @SuppressWarnings( { "unchecked" } )
    @Override
    public T target() {
        return ( T ) ProviderCache.newInstance( getClass() );
    }
}