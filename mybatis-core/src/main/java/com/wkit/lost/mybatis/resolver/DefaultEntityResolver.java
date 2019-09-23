package com.wkit.lost.mybatis.resolver;

import com.wkit.lost.mybatis.annotation.Entity;
import com.wkit.lost.mybatis.annotation.Worker;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.AnnotationUtil;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.annotation.ColumnExt;
import com.wkit.lost.mybatis.annotation.GeneratedValue;
import com.wkit.lost.mybatis.annotation.Identity;
import com.wkit.lost.mybatis.annotation.OrderBy;
import com.wkit.lost.mybatis.annotation.SequenceGenerator;
import com.wkit.lost.mybatis.annotation.Transient;
import com.wkit.lost.mybatis.annotation.extension.Dialect;
import com.wkit.lost.mybatis.annotation.extension.Executing;
import com.wkit.lost.mybatis.annotation.extension.GenerationType;
import com.wkit.lost.mybatis.annotation.extension.UseJavaType;
import com.wkit.lost.mybatis.annotation.extension.Validate;
import com.wkit.lost.mybatis.annotation.naming.Naming;
import com.wkit.lost.mybatis.annotation.naming.NamingStrategy;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.PropertyMappingForLambda;
import com.wkit.lost.mybatis.core.schema.Attribute;
import com.wkit.lost.mybatis.core.schema.Column;
import com.wkit.lost.mybatis.core.schema.Table;
import com.wkit.lost.mybatis.exception.MapperResolverException;
import com.wkit.lost.mybatis.handler.ColumnHandler;
import com.wkit.lost.mybatis.javax.JavaxPersistence;
import com.wkit.lost.mybatis.keyword.SqlKeyWords;
import com.wkit.lost.mybatis.naming.DefaultPhysicalNamingStrategy;
import com.wkit.lost.mybatis.naming.PhysicalNamingStrategy;
import com.wkit.lost.mybatis.type.handlers.SimpleTypeRegistry;
import com.wkit.lost.mybatis.type.registry.JdbcTypeMappingRegister;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * 默认实体-表映射解析器
 * @author DT
 */
@Log4j2
public class DefaultEntityResolver implements EntityResolver {

    /**
     * 自定义配置
     */
    private MyBatisCustomConfiguration configuration;

    /**
     * 命名策略
     */
    private NamingStrategy strategy;

    /**
     * 命名处理器
     */
    private PhysicalNamingStrategy physicalNamingStrategy;

    /**
     * 属性解析器
     */
    private FieldResolver fieldResolver;

    /**
     * 构造方法
     * @param configuration 配置
     */
    public DefaultEntityResolver( MyBatisCustomConfiguration configuration ) {
        this.configuration = configuration;
        init();
    }

    private void init() {
        // 命名策略处理器
        if ( configuration == null ) {
            this.physicalNamingStrategy = new DefaultPhysicalNamingStrategy();
            this.fieldResolver = new DefaultFieldResolver();
        } else {
            this.physicalNamingStrategy = Optional.ofNullable( configuration.getPhysicalNamingStrategy() ).orElse( new DefaultPhysicalNamingStrategy() );
            // 类属性解析器
            this.fieldResolver = Optional.ofNullable( configuration.getFieldResolver() ).orElse( new DefaultFieldResolver() );
            if ( configuration.getFieldResolver() == null ) {
                configuration.setFieldResolver( fieldResolver );
            }
        }
    }

