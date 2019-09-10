package com.wkit.lost.mybatis.spring.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

public class MapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor,
        InitializingBean, ApplicationContextAware, BeanNameAware {

    private String basePackage;

    private boolean addToConfig = true;

    private SqlSessionFactory sqlSessionFactory;

    private SqlSessionTemplate sqlSessionTemplate;

    private String sqlSessionFactoryBeanName;

    private String sqlSessionTemplateBeanName;

    private Class<? extends Annotation> annotationClass;

    private Class<?> markerInterface;

    private Class<? extends MapperFactoryBean> mapperFactoryBeanClass;

    private ApplicationContext applicationContext;

    private String beanName;

    private boolean processPropertyPlaceHolders;

    private BeanNameGenerator nameGenerator;

    //private MapperProcessor mapperProcessor = new MapperProcessor();

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanFactory( ConfigurableListableBeanFactory beanFactory ) throws BeansException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanDefinitionRegistry( BeanDefinitionRegistry registry ) throws BeansException {
        if ( this.processPropertyPlaceHolders ) {
            processPropertyPlaceHolders();
        }
        ClassPathMapperScanner scanner = new ClassPathMapperScanner( registry );
        scanner.setAddToConfig(this.addToConfig);
        scanner.setAnnotationClass(this.annotationClass);
        scanner.setMarkerInterface(this.markerInterface);
        scanner.setSqlSessionFactory(this.sqlSessionFactory);
        scanner.setSqlSessionTemplate(this.sqlSessionTemplate);
        scanner.setSqlSessionFactoryBeanName(this.sqlSessionFactoryBeanName);
        scanner.setSqlSessionTemplateBeanName(this.sqlSessionTemplateBeanName);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setBeanNameGenerator(this.nameGenerator);
        scanner.setMapperFactoryBeanClass(this.mapperFactoryBeanClass);
        scanner.registerFilters();
        // 泛型Mapper接口处理器
        //scanner.setMapperProcessor( this.mapperProcessor );
        scanner.scan( StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    private void processPropertyPlaceHolders() {
        Map<String, PropertyResourceConfigurer> prcs = applicationContext.getBeansOfType(PropertyResourceConfigurer.class);

        if (!prcs.isEmpty() && applicationContext instanceof ConfigurableApplicationContext) {
            BeanDefinition mapperScannerBean = ((ConfigurableApplicationContext) applicationContext)
                    .getBeanFactory().getBeanDefinition(beanName);

            DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
            factory.registerBeanDefinition(beanName, mapperScannerBean);

            for (PropertyResourceConfigurer prc : prcs.values()) {
                prc.postProcessBeanFactory(factory);
            }

            PropertyValues values = mapperScannerBean.getPropertyValues();

            this.basePackage = updatePropertyValue("basePackage", values);
            this.sqlSessionFactoryBeanName = updatePropertyValue("sqlSessionFactoryBeanName", values);
            this.sqlSessionTemplateBeanName = updatePropertyValue("sqlSessionTemplateBeanName", values);
        }
    }

    private String updatePropertyValue(String propertyName, PropertyValues values) {
        PropertyValue property = values.getPropertyValue(propertyName);

        if (property == null) {
            return null;
        }

        Object value = property.getValue();

        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return value.toString();
        } else if (value instanceof TypedStringValue ) {
            return ((TypedStringValue) value).getValue();
        } else {
            return null;
        }
    }

    public void setBasePackage( String basePackage ) {
        this.basePackage = basePackage;
    }

    public void setAddToConfig( boolean addToConfig ) {
        this.addToConfig = addToConfig;
    }

    public void setSqlSessionFactory( SqlSessionFactory sqlSessionFactory ) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void setSqlSessionTemplate( SqlSessionTemplate sqlSessionTemplate ) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public void setSqlSessionFactoryBeanName( String sqlSessionFactoryBeanName ) {
        this.sqlSessionFactoryBeanName = sqlSessionFactoryBeanName;
    }

    public void setSqlSessionTemplateBeanName( String sqlSessionTemplateBeanName ) {
        this.sqlSessionTemplateBeanName = sqlSessionTemplateBeanName;
    }

    public void setAnnotationClass( Class<? extends Annotation> annotationClass ) {
        this.annotationClass = annotationClass;
    }

    public void setMarkerInterface( Class<?> markerInterface ) {
        this.markerInterface = markerInterface;
    }

    public void setMapperFactoryBeanClass( Class<? extends MapperFactoryBean> mapperFactoryBeanClass ) {
        this.mapperFactoryBeanClass = mapperFactoryBeanClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) {
        this.applicationContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanName( String beanName ) {
        this.beanName = beanName;
    }

    public void setProcessPropertyPlaceHolders( boolean processPropertyPlaceHolders ) {
        this.processPropertyPlaceHolders = processPropertyPlaceHolders;
    }

    public void setNameGenerator( BeanNameGenerator nameGenerator ) {
        this.nameGenerator = nameGenerator;
    }
}
