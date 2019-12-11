package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.core.AbstractModifyCriteria;
import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.filling.gen.AbstractGenerator;
import com.wkit.lost.mybatis.filling.proxy.GeneratorFactory;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.PrimitiveRegistry;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 乐观锁执行器
 * @author wvkity
 */
public class OptimisticLockerExecutor extends AbstractUpdateExecutor {

    private static final String METHOD_UPDATE = "update";
    private static final String METHOD_UPDATE_SELECTIVE = "updateSelective";
    private static final String METHOD_UPDATE_BY_CRITERIA = "updateByCriteria";
    private static final String METHOD_MIXIN_UPDATE_SELECTIVE = "mixinUpdateSelective";
    private static final Map<Class<?>, Column> OPTIMISTIC_LOCKER_FIELD_CACHE = new ConcurrentHashMap<>( 128 );

    /**
     * 拦截方法集合
     */
    private static final Set<String> LOCK_METHODS_CACHE = new HashSet<>( Arrays.asList( METHOD_UPDATE,
            METHOD_UPDATE_SELECTIVE, METHOD_UPDATE_BY_CRITERIA, METHOD_MIXIN_UPDATE_SELECTIVE ) );

    @SuppressWarnings( "unchecked" )
    @Override
    public Object intercept( Invocation invocation ) throws Throwable {
        MappedStatement statement = ( MappedStatement ) invocation.getArgs()[ 0 ];
        Object parameter = invocation.getArgs()[ 1 ];
        if ( filter( statement, parameter ) ) {
            // 方法名
            String msId = statement.getId();
            String methodName = msId.substring( msId.lastIndexOf( "." ) + 1 );
            if ( LOCK_METHODS_CACHE.contains( methodName ) && parameter instanceof Map ) {
                Map<String, Object> paramMap = ( Map<String, Object> ) parameter;
                Object entity = paramMap.getOrDefault( Constants.PARAM_ENTITY, null );
                Object criteriaObject = paramMap.getOrDefault( Constants.PARAM_CRITERIA, null );
                if ( entity != null || criteriaObject != null ) {
                    // 获取实体类
                    Class<?> entityClass = entity != null ? entity.getClass() : ( ( criteriaObject instanceof Criteria ) ?
                            ( ( Criteria<?> ) criteriaObject ).getEntityClass() : null );
                    if ( entityClass != null ) {
                        // 获取乐观锁字段
                        Optional<Column> optional = Optional.ofNullable( getColumn( entityClass ) );
                        if ( optional.isPresent() ) {
                            // 填充值
                            Column column = optional.get();
                            Field field = column.getField().getField();
                            if ( METHOD_UPDATE.equals( methodName ) || METHOD_UPDATE_SELECTIVE.equals( methodName ) ) {
                                Object originalValue = getOriginalValue( entity, field );
                                if ( originalValue != null ) {
                                    Object newValue = getNewValue( originalValue, column.getJavaType() );
                                    paramMap.put( Constants.PARAM_OPTIMISTIC_LOCK_KEY, newValue );
                                    Object result = invocation.proceed();
                                    if ( result instanceof Integer ) {
                                        // 更新成功
                                        if ( ( Integer ) result != 0 ) {
                                            // 将值更新至实体对象中
                                            overwriteOriginalValue( entity, field, newValue );
                                        }
                                    }
                                    return result;
                                }
                            } else {
                                Criteria<?> criteria = ( Criteria<?> ) criteriaObject;
                                Object originalValue = criteria.getConditionVersionValue();
                                if ( originalValue != null ) {
                                    if ( METHOD_UPDATE_BY_CRITERIA.equals( methodName ) ) {
                                        Object modifyValue = criteria.getModifyVersionValue();
                                        if ( modifyValue == null ) {
                                            ( ( AbstractModifyCriteria<?> ) criteriaObject ).updateVersion(
                                                    getNewValue( originalValue, column.getJavaType() ) );
                                        }
                                    } else {
                                        Object entityValue = getOriginalValue( entity, field );
                                        if ( entity != null && ( entityValue == null || "0".equals( String.valueOf( entityValue ) ) ) ) {
                                            Object newValue = getNewValue( originalValue, column.getJavaType() );
                                            overwriteOriginalValue( entity, field, newValue );
                                            Object result = invocation.proceed();
                                            if ( result instanceof Integer ) {
                                                // 更新失败
                                                if ( ( Integer ) result == 0 ) {
                                                    // 将值还原至实体对象中
                                                    overwriteOriginalValue( entity, field, entityValue );
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

    private Column getColumn( Class<?> entityClass ) {
        if ( entityClass != null ) {
            if ( OPTIMISTIC_LOCKER_FIELD_CACHE.containsKey( entityClass ) ) {
                return OPTIMISTIC_LOCKER_FIELD_CACHE.get( entityClass );
            } else {
                Optional<Column> optional = Optional.ofNullable( EntityHandler.getTable( entityClass ) )
                        .map( Table::getOptimisticLockerColumn );
                if ( optional.isPresent() ) {
                    OPTIMISTIC_LOCKER_FIELD_CACHE.putIfAbsent( entityClass, optional.get() );
                    return OPTIMISTIC_LOCKER_FIELD_CACHE.get( entityClass );
                }
            }
        }
        return null;
    }

    private Object getOriginalValue( Object entity, Field field ) {
        if ( entity != null && field != null ) {
            try {
                field.setAccessible( true );
                return field.get( entity );
            } catch ( Exception e ) {
                // ignore
            }
        }
        return null;
    }

    private void overwriteOriginalValue( Object entity, Field field, Object newValue ) {
        if ( entity != null && field != null ) {
            try {
                field.setAccessible( true );
                field.set( entity, newValue );
            } catch ( Exception e ) {
                // ignore
            }
        }
    }

    private Object getNewValue( Object originalValue, Class<?> javaType ) {
        if ( long.class.equals( javaType ) || Long.class.equals( javaType ) ) {
            return ( long ) originalValue + 1;
        } else if ( int.class.equals( javaType ) || Integer.class.equals( javaType ) ) {
            return ( int ) originalValue + 1;
        } else {
            Class<? extends AbstractGenerator> generator = GeneratorFactory.getTimeGenerator( javaType );
            if ( generator != null ) {
                return GeneratorFactory.build( generator );
            }
        }
        return originalValue;
    }


    @Override
    protected boolean filter( MappedStatement statement, Object parameterObject ) {
        SqlCommandType exec = statement.getSqlCommandType();
        return exec == SqlCommandType.UPDATE && parameterObject != null
                && !( PrimitiveRegistry.isPrimitiveOrWrapper( parameterObject )
                || parameterObject.getClass() == String.class );
    }
}
