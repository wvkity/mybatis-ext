package com.wkit.lost.mybatis.core.parser;

import com.wkit.lost.mybatis.annotation.Column;
import com.wkit.lost.mybatis.annotation.ColumnExt;
import com.wkit.lost.mybatis.annotation.Entity;
import com.wkit.lost.mybatis.annotation.GeneratedValue;
import com.wkit.lost.mybatis.annotation.Identity;
import com.wkit.lost.mybatis.annotation.LogicDeletion;
import com.wkit.lost.mybatis.annotation.SequenceGenerator;
import com.wkit.lost.mybatis.annotation.SnowflakeSequence;
import com.wkit.lost.mybatis.annotation.Table;
import com.wkit.lost.mybatis.annotation.Transient;
import com.wkit.lost.mybatis.annotation.Version;
import com.wkit.lost.mybatis.annotation.auditing.CreatedDate;
import com.wkit.lost.mybatis.annotation.auditing.CreatedUser;
import com.wkit.lost.mybatis.annotation.auditing.CreatedUserName;
import com.wkit.lost.mybatis.annotation.auditing.DeletedDate;
import com.wkit.lost.mybatis.annotation.auditing.DeletedUser;
import com.wkit.lost.mybatis.annotation.auditing.DeletedUserName;
import com.wkit.lost.mybatis.annotation.auditing.LastModifiedDate;
import com.wkit.lost.mybatis.annotation.auditing.LastModifiedUser;
import com.wkit.lost.mybatis.annotation.auditing.LastModifiedUserName;
import com.wkit.lost.mybatis.annotation.extension.Dialect;
import com.wkit.lost.mybatis.annotation.extension.Executing;
import com.wkit.lost.mybatis.annotation.extension.GenerationType;
import com.wkit.lost.mybatis.annotation.extension.UseJavaType;
import com.wkit.lost.mybatis.annotation.extension.Validated;
import com.wkit.lost.mybatis.annotation.naming.Naming;
import com.wkit.lost.mybatis.annotation.naming.NamingStrategy;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.handler.FieldHandler;
import com.wkit.lost.mybatis.core.metadata.AnnotationMetadata;
import com.wkit.lost.mybatis.core.metadata.ColumnBuilder;
import com.wkit.lost.mybatis.core.metadata.FieldWrapper;
import com.wkit.lost.mybatis.core.metadata.PrimaryKey;
import com.wkit.lost.mybatis.core.metadata.TableBuilder;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.exception.MapperParserException;
import com.wkit.lost.mybatis.javax.JavaxPersistence;
import com.wkit.lost.mybatis.type.handlers.SimpleTypeRegistry;
import com.wkit.lost.mybatis.type.registry.JdbcTypeMappingRegister;
import com.wkit.lost.mybatis.utils.AnnotationUtil;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.PrimitiveRegistry;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * 默认实体解析器
 * @author wvkity
 */
@Log4j2
public class DefaultEntityParser implements EntityParser {

    private static final String VARIABLE_NAME = "name";
    private static final String VARIABLE_CATALOG = "catalog";
    private static final String VARIABLE_SCHEMA = "schema";
    private static final String VARIABLE_INSERTABLE = "insertable";
    private static final String VARIABLE_UPDATABLE = "updatable";
    private static final String VARIABLE_SEQUENCE_NAME = "sequenceName";
    private static final String VARIABLE_STRATEGY = "strategy";
    private static final String VARIABLE_GENERATOR = "generator";
    private static final String VARIABLE_IDENTITY = Constants.GENERATOR_IDENTITY;
    private static final String VARIABLE_UUID = Constants.GENERATOR_UUID;
    private static final String VARIABLE_JDBC = Constants.GENERATOR_JDBC;
    private static final String VARIABLE_SNOWFLAKE_SEQUENCE = Constants.GENERATOR_SNOWFLAKE_SEQUENCE;
    private static final String VARIABLE_SNOWFLAKE_SEQUENCE_STRING = Constants.GENERATOR_SNOWFLAKE_SEQUENCE_STRING;

    /**
     * 命名策略方式
     */
    private static final NamingStrategy DEFAULT_NAMING_STRATEGY = NamingStrategy.CAMEL_HUMP_UPPERCASE;

