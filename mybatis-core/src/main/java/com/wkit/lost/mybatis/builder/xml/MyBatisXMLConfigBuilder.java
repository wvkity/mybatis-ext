package com.wkit.lost.mybatis.builder.xml;

import com.wkit.lost.mybatis.session.MyBatisConfiguration;
import com.wkit.lost.mybatis.utils.StringUtil;
import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.loader.ProxyFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.JdbcType;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

public class MyBatisXMLConfigBuilder extends BaseBuilder {
    private boolean parsed;
    private final XPathParser parser;
    private String environment;
    private final ReflectorFactory localReflectorFactory = new DefaultReflectorFactory();

    public MyBatisXMLConfigBuilder( Reader reader ) {
        this( reader, null, null );
    }

    public MyBatisXMLConfigBuilder( Reader reader, String environment ) {
        this( reader, environment, null );
    }

    public MyBatisXMLConfigBuilder( Reader reader, String environment, Properties props ) {
        this( new XPathParser( reader, true, props, new XMLMapperEntityResolver() ), environment, props );
    }

    public MyBatisXMLConfigBuilder( InputStream inputStream ) {
        this( inputStream, null, null );
    }

    public MyBatisXMLConfigBuilder( InputStream inputStream, String environment ) {
        this( inputStream, environment, null );
    }

    public MyBatisXMLConfigBuilder( InputStream inputStream, String environment, Properties props ) {
        this( new XPathParser( inputStream, true, props, new XMLMapperEntityResolver() ), environment, props );
    }

    public MyBatisXMLConfigBuilder( XPathParser parser, String environment, Properties props ) {
        super( new MyBatisConfiguration() );
        ErrorContext.instance().resource( "SQL Mapper Configuration" );
        this.configuration.setVariables( props );
        this.parsed = false;
        this.environment = environment;
        this.parser = parser;
    }

    public MyBatisConfiguration parse() {
        if ( this.parsed ) {
            throw new BuilderException( "Each XMLConfigBuilder can only be used once." );
        }
        parsed = true;
        parseConfiguration( parser.evalNode( "/configuration" ) );
        return ( MyBatisConfiguration ) this.configuration;
    }

    @Override
    public MyBatisConfiguration getConfiguration() {
        return ( MyBatisConfiguration ) this.configuration;
    }

    private void parseConfiguration( XNode root ) {
        try {
            //issue #117 read properties first
            propertiesElement( root.evalNode( "properties" ) );
            Properties settings = settingsAsProperties( root.evalNode( "settings" ) );
            loadCustomVsf( settings );
            loadCustomLogImpl( settings );
            typeAliasesElement( root.evalNode( "typeAliases" ) );
            pluginElement( root.evalNode( "plugins" ) );
            objectFactoryElement( root.evalNode( "objectFactory" ) );
            objectWrapperFactoryElement( root.evalNode( "objectWrapperFactory" ) );
            reflectorFactoryElement( root.evalNode( "reflectorFactory" ) );
            settingsElement( settings );
            // read it after objectFactory and objectWrapperFactory issue #631
            environmentsElement( root.evalNode( "environment" ) );
            databaseIdProviderElement( root.evalNode( "databaseIdProvider" ) );
            typeHandlerElement( root.evalNode( "typeHandlers" ) );
            mapperElement( root.evalNode( "mappers" ) );
        } catch ( Exception e ) {
            throw new BuilderException( "Error parsing SQL Mapper Configuration. Cause: " + e, e );
        }
    }

    private void propertiesElement( XNode context ) throws Exception {
        if ( context != null ) {
            Properties defaults = context.getChildrenAsProperties();
            String resource = context.getStringAttribute( "resource" );
            String url = context.getStringAttribute( "url" );
            if ( resource == null && url == null ) {
                throw new BuilderException( "The properties element cannot specify both a URL and a resource based property file reference.  Please specify one or the other." );
            }
            if ( resource != null ) {
                defaults.putAll( Resources.getResourceAsProperties( resource ) );
            } else {
                defaults.putAll( Resources.getUrlAsProperties( url ) );
            }
            Properties vars = this.configuration.getVariables();
            if ( vars != null ) {
                defaults.putAll( vars );
            }
            this.parser.setVariables( vars );
            this.configuration.setVariables( vars );
        }
    }

