package com.wkit.lost.mybatis.plugins.data.auditing;

import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.data.auditing.MetadataAuditable;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.core.snowflake.worker.SequenceGenerator;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.MetaObjectUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Optional;

/**
 * 系统内置审计处理器
 * <p>主键、逻辑删除审计</p>
 * @author wvkity
 */
@Log4j2
public class SystemBuiltinAuditingProcessor extends AbstractAuditingProcessor {

    @Override
    protected Object auditing( MappedStatement ms, MyBatisCustomConfiguration customConfiguration,
                               MetadataAuditable __, Object parameter, TableWrapper table,
                               boolean isInsertCommand, boolean isExecLogicDeleting ) {
        if ( table != null ) {
            MetaObject metadata = ms.getConfiguration().newMetaObject( parameter );
            // 主键值审计
            injectPrimaryKeyValue( metadata, table, isInsertCommand, customConfiguration );
            // 逻辑删除审计
            injectLogicDeletionValue( ms, metadata, table, customConfiguration, isExecLogicDeleting );
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
    private void injectPrimaryKeyValue( MetaObject metadata, TableWrapper table, boolean isInsertCommand,
                                        MyBatisCustomConfiguration customConfiguration ) {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if ( isInsertCommand && primaryKey != null ) {
            String property = primaryKey.getProperty();
            if ( metadata.hasGetter( property ) && metadata.hasSetter( property ) ) {
                injectPrimaryKeyValue( metadata, primaryKey, property, customConfiguration );
            } else {
                // 采用@Param(Constants.PARAM_ENTITY)指定实体类
                if ( metadata.hasGetter( Constants.PARAM_ENTITY ) ) {
                    Object entity = metadata.getValue( Constants.PARAM_ENTITY );
                    if ( entity != null ) {
                        injectPrimaryKeyValue( MetaObjectUtil.forObject( entity ), primaryKey,
                                property, customConfiguration );
                    }
                }
            }
        }
    }

    /**
     * 注入主键值
     * @param metadata            元数据
     * @param primaryKey          主键字段
     * @param property            属性
     * @param customConfiguration 自定义配置
     */
    private void injectPrimaryKeyValue( MetaObject metadata, ColumnWrapper primaryKey,
                                        String property, MyBatisCustomConfiguration customConfiguration ) {
        Optional.ofNullable( metadata ).ifPresent( it -> {
            if ( isAuditable( it, property, this::isNullOrBlank ) ) {
                if ( primaryKey.isUuid() ) {
                    // guid
                    it.setValue( property, customConfiguration.getKeyGenerator().value() );
                } else {
                    Sequence sequence = customConfiguration.getSequence();
                    if ( primaryKey.isSnowflakeSequence() ) {
                        // 雪花算法主键(如果不开启注入Sequence对象，则默认使用mac地址分配的Sequence对象)
                        it.setValue( property, Optional.ofNullable( sequence )
                                .map( Sequence::nextValue ).orElse( SequenceGenerator.nextValue() ) );
                    } else if ( primaryKey.isSnowflakeSequenceString() ) {
                        // 雪花算法字符串主键(如果不开启注入Sequence对象，则默认使用mac地址分配的Sequence对象)
                        it.setValue( property, Optional.ofNullable( sequence )
                                .map( Sequence::nextString ).orElse( SequenceGenerator.nextString() ) );
                    }
                }
            }
        } );
    }

    /**
     * 注入逻辑删除值
     * @param __       {@link MappedStatement}
     * @param metadata 元数据对象
     * @param table    实体-表映射信息对象
     * @param ___      自定义配置对象
     */
    private void injectLogicDeletionValue( MappedStatement __, MetaObject metadata, TableWrapper table,
                                           MyBatisCustomConfiguration ___, boolean isExecLogicDeleting ) {
        if ( isExecLogicDeleting ) {
            ColumnWrapper logicDeletionColumn = table.getLogicDeletedColumn();
            if ( logicDeletionColumn == null ) {
                throw new MyBatisException( "The `" + table.getEntity().getName() + "` entity class currently does not " +
                        "have a logical deletion property" );
            }
            Class<?> javaType = logicDeletionColumn.getJavaType();
            String logicDeletedProperty = logicDeletionColumn.getProperty();
            Object logicDeleteValue = logicDeletionColumn.getLogicDeletedTrueValue();
            Object logicNotDeleteValue = logicDeletionColumn.getLogicDeletedFalseValue();
            // 注入逻辑删除值
            metadata.setValue( Constants.PARAM_LOGIC_DELETED_AUDITING_KEY, logicDeleteValue );
            // 创建实例
            MetaObject injectMetadata = injectEntityIfNecessary( metadata, table, logicDeletedProperty,
                    logicNotDeleteValue );
            // 注入值
            Optional.ofNullable( injectMetadata ).ifPresent( it -> injectEntityPropertyValue( it, logicDeletedProperty,
                    logicNotDeleteValue ) );
        }
    }

}
