package com.wvkity.mybatis.core.metadata;

import com.wvkity.mybatis.core.data.auditing.AuditMatching;
import com.wvkity.mybatis.core.immutable.ImmutableLinkedMap;
import com.wvkity.mybatis.core.immutable.ImmutableLinkedSet;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 数据库表映射
 * @author wvkity
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class TableWrapper {

    /**
     * 属性-字段包装对象缓存
     */
    @Getter(AccessLevel.NONE)
    private final Map<String, ColumnWrapper> PROPERTY_COLUMN_CACHE = new ConcurrentHashMap<>();
    /**
     * 属性-字段包装对象缓存(有序只读)
     */
    @Getter(AccessLevel.NONE)
    private final Map<String, ColumnWrapper> READ_ONLY_PROPERTY_COLUMN_CACHE;
    /**
     * 所有字段包装对象缓存
     */
    @Getter(AccessLevel.NONE)
    private final Set<ColumnWrapper> COLUMN_CACHE;
    /**
     * 所有字段包装对象缓存(只读)
     */
    @Getter(AccessLevel.NONE)
    private final Set<ColumnWrapper> READ_ONLY_COLUMN_CACHE;
    /**
     * 可保存字段包装对象缓存(只读)
     */
    @Getter(AccessLevel.NONE)
    private final Set<ColumnWrapper> READ_ONLY_INSERTABLE_COLUMN_CACHE;
    /**
     * 可更新字段包装对象缓存(只读)
     */
    @Getter(AccessLevel.NONE)
    private final Set<ColumnWrapper> READ_ONLY_UPDATABLE_COLUMN_CACHE;
    /**
     * 实体类
     */
    private final Class<?> entity;

    /**
     * 数据库表名
     */
    private final String name;

    /**
     * Mapper接口命名空间
     */
    private final String namespace;

    /**
     * 数据库目录
     */
    private final String catalog;

    /**
     * 数据库模式
     */
    private final String schema;

    /**
     * 表前缀
     */
    private final String prefix;

    /**
     * 排序
     */
    private final String order;

    /**
     * 是否启用逻辑删除
     */
    private final boolean enableLogicDeleted;

    /**
     * 主键字段
     */
    private final ColumnWrapper primaryKey;

    /**
     * 主键属性
     */
    private final String primaryKeyProperty;

    /**
     * 乐观锁字段
     */
    private final ColumnWrapper optimisticLockingColumn;

    /**
     * 逻辑删除字段
     */
    private final ColumnWrapper logicDeletedColumn;

    /**
     * 构造方法
     * @param entity                  实体类
     * @param name                    数据库表名
     * @param namespace               Mapper接口命名空间
     * @param catalog                 数据库目录
     * @param schema                  数据库模式
     * @param prefix                  表前缀
     * @param order                   排序
     * @param enableLogicDeleted      是否启用逻辑删除
     * @param primaryKey              主键字段
     * @param primaryKeyProperty      主键属性
     * @param optimisticLockingColumn 乐观锁字段
     * @param logicDeletedColumn      逻辑删除字段
     * @param columns                 所有字段
     */
    public TableWrapper(Class<?> entity, String name, String namespace, String catalog, String schema,
                        String prefix, String order, boolean enableLogicDeleted, ColumnWrapper primaryKey,
                        String primaryKeyProperty, ColumnWrapper optimisticLockingColumn,
                        ColumnWrapper logicDeletedColumn, Set<ColumnWrapper> columns) {
        this.entity = entity;
        this.name = name;
        this.namespace = namespace;
        this.catalog = catalog;
        this.schema = schema;
        this.prefix = prefix;
        this.order = order;
        this.enableLogicDeleted = enableLogicDeleted;
        this.primaryKey = primaryKey;
        this.primaryKeyProperty = primaryKeyProperty;
        this.optimisticLockingColumn = optimisticLockingColumn;
        this.logicDeletedColumn = logicDeletedColumn;
        this.COLUMN_CACHE = new LinkedHashSet<>(columns);
        this.READ_ONLY_COLUMN_CACHE = ImmutableLinkedSet.construct(this.COLUMN_CACHE);
        this.READ_ONLY_PROPERTY_COLUMN_CACHE = this.initDefinition();
        this.READ_ONLY_INSERTABLE_COLUMN_CACHE = ImmutableLinkedSet.construct(filtrate(ColumnWrapper::isInsertable));
        this.READ_ONLY_UPDATABLE_COLUMN_CACHE = ImmutableLinkedSet.construct(filtrate(ColumnWrapper::isUpdatable));
    }

    /**
     * 获取所有字段集合
     * @return 数据库表字段
     */
    public Set<ColumnWrapper> columns() {
        return this.READ_ONLY_COLUMN_CACHE;
    }

    /**
     * 获取所有可保存字段集合
     * @return 可保存字段集合
     */
    public Set<ColumnWrapper> insertableColumns() {
        return this.READ_ONLY_INSERTABLE_COLUMN_CACHE;
    }

    /**
     * 获取所有可更新字段集合
     * @return 可更新字段集合
     */
    public Set<ColumnWrapper> updatableColumns() {
        return this.READ_ONLY_UPDATABLE_COLUMN_CACHE;
    }

    /**
     * 获取除乐观锁外其他可更新字段集合
     * @return 可更新字段集合
     */
    public Set<ColumnWrapper> updatableColumnsExcludeLocking() {
        return this.optimisticLockingColumn == null ? updatableColumns() :
                filtrate(it -> it.isUpdatable() && !it.isVersion());
    }

    /**
     * 获取所有保存操作自动审计字段集合
     * @return 保存操作自动审计字段集合
     */
    public Set<ColumnWrapper> insertedAuditableColumns() {
        return auditableColumns(AuditMatching.INSERTED);
    }

    /**
     * 获取所有更新操作自动审计字段集合
     * @return 更新操作自动审计字段集合
     */
    public Set<ColumnWrapper> modifiedAuditableColumns() {
        return auditableColumns(AuditMatching.MODIFIED);
    }

    /**
     * 获取所有删除操作自动审计字段集合
     * @return 删除操作自动审计字段集合
     */
    public Set<ColumnWrapper> deletedAuditableColumns() {
        return auditableColumns(AuditMatching.DELETED);
    }

    /**
     * 获取所有非删除审计字段集合
     * @return 非删除审计字段集合
     */
    public Set<ColumnWrapper> excludeDeletedAuditableColumns() {
        return filtrate(it -> !it.deletedAuditable());
    }

    /**
     * 获取自动审计字段集合
     * @param matching 审计类型
     * @return 字段集合
     */
    private Set<ColumnWrapper> auditableColumns(AuditMatching matching) {
        return filtrate(it -> it.isAuditable(matching));
    }

    /**
     * 筛选字段
     * @param filter 过滤Lambda对象
     * @return 字段集合
     */
    private Set<ColumnWrapper> filtrate(Predicate<ColumnWrapper> filter) {
        return this.READ_ONLY_COLUMN_CACHE.stream().filter(filter).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * 初始化定义信息
     */
    private Map<String, ColumnWrapper> initDefinition() {
        if (this.COLUMN_CACHE != null && !this.COLUMN_CACHE.isEmpty()) {
            Map<String, ColumnWrapper> ret = new LinkedHashMap<>();
            this.READ_ONLY_COLUMN_CACHE.forEach(it -> {
                this.PROPERTY_COLUMN_CACHE.put(it.getProperty(), it);
                ret.put(it.getProperty(), it);
            });
            return ImmutableLinkedMap.construct(ret);
        }
        return ImmutableLinkedMap.of();
    }

    /**
     * 获取所有字段映射信息
     * @return 字段映射集合
     */
    public Map<String, ColumnWrapper> columnMappings() {
        return this.READ_ONLY_PROPERTY_COLUMN_CACHE;
    }

    /**
     * 根据属性查找对应的字段信息
     * @param property 属性
     * @return 字段信息
     */
    public Optional<ColumnWrapper> search(String property) {
        return Optional.ofNullable(property).map(this.PROPERTY_COLUMN_CACHE::get);
    }

    /**
     * 获取存在的statement
     * @param method 方法名
     * @return statement
     */
    public String getSqlStatement(final String method) {
        return this.namespace + "." + method;
    }

    /**
     * 创建实例
     * @return 实例对象
     * @throws Exception 异常信息
     */
    public Object newInstance() throws Exception {
        return this.getEntity().getDeclaredConstructor().newInstance();
    }

}