    private Properties settingsAsProperties( XNode context ) {
        if ( context == null ) {
            return new Properties();
        }
        Properties props = context.getChildrenAsProperties();
        MetaClass metaConfig = MetaClass.forClass( org.apache.ibatis.session.Configuration.class, localReflectorFactory );
        for ( Object key : props.keySet() ) {
            if ( !metaConfig.hasSetter( String.valueOf( key ) ) ) {
                throw new BuilderException( "The setting " + key + " is not known.  Make sure you spelled it correctly (case sensitive)." );
            }
        }
        return props;
    }

    private void loadCustomVsf( Properties props ) throws ClassNotFoundException {
        String value = props.getProperty( "vsfImpl" );
        if ( value != null ) {
            String[] classes = value.split( "," );
            for ( String clazz : classes ) {
                if ( StringUtil.hasText( clazz ) ) {
                    @SuppressWarnings( "unchecked" )
                    Class<? extends VFS> vfsImpl = ( Class<? extends VFS> ) Resources.classForName( clazz );
                    this.configuration.setVfsImpl( vfsImpl );
                }
            }
        }
    }

    private void loadCustomLogImpl( Properties props ) {
        Class<? extends Log> logImpl = resolveClass( props.getProperty( "logImpl" ) );
        this.configuration.setLogImpl( logImpl );
    }

    private void typeAliasesElement( XNode parent ) {
        if ( parent != null ) {
            for ( XNode child : parent.getChildren() ) {
                if ( "package".equals( child.getName() ) ) {
                    String typeAliasPackage = child.getStringAttribute( "name" );
                    this.configuration.getTypeAliasRegistry().registerAliases( typeAliasPackage );
                } else {
                    String alias = child.getStringAttribute( "alias" );
                    String type = child.getStringAttribute( "type" );
                    try {
                        Class<?> clazz = Resources.classForName( type );
                        if ( alias == null ) {
                            typeAliasRegistry.registerAlias( clazz );
                        } else {
                            typeAliasRegistry.registerAlias( alias, clazz );
                        }
                    } catch ( ClassNotFoundException e ) {
                        throw new BuilderException( "Error registering typeAlias for '" + alias + "'. Cause: " + e, e );
                    }
                }
            }
        }
    }

    private void pluginElement( XNode parent ) throws Exception {
        if ( parent != null ) {
            for ( XNode child : parent.getChildren() ) {
                String interceptor = child.getStringAttribute( "interceptor" );
                Properties properties = child.getChildrenAsProperties();
                // JDK9+ Class.newInstance() is deprecated
                Interceptor interceptorInstance = ( Interceptor ) resolveClass( interceptor ).getDeclaredConstructor().newInstance();
                interceptorInstance.setProperties( properties );
                this.configuration.addInterceptor( interceptorInstance );
            }
        }
    }

    private void objectFactoryElement( XNode context ) throws Exception {
        if ( context != null ) {
            String type = context.getStringAttribute( "type" );
            Properties properties = context.getChildrenAsProperties();
            ObjectFactory factory = ( ObjectFactory ) resolveClass( type ).getDeclaredConstructor().newInstance();
            factory.setProperties( properties );
            this.configuration.setObjectFactory( factory );
        }
    }

    private void objectWrapperFactoryElement( XNode context ) throws Exception {
        if ( context != null ) {
            String type = context.getStringAttribute( "type" );
            ObjectWrapperFactory factory = ( ObjectWrapperFactory ) resolveClass( type ).getDeclaredConstructor().newInstance();
            this.configuration.setObjectWrapperFactory( factory );
        }
    }

    private void reflectorFactoryElement( XNode context ) throws Exception {
        if ( context != null ) {
            String type = context.getStringAttribute( "type" );
            ReflectorFactory factory = ( ReflectorFactory ) resolveClass( type ).getDeclaredConstructor().newInstance();
            this.configuration.setReflectorFactory( factory );
        }
    }