    @Override
    public TableWrapper parse(TableBuilder builder) {
        // region 解析实体类-数据库表映射信息
        MyBatisCustomConfiguration configuration = builder.configuration();
        NamingStrategy naming = naming(configuration, builder.entity());
        Class<?> entity = builder.entity();
        builder.namingStrategy(naming);
        if (entity.isAnnotationPresent(Table.class)) {
            Table annotation = entity.getDeclaredAnnotation(Table.class);
            builder.name(annotation.name())
                    .catalog(annotation.catalog())
                    .schema(annotation.schema())
                    .prefix(annotation.prefix());
        } else if (AnnotationUtil.isAnnotationPresent(entity, JavaxPersistence.TABLE)) {
            AnnotationMetadata metadata = AnnotationMetadata.forObject(entity, JavaxPersistence.TABLE);
            builder.name(metadata.stringValue(VARIABLE_NAME, null))
                    .catalog(metadata.stringValue(VARIABLE_CATALOG, null))
                    .schema(metadata.stringValue(VARIABLE_SCHEMA, null));
        }
        if (Ascii.isNullOrEmpty(builder.name())) {
            if (entity.isAnnotationPresent(Entity.class)) {
                builder.name(entity.getDeclaredAnnotation(Entity.class).name());
            } else if (AnnotationUtil.isAnnotationPresent(entity, JavaxPersistence.ENTITY)) {
                builder.name(AnnotationMetadata.forObject(entity, JavaxPersistence.ENTITY)
                        .stringValue(VARIABLE_NAME, null));
            }
        }
        // endregion
        // region 解析实体类属性-数据库表字段映射信息
        List<FieldWrapper> fields;
        FieldParser fieldParser = builder.fieldParser();
        if (builder.isEnableMethodAnnotation()) {
            fields = FieldHandler.merge(fieldParser, entity);
        } else {
            fields = FieldHandler.parse(fieldParser, entity);
        }
        boolean autoAddIsPrefix = configuration.isBooleanPropertyAutoAddIsPrefix();
        fields.stream().filter(it -> this.filter(it, configuration)).forEach(it -> {
            ColumnBuilder columnBuilder = ColumnBuilder.create();
            columnBuilder.entity(entity)
                    .field(it)
                    .property(it.getName())
                    .javaType(it.getJavaType())
                    .autoAddIsPrefix(autoAddIsPrefix)
                    .configuration(configuration)
                    .namingStrategy(naming);
            parseField(builder, it, columnBuilder, configuration);
        });
        // endregion
        return builder.build();
    }

    /**
     * 解析属性
     * @param table         数据库表映射对象
     * @param field         属性对象
     * @param builder       字段构建器
     * @param configuration 自定义配置对象
     */
    protected void parseField(final TableBuilder table, final FieldWrapper field, final ColumnBuilder builder,
                              final MyBatisCustomConfiguration configuration) {
        if (field.isAnnotationPresent(Transient.class, JavaxPersistence.TRANSIENT)) {
            return;
        }
        // 处理@Column注解
        parseColumnAnnotation(field, builder, configuration);
        // 检查是否存在主键注解(@Id/@Worker)
        if (field.isAnnotationPresentOfPrimaryKey()) {
            if (table.hasPrimaryKey()) {
                // 暂时不支持复合主键
                log.warn("The primary key attribute already exists for the current entity class. -- `{}({})`",
                        builder.entity().getCanonicalName(), table.primaryKey().property());
            } else {
                builder.primaryKey(true).updatable(false);
                table.primaryKey(builder).primaryKeyProperty(builder.property());
            }
        }
        // 解析逻辑删除注解(@LogicDeletion)
        parseLogicDeletionAnnotation(table, field, builder, configuration);
        // 解析主键生成策略
        parseKeyGenerator(table, builder, field, configuration);
        // 非主键标识则根据全局配置自动识别
        parseAutoDiscernPrimaryKey(table, builder, field, configuration);
        // 解析审计注解
        parseAuditingAnnotation(table, builder, field, configuration);
        // 检查是否为主键
        if (builder.primaryKey()) {
            // 未指定主键生成方式，则根据全局配置识别
            parseCustomKeyGenerator(table, builder, field, configuration);
        }
        // 乐观锁
        if (field.isAnnotationPresent(Version.class, JavaxPersistence.VERSION)) {
            if (table.optimisticLockingColumn() == null) {
                table.optimisticLockingColumn(builder);
                builder.version(true);
            } else {
                log.warn("The current Entity class({}) already has a `{}` for the @version tag. " +
                                "The framework only supports one optimistic lock. The system will ignore other " +
                                "@version tag properties ({}).", table.entity().getCanonicalName(),
                        table.optimisticLockingColumn().property(), builder.property());

            }
        }
        table.addColumn(builder);
    }

