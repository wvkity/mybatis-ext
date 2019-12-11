package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.condition.criterion.Restrictions;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.filling.MetaObjectFillingHandler;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.snowflake.worker.SequenceWorker;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.PrimitiveRegistry;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 自动填充值执行器
 * @author wvkity
 * @see com.wkit.lost.mybatis.scripting.defaults.MyBatisDefaultParameterHandler
 */
@Log4j2
public class MetaObjectFillingExecutor extends AbstractUpdateExecutor {

    private static final Set<String> LOGICAL_DELETION_METHODS = new HashSet<>( Arrays.asList( "logicDelete", "logicDeleteByCriteria" ) );

    public Object intercept( Invocation invocation ) throws Throwable {
        MappedStatement statement = ( MappedStatement ) invocation.getArgs()[ 0 ];
        Object parameterObject = invocation.getArgs()[ 1 ];
        if ( filter( statement, parameterObject ) ) {
            Object newParameter = processFillValue( statement, parameterObject );
            invocation.getArgs()[ 1 ] = newParameter;
        }
        return invocation.proceed();
    }

    /**
     * 处理自动填充
     * @param statement       {@link MappedStatement}对象
     * @param parameterObject 参数
     * @return 处理后的参数
     */
    @SuppressWarnings( "unchecked" )
    private static Object processFillValue( MappedStatement statement, Object parameterObject ) {
        boolean isInsert = statement.getSqlCommandType() == SqlCommandType.INSERT;
        Collection<Object> parameters = getParameters( parameterObject );
        if ( parameters != null ) {
            List<Object> objects = new ArrayList<>( parameters.size() );
            for ( Object parameter : parameters ) {
                Table table = EntityHandler.getTable( parameter.getClass() );
                if ( table != null ) {
                    objects.add( fillValue( statement, parameter, table, isInsert ) );
                } else {
                    objects.add( parameter );
                }
            }
            return objects;
        } else {
            Table table = null;
            if ( parameterObject instanceof Map ) {
                Map<String, Object> map = ( Map<String, Object> ) parameterObject;
                if ( map.containsKey( Constants.PARAM_ENTITY ) ) {
                    Object entity = map.get( Constants.PARAM_ENTITY );
                    if ( entity != null ) {
                        table = EntityHandler.getTable( entity.getClass() );
                    }
                } else if ( map.containsKey( Constants.PARAM_CRITERIA ) ) {
                    Object criteria = map.get( Constants.PARAM_CRITERIA );
                    if ( criteria != null ) {
                        MetaObject criteriaMetaObject = SystemMetaObject.forObject( criteria );
                        if ( criteriaMetaObject.hasGetter( "entity" ) ) {
                            Object entity = criteriaMetaObject.getValue( "entity" );
                            if ( entity instanceof Class ) {
                                table = EntityHandler.getTable( ( Class<?> ) entity );
                            }
                        }
                    }
                }
            } else {
                table = EntityHandler.getTable( parameterObject.getClass() );
            }
            return fillValue( statement, parameterObject, table, isInsert );
        }
    }

    /**
     * 自动填充值
     * @param statement {@link MappedStatement}对象
     * @param parameter 参数
     * @param table     表映射对象
     * @param isInsert  是否为保存操作
     * @return 参数对象
     */
    private static Object fillValue( MappedStatement statement, Object parameter, Table table, boolean isInsert ) {
        if ( table == null ) {
            return parameter;
        }

        MetaObject metaObject = statement.getConfiguration().newMetaObject( parameter );
        // 保存操作填充主键值
        MyBatisCustomConfiguration customConfiguration =
                MyBatisConfigCache.getCustomConfiguration( statement.getConfiguration() );
        if ( isInsert && table.getPrimaryKey() != null ) {
            MetaObject realMetaObject = null;
            Column primaryKey = table.getPrimaryKey();
            String property = primaryKey.getProperty();
            if ( metaObject.hasGetter( property ) && metaObject.hasSetter( property ) ) {
                realMetaObject = metaObject;
            } else {
                // 采用@Param方式指定
                if ( metaObject.hasGetter( Constants.PARAM_ENTITY ) ) {
                    Object entity = metaObject.getValue( Constants.PARAM_ENTITY );
                    if ( entity != null ) {
                        realMetaObject = SystemMetaObject.forObject( entity );
                    }
                }
            }
            if ( realMetaObject != null ) {
                Object value = realMetaObject.getValue( property );
                if ( isNullOrEmpty( value ) ) {
                    Sequence sequence = customConfiguration.getSequence();
                    if ( primaryKey.isUuid() ) {
                        // guid
                        realMetaObject.setValue( property, customConfiguration.getKeyGenerator().value() );
                    } else if ( primaryKey.isWorker() ) {
                        // 雪花算法主键(如果不开启注入Sequence对象，则默认使用mac地址分配的Sequence对象)
                        realMetaObject.setValue( property, Optional.ofNullable( sequence ).map( Sequence::nextId ).orElse( SequenceWorker.nextId() ) );
                    } else if ( primaryKey.isWorkerString() ) {
                        // 雪花算法字符串主键(如果不开启注入Sequence对象，则默认使用mac地址分配的Sequence对象)
                        realMetaObject.setValue( property, Optional.ofNullable( sequence ).map( Sequence::nextStringId ).orElse( SequenceWorker.nextStringId() ) );
                    }
                }
            }
        }
        String id = statement.getId();
        String execTarget = id.substring( id.lastIndexOf( "." ) + 1 );
        boolean execLogicDelete = LOGICAL_DELETION_METHODS.contains( execTarget );
        // 逻辑删除填充逻辑删除属性值
        if ( execLogicDelete ) {
            // 如果是逻辑删除操作,将逻辑删除标识值填充到元对象中
            Column logicalDeletionColumn = table.getLogicalDeletionColumn();
            if ( logicalDeletionColumn == null ) {
                throw new MyBatisException( "The `" + table.getEntity().getName() + "` entity class currently does not " +
                        "have a logical deletion property" );
            }
            Class<?> javaType = logicalDeletionColumn.getJavaType();
            Object logicDeleteValue = convert( javaType, logicalDeletionColumn.getLogicDeleteValue() );
            Object logicNotDeleteValue = convert( javaType, logicalDeletionColumn.getLogicNotDeleteValue() );
            metaObject.setValue( Constants.PARAM_LOGIC_DELETED_AUTO_KEY, logicDeleteValue );
            // 创建实例
            injectEntityParam( metaObject, table, logicalDeletionColumn.getProperty(), logicNotDeleteValue );
            // 填充实体对象属性值
            entityFillingValue( metaObject, logicalDeletionColumn.getProperty(), logicNotDeleteValue );
        }
        MetaObjectFillingHandler fillingHandler = customConfiguration.getMetaObjectFillingHandler();
        if ( fillingHandler != null ) {
            if ( isInsert && fillingHandler.enableInsert() ) {
                // 保存操作自动填充
                fillingHandler.insertFilling( metaObject );
            } else if ( !isInsert ) {
                // 检查是否为逻辑删除
                if ( fillingHandler.enableDelete() && execLogicDelete ) {
                    // 逻辑删除自动填充
                    fillingHandler.deleteFilling( metaObject );
                } else if ( fillingHandler.enableUpdate() && !execLogicDelete ) {
                    // 更新操作自动填充
                    fillingHandler.updateFilling( metaObject );
                }
            }
        }
        return metaObject.getOriginalObject();
    }