    private void settingsElement( Properties props ) {
        this.configuration.setAutoMappingBehavior( AutoMappingBehavior.valueOf( props.getProperty( "autoMappingBehavior", "PARTIAL" ) ) );
        this.configuration.setAutoMappingUnknownColumnBehavior( AutoMappingUnknownColumnBehavior.valueOf( props.getProperty( "autoMappingUnknownColumnBehavior", "NONE" ) ) );
        this.configuration.setCacheEnabled( booleanValueOf( props.getProperty( "cacheEnabled" ), true ) );
        this.configuration.setProxyFactory( ( ProxyFactory ) createInstance( props.getProperty( "proxyFactory" ) ) );
        this.configuration.setLazyLoadingEnabled( booleanValueOf( props.getProperty( "lazyLoadingEnabled" ), true ) );
        this.configuration.setAggressiveLazyLoading( booleanValueOf( props.getProperty( "aggressiveLazyLoading" ), true ) );
        this.configuration.setMultipleResultSetsEnabled( booleanValueOf( props.getProperty( "multipleResultSetsEnabled" ), true ) );
        this.configuration.setUseGeneratedKeys( booleanValueOf( props.getProperty( "useGeneratedKeys" ), false ) );
        this.configuration.setDefaultExecutorType( ExecutorType.valueOf( props.getProperty( "defaultExecutorType", "SIMPLE" ) ) );
        this.configuration.setDefaultStatementTimeout( integerValueOf( props.getProperty( "defaultStatementTimeout" ), null ) );
        this.configuration.setDefaultFetchSize( integerValueOf( props.getProperty( "defaultFetchSize" ), null ) );
        // mapUnderscoreToCamelCase属性默认为: true
        this.configuration.setMapUnderscoreToCamelCase( booleanValueOf( props.getProperty( "mapUnderscoreToCamelCase" ), true ) );
        this.configuration.setSafeRowBoundsEnabled( booleanValueOf( props.getProperty( "safeRowBoundsEnabled" ), false ) );
        this.configuration.setLocalCacheScope( LocalCacheScope.valueOf( props.getProperty( "localCacheScope", "SESSION" ) ) );
        this.configuration.setJdbcTypeForNull( JdbcType.valueOf( props.getProperty( "jdbcTypeForNull", "OTHER" ) ) );
        this.configuration.setLazyLoadTriggerMethods( stringSetValueOf( props.getProperty( "lazyLoadTriggerMethods" ), "equals,clone,hashCode,toString" ) );
        this.configuration.setSafeResultHandlerEnabled( booleanValueOf( props.getProperty( "safeResultHandlerEnabled" ), true ) );
        this.configuration.setDefaultScriptingLanguage( resolveClass( props.getProperty( "defaultScriptingLanguage" ) ) );
        this.configuration.setDefaultEnumTypeHandler( resolveClass( props.getProperty( "defaultEnumTypeHandler" ) ) );
        this.configuration.setCallSettersOnNulls( booleanValueOf( props.getProperty( "callSettersOnNulls" ), false ) );
        this.configuration.setUseActualParamName( booleanValueOf( props.getProperty( "useActualParamName" ), true ) );
        this.configuration.setReturnInstanceForEmptyRow( booleanValueOf( props.getProperty( "returnInstanceForEmptyRow" ), false ) );
        this.configuration.setLogPrefix( props.getProperty( "logPrefix" ) );
        this.configuration.setConfigurationFactory( resolveClass( props.getProperty( "configurationFactory" ) ) );
    }

    private void environmentsElement( XNode context ) throws Exception {
        if ( context != null ) {
            if ( environment == null ) {
                environment = context.getStringAttribute( "default" );
            }
            for ( XNode child : context.getChildren() ) {
                String id = child.getStringAttribute( "id" );
                if ( isSpecifiedEnvironment( id ) ) {
                    TransactionFactory txFactory = transactionManagerElement( child.evalNode( "transactionManager" ) );
                    DataSourceFactory dsFactory = dataSourceElement( child.evalNode( "dataSource" ) );
                    DataSource dataSource = dsFactory.getDataSource();
                    Environment.Builder environmentBuilder = new Environment.Builder( id )
                            .transactionFactory( txFactory )
                            .dataSource( dataSource );
                    this.configuration.setEnvironment( environmentBuilder.build() );
                }
            }
        }
    }

    private boolean isSpecifiedEnvironment( String id ) {
        if ( environment == null ) {
            throw new BuilderException( "No environment specified." );
        } else if ( id == null ) {
            throw new BuilderException( "Environment requires an id attribute." );
        }
        return environment.equals( id );
    }

