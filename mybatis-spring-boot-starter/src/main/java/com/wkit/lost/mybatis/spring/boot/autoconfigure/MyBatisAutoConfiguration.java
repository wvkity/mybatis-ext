package com.wkit.lost.mybatis.spring.boot.autoconfigure;


import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.keygen.GuidGenerator;
import com.wkit.lost.mybatis.keygen.KeyGenerator;
import com.wkit.lost.mybatis.resolver.EntityResolver;
import com.wkit.lost.mybatis.resolver.FieldResolver;
import com.wkit.lost.mybatis.scripting.xmltags.MyBatisXMLLanguageDriver;
import com.wkit.lost.mybatis.session.MyBatisConfiguration;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.spring.SqlSessionFactoryBean;
import com.wkit.lost.mybatis.sql.injector.SqlInjector;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Log4j2
@org.springframework.context.annotation.Configuration
@ConditionalOnClass( { SqlSessionFactory.class, org.mybatis.spring.SqlSessionFactoryBean.class } )
@ConditionalOnSingleCandidate( DataSource.class )
@EnableConfigurationProperties( MyBatisProperties.class )
@AutoConfigureAfter( DataSourceAutoConfiguration.class )
@AutoConfigureBefore( name = "org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration" )
public class MyBatisAutoConfiguration implements InitializingBean {

    private final MyBatisProperties properties;
    private final Interceptor[] interceptors;
    private final ResourceLoader resourceLoader;
    private final DatabaseIdProvider databaseIdProvider;
    private final List<ConfigurationCustomizer> configurationCustomizers;
    private final List<PropertiesCustomizer> propertiesCustomizers;
    private final ApplicationContext applicationContext;

    public MyBatisAutoConfiguration( MyBatisProperties properties,
                                     ObjectProvider<Interceptor[]> interceptorsProvider,
                                     ResourceLoader resourceLoader,
                                     ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                                     ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
                                     ObjectProvider<List<PropertiesCustomizer>> propertiesCustomizersProvider,
                                     ApplicationContext applicationContext ) {
        this.properties = properties;
        this.interceptors = interceptorsProvider.getIfAvailable();
        this.resourceLoader = resourceLoader;
        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
        this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
        this.propertiesCustomizers = propertiesCustomizersProvider.getIfAvailable();
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if ( !CollectionUtils.isEmpty( propertiesCustomizers ) ) {
            this.propertiesCustomizers.forEach( customizer -> customizer.customize( this.properties ) );
        }
        checkConfigFileExists();
    }

