package com.wkit.lost.mybatis.plugins.data.auditing;

import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.data.auditing.MetadataAuditable;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.plugins.executor.Argument;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.snowflake.worker.SequenceWorker;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.MetaObjectUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Optional;

@Log4j2
public class DefaultBuiltinAuditingProcessor extends AbstractAuditingProcessor {

    @Override
    public boolean filter( Argument arg ) {
        return false;
    }

    @Override
    protected Object auditing( MappedStatement ms, MyBatisCustomConfiguration customConfiguration, 
                               MetadataAuditable __, Object parameter, Table table, boolean isInsertCommand ) {
        if ( table != null ) {
            MetaObject metadata = ms.getConfiguration().newMetaObject( parameter );
            // 主键值审计
            injectPrimaryKeyValue( metadata, table, isInsertCommand, customConfiguration );
            // 逻辑删除审计
            injectLogicDeletionValue( ms, metadata, table, customConfiguration );
            return metadata.getOriginalObject();
        }
        return parameter;
    }

    /**
     * 注入主键值
     * @param metadata            元数据对象
     * @param table               实体-表映射信息对象
     * @param isInsertCommand     是否为保存操作
     * @param customConfiguration 自定义配置对象
     */
    private void injectPrimaryKeyValue( MetaObject metadata, Table table, boolean isInsertCommand,
                                        MyBatisCustomConfiguration customConfiguration ) {
        Column primaryKey = table.getPrimaryKey();
        if ( isInsertCommand && primaryKey != null ) {
            String property = primaryKey.getProperty();
            MetaObject realMetadata = null;
            if ( metadata.hasGetter( property ) && metadata.hasSetter( property ) ) {
                realMetadata = metadata;
            } else {
                // 采用@Param(Constants.PARAM_ENTITY)指定实体类
                if ( metadata.hasGetter( Constants.PARAM_ENTITY ) ) {
                    Object entity = metadata.getValue( Constants.PARAM_ENTITY );
                    if ( entity != null ) {
                        realMetadata = MetaObjectUtil.forObject( entity );
                    }
                }
            }
            Optional.ofNullable( realMetadata ).ifPresent( it -> {
                if ( isAuditable( it, property, this::isNullOrBlank ) ) {
                    if ( primaryKey.isUuid() ) {
                        // guid
                        it.setValue( property, customConfiguration.getKeyGenerator().value() );
                    } else {
                        Sequence sequence = customConfiguration.getSequence();
                        if ( primaryKey.isWorker() ) {
                            // 雪花算法主键(如果不开启注入Sequence对象，则默认使用mac地址分配的Sequence对象)
                            it.setValue( property, Optional.ofNullable( sequence )
                                    .map( Sequence::nextValue ).orElse( SequenceWorker.nextValue() ) );
                        } else if ( primaryKey.isWorkerString() ) {
                            // 雪花算法字符串主键(如果不开启注入Sequence对象，则默认使用mac地址分配的Sequence对象)
                            it.setValue( property, Optional.ofNullable( sequence )
                                    .map( Sequence::nextString ).orElse( SequenceWorker.nextString() ) );
                        }
                    }
                }
            } );
        }
    }

    /**
     * 注入逻辑删除值
     * @param ms       {@link MappedStatement}
     * @param metadata 元数据对象
     * @param table    实体-表映射信息对象
     * @param __       自定义配置对象
     */
    private void injectLogicDeletionValue( MappedStatement ms, MetaObject metadata, Table table,
                                           MyBatisCustomConfiguration __ ) {
        String execMethod = execMethod( ms );
        boolean isExecLogicDelete = LOGIC_DELETE_METHOD_CACHE.contains( execMethod );
        if ( isExecLogicDelete ) {
            Column logicDeletionColumn = table.getLogicDeletionColumn();
            if ( logicDeletionColumn == null ) {
                throw new MyBatisException( "The `" + table.getEntity().getName() + "` entity class currently does not " +
                        "have a logical deletion property" );
            }
            Class<?> javaType = logicDeletionColumn.getJavaType();
            String logicDeletedProperty = logicDeletionColumn.getProperty();
            Object logicDeleteValue = primitiveConvert( javaType, logicDeletionColumn.getLogicDeletedTrueValue() );
            Object logicNotDeleteValue = primitiveConvert( javaType, logicDeletionColumn.getLogicDeletedFalseValue() );
            // 注入逻辑删除值
            metadata.setValue( Constants.PARAM_LOGIC_DELETED_AUDITING_KEY, logicDeleteValue );
            // 创建实例
            injectEntityIfNecessary( metadata, table, logicDeletedProperty, logicNotDeleteValue );
            // 注入值
            injectEntityPropertyValue( metadata, logicDeletedProperty, logicNotDeleteValue );
        }
    }

}