    /**
     * 解析属性上的{@link Column @Column}注解、{@link ColumnExt @ColumnExt}注解
     * @param field         属性对象
     * @param builder       数据库表字段构建器
     * @param configuration 自定配置对象
     */
    protected void parseColumnAnnotation(final FieldWrapper field, final ColumnBuilder builder,
                                         final MyBatisCustomConfiguration configuration) {
        if (field.isAnnotationPresent(Column.class)) {
            // 自定义@Column注解
            Column column = field.getAnnotation(Column.class);
            builder.column(column.name()).insertable(column.insertable()).updatable(column.updatable());
        } else if (field.isAnnotationPresent(JavaxPersistence.COLUMN)) {
            // javax @Column注解
            AnnotationMetadata metadata = AnnotationMetadata.forObject(field.getField(), JavaxPersistence.COLUMN);
            builder.column(metadata.stringValue(VARIABLE_NAME))
                    .insertable(metadata.booleanValue(VARIABLE_INSERTABLE))
                    .updatable(metadata.booleanValue(VARIABLE_UPDATABLE));
        }
        // 处理@ColumnExt注解
        if (field.isAnnotationPresent(ColumnExt.class)) {
            ColumnExt columnExt = field.getAnnotation(ColumnExt.class);
            if (Ascii.isNullOrEmpty(builder.column()) && Ascii.hasText(columnExt.column())) {
                builder.column(columnExt.column());
            }
            builder.blob(columnExt.blob());
            Validated validated = columnExt.empty();
            if (validated != Validated.CONFIG) {
                builder.checkNotEmpty(validated == Validated.REQUIRED);
            }
            UseJavaType useJavaType = columnExt.useJavaType();
            if (useJavaType != UseJavaType.CONFIG) {
                builder.useJavaType(useJavaType == UseJavaType.REQUIRED);
            }
            JdbcType jdbcType = columnExt.jdbcType();
            if (jdbcType != JdbcType.UNDEFINED) {
                builder.jdbcType(jdbcType);
            }
            Class<? extends TypeHandler<?>> typeHandler = columnExt.typeHandler();
            if (typeHandler != UnknownTypeHandler.class) {
                builder.typeHandler(typeHandler);
            }
        }
        if (builder.jdbcType() == null && configuration.isJdbcTypeAutoMapping()) {
            builder.jdbcType(JdbcTypeMappingRegister.getJdbcType(field.getJavaType()));
        }
        // 使用基本类型警告
        if (builder.javaType().isPrimitive()) {
            log.warn("Warning: The `{}` attribute in the `{}` entity is defined as a primitive type. " +
                            "The primitive type is not null at any time in dynamic SQL because it has a default value. It is " +
                            "recommended to modify the primitive type to the corresponding wrapper type!", builder.property(),
                    builder.entity().getCanonicalName());
        }
    }

