package com.wkit.lost.mybatis.builder.annotation;

import com.wkit.lost.mybatis.binding.MyBatisMapperMethod;
import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.mybatis.session.MyBatisConfiguration;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Case;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Property;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.TypeDiscriminator;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.CacheRefResolver;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.builder.annotation.MethodResolver;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Discriminator;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.parsing.PropertyParser;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * 继承{@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder}实现无XML配置文件CURD操作
 */
@Log4j2
public class MyBatisMapperAnnotationBuilder extends MapperAnnotationBuilder {

    private static final Set<Class<? extends Annotation>> SQL_ANNOTATION_TYPES = new HashSet<>();
    private static final Set<Class<? extends Annotation>> SQL_PROVIDER_ANNOTATION_TYPES = new HashSet<>();
    private final MyBatisConfiguration configuration;
    private final MapperBuilderAssistant assistant;
    private final Class<?> type;

    static {
        SQL_ANNOTATION_TYPES.add( Select.class );
        SQL_ANNOTATION_TYPES.add( Insert.class );
        SQL_ANNOTATION_TYPES.add( Update.class );
        SQL_ANNOTATION_TYPES.add( Delete.class );
        SQL_PROVIDER_ANNOTATION_TYPES.add( SelectProvider.class );
        SQL_PROVIDER_ANNOTATION_TYPES.add( InsertProvider.class );
        SQL_PROVIDER_ANNOTATION_TYPES.add( UpdateProvider.class );
        SQL_PROVIDER_ANNOTATION_TYPES.add( DeleteProvider.class );
    }

    public MyBatisMapperAnnotationBuilder( MyBatisConfiguration configuration, Class<?> type ) {
        super( configuration, type );
        String resource = type.getName().replace( '.', '/' ) + ".java (best guess)";
        this.assistant = new MapperBuilderAssistant( configuration, resource );
        this.configuration = configuration;
        this.type = type;
    }

    @Override
    public void parse() {
        String resource = this.type.toString();
        if ( !this.configuration.isResourceLoaded( resource ) ) {
            // 加载XML映射资源
            loadXmlResource();
            this.configuration.addLoadedResource( resource );
            this.assistant.setCurrentNamespace( this.type.getName() );
            // 解析缓存
            parseCache();
            parseCacheRef();
            Method[] methods = this.type.getMethods();
            for ( Method method : methods ) {
                try {
                    // issue #237
                    if ( !method.isBridge() ) {
                        parseStatement( method );
                    }
                } catch ( IncompleteElementException e ) {
                    //configuration.addIncompleteMethod( new MethodResolver( this, method ) );
                    configuration.addIncompleteMethod( new MyBatisMethodResolver( this, method ) );
                }
            }
            // 注入无XML配置文件CURD-SQL
            if ( MapperExecutor.class.isAssignableFrom( this.type ) ) {
                log.debug( "Customize processing the mapper interface: `{}`", this.type.getCanonicalName() );
                MyBatisConfigCache.getSqlInjector( this.configuration ).inject( this.assistant, this.type );
            }
        }
        parsePendingMethods();
    }
    
    private void parsePendingMethods() {
        Collection<MethodResolver> incompleteMethods = this.configuration.getIncompleteMethods();
        synchronized( incompleteMethods ) {
            Iterator<MethodResolver> iterator = incompleteMethods.iterator();
            while ( iterator.hasNext() ) {
                try {
                    iterator.next().resolve();
                    iterator.remove();
                } catch ( IncompleteElementException e ) {
                    // This method is still missing a resource
                }
            }
        }
    }

    /**
     * 加载XML映射资源
     */
    private void loadXmlResource() {
        // Spring may not know the real resource name so we check a flag
        // to prevent loading again a resource twice
        // this flag is set at XMLMapperBuilder#bindMapperForNamespace
        if ( !this.configuration.isResourceLoaded( "namespace:" + type.getName() ) ) {
            String xmlResource = this.type.getName().replace( '.', '/' ) + ".xml";
            InputStream inputStream = this.type.getResourceAsStream( "/" + xmlResource );
            if ( inputStream == null ) {
                try ( InputStream newInputStream = Resources.getResourceAsStream( this.type.getClassLoader(), xmlResource ) ) {
                    if ( newInputStream != null ) {
                        inputStream = newInputStream;
                    }
                } catch ( Exception e ) {
                    // ignore
                }
            }
            if ( inputStream != null ) {
                XMLMapperBuilder xmlBuilder = new XMLMapperBuilder( inputStream, this.assistant.getConfiguration(), xmlResource, configuration.getSqlFragments(), this.type.getName() );
                xmlBuilder.parse();
            }
        }
    }