    @Override
    public Table resolve( Class<?> entity ) {
        if ( entity == null ) {
            throw new MapperResolverException( "The entity class parameter cannot be empty." );
        }
        // 命名策略
        NamingStrategy strategy = configuration.getStrategy();
        // 检查实体类是否存在@Naming注解
        if ( AnnotationUtil.isAnnotationPresent( entity, Naming.class ) ) {
            // 优先级高于全局配置的命名策略
            Naming naming = entity.getAnnotation( Naming.class );
            strategy = naming.value();
        }
        if ( strategy == null ) {
            strategy = NamingStrategy.CAMEL_HUMP_UPPERCASE;
        }
        this.strategy = strategy;
        // 解析@Table注解(处理表映射信息)
        Table table = processTableMappingFromEntity( entity );
        // 处理字段映射信息
        List<Attribute> attributes;
        if ( configuration.isEnableMethodAnnotation() ) {
            attributes = ColumnHandler.merge( entity, fieldResolver );
        } else {
            attributes = ColumnHandler.getAllAttributes( entity, fieldResolver );
        }
        boolean enableAutoJdbcMapping = this.configuration.isAutoJdbcTypeMapping();
        attributes.stream().filter( this::attributeFilter )
                .forEach( attribute -> processAttribute( table, attribute, enableAutoJdbcMapping ) );
        // 初始化定义信息
        table.initDefinition();
        // 缓存映射信息
        PropertyMappingForLambda.createCache( table );
        return table;
    }

    /**
     * 处理表映射信息
     * @param entity 实体类
     * @return 表映射信息
     */
    private Table processTableMappingFromEntity( final Class<?> entity ) {
        String tableName = null;
        String catalog = null;
        String schema = null;
        String prefix = null;
        // 检查是否存在注解
        if ( AnnotationUtil.hasAnnotation( entity ) ) {
            // 自定义@Table注解
            if ( entity.isAnnotationPresent( com.wkit.lost.mybatis.annotation.Table.class ) ) {
                com.wkit.lost.mybatis.annotation.Table tableAnnotation = entity.getDeclaredAnnotation( com.wkit.lost.mybatis.annotation.Table.class );
                tableName = tableAnnotation.name();
                catalog = tableAnnotation.catalog();
                schema = tableAnnotation.schema();
                prefix = tableAnnotation.prefix();
            } else {
                // javax定义的@Table注解
                if ( AnnotationUtil.isAnnotationPresent( entity, JavaxPersistence.TABLE ) ) {
                    javax.persistence.Table tableAnnotation = entity.getDeclaredAnnotation( javax.persistence.Table.class );
                    tableName = tableAnnotation.name();
                    catalog = tableAnnotation.catalog();
                    schema = tableAnnotation.schema();
                }
            }
            //  解析@Entity注解
            if ( Ascii.isNullOrEmpty( tableName ) ) {
                tableName = processEntityAnnotation( entity );
            }
        }
        if ( StringUtil.isBlank( catalog ) ) {
            catalog = this.configuration.getCatalog();
        }
        if ( StringUtil.isBlank( schema ) ) {
            schema = this.configuration.getSchema();
        }
        if ( StringUtil.isBlank( tableName ) ) {
            tableName = entity.getSimpleName();
        }
        tableName = transformStrategy( true, tableName, this.configuration );
        // 表名前缀
        if ( StringUtil.isBlank( prefix ) ) {
            prefix = this.configuration.getTablePrefix();
        }
        String refer = this.strategy.name().toUpperCase( Locale.ROOT );
        int mode = refer.contains( "LOWERCASE" ) ? 0 : refer.contains( "UPPERCASE" ) ? 1 : 2;
        // 表名前缀
        prefix = Optional.ofNullable( prefix ).map( value -> mode == 0 ? value.toLowerCase( Locale.ROOT ) : mode == 1 ? value.toUpperCase( Locale.ROOT ) : value ).orElse( "" );
        Table table = new Table( ( prefix + tableName ), catalog, schema );
        table.setEntity( entity ).setPrefix( prefix );
        return table;
    }

    /**
     * 处理@Entity注解
     * @param entity 实体类
     * @return 表名
     */
    protected String processEntityAnnotation( final Class<?> entity ) {
        if ( entity.isAnnotationPresent( Entity.class ) ) {
            return entity.getDeclaredAnnotation( Entity.class ).name();
        } else if ( entity.isAnnotationPresent( javax.persistence.Entity.class ) ) {
            return entity.getDeclaredAnnotation( javax.persistence.Entity.class ).name();
        }
        return null;
    }