    /**
     * 处理逻辑删除注解
     * @param builder       数据库表字段构建器
     * @param table         数据库表映射对象
     * @param field         属性对象
     * @param configuration 自定义配置对象
     */
    protected void parseLogicDeletionAnnotation(final TableBuilder table, final FieldWrapper field,
                                                final ColumnBuilder builder,
                                                final MyBatisCustomConfiguration configuration) {
        String logicDeletedProperty = configuration.getLogicDeletedProperty();
        if (field.isAnnotationPresent(LogicDeletion.class)
                || (logicDeletedProperty != null && logicDeletedProperty.equals(builder.property()))) {
            // 检查是否存在逻辑删除属性
            if (table.enableLogicDeleted()) {
                throw new MapperParserException("There are already `" + table.logicDeletedColumn()
                        .property() + "` attributes in `" + table.entity().getName()
                        + "` entity class identified as logical deleted. Only one deleted attribute " +
                        "can exist in an entity class. Please check the entity class attributes.");
            }
            table.enableLogicDeleted(true).logicDeletedColumn(builder);
            builder.logicDelete(true);
            LogicDeletion logicDeletion = field.getAnnotation(LogicDeletion.class);
            String deletedValue;
            String notDeletedValue;
            if (logicDeletion != null) {
                // 注解-全局
                deletedValue = Optional.ofNullable(logicDeletion.trueValue()).filter(Ascii::hasText)
                        .orElseGet(configuration::getLogicDeletedTrueValue);
                notDeletedValue = Optional.ofNullable(logicDeletion.falseValue()).filter(Ascii::hasText)
                        .orElseGet(configuration::getLogicDeletedFalseValue);
            } else {
                // 全局
                deletedValue = Optional.ofNullable(configuration.getLogicDeletedTrueValue()).orElse(null);
                notDeletedValue = Optional.ofNullable(configuration.getLogicDeletedFalseValue()).orElse(null);
            }
            // 将值转换成对应类型值
            builder.logicDeletedTrueValue(Optional.ofNullable(deletedValue).map(it ->
                    PrimitiveRegistry.convert(builder.javaType(), it)).orElse(null));
            builder.logicDeletedFalseValue(Optional.ofNullable(notDeletedValue).map(it ->
                    PrimitiveRegistry.convert(builder.javaType(), it)).orElse(null));
        }
    }

    /**
     * 解析主键生成策略
     * @param table   数据表映射构建器
     * @param builder 字段构建器
     * @param field   属性
     * @param __      自定义配置
     */
    protected void parseKeyGenerator(final TableBuilder table, final ColumnBuilder builder,
                                     final FieldWrapper field, final MyBatisCustomConfiguration __) {
        if (field.isAnnotationPresent(Identity.class)) {
            parseIdentityAnnotation(table, builder, field);
        } else if (field.isAnnotationPresent(SequenceGenerator.class, JavaxPersistence.SEQUENCE_GENERATOR)) {
            parseSequenceGeneratorAnnotation(table, builder, field);
        } else if (field.isAnnotationPresent(GeneratedValue.class, JavaxPersistence.GENERATED_VALUE)) {
            parseGeneratedValueAnnotation(table, builder, field);
        } else if (field.isAnnotationPresent(SnowflakeSequence.class)) {
            SnowflakeSequence sequence = field.getAnnotation(SnowflakeSequence.class);
            if (sequence.value()) {
                builder.snowflakeSequenceString(true);
            } else {
                builder.snowflakeSequence(true);
            }
        }
    }

    /**
     * 解析<b>{@link Identity @Identity}</b>注解
     * @param table   数据表映射对象
     * @param builder 字段构建器
     * @param field   属性
     */
    private void parseIdentityAnnotation(final TableBuilder table, final ColumnBuilder builder,
                                         final FieldWrapper field) {
        Identity identity = field.getAnnotation(Identity.class);
        if (identity.useJdbc()) {
            builder.identity(true).generator("JDBC");
            table.primaryKeyProperty(builder.property());
        } else if (identity.dialect() != Dialect.UNDEFINED) {
            builder.identity(true).executing(Executing.AFTER).generator(identity.dialect().getKeyGenerator());
        } else {
            if (Ascii.isNullOrEmpty(identity.sql())) {
                throw new MapperParserException(StringUtil.format("The @identity annotation on the '{}' " +
                                "class's attribute '{}' is invalid",
                        builder.entity().getCanonicalName(), builder.property()));
            }
            builder.identity(true).executing(identity.executing()).generator(identity.sql());
        }
    }