    private void parseCache() {
        CacheNamespace cacheDomain = this.type.getAnnotation( CacheNamespace.class );
        if ( cacheDomain != null ) {
            Integer size = cacheDomain.size() == 0 ? null : cacheDomain.size();
            Long flushInterval = cacheDomain.flushInterval() == 0 ? null : cacheDomain.flushInterval();
            Properties pros = convertToProperties( cacheDomain.properties() );
            this.assistant.useNewCache( cacheDomain.implementation(), cacheDomain.eviction(), flushInterval, size, cacheDomain.readWrite(), cacheDomain.blocking(), pros );
        }
    }

    private Properties convertToProperties( Property[] properties ) {
        if ( properties.length == 0 ) {
            return null;
        }
        Properties props = new Properties();
        for ( Property property : properties ) {
            props.setProperty( property.name(), PropertyParser.parse( property.value(), configuration.getVariables() ) );
        }
        return props;
    }

    private void parseCacheRef() {
        CacheNamespaceRef cacheDomainRef = this.type.getAnnotation( CacheNamespaceRef.class );
        if ( cacheDomainRef != null ) {
            Class<?> refType = cacheDomainRef.value();
            String refName = cacheDomainRef.name();
            if ( refType == Void.class && refName.isEmpty() ) {
                throw new BuilderException( "Should be specified either value() or name() attribute in the @CacheNamespaceRef" );
            }
            if ( refType != Void.class && !refName.isEmpty() ) {
                throw new BuilderException( "Cannot use both value() and name() attribute in the @CacheNamespaceRef" );
            }
            String namespace = refType != Void.class ? refType.getName() : refName;
            try {
                this.assistant.useCacheRef( namespace );
            } catch ( IncompleteElementException e ) {
                configuration.addIncompleteCacheRef( new CacheRefResolver( assistant, namespace ) );
            }
        }
    }

    void parseStatement( Method method ) {
        Class<?> parameterTypeClass = getParameterType( method );
        LanguageDriver languageDriver = getLanguageDriver( method );
        SqlSource sqlSource = getSqlSourceFromAnnotations( method, parameterTypeClass, languageDriver );
        if ( sqlSource != null ) {
            Options options = method.getAnnotation( Options.class );
            final String mappedStatementId = this.type.getName() + "." + method.getName();
            Integer fetchSize = null;
            Integer timeout = null;
            StatementType statementType = StatementType.PREPARED;
            ResultSetType resultSetType = null;
            SqlCommandType sqlCommandType = getSqlCommandType( method );
            boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
            boolean flushCache = !isSelect;
            boolean useCache = isSelect;

            KeyGenerator keyGenerator;
            String keyProperty = null;
            String keyColumn = null;
            if ( SqlCommandType.INSERT.equals( sqlCommandType ) || SqlCommandType.UPDATE.equals( sqlCommandType ) ) {
                // first check for SelectKey annotation - that overrides everything else
                SelectKey selectKey = method.getAnnotation( SelectKey.class );
                if ( selectKey != null ) {
                    keyGenerator = handleSelectKeyAnnotation( selectKey, mappedStatementId, getParameterType( method ), languageDriver );
                    keyProperty = selectKey.keyProperty();
                } else if ( options == null ) {
                    keyGenerator = this.configuration.isUseGeneratedKeys() ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
                } else {
                    keyGenerator = options.useGeneratedKeys() ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
                    keyProperty = options.keyProperty();
                    keyColumn = options.keyColumn();
                }
            } else {
                keyGenerator = NoKeyGenerator.INSTANCE;
            }

            if ( options != null ) {
                if ( Options.FlushCachePolicy.TRUE.equals( options.flushCache() ) ) {
                    flushCache = true;
                } else if ( Options.FlushCachePolicy.FALSE.equals( options.flushCache() ) ) {
                    flushCache = false;
                }
                useCache = options.useCache();
                fetchSize = options.fetchSize() > -1 || options.fetchSize() == Integer.MIN_VALUE ? options.fetchSize() : null;
                timeout = options.timeout() > -1 ? options.timeout() : null;
                statementType = options.statementType();
                resultSetType = options.resultSetType();
            }

            String resultMapId = null;
            ResultMap resultMapAnnotation = method.getAnnotation( ResultMap.class );
            if ( resultMapAnnotation != null ) {
                resultMapId = String.join( ",", resultMapAnnotation.value() );
            } else if ( isSelect ) {
                resultMapId = parseResultMap( method );
            }
            this.assistant.addMappedStatement(
                    mappedStatementId,
                    sqlSource,
                    statementType,
                    sqlCommandType,
                    fetchSize,
                    timeout,
                    null,
                    parameterTypeClass,
                    resultMapId,
                    getReturnType( method ),
                    resultSetType,
                    flushCache,
                    useCache,
                    false,
                    keyGenerator,
                    keyProperty,
                    keyColumn,
                    null,
                    languageDriver,
                    options != null ? nullOrEmpty( options.resultSets() ) : null );
        }
    }