    /**
     * 处理属性
     * @param table                 表映射对象
     * @param attribute             属性对象
     * @param enableAutoJdbcMapping 是否开启自动映射JDBC类型
     */
    private void processAttribute( final Table table, final Attribute attribute, final boolean enableAutoJdbcMapping ) {
        if ( attribute.isAnnotationPresent( Transient.class, JavaxPersistence.TRANSIENT ) ) {
            return;
        }
        // 处理@Column注解
        Column column = processColumnAnnotation( table.getEntity(), attribute, enableAutoJdbcMapping );
        //var column = new Column( table.getEntity() );
        column.setAttribute( attribute ).setUseJavaType( this.configuration.isUseJavaType() );
        // 处理@ID注解
        if ( attribute.isAnnotationPresentOfId() ) {
            // 检查是否存在主键
            if ( table.hasPrimaryKey() ) {
                // 组合主键需要清空主键
                table.setPrimaryKey( null );
            } else {
                // 设置主键
                table.setPrimaryKey( column );
            }
        }
        // 获取全局配置
        //column.setCheckNotEmpty( this.configuration.isCheckNotEmpty() );
        //column.setUseJavaType( this.configuration.isUseJavaType() );

        // 处理排序
        processOrderByAnnotation( table, column, attribute );
        // 处理主键策略
        processKeyGenerator( table, column, attribute );
        // 非主动标识主键则根据全局配置自动识别主键
        if ( !column.isPrimaryKey() ) {
            processAutoDiscernPrimaryKey( table, column, attribute );
        }
        table.addColumn( column );
        // 检查是否为主键
        if ( column.isPrimaryKey() ) {
            column.setUpdatable( false );
            table.addPrimaryKey( column );
            // 全局配置-ID值生成方式
            processCustomKeyGenerator( table, column );
        }
    }

    /**
     * 处理属性上的{@code @Column}注解
     * @param entity                实体类
     * @param attribute             属性对象
     * @param enableAutoJdbcMapping 是否开启自动映射JDBC类型
     */
    private Column processColumnAnnotation( final Class<?> entity, final Attribute attribute, final boolean enableAutoJdbcMapping ) {
        String columnName = null;
        boolean insertable = true;
        boolean updatable = true;
        JdbcType jdbcType = null;
        Class<? extends TypeHandler<?>> typeHandler = null;
        boolean checkNotEmpty = this.configuration.isCheckNotEmpty();
        boolean useJavaType = this.configuration.isUseJavaType();
        boolean blob = false;
        if ( attribute.isAnnotationPresent( com.wkit.lost.mybatis.annotation.Column.class ) ) {
            // 自定义@Column注解
            com.wkit.lost.mybatis.annotation.Column columnAnnotation = attribute.getAnnotation( com.wkit.lost.mybatis.annotation.Column.class );
            insertable = columnAnnotation.insertable();
            updatable = columnAnnotation.updatable();
            columnName = columnAnnotation.name();
        } else if ( attribute.isAnnotationPresent( JavaxPersistence.COLUMN ) ) {
            // JPA @Column注解
            javax.persistence.Column columnAnnotation = attribute.getAnnotation( javax.persistence.Column.class );
            insertable = columnAnnotation.insertable();
            updatable = columnAnnotation.updatable();
            columnName = columnAnnotation.name();
        }
        // 处理扩展注解
        if ( attribute.isAnnotationPresent( ColumnExt.class ) ) {
            ColumnExt columnExt = attribute.getAnnotation( ColumnExt.class );
            blob = columnExt.blob();
            if ( StringUtil.isBlank( columnName ) && StringUtil.hasText( columnExt.column() ) ) {
                columnName = columnExt.column();
            }
            // JDBC类型
            if ( columnExt.jdbcType() != JdbcType.UNDEFINED ) {
                jdbcType = columnExt.jdbcType();
            }
            if ( columnExt.typeHandler() != UnknownTypeHandler.class ) {
                typeHandler = columnExt.typeHandler();
            }
            // 字符串类型空值校验
            Validate validate = columnExt.notEmpty();
            if ( validate != Validate.CONFIG ) {
                checkNotEmpty = validate == Validate.REQUIRED;
            }
            // 使用JAVA类型
            UseJavaType using = columnExt.useJavaType();
            if ( using != UseJavaType.CONFIG ) {
                useJavaType = using == UseJavaType.REQUIRED;
            }
        }
        if ( jdbcType == null && enableAutoJdbcMapping ) {
            // 开启自动映射
            jdbcType = JdbcTypeMappingRegister.getJdbcType( attribute.getJavaType() );
        }
        if ( StringUtil.isBlank( columnName ) ) {
            columnName = attribute.getName();
        }
        // 字段名策略处理
        columnName = transformStrategy( false, columnName, this.configuration );
        // 关键字处理
        String wrapKeyWord = this.configuration.getWrapKeyWord();
        if ( StringUtil.hasText( wrapKeyWord ) && SqlKeyWords.containsWord( columnName ) ) {
            columnName = MessageFormat.format( wrapKeyWord, columnName );
        }
        Column column = new Column( entity, attribute.getName(), columnName );
        column.setInsertable( insertable ).setUpdatable( updatable );
        column.setBlob( blob ).setCheckNotEmpty( checkNotEmpty );
        column.setJdbcType( jdbcType ).setTypeHandler( typeHandler );
        column.setJavaType( attribute.getJavaType() ).setUseJavaType( useJavaType );
        // 使用基本类型警告
        if ( column.getJavaType().isPrimitive() ) {
            log.warn( "Warning: The `{}` attribute in the `{}` entity is defined as a primitive type. " +
                    "The primitive type is not null at any time in dynamic SQL because it has a default value. It is " +
                    "recommended to modify the primitive type to the corresponding wrapper type!", column.getProperty(), column.getEntity().getCanonicalName() );
        }
        return column;
    }

