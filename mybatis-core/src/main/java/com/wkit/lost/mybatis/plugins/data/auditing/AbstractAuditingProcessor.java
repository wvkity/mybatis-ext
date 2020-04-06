package com.wkit.lost.mybatis.plugins.data.auditing;

import com.wkit.lost.mybatis.batch.BatchDataBeanWrapper;
import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.conditional.Restrictions;
import com.wkit.lost.mybatis.core.data.auditing.MetadataAuditable;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.plugins.processor.UpdateProcessorSupport;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.MetaObjectUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 抽象审计处理器
 * @author wvkity
 */
@Log4j2
abstract class AbstractAuditingProcessor extends UpdateProcessorSupport {

    protected static final String PARAM_KEY_COLLECTION = "collection";
    protected static final String PARAM_KEY_LIST = "list";
    protected static final String PARAM_KEY_ARRAY = "array";
    protected static final String METHOD_BATCH_INSERT_NOT_WITH_AUDIT = "batchInsertNotWithAudit";
    protected static final Set<String> LOGIC_DELETE_METHOD_CACHE =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( "logicDelete", "logicDeleteByCriteria" ) ) );

    @Override
    public boolean filter( MappedStatement ms, Object parameter ) {
        return super.filter( ms, parameter ) && !( METHOD_BATCH_INSERT_NOT_WITH_AUDIT.equals( execMethod( ms ) ) );
    }

    @Override
    protected Object doProceed( Invocation invocation, MappedStatement ms, Object parameter ) throws Throwable {
        if ( filter( ms, parameter ) ) {
            invocation.getArgs()[ 1 ] = processParameter( ms, parameter );
        }
        return invocation.proceed();
    }

    /**
     * 获取表映射信息对象
     * @param parameter 参数
     * @return 表对象
     */
    @SuppressWarnings( { "unchecked" } )
    protected TableWrapper parse( Object parameter ) {
        if ( parameter instanceof Map ) {
            Map<String, Object> map = ( Map<String, Object> ) parameter;
            // 检查参数是否包含实体对象
            if ( map.containsKey( Constants.PARAM_ENTITY ) ) {
                return Optional.ofNullable( map.getOrDefault( Constants.PARAM_ENTITY, null ) )
                        .map( it -> TableHandler.getTable( it.getClass() ) )
                        .orElse( null );
            }
            // 检查参数是否包含条件对象
            if ( map.containsKey( Constants.PARAM_CRITERIA ) ) {
                return Optional.ofNullable( map.getOrDefault( Constants.PARAM_CRITERIA, null ) )
                        .map( it -> {
                            MetaObject metadata = MetaObjectUtil.forObject( it );
                            if ( metadata.hasGetter( Constants.PARAM_ENTITY_CLASS ) ) {
                                return Optional.ofNullable( metadata.getValue( Constants.PARAM_ENTITY_CLASS ) )
                                        .filter( klass -> klass instanceof Class )
                                        .map( klass -> TableHandler.getTable( ( Class<?> ) klass ) )
                                        .orElse( null );
                            }
                            return null;
                        } ).orElse( null );
            }
        } else {
            return TableHandler.getTable( parameter.getClass() );
        }
        return null;
    }

    /**
     * 获取参数
     * @param parameter 参数对象
     * @return 参数
     */
    @SuppressWarnings( { "unchecked" } )
    protected static Collection<Object> getOriginalParameter( Object parameter ) {
        if ( parameter instanceof Collection ) {
            return ( Collection<Object> ) parameter;
        }
        if ( parameter instanceof Map ) {
            Map<String, Object> map = ( Map<String, Object> ) parameter;
            if ( map.containsKey( PARAM_KEY_COLLECTION ) ) {
                return ( Collection<Object> ) map.getOrDefault( PARAM_KEY_COLLECTION, null );
            }
            if ( map.containsKey( PARAM_KEY_LIST ) ) {
                return ( Collection<Object> ) map.getOrDefault( PARAM_KEY_LIST, null );
            }
            if ( map.containsKey( PARAM_KEY_ARRAY ) ) {
                return ArrayUtil.toList( ( Object[] ) map.getOrDefault( PARAM_KEY_ARRAY, null ) );
            }
        }
        return null;
    }

    /**
     * 必要时注入实体对象参数(注入空对象，交由)
     * @param metadata 元数据对象
     * @param table    实体-表映射信息对象
     * @param property 属性
     * @param value    值
     */
    protected void injectEntityIfNecessary( MetaObject metadata, TableWrapper table, String property, Object value ) {
        // 检查是否包含实体对象参数
        if ( !metadata.hasGetter( Constants.PARAM_ENTITY ) ) {
            if ( metadata.hasGetter( Constants.PARAM_CRITERIA ) ) {
                Object parameter = metadata.getValue( Constants.PARAM_CRITERIA );
                if ( parameter instanceof Criteria ) {
                    try {
                        Criteria<?> criteria = ( Criteria<?> ) parameter;
                        // 注入条件
                        //criteria.add( Restrictions.eq( property, value ) );
                        criteria.add( Restrictions.eq( criteria, property, value ) );
                        // 注入实体参数对象，但不填充值
                        metadata.setValue( Constants.PARAM_ENTITY, table.newInstance() );
                    } catch ( Exception e ) {
                        throw new MyBatisException( "Failed to create an instance based on the `"
                                + table.getEntity().getName() + "` class", e );
                    }
                }
            }
        }
    }

    /**
     * 注入指定实体属性值
     * @param metadata 元数据对象
     * @param property 属性
     * @param value    值
     */
    protected void injectEntityPropertyValue( MetaObject metadata, String property, Object value ) {
        if ( isAuditable( metadata, property, value, this::isNullOrEmpty ) ) {
            metadata.setValue( property, value );
        } else if ( metadata.hasGetter( Constants.PARAM_ENTITY ) ) {
            Object entity = metadata.getValue( Constants.PARAM_ENTITY );
            if ( entity != null ) {
                injectEntityPropertyValue( MetaObjectUtil.forObject( entity ), property, value );
            }
        }
    }

    /**
     * 检查元数据指定属性是否可审计
     * @param metadata 元数据对象
     * @param property 属性
     * @param function Lambda对象
     * @return true: 是 false: 否
     */
    protected boolean isAuditable( MetaObject metadata, String property, Function<Object, Boolean> function ) {
        return Optional.of( metadata )
                .filter( it -> it.hasGetter( property ) && it.hasSetter( property ) )
                .map( it -> function.apply( it.getValue( property ) ) )
                .orElse( false );
    }

    /**
     * 检查元数据指定属性、值是否可审计
     * @param metadata   元数据对象
     * @param property   属性
     * @param auditValue 审计值
     * @param function   Lambda对象
     * @return true: 是 false: 否
     */
    protected boolean isAuditable( MetaObject metadata, String property,
                                   Object auditValue, Function<Object, Boolean> function ) {
        return Optional.ofNullable( auditValue )
                .map( __ -> this.isAuditable( metadata, property, function ) ).orElse( false );
    }

    /**
     * 检查值是否为null或空白
     * @param value 待检查值
     * @return true: 是 false: 否
     */
    protected boolean isNullOrBlank( Object value ) {
        return Optional.ofNullable( value ).map( it -> {
            if ( it instanceof CharSequence ) {
                return !Ascii.hasText( value.toString() );
            }
            return false;
        } ).orElse( true );
    }

    /**
     * 检查值是否为null或空
     * @param value 待检查值
     * @return true: 是 false: 否
     */
    protected boolean isNullOrEmpty( Object value ) {
        return Optional.ofNullable( value ).map( it -> {
            if ( it instanceof CharSequence ) {
                return Ascii.isNullOrEmpty( value.toString() );
            }
            return false;
        } ).orElse( true );
    }

    /**
     * 处理参数
     * @param ms        {@link MappedStatement}
     * @param parameter 方法参数
     * @return 处理后的参数
     */
    @SuppressWarnings( { "unchecked" } )
    protected Object processParameter( MappedStatement ms, Object parameter ) {
        boolean isInsertCommand = ms.getSqlCommandType() == SqlCommandType.INSERT;
        String execMethod = execMethod( ms );
        boolean isExecLogicDeleting = LOGIC_DELETE_METHOD_CACHE.contains( execMethod );
        Collection<Object> parameters = getOriginalParameter( parameter );
        MyBatisCustomConfiguration configuration = MyBatisConfigCache.getCustomConfiguration( ms.getConfiguration() );
        MetadataAuditable auditable = configuration.getMetadataAuditable();
        if ( parameters != null && !parameters.isEmpty() ) {
            List<Object> objects = new ArrayList<>( parameters.size() );
            for ( Object param : parameters ) {
                objects.add( Optional.ofNullable( parse( param ) )
                        .map( it -> auditing( ms, configuration, auditable, param, it,
                                isInsertCommand, isExecLogicDeleting ) )
                        .orElse( param ) );
            }
            return objects;
        } else if ( parameter instanceof Map
                && ( ( Map<?, ?> ) parameter ).containsKey( Constants.PARAM_BATCH_BEAN_WRAPPER ) ) {
            Object wrapperTarget = ( ( Map<?, ?> ) parameter ).getOrDefault( Constants.PARAM_BATCH_BEAN_WRAPPER, null );
            if ( wrapperTarget != null ) {
                BatchDataBeanWrapper<Object> wrapper = ( BatchDataBeanWrapper<Object> ) wrapperTarget;
                Collection<Object> data = wrapper.getData();
                if ( data != null && !data.isEmpty() ) {
                    for ( Object entityTarget : data ) {
                        Optional.ofNullable( parse( entityTarget ) ).map( it -> auditing( ms, configuration,
                                auditable, entityTarget, it, isInsertCommand, isExecLogicDeleting ) );
                    }
                }
            }
            return parameter;
        } else {
            return Optional.ofNullable( parse( parameter ) )
                    .map( it -> auditing( ms, configuration, auditable, parameter, it,
                            isInsertCommand, isExecLogicDeleting ) )
                    .orElse( parameter );
        }
    }

    /**
     * 审计
     * @param ms                  {@link MappedStatement}
     * @param configuration       自定义配置对象
     * @param auditable           审计对象
     * @param parameter           方法参数
     * @param table               实体-表映射信息对象
     * @param isInsertCommand     是否为保存操作
     * @param isExecLogicDeleting 是否为逻辑删除
     * @return 审计后的方法参数
     */
    protected abstract Object auditing( MappedStatement ms, MyBatisCustomConfiguration configuration,
                                        MetadataAuditable auditable, Object parameter,
                                        TableWrapper table, boolean isInsertCommand, boolean isExecLogicDeleting );
}
