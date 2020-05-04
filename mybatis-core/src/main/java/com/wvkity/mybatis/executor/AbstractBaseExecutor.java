package com.wvkity.mybatis.executor;

import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 重写{@link BaseExecutor}
 * @author wvkity
 */
public abstract class AbstractBaseExecutor extends BaseExecutor {

    protected AbstractBaseExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        if (super.isClosed()) {
            throw new ExecutorException("Executor was closed.");
        }
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(ms.getId());
        Object offset = rowBounds.getOffset();
        Object limit = rowBounds.getLimit();
        if (parameterObject instanceof Map) {
            Map<?, ?> parameterMap = (Map<?, ?>) parameterObject;
            Optional<? extends Map.Entry<?, ?>> optional = parameterMap.entrySet().stream()
                    .filter(it -> it.getValue() instanceof Pageable).findFirst();
            if (optional.isPresent()) {
                Pageable pageable = (Pageable) optional.get().getValue();
                offset = pageable.getPage();
                limit = pageable.getSize();
            }
        } else if (parameterObject instanceof Pageable) {
            Pageable pageable = (Pageable) parameterObject;
            offset = pageable.getPage();
            limit = pageable.getSize();
        }
        cacheKey.update(offset);
        cacheKey.update(limit);
        cacheKey.update(boundSql.getSql());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();
        // mimic DefaultParameterHandler logic
        for (ParameterMapping parameterMapping : parameterMappings) {
            if (parameterMapping.getMode() != ParameterMode.OUT) {
                Object value;
                String propertyName = parameterMapping.getProperty();
                if (boundSql.hasAdditionalParameter(propertyName)) {
                    value = boundSql.getAdditionalParameter(propertyName);
                } else if (parameterObject == null) {
                    value = null;
                } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }
                cacheKey.update(value);
            }
        }
        if (configuration.getEnvironment() != null) {
            // issue #176
            cacheKey.update(configuration.getEnvironment().getId());
        }
        return cacheKey;
    }
}
