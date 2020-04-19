package com.wkit.lost.mybatis.core.data.auditing;

import com.wkit.lost.mybatis.core.data.auditing.time.proxy.DateTimeProviderFactory;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 默认元数据审计处理器
 * @author wvkity
 */
@Log4j2
@Setter
public class MetadataAuditingHandler extends AbstractMetadataAuditable {

    private boolean inserted;
    private boolean modified;
    private boolean deleted;
    private boolean automatic;
    private AuditorAware auditorAware;

    @Override
    public void inserted(MetaObject metadata, TableWrapper table) {
        Optional.ofNullable(table).ifPresent(it ->
                auditing(metadata, it, it.insertedAuditableColumns(), AuditMatching.INSERTED));
    }

    @Override
    public void modified(MetaObject metadata, TableWrapper table, String method) {
        Optional.ofNullable(table).ifPresent(it ->
                auditing(metadata, it, it.modifiedAuditableColumns(), AuditMatching.MODIFIED));
    }

    @Override
    public void deleted(MetaObject metadata, TableWrapper table, String method) {
        Optional.ofNullable(table).ifPresent(it ->
                auditing(metadata, it, it.deletedAuditableColumns(), AuditMatching.DELETED));
    }

    private void auditing(MetaObject metadata, TableWrapper __, Set<ColumnWrapper> columns,
                          AuditMatching matching) {
        Optional<AuditorAware> auditorAwareOptional = Optional.ofNullable(auditorAware);
        boolean isPresent = auditorAwareOptional.isPresent();
        if (isNotEmpty(columns)) {
            for (ColumnWrapper column : columns) {
                if (column.isDateAuditable()) {
                    auditing(metadata, column, AuditType.TIME);
                } else if (isPresent) {
                    if (column.isUserAuditable()) {
                        auditing(metadata, column, AuditType.USER_ID);
                    } else if (column.isUserNameAuditable()) {
                        auditing(metadata, column, AuditType.USER_NAME);
                    }
                }
            }
        }
    }

    /**
     * 审计
     * @param metadata 元数据
     * @param column   字段包装对象
     * @param type     审计类型
     */
    private void auditing(MetaObject metadata, ColumnWrapper column, AuditType type) {
        switch (type) {
            case TIME:
                invoke(metadata, column);
                break;
            case USER_ID:
                invoke(metadata, column, AuditorAware::currentUserId);
                break;
            case USER_NAME:
                invoke(metadata, column, AuditorAware::currentUserName);
                break;
        }
    }

    /**
     * 时间类型数据审计
     * @param metadata 元数据
     * @param column   字段包装对象
     */
    private void invoke(MetaObject metadata, ColumnWrapper column) {
        Optional.ofNullable(DateTimeProviderFactory.ProviderBuilder.create()
                .target(column.getJavaType()).build()).ifPresent(v ->
                invoke(metadata, column.getProperty(), v.getNow()));
    }

    /**
     * 其他类型数据审计
     * @param metadata 元数据
     * @param column   字段包装对象
     * @param function {@link Function}
     */
    private void invoke(MetaObject metadata, ColumnWrapper column, Function<AuditorAware, Object> function) {
        if (auditorAware != null && function != null) {
            invoke(metadata, column.getProperty(), function.apply(auditorAware));
        }
    }

    private boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    @Override
    public boolean enableInsertedAuditable() {
        return this.inserted;
    }

    @Override
    public boolean enableModifiedAuditable() {
        return this.modified;
    }

    @Override
    public boolean enableDeletedAuditable() {
        return this.deleted;
    }

    @Override
    public boolean enableAutomaticAuditable() {
        return this.automatic;
    }
}