    /**
     * 注入实体对象参数
     * @param metaObject          元对象
     * @param table               表映射对象
     * @param property            逻辑删除属性
     * @param logicNotDeleteValue 未删除标识值
     */
    private static void injectEntityParam( MetaObject metaObject, Table table, String property, Object logicNotDeleteValue ) {
        if ( !metaObject.hasGetter( Constants.PARAM_ENTITY ) ) {
            if ( metaObject.hasGetter( Constants.PARAM_CRITERIA ) ) {
                Object criteria = metaObject.getValue( Constants.PARAM_CRITERIA );
                if ( criteria instanceof Criteria ) {
                    try {
                        // 设置未标识删除值
                        Criteria<?> criteriaInstance = ( Criteria<?> ) criteria;
                        criteriaInstance.add( Restrictions.eq( property, logicNotDeleteValue ) );
                        metaObject.setValue( Constants.PARAM_ENTITY, table.getEntity().getDeclaredConstructor().newInstance() );
                    } catch ( Exception e ) {
                        throw new MyBatisException( "Failed to create an instance based on the `"
                                + table.getEntity().getName() + "` class", e );
                    }
                }
            }
        }
    }

    /**
     * 实体参数对象填充值
     * @param metaObject 元对象
     * @param property   属性
     * @param value      值
     */
    private static void entityFillingValue( MetaObject metaObject, String property, Object value ) {
        if ( value != null ) {
            if ( metaObject.hasGetter( property ) && metaObject.hasSetter( property ) ) {
                metaObject.setValue( property, value );
            } else if ( metaObject.hasGetter( Constants.PARAM_ENTITY ) ) {
                Object entity = metaObject.getValue( Constants.PARAM_ENTITY );
                if ( entity != null ) {
                    entityFillingValue( SystemMetaObject.forObject( entity ), property, value );
                }
            }
        }
    }

    /**
     * 判断对象值是否为空
     * @param value 值
     * @return true | false
     */
    private static boolean isNullOrEmpty( Object value ) {
        if ( value != null ) {
            if ( value instanceof CharSequence ) {
                return Ascii.isNullOrEmpty( value.toString() );
            }
            return false;
        }
        return true;
    }

    /**
     * 获取参数列表
     * @param parameter 参数对象
     * @return 参数列表
     */
    @SuppressWarnings( { "unchecked" } )
    private static Collection<Object> getParameters( Object parameter ) {
        Collection<Object> parameters = null;
        if ( parameter instanceof Collection ) {
            parameters = ( Collection<Object> ) parameter;
        } else if ( parameter instanceof Map ) {
            Map<String, Object> map = ( Map<String, Object> ) parameter;
            if ( map.containsKey( "collection" ) ) {
                parameters = ( Collection<Object> ) map.get( "collection" );
            } else if ( map.containsKey( "list" ) ) {
                parameters = ( Collection<Object> ) map.get( "list" );
            } else if ( map.containsKey( "array" ) ) {
                parameters = Arrays.asList( ( Object[] ) map.get( "array" ) );
            }
        }
        return parameters;
    }

    /**
     * 逻辑删除值转换
     * @param javaType 数据类型
     * @param value    值
     * @return 转换后的值
     */
    private static Object convert( Class<?> javaType, String value ) {
        if ( javaType == null || value == null || javaType == String.class ) {
            return value;
        }
        if ( javaType == Integer.class || javaType == int.class ) {
            return Integer.valueOf( value );
        } else if ( javaType == Long.class || javaType == long.class ) {
            return Long.valueOf( value );
        }
        if ( javaType == Short.class || javaType == short.class ) {
            return Short.valueOf( value );
        }
        if ( javaType == Character.class || javaType == char.class ) {
            return value.charAt( 0 );
        }
        if ( javaType == Boolean.class || javaType == boolean.class ) {
            return Boolean.valueOf( value );
        }
        return value;
    }
}