    /**
     * 处理属性上的{@code @OrderBy}注解
     * @param table     表映射信息对象
     * @param column    字段映射对象
     * @param attribute 属性信息对象
     */
    private void processOrderByAnnotation( final Table table, final Column column, final Attribute attribute ) {
        String orderValue = null;
        if ( attribute.isAnnotationPresent( OrderBy.class ) ) {
            orderValue = attribute.getAnnotation( OrderBy.class ).value();
        } else if ( attribute.isAnnotationPresent( JavaxPersistence.ORDER_BY ) ) {
            orderValue = attribute.getAnnotation( javax.persistence.OrderBy.class ).value();
        }
        if ( orderValue != null ) {
            orderValue = "".equals( StringUtil.strip( orderValue ) ) ? "ASC" : orderValue;
            column.setOrderBy( orderValue );
        }
    }

    /**
     * 处理主键生成策略
     * @param table     表映射信息对象
     * @param column    字段映射对象
     * @param attribute 属性信息对象
     */
    private void processKeyGenerator( final Table table, final Column column, final Attribute attribute ) {
        if ( attribute.isAnnotationPresent( Identity.class ) ) {
            // @Identity优先级最高
            processIdentityAnnotation( table, column, attribute );
        } else if ( attribute.isAnnotationPresent( SequenceGenerator.class, JavaxPersistence.SEQUENCE_GENERATOR ) ) {
            // @SequenceGenerator序列
            processSequenceGeneratorAnnotation( table, column, attribute );
        } else if ( attribute.isAnnotationPresent( GeneratedValue.class, JavaxPersistence.GENERATED_VALUE ) ) {
            // @GeneratedValue
            processGeneratedValueAnnotation( table, column, attribute );
        } else if ( attribute.isAnnotationPresent( Worker.class ) ) {
            Worker worker = attribute.getAnnotation( Worker.class );
            if ( worker.value() ) {
                column.setWorker( true );
            } else {
                column.setWorkerString( true );
            }
        }
    }

