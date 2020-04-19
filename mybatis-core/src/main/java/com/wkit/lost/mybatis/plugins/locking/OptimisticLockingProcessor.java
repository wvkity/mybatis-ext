package com.wkit.lost.mybatis.plugins.locking;

import com.wkit.lost.mybatis.core.data.auditing.time.provider.DateTimeProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.proxy.DateTimeProviderFactory;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.AbstractUpdateCriteriaWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.plugins.processor.UpdateProcessorSupport;
import com.wkit.lost.mybatis.utils.Constants;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OptimisticLockingProcessor extends UpdateProcessorSupport {

    private static final String METHOD_UPDATE = "update";
    private static final String METHOD_UPDATE_NOT_WITH_NULL = "updateNotWithNull";
    private static final String METHOD_UPDATE_BY_CRITERIA = "updateByCriteria";
    private static final String METHOD_MIXIN_UPDATE_NOT_WITH_NULL = "mixinUpdateNotWithNull";
    private static final Map<Class<?>, ColumnWrapper> OPTIMISTIC_LOCKER_FIELD_CACHE = new ConcurrentHashMap<>(32);

    /**
     * 拦截方法集合
     */
    private static final Set<String> LOCK_METHODS_CACHE = new HashSet<>(Arrays.asList(METHOD_UPDATE,
            METHOD_UPDATE_NOT_WITH_NULL, METHOD_UPDATE_BY_CRITERIA, METHOD_MIXIN_UPDATE_NOT_WITH_NULL));

    @SuppressWarnings({"unchecked"})
    @Override
    protected Object doProceed(Invocation invocation, MappedStatement ms, Object parameter) throws Throwable {
        if (filter(ms, parameter)) {
            // 方法名
            String execMethod = execMethod(ms);
            if (LOCK_METHODS_CACHE.contains(execMethod) && parameter instanceof Map) {
                Map<String, Object> paramMap = (Map<String, Object>) parameter;
                Object entity = paramMap.getOrDefault(Constants.PARAM_ENTITY, null);
                Object criteriaObject = paramMap.getOrDefault(Constants.PARAM_CRITERIA, null);
                if (entity != null || criteriaObject != null) {
                    // 获取实体类
                    Class<?> entityClass = entity != null ? entity.getClass() : ((criteriaObject instanceof Criteria) ?
                            ((Criteria<?>) criteriaObject).getEntityClass() : null);
                    if (entityClass != null) {
                        // 获取乐观锁字段
                        Optional<ColumnWrapper> optional = Optional.ofNullable(getColumn(entityClass));
                        if (optional.isPresent()) {
                            // 填充值
                            ColumnWrapper column = optional.get();
                            Field field = column.getField();
                            if (METHOD_UPDATE.equals(execMethod) || METHOD_UPDATE_NOT_WITH_NULL.equals(execMethod)) {
                                Object originalValue = originalValue(entity, field);
                                if (originalValue != null) {
                                    Object newValue = newValue(originalValue, column.getJavaType());
                                    paramMap.put(Constants.PARAM_OPTIMISTIC_LOCKING_KEY, newValue);
                                    Object result = invocation.proceed();
                                    if (result instanceof Integer) {
                                        // 更新成功
                                        if ((Integer) result != 0) {
                                            // 将值更新至实体对象中
                                            overwriteOriginalValue(entity, field, newValue);
                                        }
                                    }
                                    return result;
                                }
                            } else {
                                Criteria<?> criteria = (Criteria<?>) criteriaObject;
                                Object originalValue = criteria.getConditionVersionValue();
                                if (originalValue != null) {
                                    if (METHOD_UPDATE_BY_CRITERIA.equals(execMethod)) {
                                        Object modifyValue = criteria.getModifyVersionValue();
                                        if (modifyValue == null) {
                                            ((AbstractUpdateCriteriaWrapper<?>) criteriaObject).version(
                                                    newValue(originalValue, column.getJavaType()));
                                        }
                                    } else {
                                        Object entityValue = originalValue(entity, field);
                                        if (entity != null && (entityValue == null
                                                || "0".equals(String.valueOf(entityValue)))) {
                                            Object newValue = newValue(originalValue, column.getJavaType());
                                            overwriteOriginalValue(entity, field, newValue);
                                            Object result = invocation.proceed();
                                            if (result instanceof Integer) {
                                                // 更新失败
                                                if ((Integer) result == 0) {
                                                    // 将值还原至实体对象中
                                                    overwriteOriginalValue(entity, field, entityValue);
                                                }
                                            }
                                            return result;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return invocation.proceed();
    }

    private ColumnWrapper getColumn(Class<?> entityClass) {
        if (entityClass != null) {
            if (OPTIMISTIC_LOCKER_FIELD_CACHE.containsKey(entityClass)) {
                return OPTIMISTIC_LOCKER_FIELD_CACHE.get(entityClass);
            } else {
                Optional<ColumnWrapper> optional = Optional.ofNullable(TableHandler.getTable(entityClass))
                        .map(TableWrapper::getOptimisticLockingColumn);
                if (optional.isPresent()) {
                    OPTIMISTIC_LOCKER_FIELD_CACHE.putIfAbsent(entityClass, optional.get());
                    return OPTIMISTIC_LOCKER_FIELD_CACHE.get(entityClass);
                }
            }
        }
        return null;
    }

    private Object originalValue(Object entity, Field field) {
        if (entity != null && field != null) {
            try {
                field.setAccessible(true);
                return field.get(entity);
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    private void overwriteOriginalValue(Object entity, Field field, Object newValue) {
        if (entity != null && field != null) {
            try {
                field.setAccessible(true);
                field.set(entity, newValue);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    private Object newValue(Object originalValue, Class<?> javaType) {
        if (long.class.equals(javaType) || Long.class.equals(javaType)) {
            return (long) originalValue + 1;
        } else if (int.class.equals(javaType) || Integer.class.equals(javaType)) {
            return (int) originalValue + 1;
        } else {
            Optional<DateTimeProvider> provider = Optional.ofNullable(DateTimeProviderFactory.ProviderBuilder
                    .create().target(javaType).build());
            if (provider.isPresent()) {
                return provider.get().getNow();
            }
        }
        return originalValue;
    }

}