    private Class<?> getParameterType( Method method ) {
        Class<?> parameterType = null;
        Class<?>[] parameterTypes = method.getParameterTypes();
        for ( Class<?> currentParameterType : parameterTypes ) {
            if ( !RowBounds.class.isAssignableFrom( currentParameterType ) && ResultHandler.class.isAssignableFrom( currentParameterType ) ) {
                if ( parameterType == null ) {
                    parameterType = currentParameterType;
                } else {
                    parameterType = MyBatisMapperMethod.ParamMap.class;
                }
            }
        }
        return parameterType;
    }

    private LanguageDriver getLanguageDriver( Method method ) {
        Lang lang = method.getAnnotation( Lang.class );
        Class<? extends LanguageDriver> langClass = null;
        if ( lang != null ) {
            langClass = lang.value();
        }
        return this.configuration.getLanguageDriver( langClass );
    }

    private SqlSource getSqlSourceFromAnnotations( Method method, Class<?> parameterType, LanguageDriver languageDriver ) {
        try {
            Class<? extends Annotation> sqlAnnotationType = getSqlAnnotationType( method );
            Class<? extends Annotation> sqlProviderAnnotationType = getSqlProviderAnnotationType( method );
            if ( sqlAnnotationType != null ) {
                if ( sqlProviderAnnotationType != null ) {
                    throw new BindingException( "You cannot supply both a static SQL and SqlProvider to method named " + method.getName() );
                }
                Annotation sqlAnnotation = method.getAnnotation( sqlAnnotationType );
                final String[] strings = (String[]) sqlAnnotation.getClass().getMethod( "value" ).invoke( sqlAnnotation );
                return buildSqlSourceFromStrings( strings, parameterType, languageDriver );
            } else if ( sqlProviderAnnotationType != null ) {
                return new ProviderSqlSource( this.assistant.getConfiguration(), method.getAnnotation( sqlProviderAnnotationType ), type, method );
            }
            return null;
        } catch ( Exception e ) {
            throw new BuilderException( "Could not find value method on SQL annotation.  Cause: " + e, e );
        }
    }

    private Class<? extends Annotation> getSqlAnnotationType( Method method ) {
        return chooseAnnotationType( method, SQL_ANNOTATION_TYPES );
    }

    private Class<? extends Annotation> chooseAnnotationType( Method method, Set<Class<? extends Annotation>> types ) {
        for ( Class<? extends Annotation> type : types ) {
            Annotation annotation = method.getAnnotation( type );
            if ( annotation != null ) {
                return type;
            }
        }
        return null;
    }

    private Class<? extends Annotation> getSqlProviderAnnotationType( Method method ) {
        return chooseAnnotationType( method, SQL_PROVIDER_ANNOTATION_TYPES );
    }