    /**
     * 解析<b>{@link SequenceGenerator @SequenceGenerator}</b>注解
     * @param __      数据库表映射构建器
     * @param builder 数据表字段构建器
     * @param field   属性
     */
    private void parseSequenceGeneratorAnnotation(final TableBuilder __, final ColumnBuilder builder,
                                                  final FieldWrapper field) {
        if (field.isAnnotationPresent(SequenceGenerator.class)) {
            SequenceGenerator generator = field.getAnnotation(SequenceGenerator.class);
            builder.sequenceName(Optional.ofNullable(generator.name())
                    .filter(Ascii::hasText)
                    .orElse(Optional.ofNullable(generator.sequenceName())
                            .filter(Ascii::hasText)
                            .orElse(null)));
        } else if (field.isAnnotationPresent(JavaxPersistence.SEQUENCE_GENERATOR)) {
            AnnotationMetadata metadata = AnnotationMetadata.forObject(field.getField(),
                    JavaxPersistence.SEQUENCE_GENERATOR);
            builder.sequenceName(Optional.ofNullable(metadata.stringValue(VARIABLE_NAME, null))
                    .orElse(Optional.ofNullable(metadata.stringValue(VARIABLE_SEQUENCE_NAME, null))
                            .orElse(null)));
        }
        if (Ascii.isNullOrEmpty(builder.sequenceName())) {
            throw new MapperParserException(StringUtil.format("The @SequenceGenerator on the `{}` " +
                            "attribute of the `{}` class does not specify the sequenceName value.",
                    builder.property(), builder.entity().getCanonicalName()));
        }
    }

