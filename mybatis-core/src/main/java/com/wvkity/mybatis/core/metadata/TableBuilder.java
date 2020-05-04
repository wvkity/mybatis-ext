package com.wvkity.mybatis.core.metadata;

import com.wvkity.mybatis.config.MyBatisCustomConfiguration;
import com.wvkity.mybatis.core.parser.DefaultFieldParser;
import com.wvkity.mybatis.core.parser.FieldParser;
import com.wvkity.mybatis.exception.MapperParserException;
import com.wvkity.mybatis.utils.Ascii;
import com.wvkity.mybatis.utils.StringUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 数据库表-实体映射类构建器
 * @author wvkity
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class TableBuilder extends BuilderSupport implements Builder<TableWrapper> {

    /**
     * 默认实体属性解析器
     */
    private static final FieldParser DEFAULT_FIELD_PARSER = new DefaultFieldParser();

    /**
     * 实体类
     */
    private Class<?> entity;

    /**
     * Mapper命名空间
     */
    private String namespace;

    /**
     * 表名
     */
    private String name;

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
     * 主键属性
     */
    private String primaryKeyProperty;

    /**
     * 主键字段
     */
    private ColumnBuilder primaryKey;

    /**
     * 乐观锁字段
     */
    private ColumnBuilder optimisticLockingColumn;

    /**
     * 逻辑删除字段
     */
    private ColumnBuilder logicDeletedColumn;

    /**
     * 所有字段
     */
    @Getter(AccessLevel.NONE)
    private Set<ColumnBuilder> columns = new LinkedHashSet<>();

    @Override
    public TableWrapper build() {
        int mode = namingMode();
        // 数据库表名前缀
        String realPrefix = Optional.ofNullable(Ascii.isNullOrEmpty(this.prefix) ?
                configuration.getTablePrefix() : this.prefix).map(it -> mode == 0 ?
                it.toLowerCase(Locale.ENGLISH) : mode == 1 ? it.toUpperCase(Locale.ENGLISH) : it)
                .orElse("");
        // 数据表名
        String realName = realPrefix + Optional.ofNullable(Ascii.isNullOrEmpty(this.name) ?
                this.entity.getSimpleName() : this.name).map(this::tableNameTransform).orElse("");
        final String realCatalog = StringUtil.nvl(this.catalog, this.configuration.getCatalog());
        final String realSchema = StringUtil.nvl(this.schema, this.configuration.getSchema());
        // 创建字段对象
        ColumnWrapper pk = null;
        String pkProperty = null;
        ColumnWrapper deleteColumn = null;
        ColumnWrapper versionColumn = null;
        Set<ColumnWrapper> columnWrappers = new LinkedHashSet<>();
        if (!columns.isEmpty()) {
            for (ColumnBuilder builder : columns) {
                ColumnWrapper column = builder.build();
                if (column.isPrimaryKey() && pk == null) {
                    // 主键
                    pk = column;
                    pkProperty = column.getProperty();
                } else if (column.isLogicDelete() && deleteColumn == null) {
                    // 逻辑删除标识列
                    deleteColumn = column;
                } else if (column.isVersion() && versionColumn == null) {
                    // 乐观锁列
                    versionColumn = column;
                }
                columnWrappers.add(column);
            }
        }
        TableWrapper wrapper = new TableWrapper(this.entity, realName, this.namespace, realCatalog, realSchema,
                this.prefix, this.order, this.enableLogicDeleted, pk, pkProperty, versionColumn,
                deleteColumn, columnWrappers);
        // 构建缓存
        PropertyMappingCache.build(wrapper);
        return wrapper;
    }

    /**
     * 创建构建器
     * @return 构建器对象
     */
    public static TableBuilder create() {
        return new TableBuilder();
    }

    /**
     * 获取属性解析器
     * @return 属性解析器
     */
    public FieldParser fieldParser() {
        return Optional.ofNullable(this.configuration)
                .map(MyBatisCustomConfiguration::getFieldParser)
                .filter(Objects::nonNull)
                .orElse(DEFAULT_FIELD_PARSER);
    }

    /**
     * 是否启用解析实体类get/set方法上的注解
     * @return true: 是, false: 否
     */
    public boolean isEnableMethodAnnotation() {
        return Optional.ofNullable(this.configuration).map(MyBatisCustomConfiguration::isEnableMethodAnnotation)
                .orElse(false);
    }

    /**
     * 设置实体类
     * @param entity 实体类
     * @return {@code this}
     */
    public TableBuilder entity(Class<?> entity) {
        if (entity == null) {
            throw new MapperParserException("The entity class parameter cannot be empty.");
        }
        this.entity = entity;
        return this;
    }

    @Override
    public TableBuilder configuration(MyBatisCustomConfiguration configuration) {
        super.configuration(configuration);
        return this;
    }

    /**
     * 检查是否存在主键
     * @return true: 是, false: 否
     */
    public boolean hasPrimaryKey() {
        return this.primaryKey != null;
    }

    /**
     * 添加字段构建器
     * @param builder 字段构建器
     * @return {@code this}
     */
    public TableBuilder addColumn(ColumnBuilder builder) {
        this.columns.add(builder);
        return this;
    }

}