    private SqlSource buildSqlSourceFromStrings( String[] strings, Class<?> parameterTypeClass, LanguageDriver languageDriver ) {
        final StringBuilder sql = new StringBuilder();
        for ( String fragment : strings ) {
            sql.append( fragment );
            sql.append( " " );
        }
        return languageDriver.createSqlSource( this.configuration, sql.toString().trim(), parameterTypeClass );
    }

    private SqlCommandType getSqlCommandType( Method method ) {
        Class<? extends Annotation> command = getSqlAnnotationType( method );
        if ( command == null ) {
            command = getSqlProviderAnnotationType( method );
            if ( command == null ) {
                return SqlCommandType.UNKNOWN;
            }
            if ( command == SelectProvider.class ) {
                command = Select.class;
            } else if ( command == InsertProvider.class ) {
                command = Insert.class;
            } else if ( command == UpdateProvider.class ) {
                command = Update.class;
            } else if ( command == DeleteProvider.class ) {
                command = Delete.class;
            }
        }
        return SqlCommandType.valueOf( command.getSimpleName().toUpperCase( Locale.ENGLISH ) );
    }

    private KeyGenerator handleSelectKeyAnnotation( SelectKey selectKeyAnnotation, String baseStatementId, Class<?> parameterTypeClass, LanguageDriver languageDriver ) {
        String id = baseStatementId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        Class<?> resultTypeClass = selectKeyAnnotation.resultType();
        StatementType statementType = selectKeyAnnotation.statementType();
        String keyProperty = selectKeyAnnotation.keyProperty();
        String keyColumn = selectKeyAnnotation.keyColumn();
        boolean executeBefore = selectKeyAnnotation.before();

        // default
        boolean useCache = false;
        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        Integer fetchSize = null;
        Integer timeout = null;
        ResultSetType resultSetTypeEnum = null;

        SqlSource sqlSource = buildSqlSourceFromStrings( selectKeyAnnotation.statement(), parameterTypeClass, languageDriver );
        SqlCommandType sqlCommandType = SqlCommandType.SELECT;
        assistant.addMappedStatement( id, sqlSource, statementType, sqlCommandType, fetchSize, timeout,
                null, parameterTypeClass, null, resultTypeClass, null, false, false, false,
                keyGenerator, keyProperty, keyColumn, null, languageDriver, null );
        id = assistant.applyCurrentNamespace( id, false );
        MappedStatement keyStatement = this.configuration.getMappedStatement( id, false );
        SelectKeyGenerator answer = new SelectKeyGenerator( keyStatement, executeBefore );
        this.configuration.addKeyGenerator( id, answer );
        return answer;
    }

    private String parseResultMap( Method method ) {
        Class<?> returnType = getReturnType( method );
        ConstructorArgs args = method.getAnnotation( ConstructorArgs.class );
        Results results = method.getAnnotation( Results.class );
        TypeDiscriminator typeDiscriminator = method.getAnnotation( TypeDiscriminator.class );
        String resultMapId = generateResultMapName( method );
        applyResultMap( resultMapId, returnType, argsIf( args ), resultsIf( results ), typeDiscriminator );
        return resultMapId;
    }