    /**
     * 解析<b>{@link GeneratedValue @GeneratedValue}</b>注解
     * @param table   数据库表映射构建器
     * @param builder 数据表字段构建器
     * @param field   属性
     */
    private void parseGeneratedValueAnnotation(final TableBuilder table, final ColumnBuilder builder,
                                               final FieldWrapper field) {
        String generator;
        boolean isIdentity;
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            generator = generatedValue.generator();
            isIdentity = generatedValue.strategy() == GenerationType.IDENTITY;
        } else if (field.isAnnotationPresent(JavaxPersistence.GENERATED_VALUE)) {
            AnnotationMetadata metadata = AnnotationMetadata.forObject(field.getField(),
                    JavaxPersistence.GENERATED_VALUE);
            generator = metadata.stringValue(VARIABLE_GENERATOR, null);
            Enum<?> enumValue = metadata.enumValue(VARIABLE_STRATEGY, null);
            isIdentity = enumValue != null && VARIABLE_IDENTITY.equalsIgnoreCase(enumValue.name());
        } else {
            generator = null;
            isIdentity = false;
        }
        if (VARIABLE_UUID.equalsIgnoreCase(generator)) {
            builder.uuid(true);
        } else if (VARIABLE_JDBC.equalsIgnoreCase(generator)) {
            builder.identity(true).generator(generator.toLowerCase(Locale.ENGLISH));
            if (table.primaryKey() == null) {
                table.primaryKeyProperty(builder.property());
                table.primaryKey(builder);
            }
        } else if (VARIABLE_SNOWFLAKE_SEQUENCE.equalsIgnoreCase(generator)) {
            builder.snowflakeSequence(true);
        } else if (VARIABLE_SNOWFLAKE_SEQUENCE_STRING.equalsIgnoreCase(generator)) {
            builder.snowflakeSequenceString(true);
        } else {
            if (isIdentity) {
                builder.identity(true);
                if (Ascii.hasText(generator)) {
                    Dialect dialect = Dialect.getDBDialect(generator);
                    if (dialect != null) {
                        builder.generator(dialect.getKeyGenerator());
                    } else {
                        builder.generator(generator);
                    }
                }
            } else {
                throw new MapperParserException(StringUtil.format("The @generatedValue annotation on the '{}' class's attribute '{}' supports the following form: \n1.{}\n2.{}\n3.{}",
                        builder.entity().getCanonicalName(), builder.property(),
                        "@GeneratedValue(generator = \"UUID\")",
                        "@GeneratedValue(generator = \"JDBC\")",
                        "@GeneratedValue(generator = \"SNOWFLAKE_SEQUENCE\")",
                        "@GeneratedValue(generator = \"SNOWFLAKE_SEQUENCE_STRING\")",
                        "@GeneratedValue(strategy = GenerationType.IDENTITY, [ generator = \"[ MySql, SQLServer... ]\" ])"));
            }
        }
    }

    /**
     * 自动识别主键
     * @param table         数据库表映射构建器
     * @param builder       数据表字段构建器
     * @param __            属性
     * @param configuration 自定义配置
     */
    private void parseAutoDiscernPrimaryKey(final TableBuilder table, final ColumnBuilder builder,
                                            final FieldWrapper __, final MyBatisCustomConfiguration configuration) {
        if (configuration.isAutoDiscernPrimaryKey() && !table.hasPrimaryKey() && !builder.primaryKey()) {
            String[] primaryKeys = configuration.getPrimaryKeys();
            boolean include = !ArrayUtil.isEmpty(primaryKeys) && StringUtil.include(primaryKeys, builder.property());
            if (include) {
                builder.primaryKey(true).updatable(false);
                table.primaryKey(builder).primaryKeyProperty(builder.property());
            }
        }
    }

    /**
     * 解析审计注解
     * @param __            数据库表映射构建器
     * @param builder       数据表字段构建器
     * @param field         属性
     * @param configuration 自定义配置
     */
    protected void parseAuditingAnnotation(final TableBuilder __, final ColumnBuilder builder,
                                           final FieldWrapper field, final MyBatisCustomConfiguration configuration) {
        boolean modifiable = !builder.logicDelete() && builder.updatable();
        builder.createdDate(field.isAnnotationPresent(CreatedDate.class))
                .createdUser(field.isAnnotationPresent(CreatedUser.class))
                .createdUserName(field.isAnnotationPresent(CreatedUserName.class));
        builder.deletedDate(modifiable && field.isAnnotationPresent(DeletedDate.class))
                .deletedUser(modifiable && field.isAnnotationPresent(DeletedUser.class))
                .deletedUserName(modifiable && field.isAnnotationPresent(DeletedUserName.class));
        builder.lastModifiedDate(modifiable && field.isAnnotationPresent(LastModifiedDate.class))
                .lastModifiedUser(modifiable && field.isAnnotationPresent(LastModifiedUser.class))
                .lastModifiedUserName(modifiable && field.isAnnotationPresent(LastModifiedUserName.class));
    }

    /**
     * 解析全局主键生成方式
     * @param __            数据库表映射构建器
     * @param builder       数据表字段构建器
     * @param field         属性
     * @param configuration 自定义配置
     */
    protected void parseCustomKeyGenerator(final TableBuilder __, final ColumnBuilder builder,
                                           final FieldWrapper field, final MyBatisCustomConfiguration configuration) {
        if (!builder.hasKeyGenerator()) {
            PrimaryKey keyType = configuration.getPrimaryKey();
            builder.uuid(keyType == PrimaryKey.UUID)
                    .identity(keyType == PrimaryKey.IDENTITY || keyType == PrimaryKey.JDBC)
                    .snowflakeSequence(keyType == PrimaryKey.SNOWFLAKE_SEQUENCE)
                    .snowflakeSequenceString(keyType == PrimaryKey.SNOWFLAKE_SEQUENCE_STRING);
        }
    }

    /**
     * 命名策略方式
     * @param configuration 自定义配置
     * @param entity        实体类
     * @return 命名策略
     */
    protected NamingStrategy naming(final MyBatisCustomConfiguration configuration, final Class<?> entity) {
        if (AnnotationUtil.isAnnotationPresent(entity, Naming.class)) {
            return entity.getAnnotation(Naming.class).value();
        }
        return Optional.ofNullable(configuration.getStrategy()).orElse(DEFAULT_NAMING_STRATEGY);
    }

    /**
     * 过滤属性
     * @param field         属性
     * @param configuration 自定义配置
     * @return true/false
     */
    protected boolean filter(final FieldWrapper field, final MyBatisCustomConfiguration configuration) {
        return !(!configuration.isUseSimpleType()
                && field.isAnnotationPresent(Column.class, JavaxPersistence.COLUMN)
                && field.isAnnotationPresent(ColumnExt.class)
                && (SimpleTypeRegistry.isSimpleType(field.getJavaType())
                || (configuration.isEnumAsSimpleType() && Enum.class.isAssignableFrom(field.getJavaType()))));
    }
}
