package com.wkit.lost.mybatis.core.data.auditing;

import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.AbstractUpdateCriteriaWrapper;
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
     * @return {@code this}
     */
    default MetadataAuditable inserted(MetaObject metadata, String property, Object value) {
        return invoke(metadata, property, value, AuditMatching.INSERTED);
    }

    /**
     * 更新操作审计
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default MetadataAuditable modified(MetaObject metadata, String property, Object value) {
        return invoke(metadata, property, value, AuditMatching.MODIFIED);
    }

    /**
     * 删除操作审计
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @return {@code this}
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
     * @param table    表包装对象
     */
    void inserted(MetaObject metadata, TableWrapper table);

    /**
     * 更新操作审计
     * @param metadata 元数据
     * @param table    表包装对象
     * @param method   执行的方法名
     */
    void modified(MetaObject metadata, TableWrapper table, String method);

    /**
     * 删除操作审计
     * @param metadata 元数据
     * @param table    表包装对象
     * @param method   执行的方法名
     */
    void deleted(MetaObject metadata, TableWrapper table, String method);

    /**
     * 注入值
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    MetadataAuditable invoke(MetaObject metadata, String property, Object value);

    /**
     * 注入值
     * @param metadata 元数据
     * @param property 属性
     * @param value    值
     * @param matching 匹配类型
     * @return {@code this}
     */
    MetadataAuditable invoke(MetaObject metadata, String property, Object value, AuditMatching matching);

    /**
     * 注入更新值
     * @param criteria 更新条件包装条件对象
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    MetadataAuditable invoke(AbstractUpdateCriteriaWrapper<?> criteria, String property, Object value);

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
