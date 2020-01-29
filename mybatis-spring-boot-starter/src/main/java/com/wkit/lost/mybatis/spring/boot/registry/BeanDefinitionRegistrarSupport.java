package com.wkit.lost.mybatis.spring.boot.registry;

import com.wkit.lost.mybatis.utils.Ascii;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;

public abstract class BeanDefinitionRegistrarSupport<T> implements ImportBeanDefinitionRegistrar {

    protected static final String BEAN_CONFIGURER_ASPECT_CLASS_NAME = "org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect";
    protected static final String BEAN_CONFIGURER_ASPECT_BEAN_NAME = "org.springframework.context.config.internalBeanConfigurerAspect";
    protected static final BeanNameGenerator BEAN_NAME_GENERATOR = new AnnotationBeanNameGenerator();

    @Override
    public void registerBeanDefinitions( AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry ) {
        Assert.notNull( annotationMetadata, "AnnotationMetadata must not be null!" );
        Assert.notNull( registry, "BeanDefinitionRegistry must not be null!" );
        registerBeanDefinition( registry, getConfiguration( annotationMetadata ) );
    }

    /**
     * 注册bean
     * @param registry      注册器
     * @param configuration 属性配置
     * @param beanName      bean名称
     * @param rootClass     bean-class
     * @return bean定义信息
     */
    protected AbstractBeanDefinition createBeanDefinition( BeanDefinitionRegistry registry, T configuration,
                                                           String beanName, Class<?> rootClass ) {
        Assert.notNull( registry, "BeanDefinitionRegistry must not be null!" );
        Assert.notNull( configuration, "configuration must not be null!" );
        AbstractBeanDefinition beanDefinition =
                setRegisterBeanAttributes( configuration, createRootBeanDefinitionBuilder( rootClass ) ).getBeanDefinition();
        registry.registerBeanDefinition( getBeanName( registry, beanDefinition, beanName ), beanDefinition );
        return beanDefinition;
    }

    /**
     * 创建bean构建器
     * @param rootClass 待注册bean class
     * @return bean构建器
     */
    protected BeanDefinitionBuilder createRootBeanDefinitionBuilder( Class<?> rootClass ) {
        Assert.notNull( rootClass, "Root class must not be null!" );
        return BeanDefinitionBuilder.rootBeanDefinition( rootClass );
    }

    /**
     * 获取bean名
     * @param registry       bean注册器
     * @param beanDefinition bean定义信息
     * @param beanName       bean名
     * @return 实例名
     */
    protected String getBeanName( BeanDefinitionRegistry registry, BeanDefinition beanDefinition, String beanName ) {
        if ( Ascii.isNullOrEmpty( beanName ) ) {
            return BEAN_NAME_GENERATOR.generateBeanName( beanDefinition, registry );
        }
        return beanName;
    }

    /**
     * 创建引用bean资源信息
     * @param beanAwareRef 引用bean
     * @return 引用bean定义信息
     */
    protected BeanDefinition createLazyInitTargetSourceBeanDefinition( String beanAwareRef ) {
        BeanDefinitionBuilder targetSourceBuilder = BeanDefinitionBuilder.rootBeanDefinition( LazyInitTargetSource.class );
        targetSourceBuilder.addPropertyValue( "targetBeanName", beanAwareRef );
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition( ProxyFactoryBean.class );
        builder.addPropertyValue( "targetSource", targetSourceBuilder.getBeanDefinition() );
        return builder.getBeanDefinition();
    }

    /**
     * @param registry, the {@link BeanDefinitionRegistry} to be used to register the
     *                  {@link org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect}.
     */
    protected void registerBeanConfigurerAspectIfNecessary( BeanDefinitionRegistry registry ) {

        if ( registry.containsBeanDefinition( BEAN_CONFIGURER_ASPECT_BEAN_NAME ) ) {
            return;
        }

        if ( !ClassUtils.isPresent( BEAN_CONFIGURER_ASPECT_CLASS_NAME, getClass().getClassLoader() ) ) {
            throw new BeanDefinitionStoreException( BEAN_CONFIGURER_ASPECT_CLASS_NAME + " not found. \n"
                    + "Could not configure Spring Data JPA auditing-feature because"
                    + " spring-aspects.jar is not on the classpath!\n"
                    + "If you want to use auditing please add spring-aspects.jar to the classpath." );
        }

        RootBeanDefinition def = new RootBeanDefinition();
        def.setBeanClassName( BEAN_CONFIGURER_ASPECT_CLASS_NAME );
        def.setFactoryMethodName( "aspectOf" );
        def.setRole( BeanDefinition.ROLE_INFRASTRUCTURE );

        registry.registerBeanDefinition( BEAN_CONFIGURER_ASPECT_BEAN_NAME,
                new BeanComponentDefinition( def, BEAN_CONFIGURER_ASPECT_BEAN_NAME ).getBeanDefinition() );
    }

    /**
     * 注册bean
     * @param registry      注册器
     * @param configuration 属性配置
     * @return bean定义信息
     */
    protected abstract AbstractBeanDefinition registerBeanDefinition( BeanDefinitionRegistry registry, T configuration );

    /**
     * 获取属性配置
     * @param annotationMetadata 注解元数据
     * @return 属性配置对象
     */
    protected abstract T getConfiguration( AnnotationMetadata annotationMetadata );

    /**
     * 设置bean属性
     * @param configuration 属性配置对象
     * @param builder       构建器
     * @return bean构建器
     */
    protected abstract BeanDefinitionBuilder setRegisterBeanAttributes( T configuration, BeanDefinitionBuilder builder );

    /**
     * 注册Bean名称
     * @return 实例名称
     */
    protected abstract String getBeanName();

    /**
     * 根据指定注解获取对应配置信息
     * @return 注解对象
     */
    protected abstract Class<? extends Annotation> getAnnotation();
}
