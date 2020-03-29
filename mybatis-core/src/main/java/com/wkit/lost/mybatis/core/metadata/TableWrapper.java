package com.wkit.lost.mybatis.core.metadata;

import com.wkit.lost.mybatis.data.auditing.AuditMatching;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 数据库表映射
 * @author wvkity
 */
@Getter
@Setter( AccessLevel.PACKAGE )
@ToString
@EqualsAndHashCode
@Accessors( chain = true )
public class TableWrapper {

    /**
     * 属性-字段包装对象缓存(只读)
     */
    @Getter( AccessLevel.NONE )
    private Map<String, ColumnWrapper> PROPERTY_COLUMN_CACHE;

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
    private String namespace;

    /**
     * 别名
     */
    private String alias;

    /**
     * 数据库目录
     */
    private String catalog;

    /**
     * 数据库模式
     */
    private String schema;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 排序
     */
    private String order;

    /**
     * 是否启用逻辑删除
     */
    private boolean enableLogicDeleted;

    /**
     * 主键字段
     */
    private ColumnWrapper primaryKey;

    /**
     * 主键属性
     */
    private String primaryKeyProperty;

    /**
     * 乐观锁字段
     */
    private ColumnWrapper optimisticLockingColumn;

    /**
     * 逻辑删除字段
     */
    private ColumnWrapper logicDeletedColumn;

    /**
     * 所有字段
     */
    @Getter( AccessLevel.NONE )
    private Set<ColumnWrapper> columns = new LinkedHashSet<>();

    /**
     * 构造方法
     * @param entity 实体类
     * @param name   表名
     */
    public TableWrapper( Class<?> entity, String name ) {
        this.entity = entity;
        this.name = name;
    }

    /**
     * 添加字段信息
     * @param column 字段映射对象
     * @return {@code this}
     */
    TableWrapper addColumn( ColumnWrapper column ) {
        if ( column != null ) {
            this.columns.add( column );
        }
        return this;
    }

    /**
     * 获取所有字段集合
     * @return 数据库表字段
     */
    public Set<ColumnWrapper> columns() {
        return new LinkedHashSet<>( this.columns );
    }

    /**
     * 获取所有可保存字段集合
     * @return 可保存字段集合
     */
    public Set<ColumnWrapper> insertableColumns() {
        return filtrate( ColumnWrapper::isInsertable );
    }

    /**
     * 获取所有可更新字段集合
     * @return 可更新字段集合
     */
    public Set<ColumnWrapper> updatableColumns() {
        return filtrate( ColumnWrapper::isUpdatable );
    }

    /**
     * 获取除乐观锁外其他可更新字段集合
     * @return 可更新字段集合
     */
    public Set<ColumnWrapper> updatableColumnsExcludeLocking() {
        return this.optimisticLockingColumn == null ? updatableColumns() :
                filtrate( it -> it.isUpdatable() && !it.isVersion() );
    }

    /**
     * 获取所有保存操作自动审计字段集合
     * @return 保存操作自动审计字段集合
     */
    public Set<ColumnWrapper> insertedAuditableColumns() {
        return auditableColumns( AuditMatching.INSERTED );
    }

    /**
     * 获取所有更新操作自动审计字段集合
     * @return 更新操作自动审计字段集合
     */
    public Set<ColumnWrapper> modifiedAuditableColumns() {
        return auditableColumns( AuditMatching.MODIFIED );
    }

    /**
     * 获取所有删除操作自动审计字段集合
     * @return 删除操作自动审计字段集合
     */
    public Set<ColumnWrapper> deletedAuditableColumns() {
        return auditableColumns( AuditMatching.DELETED );
    }

    /**
     * 获取所有非删除审计字段集合
     * @return 非删除审计字段集合
     */
    public Set<ColumnWrapper> excludeDeletedAuditableColumns() {
        return filtrate( it -> !it.deletedAuditable() );
    }

    /**
     * 获取自动审计字段集合
     * @param matching 审计类型
     * @return 字段集合
     */
    private Set<ColumnWrapper> auditableColumns( AuditMatching matching ) {
        return filtrate( it -> it.isAuditable( matching ) );
    }

    /**
     * 筛选字段
     * @param filter 过滤Lambda对象
     * @return 字段集合
     */
    private Set<ColumnWrapper> filtrate( Predicate<ColumnWrapper> filter ) {
        return this.columns.stream().filter( filter ).collect( Collectors.toCollection( LinkedHashSet::new ) );
    }

    /**
     * 初始化定义信息
     */
    void initDefinition() {
        if ( this.columns != null && !this.columns.isEmpty() && this.PROPERTY_COLUMN_CACHE == null ) {
            this.PROPERTY_COLUMN_CACHE = Collections.unmodifiableMap( this.columns.stream().collect(
                    Collectors.toMap( ColumnWrapper::getProperty, Function.identity() ) ) );
        } else {
            this.PROPERTY_COLUMN_CACHE = Collections.unmodifiableMap( new HashMap<>( 0 ) );
        }
    }

    /**
     * 获取所有字段映射信息
     * @return 字段映射集合
     */
    public Map<String, ColumnWrapper> columnMappings() {
        return this.PROPERTY_COLUMN_CACHE;
    }

    /**
     * 根据属性查找对应的字段信息
     * @param property 属性
     * @return 字段信息
     */
    public Optional<ColumnWrapper> search( String property ) {
        return Optional.ofNullable( property ).flatMap( value -> this.columns.parallelStream()
                .filter( column -> column.getProperty().equals( property ) ).findAny() );
    }

    /**
     * 获取存在的statement
     * @param method 方法名
     * @return statement
     */
    public String getSqlStatement( final String method ) {
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
