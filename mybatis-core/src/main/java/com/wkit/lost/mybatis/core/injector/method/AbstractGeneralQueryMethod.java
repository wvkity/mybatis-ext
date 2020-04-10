package com.wkit.lost.mybatis.core.injector.method;

import com.wkit.lost.mybatis.core.mapping.sql.Provider;
import com.wkit.lost.mybatis.core.mapping.sql.ProviderBuilder;
import com.wkit.lost.mybatis.core.mapping.sql.ProviderCache;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

public abstract class AbstractGeneralQueryMethod<T extends Provider> extends AbstractMethod
        implements ProviderBuilder<T> {

    @Override
    public MappedStatement injectMappedStatement(TableWrapper table, Class<?> mapperInterface, Class<?> resultType) {
        Class<?> entity = table.getEntity();
        return addSelectMappedStatement(mapperInterface, applyMethod(), createSqlSource(
                createScriptBuilder(table, target()), entity), entity, resultType, table);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public T target() {
        return (T) ProviderCache.newInstance(getClass());
    }
}