    private TransactionFactory transactionManagerElement( XNode context ) throws Exception {
        if ( context != null ) {
            String type = context.getStringAttribute( "type" );
            Properties props = context.getChildrenAsProperties();
            TransactionFactory factory = ( TransactionFactory ) resolveClass( type ).getDeclaredConstructor().newInstance();
            factory.setProperties( props );
            return factory;
        }
        throw new BuilderException( "Environment declaration requires a TransactionFactory." );
    }

    private DataSourceFactory dataSourceElement( XNode context ) throws Exception {
        if ( context != null ) {
            String type = context.getStringAttribute( "type" );
            Properties props = context.getChildrenAsProperties();
            DataSourceFactory factory = ( DataSourceFactory ) resolveClass( type ).getDeclaredConstructor().newInstance();
            factory.setProperties( props );
            return factory;
        }
        throw new BuilderException( "Environment declaration requires a DataSourceFactory." );
    }

    private void databaseIdProviderElement( XNode context ) throws Exception {
        DatabaseIdProvider databaseIdProvider = null;
        if ( context != null ) {
            String type = context.getStringAttribute( "type" );
            if ( "VENDOR".equals( type ) ) {
                type = "DB_VENDOR";
            }
            Properties properties = context.getChildrenAsProperties();
            databaseIdProvider = ( DatabaseIdProvider ) resolveClass( type ).getDeclaredConstructor().newInstance();
            databaseIdProvider.setProperties( properties );
        }
        Environment environment = this.configuration.getEnvironment();
        if ( environment != null && databaseIdProvider != null ) {
            String databaseId = databaseIdProvider.getDatabaseId( environment.getDataSource() );
            this.configuration.setDatabaseId( databaseId );
        }
    }

    private void typeHandlerElement( XNode parent ) {
        if ( parent != null ) {
            for ( XNode child : parent.getChildren() ) {
                if ( "package".equals( child.getName() ) ) {
                    String typeHandlerPackage = child.getStringAttribute( "name" );
                    typeHandlerRegistry.register( typeHandlerPackage );
                } else {
                    String javaTypeName = child.getStringAttribute( "javaType" );
                    String jdbcTypeName = child.getStringAttribute( "jdbcType" );
                    String handlerTypeName = child.getStringAttribute( "handler" );
                    Class<?> javaTypeClass = resolveClass( javaTypeName );
                    JdbcType jdbcType = resolveJdbcType( jdbcTypeName );
                    Class<?> typeHandlerClass = resolveClass( handlerTypeName );
                    if ( javaTypeClass != null ) {
                        if ( jdbcType == null ) {
                            typeHandlerRegistry.register( javaTypeClass, typeHandlerClass );
                        } else {
                            typeHandlerRegistry.register( javaTypeClass, jdbcType, typeHandlerClass );
                        }
                    } else {
                        typeHandlerRegistry.register( typeHandlerClass );
                    }
                }
            }
        }
    }

    private void mapperElement( XNode parent ) throws Exception {
        if ( parent != null ) {
            for ( XNode child : parent.getChildren() ) {
                if ( "package".equals( parent.getName() ) ) {
                    String mapperPackage = child.getStringAttribute( "name" );
                    this.configuration.addMappers( mapperPackage );
                } else {
                    String resource = child.getStringAttribute( "resource" );
                    String url = child.getStringAttribute( "url" );
                    String mapperClass = child.getStringAttribute( "class" );
                    if ( resource != null && url == null && mapperClass == null ) {
                        ErrorContext.instance().resource( resource );
                        InputStream inputStream = Resources.getResourceAsStream( resource );
                        XMLMapperBuilder mapperParser = new XMLMapperBuilder( inputStream, this.configuration, resource, this.configuration.getSqlFragments() );
                        mapperParser.parse();
                    } else if ( resource == null && url != null && mapperClass == null ) {
                        ErrorContext.instance().resource( url );
                        InputStream inputStream = Resources.getResourceAsStream( url );
                        XMLMapperBuilder mapperParser = new XMLMapperBuilder( inputStream, this.configuration, url, this.configuration.getSqlFragments() );
                        mapperParser.parse();
                    } else if ( resource == null && url == null && mapperClass != null ) {
                        Class<?> mapperInterface = Resources.classForName( mapperClass );
                        this.configuration.addMapper( mapperInterface );
                    } else {
                        throw new BuilderException( "A mapper element may only specify a url, resource or class, but not more than one." );
                    }
                }
            }
        }
    }
}