    private Class<?> getReturnType( Method method ) {
        Class<?> returnType = method.getReturnType();
        Type resolvedReturnType = TypeParameterResolver.resolveReturnType( method, this.type );
        if ( resolvedReturnType instanceof Class ) {
            returnType = (Class<?>) resolvedReturnType;
            if ( returnType.isArray() ) {
                return returnType.getComponentType();
            }
            // gcode issue #508
            if ( Void.class.equals( returnType ) ) {
                ResultType rt = method.getAnnotation( ResultType.class );
                if ( rt != null ) {
                    returnType = rt.value();
                }
            }
        } else if ( resolvedReturnType instanceof ParameterizedType ) {
            // 泛型
            ParameterizedType parameterizedType = (ParameterizedType) resolvedReturnType;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            if ( Collection.class.isAssignableFrom( rawType ) || Cursor.class.isAssignableFrom( rawType ) ) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if ( actualTypeArguments != null && actualTypeArguments.length == 1 ) {
                    Type returnTypeParameter = actualTypeArguments[ 0 ];
                    if ( returnTypeParameter instanceof Class<?> ) {
                        returnType = (Class<?>) returnTypeParameter;
                    } else if ( returnTypeParameter instanceof ParameterizedType ) {
                        // (gcode issue #443) actual type can be a also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    } else if ( returnTypeParameter instanceof GenericArrayType ) {
                        Class<?> componentType = (Class<?>) ((GenericArrayType) returnTypeParameter).getGenericComponentType();
                        // (gcode issue #525) support List<byte[]>
                        returnType = Array.newInstance( componentType, 0 ).getClass();
                    }
                }
            } else if ( method.isAnnotationPresent( MapKey.class ) && Map.class.isAssignableFrom( rawType ) ) {
                // (gcode issue 504) Do not look into Maps if there is not MapKey annotation
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if ( actualTypeArguments != null && actualTypeArguments.length == 2 ) {
                    Type returnTypeParameter = actualTypeArguments[ 1 ];
                    if ( returnTypeParameter instanceof Class<?> ) {
                        returnType = (Class<?>) returnTypeParameter;
                    } else if ( returnTypeParameter instanceof ParameterizedType ) {
                        // (gcode issue 443) actual type can be a also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    }
                }
            } else if ( Optional.class.equals( rawType ) ) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type returnTypeParameter = actualTypeArguments[ 0 ];
                if ( returnTypeParameter instanceof Class<?> ) {
                    returnType = (Class<?>) returnTypeParameter;
                }
            }
        }
        return returnType;
    }

    private String generateResultMapName( Method method ) {
        Results results = method.getAnnotation( Results.class );
        if ( results != null && !results.id().isEmpty() ) {
            return this.type.getName() + "." + results.id();
        }
        Class<?>[] classArray = method.getParameterTypes();
        StringBuilder suffix = new StringBuilder();
        for ( Class<?> clazz : classArray ) {
            suffix.append( "-" );
            suffix.append( clazz.getSimpleName() );
        }
        if ( suffix.length() < 1 ) {
            suffix.append( "-void" );
        }
        return this.type.getName() + "." + method.getName() + suffix;
    }

    private Arg[] argsIf( ConstructorArgs args ) {
        return args == null ? new Arg[ 0 ] : args.value();
    }

    private Result[] resultsIf( Results results ) {
        return results == null ? new Result[ 0 ] : results.value();
    }

    private void applyResultMap( String resultMapId, Class<?> returnType, Arg[] args, Result[] results, TypeDiscriminator discriminator ) {
        List<ResultMapping> resultMappings = new ArrayList<>();
        applyConstructorArgs( args, returnType, resultMappings );
        applyResults( results, returnType, resultMappings );
        Discriminator disc = applyDiscriminator( resultMapId, returnType, discriminator );
        // TODO add AutoMappingBehaviour
        this.assistant.addResultMap( resultMapId, returnType, null, disc, resultMappings, null );
        createDiscriminatorResultMaps( resultMapId, returnType, discriminator );
    }

    private void applyConstructorArgs( Arg[] args, Class<?> resultType, List<ResultMapping> resultMappings ) {
        for ( Arg arg : args ) {
            List<ResultFlag> flags = new ArrayList<>();
            flags.add( ResultFlag.CONSTRUCTOR );
            if ( arg.id() ) {
                flags.add( ResultFlag.ID );
            }
            @SuppressWarnings( "unchecked" )
            Class<? extends TypeHandler<?>> typeHandler = (Class<? extends TypeHandler<?>>) (arg.typeHandler() == UnknownTypeHandler.class ? null : arg.typeHandler());
            ResultMapping resultMapping = this.assistant.buildResultMapping(
                    resultType,
                    nullOrEmpty( arg.name() ),
                    nullOrEmpty( arg.column() ),
                    arg.javaType() == void.class ? null : arg.javaType(),
                    arg.jdbcType() == JdbcType.UNDEFINED ? null : arg.jdbcType(),
                    nullOrEmpty( arg.select() ),
                    nullOrEmpty( arg.resultMap() ),
                    null,
                    nullOrEmpty( arg.columnPrefix() ),
                    typeHandler,
                    flags,
                    null,
                    null,
                    false );
            resultMappings.add( resultMapping );
        }
    }

    private void applyResults( Result[] results, Class<?> resultType, List<ResultMapping> resultMappings ) {
        for ( Result result : results ) {
            List<ResultFlag> flags = new ArrayList<>();
            if ( result.id() ) {
                flags.add( ResultFlag.ID );
            }
            Class<? extends TypeHandler<?>> typeHandler = applyTypeHandler( result );
            ResultMapping resultMapping = this.assistant.buildResultMapping(
                    resultType,
                    nullOrEmpty( result.property() ),
                    nullOrEmpty( result.column() ),
                    result.javaType() == void.class ? null : result.javaType(),
                    result.jdbcType() == JdbcType.UNDEFINED ? null : result.jdbcType(),
                    hasNestedSelect( result ) ? nestedSelectId( result ) : null,
                    null,
                    null,
                    null,
                    typeHandler,
                    flags,
                    null,
                    null,
                    isLazy( result ) );
            resultMappings.add( resultMapping );
        }
    }

    @SuppressWarnings( "unchecked" )
    private Class<? extends TypeHandler<?>> applyTypeHandler( Result result ) {
        return (Class<? extends TypeHandler<?>>) (result.typeHandler() == UnknownTypeHandler.class ? null : result.typeHandler());
    }

    private boolean hasNestedSelect( Result result ) {
        if ( result.one().select().length() > 0 && result.many().select().length() > 0 ) {
            throw new BindingException( "Cannot use both @One and @Many annotations in the same @Result" );
        }
        return result.one().select().length() > 0 || result.many().select().length() > 0;
    }

    private String nestedSelectId( Result result ) {
        String nestedSelect = result.one().select();
        if ( nestedSelect.length() < 1 ) {
            nestedSelect = result.many().select();
        }
        if ( !nestedSelect.contains( "." ) ) {
            nestedSelect = this.type.getName() + "." + nestedSelect;
        }
        return nestedSelect;
    }

    private boolean isLazy( Result result ) {
        boolean isLazy = this.configuration.isLazyLoadingEnabled();
        if ( result.one().select().length() > 0 && FetchType.DEFAULT != result.one().fetchType() ) {
            isLazy = result.one().fetchType() == FetchType.LAZY;
        } else if ( result.many().select().length() > 0 && FetchType.DEFAULT != result.many().fetchType() ) {
            isLazy = result.many().fetchType() == FetchType.LAZY;
        }
        return isLazy;
    }

    private String nullOrEmpty( String value ) {
        return StringUtil.isBlank( value ) ? null : value;
    }

    private Discriminator applyDiscriminator( String resultMapId, Class<?> resultType, TypeDiscriminator discriminator ) {
        if ( discriminator != null ) {
            String column = discriminator.column();
            Class<?> javaType = discriminator.javaType() == void.class ? String.class : discriminator.javaType();
            JdbcType jdbcType = discriminator.jdbcType() == JdbcType.UNDEFINED ? null : discriminator.jdbcType();
            @SuppressWarnings( "unchecked" )
            Class<? extends TypeHandler<?>> typeHandler = (Class<? extends TypeHandler<?>>) (discriminator.typeHandler() == UnknownTypeHandler.class ? null : discriminator.typeHandler());
            Map<String, String> discriminatorMap = new HashMap<>();
            Case[] cases = discriminator.cases();
            for ( Case c : cases ) {
                String value = c.value();
                String caseResultMapId = resultMapId + "-" + value;
                discriminatorMap.put( value, caseResultMapId );
            }
            return this.assistant.buildDiscriminator( resultType, column, javaType, jdbcType, typeHandler, discriminatorMap );
        }
        return null;
    }

    private void createDiscriminatorResultMaps( String resultMapId, Class<?> resultType, TypeDiscriminator discriminator ) {
        if ( discriminator != null ) {
            for ( Case c : discriminator.cases() ) {
                String caseResultMapId = resultMapId + "-" + c.value();
                List<ResultMapping> resultMappings = new ArrayList<>();
                // issue #136
                applyConstructorArgs( c.constructArgs(), resultType, resultMappings );
                applyResults( c.results(), resultType, resultMappings );
                // TODO add AutoMappingBehaviour
                this.assistant.addResultMap( caseResultMapId, c.type(), resultMapId, null, resultMappings, null );
            }
        }
    }
}
