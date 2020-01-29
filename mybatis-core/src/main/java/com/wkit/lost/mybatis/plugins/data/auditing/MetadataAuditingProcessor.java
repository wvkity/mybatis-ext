package com.wkit.lost.mybatis.plugins.data.auditing;

import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.metadata.Table;
import com.wkit.lost.mybatis.data.auditing.MetadataAuditable;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Log4j2
public class MetadataAuditingProcessor extends AbstractAuditingProcessor {

    private static final String PARAM_KEY_COLLECTION = "collection";
    private static final String PARAM_KEY_LIST = "list";
    private static final String PARAM_KEY_ARRAY = "array";
    private static final Set<String> LOGIC_DELETE_METHOD_CACHE =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( "logicDelete", "logicDeleteByCriteria" ) ) );

    @Override
    protected Object auditing( MappedStatement ms, MyBatisCustomConfiguration customConfiguration,
                               MetadataAuditable auditable, Object parameter,
                               Table table, boolean isInsertCommand ) {
        if ( table != null ) {
            String execMethod = execMethod( ms );
            boolean isExecLogicDeleting = LOGIC_DELETE_METHOD_CACHE.contains( execMethod );
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