    /**
     * 处理{@code @Identity}注解
     * @param table     表映射信息对象
     * @param column    字段映射对象
     * @param attribute 属性信息对象
     */
    private void processIdentityAnnotation( final Table table, final Column column, final Attribute attribute ) {
        Identity identity = attribute.getAnnotation( Identity.class );
        if ( identity.useJdbcGenerated() ) {
            column.setIdentity( true ).setGenerator( "JDBC" );
            table.addPrimaryKeyProperty( column.getProperty() );
        } else if ( identity.dialect() != Dialect.UNDEFINED ) {
            column.setIdentity( true ).setExecuting( Executing.AFTER ).setGenerator( identity.dialect().getKeyGenerator() );
        } else {
            if ( StringUtil.isBlank( identity.identitySql() ) ) {
                throw new MapperResolverException( StringUtil.format( "The @identity annotation on the '{}' class's attribute '{}' is invalid", column.getEntity().getCanonicalName(), column.getProperty() ) );
            }
            column.setIdentity( true ).setExecuting( identity.execution() ).setGenerator( identity.identitySql() );
        }
    }

    /**
     * 处理{@code @SequenceGenerator}注解
     * @param table     表映射信息对象
     * @param column    字段映射对象
     * @param attribute 属性信息对象
     */
    private void processSequenceGeneratorAnnotation( final Table table, final Column column, final Attribute attribute ) {
        String sequenceName = null;
        if ( attribute.isAnnotationPresent( SequenceGenerator.class ) ) {
            // 自定义@SequenceGenerator注解
            SequenceGenerator sequenceAnnotation = attribute.getAnnotation( SequenceGenerator.class );
            sequenceName = sequenceAnnotation.name();
            if ( StringUtil.isBlank( sequenceName ) ) {
                sequenceName = sequenceAnnotation.sequenceName();
            }
        } else if ( attribute.isAnnotationPresent( JavaxPersistence.SEQUENCE_GENERATOR ) ) {
            // JPA @SequenceGenerator注解
            javax.persistence.SequenceGenerator sequenceAnnotation = attribute.getAnnotation( javax.persistence.SequenceGenerator.class );
            sequenceName = sequenceAnnotation.name();
            if ( StringUtil.isBlank( sequenceName ) ) {
                sequenceName = sequenceAnnotation.sequenceName();
            }
        }
        if ( StringUtil.isBlank( sequenceName ) ) {
            throw new MapperResolverException( StringUtil.format( "The @SequenceGenerator on the `{}` attribute of the `{}` class does not specify the sequenceName value.",
                    column.getProperty(), column.getEntity().getCanonicalName() ) );
        }
        column.setSequenceName( sequenceName );
    }

