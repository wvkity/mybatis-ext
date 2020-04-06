package com.wkit.lost.mybatis.core.injector.method;

import com.wkit.lost.mybatis.core.mapping.script.ScriptBuilder;
import com.wkit.lost.mybatis.core.mapping.script.ScriptBuilderFactory;
import com.wkit.lost.mybatis.core.mapping.sql.Provider;
import com.wkit.lost.mybatis.core.mapping.sql.ProviderBuilder;
import com.wkit.lost.mybatis.core.mapping.sql.ProviderCache;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * Criteria相关方法注入器
 * @param <T> SQL提供类
 * @author wvkity
 * @see com.wkit.lost.mybatis.core.wrapper.criteria.Criteria
 */
public abstract class AbstractCriteriaMethod<T extends Provider> extends AbstractMethod implements ProviderBuilder<T> {

    @Override
    public MappedStatement injectMappedStatement( TableWrapper table, Class<?> mapperInterface, Class<?> resultType ) {
        Class<?> entity = table.getEntity();
        ScriptBuilder builder = ScriptBuilderFactory.create( target(), table, entity, table.getAlias() );
        Class<?> returnType = getResultType();
        return addSelectMappedStatement( mapperInterface, applyMethod(), createSqlSource( builder, entity ),
                null, returnType == null ? resultType : returnType, table );
    }

    /**
     * 获取返回值类型
     * @return 返回值类型
     */
    public Class<?> getResultType() {
        return null;
    }

    @SuppressWarnings( { "unchecked" } )
    @Override
    public T target() {
        return ( T ) ProviderCache.newInstance( getClass() );
    }
}
