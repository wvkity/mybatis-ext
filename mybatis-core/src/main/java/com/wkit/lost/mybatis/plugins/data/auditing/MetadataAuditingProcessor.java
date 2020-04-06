package com.wkit.lost.mybatis.plugins.data.auditing;

import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.data.auditing.MetadataAuditable;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 元数据审计处理器
 * @author wvkity
 */
@Log4j2
public class MetadataAuditingProcessor extends AbstractAuditingProcessor {
    
    @Override
    protected Object auditing( MappedStatement ms, MyBatisCustomConfiguration customConfiguration,
                               MetadataAuditable auditable, Object parameter,
                               TableWrapper table, boolean isInsertCommand, boolean isExecLogicDeleting ) {
        if ( table != null ) {
            return Optional.ofNullable( auditable ).map( it -> {
                MetaObject metadata = ms.getConfiguration().newMetaObject( parameter );
                if ( isInsertCommand && auditable.enableInsertedAuditable() ) {
                    // 保存操作审计
                    it.inserted( metadata );
                } else if ( !isInsertCommand ) {
                    if ( isExecLogicDeleting && it.enableDeletedAuditable() ) {
                        // 逻辑删除操作审计(审计其他信息)
                        it.deleted( metadata );
                    } else if ( !isExecLogicDeleting && it.enableModifiedAuditable() ) {
                        // 更新操作审计
                        it.modified( metadata );
                    }
                }
                return metadata.getOriginalObject();
            } ).orElse( parameter );
        }
        return parameter;
    }

}