    private void checkConfigFileExists() {
        if ( this.properties.isCheckConfigLocation() && StringUtils.hasText( this.properties.getConfigLocation() ) ) {
            Resource resource = this.resourceLoader.getResource( this.properties.getConfigLocation() );
            Assert.state( resource.exists(), "Cannot find config location: " + resource + " (please add config file or check your MyBatis configuration)" );
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory( DataSource dataSource ) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource( dataSource );
        factory.setVfs( SpringBootVFS.class );
        if ( StringUtil.hasText( this.properties.getConfigLocation() ) ) {
            factory.setConfigLocation( this.resourceLoader.getResource( this.properties.getConfigLocation() ) );
        }
        applyConfiguration( factory );
        if ( this.properties.getConfigurationProperties() != null ) {
            factory.setConfigurationProperties( this.properties.getConfigurationProperties() );
        }
        if ( !ObjectUtils.isEmpty( this.interceptors ) ) {
            factory.setPlugins( this.interceptors );
        }
        if ( this.databaseIdProvider != null ) {
            factory.setDatabaseIdProvider( this.databaseIdProvider );
        }
        if ( StringUtils.hasLength( this.properties.getTypeAliasesPackage() ) ) {
            factory.setTypeAliasesPackage( this.properties.getTypeAliasesPackage() );
        }
        if ( StringUtils.hasLength( this.properties.getTypeEnumsPackage() ) ) {
            factory.setTypeEnumsPackage( this.properties.getTypeEnumsPackage() );
        }
        if ( this.properties.getTypeAliasesSuperType() != null ) {
            factory.setTypeAliasesSuperType( this.properties.getTypeAliasesSuperType() );
        }
        if ( StringUtils.hasLength( this.properties.getTypeHandlersPackage() ) ) {
            factory.setTypeHandlersPackage( this.properties.getTypeHandlersPackage() );
        }
        if ( !ObjectUtils.isEmpty( this.properties.resolveMapperLocations() ) ) {
            factory.setMapperLocations( this.properties.resolveMapperLocations() );
        }
        // 全局配置
        MyBatisCustomConfiguration customConfig;
        if ( !ObjectUtils.isEmpty( this.properties.getCustomConfiguration() ) ) {
            customConfig = this.properties.getCustomConfiguration();
        } else {
            // 优先从容器中获取
            if ( hasBeanFromContext( MyBatisCustomConfiguration.class ) ) {
                customConfig = getBean( MyBatisCustomConfiguration.class );
            } else {
                // 直接创建
                customConfig = MyBatisConfigCache.defaults();
            }
        }
        // SQL注入器
        if ( hasBeanFromContext( SqlInjector.class ) ) {
            customConfig.setInjector( getBean( SqlInjector.class ) );
        }
        // 实体解析器
        if ( hasBeanFromContext( EntityResolver.class ) ) {
            customConfig.setEntityResolver( getBean( EntityResolver.class ) );
        }
        // 属性解析器
        if ( hasBeanFromContext( FieldResolver.class ) ) {
            customConfig.setFieldResolver( getBean( FieldResolver.class ) );
        }
        // 主键生成器
        if ( hasBeanFromContext( KeyGenerator.class ) ) {
            customConfig.setKeyGenerator( getBean( KeyGenerator.class ) );
        } else {
            customConfig.setKeyGenerator( new GuidGenerator() );
        }
        // 雪花算法主键生成器
        if ( hasBeanFromContext( Sequence.class ) ) {
            customConfig.setSequence( getBean( Sequence.class ) );
        }
        factory.setCustomConfiguration( customConfig );
        return factory.getObject();
    }

    private void applyConfiguration( SqlSessionFactoryBean factory ) {
        MyBatisConfiguration configuration = this.properties.getConfiguration();
        if ( configuration == null && !StringUtils.hasText( this.properties.getConfigLocation() ) ) {
            configuration = new MyBatisConfiguration();
            // 默认开启下划线大写转驼峰命名规则
            configuration.setMapUnderscoreToCamelCase( true );
        }
        if ( configuration != null && !CollectionUtils.isEmpty( this.configurationCustomizers ) ) {
            for ( ConfigurationCustomizer customizer : this.configurationCustomizers ) {
                customizer.customize( configuration );
            }
        }
        if ( configuration != null ) {
            configuration.setDefaultScriptingLanguage( MyBatisXMLLanguageDriver.class );
        }
        factory.setConfiguration( configuration );
    }

    private boolean hasBeanFromContext( final Class<?> target ) {
        return this.applicationContext.getBeanNamesForType( target, false, false ).length > 0;
    }

    private <T> T getBean( Class<T> clazz ) {
        return this.applicationContext.getBean( clazz );
    }

    /*@Bean
    @ConditionalOnMissingBean
    public CamelCaseObjectWrapperFactory getObjectWrapperFactory() {
        return new CamelCaseObjectWrapperFactory();
    }*/

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate( SqlSessionFactory sqlSessionFactory ) {
        ExecutorType executorType = this.properties.getExecutorType();
        if ( executorType != null ) {
            return new SqlSessionTemplate( sqlSessionFactory, executorType );
        } else {
            return new SqlSessionTemplate( sqlSessionFactory );
        }
    }

    /**
     * This will just scan the same base package as Spring Boot does. If you want
     * more power, you can explicitly use
     * {@link org.mybatis.spring.annotation.MapperScan} but this will get typed
     * mappers working correctly, out-of-the-box, similar to using Spring Data JPA
     * repositories.
     */
    public static class AutoConfiguredMapperScannerRegistrar implements BeanFactoryAware,
            ImportBeanDefinitionRegistrar, ResourceLoaderAware {

        private BeanFactory beanFactory;
        private ResourceLoader resourceLoader;

        @Override
        public void registerBeanDefinitions( @Nullable AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry ) {
            log.debug( "Searching for mappers annotated with @Mapper" );
            ClassPathMapperScanner scanner = new ClassPathMapperScanner( registry );
            try {
                if ( this.resourceLoader != null ) {
                    scanner.setResourceLoader( resourceLoader );
                }
                List<String> packages = AutoConfigurationPackages.get( this.beanFactory );
                if ( log.isDebugEnabled() ) {
                    packages.forEach( pkg -> log.debug( "Using auto-configuration base package '{}'", pkg ) );
                }
                scanner.setAnnotationClass( Mapper.class );
                scanner.registerFilters();
                scanner.doScan( StringUtils.toStringArray( packages ) );
            } catch ( IllegalStateException ex ) {
                log.debug( "Could not determine auto-configuration package, automatic mapper scanning disabled.", ex );
            }
        }

        @Override
        public void setBeanFactory( @Nullable BeanFactory beanFactory ) throws BeansException {
            this.beanFactory = beanFactory;
        }

        @Override
        public void setResourceLoader( @Nullable ResourceLoader resourceLoader ) {
            this.resourceLoader = resourceLoader;
        }
    }

    /**
     * {@link org.mybatis.spring.annotation.MapperScan} ultimately ends up
     * creating instances of {@link MapperFactoryBean}. If
     * {@link org.mybatis.spring.annotation.MapperScan} is used then this
     * auto-configuration is not needed. If it is _not_ used, however, then this
     * will bring in a bean registrar and automatically register components based
     * on the same component-scanning path as Spring Boot itself.
     */
    @org.springframework.context.annotation.Configuration
    @Import( { AutoConfiguredMapperScannerRegistrar.class } )
    @ConditionalOnMissingBean( MapperFactoryBean.class )
    public static class MapperScannerRegistrarNotFoundConfiguration {

        @PostConstruct
        public void afterPropertiesSet() {
            log.debug( "No {} found.", MapperFactoryBean.class.getName() );
        }
    }
}
