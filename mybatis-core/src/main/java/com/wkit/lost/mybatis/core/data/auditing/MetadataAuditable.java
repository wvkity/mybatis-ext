package com.wkit.lost.mybatis.core.data.auditing;

import org.apache.ibatis.reflection.MetaObject;

/**
 * 元数据审计处理接口
 * @author wvkity
 */
public interface MetadataAuditable {

    /**
     * 保存操作审计
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default MetadataAuditable inserted(MetaObject metadata, String property, Object value) {
        return invoke(metadata, property, value, AuditMatching.INSERTED);
    }

    /**
     * 更新操作审计
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default MetadataAuditable modified(MetaObject metadata, String property, Object value) {
        return invoke(metadata, property, value, AuditMatching.MODIFIED);
    }

    /**
     * 删除操作审计
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default MetadataAuditable deleted(MetaObject metadata, String property, Object value) {
        return invoke(metadata, property, value, AuditMatching.DELETED);
    }

    /**
     * 检查是否可审计
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @return true: 是, false: 否
     */
    boolean isAuditable(MetaObject metadata, String property, Object value);

    /**
     * 检查是否可审计
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @param matching 匹配类型
     * @return true: 是, false: 否
     */
    boolean isAuditable(MetaObject metadata, String property, Object value, AuditMatching matching);

    /**
     * 保存操作审计
     * @param metadata 元数据
     */
    void inserted(MetaObject metadata);

    /**
     * 更新操作审计
     * @param metadata 元数据
     */
    void modified(MetaObject metadata);

    /**
     * 删除操作审计
     * @param metadata 元数据
     */
    void deleted(MetaObject metadata);

    /**
     * 填充值
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    MetadataAuditable invoke(MetaObject metadata, String property, Object value);

    /**
     * 填充值
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @param matching 匹配类型
     * @return 当前对象
     */
    MetadataAuditable invoke(MetaObject metadata, String property, Object value, AuditMatching matching);

    /**
     * 是否启用保存操作审计
     * @return true: 是, false: 否
     */
    boolean enableInsertedAuditable();

    /**
     * 是否启用更新操作审计
     * @return true: 是, false: 否
     */
    boolean enableModifiedAuditable();

    /**
     * 是否启用删除操作审计
     * @return true: 是, false: 否
     */
    boolean enableDeletedAuditable();

    /**
     * 是否启用自动审计
     * @return true: 是, false: 否
     */
    boolean enableAutomaticAuditable();
}