    /**
     * 处理{@code @GeneratedValue}注解
     * @param table     表映射信息对象
     * @param column    字段映射对象
     * @param attribute 属性信息对象
     */
    private void processGeneratedValueAnnotation( final Table table, final Column column, final Attribute attribute ) {
        boolean isIdentity = false;
        String generator = null;
        if ( attribute.isAnnotationPresent( GeneratedValue.class ) ) {
            // 自定义@GeneratedValue
            GeneratedValue generatedValueAnnotation = attribute.getAnnotation( GeneratedValue.class );
            generator = generatedValueAnnotation.generator();
            isIdentity = generatedValueAnnotation.strategy() == GenerationType.IDENTITY;
        } else if ( attribute.isAnnotationPresent( JavaxPersistence.GENERATED_VALUE ) ) {
            // JPA @GeneratedValue
            javax.persistence.GeneratedValue generatedValueAnnotation = attribute.getAnnotation( javax.persistence.GeneratedValue.class );
            generator = generatedValueAnnotation.generator();
            isIdentity = generatedValueAnnotation.strategy() == javax.persistence.GenerationType.IDENTITY;
        }
        if ( "UUID".equalsIgnoreCase( generator ) ) {
            column.setUuid( true );
        } else if ( "JDBC".equalsIgnoreCase( generator ) ) {
            column.setIdentity( true ).setGenerator( generator.toUpperCase( Locale.ROOT ) );
            table.addPrimaryKeyProperty( column.getProperty() );
            table.addPrimaryKeyColumn( column.getColumn() );
        } else if ( "WORKER".equalsIgnoreCase( generator ) ) {
            column.setWorker( true );
        } else if ( "WORKER_STR".equalsIgnoreCase( generator ) || "WORKER_STRING".equalsIgnoreCase( generator ) || "WORKERSTR".equalsIgnoreCase( generator ) ) {
            column.setWorkerString( true );
        } else {
            if ( isIdentity ) {
                column.setIdentity( true );
                if ( StringUtil.hasText( generator ) ) {
                    Dialect dialect = Dialect.getDBDialect( generator );
                    if ( dialect != null ) {
                        generator = dialect.getKeyGenerator();
                    }
                    column.setGenerator( generator );
                }
            } else {
                throw new MapperResolverException( StringUtil.format( "The @generatedValue annotation on the '{}' class's attribute '{}' supports the following form: \n1.{}\n2.{}\n3.{}",
                        column.getEntity().getCanonicalName(), column.getProperty(),
                        "@GeneratedValue(generator = \"UUID\")",
                        "@GeneratedValue(generator = \"JDBC\")",
                        "@GeneratedValue(generator = \"WORKER\")",
                        "@GeneratedValue(generator = \"WORKER_STR\")",
                        "@GeneratedValue(strategy = GenerationType.IDENTITY, [ generator = \"[ MySql, SQLServer... ]\" ])" ) );
            }
        }
    }

    /**
     * 处理自动识别主键
     * @param table     表映射信息对象
     * @param column    字段映射对象
     * @param attribute 属性信息对象
     */
    private void processAutoDiscernPrimaryKey( final Table table, final Column column, final Attribute attribute ) {
        if ( this.configuration.isAutoDiscernPrimaryKey() ) {
            String property = column.getProperty();
            String[] array = this.configuration.getPrimaryKeys();
            boolean include = ArrayUtil.isEmpty( array ) ? "id".equalsIgnoreCase( property ) : StringUtil.include( array, property );
            if ( include ) {
                column.setPrimaryKey( true );
                table.setPrimaryKey( column );
            }
        }
    }

    private void processCustomKeyGenerator( final Table table, final Column column ) {
        // 检测是否存在主键值生成方式
        if ( !column.isUuid() && !column.isIdentity() && !column.isWorker() && !column.isWorkerString()
                && StringUtil.isBlank( column.getGenerator() ) ) {
            column.setUuid( this.configuration.isUuid() );
            column.setIdentity( this.configuration.isIdentity() );
            column.setWorker( this.configuration.isWorker() );
            column.setWorkerString( this.configuration.isWorkerString() );
        }
    }

    /**
     * 命名转换
     * @param value         值
     * @param configuration 自定义配置
     * @return 新字符串
     */
    private String transformStrategy( boolean isTable, String value, MyBatisCustomConfiguration configuration ) {
        if ( StringUtil.isBlank( value ) ) {
            return null;
        }
        if ( isTable ) {
            return this.physicalNamingStrategy.tableNameValueOf( value, this.strategy );
        } else {
            return this.physicalNamingStrategy.columnNameValueOf( value, this.strategy );
        }
    }

    /**
     * 过滤属性
     * @param attribute 属性信息
     * @return boolean
     */
    private boolean attributeFilter( final Attribute attribute ) {
        return !( !configuration.isUseSimpleType()
                && attribute.isAnnotationPresent( com.wkit.lost.mybatis.annotation.Column.class, JavaxPersistence.COLUMN )
                && attribute.isAnnotationPresent( ColumnExt.class )
                && ( SimpleTypeRegistry.isSimpleType( attribute.getJavaType() )
                || ( this.configuration.isEnumAsSimpleType() && Enum.class.isAssignableFrom( attribute.getJavaType() ) ) ) );
    }
}




























