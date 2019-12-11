package com.wkit.lost.mybatis.core.meta;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 数据库表映射信息
 * @author wvkity
 */
@Accessors( chain = true )
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Table {

    public static final Pattern PATTERN = Pattern.compile( "^[`\\[\"]?(.*?)[`\\]\"]?$" );

    /**
     * 定义信息
     */
    @Getter
    private Map<String, Column> definitions = new ConcurrentHashMap<>();

    /**
     * 实体类
     */
    @Getter
    private Class<?> entity;

    /**
     * 表名
     */
    @Getter
    @Setter( AccessLevel.PACKAGE )
    private String name;

    /**
     * 别名
     */
    @Getter
    private String alias;

    /**
     * 数据库CATALOG
     */
    @Getter
    @Setter( AccessLevel.PACKAGE )
    private String catalog;

    /**
     * 数据库SCHEMA
     */
    @Getter
    @Setter( AccessLevel.PACKAGE )
    private String schema;

    /**
     * 前缀
     */
    @Getter
    @Setter( AccessLevel.PACKAGE )
    private String prefix;

    /**
     * 排序
     */
    @Getter
    @Setter( AccessLevel.PACKAGE )
    private String order;

    /**
     * 主键字段
     */
    @Getter
    @Setter( AccessLevel.PACKAGE )
    private Column primaryKey;

    /**
     * 标识是否启用逻辑删除
     */
    @Getter
    @Setter( AccessLevel.PACKAGE )
    private boolean enableLogicDelete;

    /**
     * 逻辑删除标识字段
     */
    @Getter
    @Setter( AccessLevel.PACKAGE )
    private Column logicalDeletionColumn;

    /**
     * 所有字段
     */
    private Set<Column> columns = new LinkedHashSet<>();

    /**
     * 组合主键字段
     */
    @Getter
    private Set<Column> compositeColumns = new LinkedHashSet<>();

    /**
     * 主键属性
     */
    @Getter
    private Set<String> primaryKeyProperties = new LinkedHashSet<>();

    /**
     * 主键字段
     */
    @Getter
    private Set<String> primaryKeyColumns = new LinkedHashSet<>();

    /**
     * 构造方法
     * @param entity 实体类
     */
    public Table( Class<?> entity ) {
        this.setEntity( entity );
    }

    /**
     * 构造方法
     * @param name    表名
     * @param catalog 数据库CATALOG
     * @param schema  数据库SCHEMA
     */
    public Table( String name, String catalog, String schema ) {
        this.name = name;
        this.catalog = catalog;
        this.schema = schema;
    }

    /**
     * 检查是否存在主键
     * @return true: 存在 | false: 不存在
     */
    public boolean hasPrimaryKey() {
        return this.primaryKey != null;
    }

    /**
     * 添加字段映射信息
     * @param column {@link Column}(字段信息对象)
     */
    void addColumn( Column column ) {
        if ( column != null ) {
            this.columns.add( column );
        }
    }

    /**
     * 添加主键属性
     * @param property 属性
     */
    void addPrimaryKeyProperty( final String property ) {
        if ( StringUtil.hasText( property ) ) {
            this.primaryKeyProperties.add( property );
        }
    }

    /**
     * 添加主键字段名
     * @param column 字段名
     */
    void addPrimaryKeyColumn( final String column ) {
        if ( StringUtil.hasText( column ) ) {
            this.primaryKeyColumns.add( column );
        }
    }

    /**
     * 添加主键字段
     * @param column 字段映射信息
     */
    void addPrimaryKey( final Column column ) {
        if ( column != null ) {
            this.compositeColumns.add( column );
        }
    }

    /**
     * 根据属性查找对应的字段信息
     * @param property 属性
     * @return 字段信息
     */
    public Optional<Column> search( String property ) {
        return Optional.ofNullable( property ).flatMap( value -> this.columns.parallelStream()
                .filter( column -> column.getProperty().equals( property ) ).findAny() );
    }

    /**
     * 获取所有字段信息
     * @return 字段集合
     */
    public Set<Column> getColumns() {
        return new LinkedHashSet<>( this.columns );
    }

    /**
     * 获取可更新字段信息
     * @return 字段集合
     */
    public Set<Column> getUpdatableColumns() {
        return this.columns.stream()
                .filter( Column::isUpdatable )
                .collect( Collectors.toCollection( LinkedHashSet::new ) );
    }

    /**
     * 获取可更新字段信息(排除乐观锁字段)
     * @return 字段集合
     */
    public Set<Column> getUpdatableColumnsExcludeLocker() {
        return this.columns.stream()
                .filter( Column::isUpdatable )
                .filter( column -> !column.isVersion() )
                .collect( Collectors.toCollection( LinkedHashSet::new ) );
    }

    /**
     * 获取所有可保存字段信息
     * @return 字段集合
     */
    public Set<Column> getInsertableColumns() {
        return this.columns.stream()
                .filter( Column::isInsertable )
                .collect( Collectors.toCollection( LinkedHashSet::new ) );
    }

    /**
     * 获取所有保存操作时可自动插入值字段
     * @return 字段集合
     */
    public Set<Column> getInsertFillings() {
        return this.columns.stream()
                .filter( Column::enableInsertFilling )
                .collect( Collectors.toCollection( LinkedHashSet::new ) );
    }

    /**
     * 获取所有更新操作时可自动插入值字段
     * @return 字段集合
     */
    public Set<Column> getUpdateFillings() {
        return this.columns.stream()
                .filter( Column::enableUpdateFilling )
                .collect( Collectors.toCollection( LinkedHashSet::new ) );
    }

    /**
     * 获取所有逻辑删除操作时可自动插入值字段
     * @return 字段集合
     */
    public Set<Column> getDeleteFillings() {
        return this.columns.stream()
                .filter( Column::enableDeleteFilling )
                .collect( Collectors.toCollection( LinkedHashSet::new ) );
    }

    /**
     * 获取非逻辑删除填充字段
     * @return 字段列表
     */
    public Set<Column> getIgnoreDeleteFillingsColumns() {
        return this.columns.stream()
                .filter( column -> !column.enableDeleteFilling() )
                .collect( Collectors.toCollection( LinkedHashSet::new ) );
    }

    /**
     * 获取乐观锁字段
     * @return 字段信息
     */
    public Column getOptimisticLockerColumn() {
        return this.columns.stream().filter( Column::isVersion ).findFirst().orElse( null );
    }


    /**
     * 初始化定义信息
     */
    void initDefinition() {
        if ( CollectionUtil.hasElement( this.columns ) && definitions.isEmpty() ) {
            this.definitions.putAll( this.columns.stream()
                    .collect( Collectors.toMap( Column::getProperty, Function.identity() ) ) );
        }
    }

    Table setEntity( Class<?> entity ) {
        this.entity = entity;
        if ( this.alias == null ) {
            this.alias = StringUtil.getSimpleNameOfSplitFirstUpper( this.entity );
        }
        return this;
    }

}
























