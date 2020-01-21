package com.wkit.lost.mybatis.spring.boot.registry;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;

public abstract class BeanDefinitionRegistrarSupport<T> implements ImportBeanDefinitionRegistrar {

    protected static final String BEAN_CONFIGURER_ASPECT_CLASS_NAME = "org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect";
    protected static final String BEAN_CONFIGURER_ASPECT_BEAN_NAME = "org.springframework.context.config.internalBeanConfigurerAspect";

    @Override
    public void registerBeanDefinitions( AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry ) {
        Assert.notNull( annotationMetadata, "AnnotationMetadata must not be null!" );
        Assert.notNull( registry, "BeanDefinitionRegistry must not be null!" );
        AbstractBeanDefinition beanDefinition = registerHandlerBeanDefinition( registry, getConfiguration( annotationMetadata ) );
        registerHandlerListenerBeanDefinition( beanDefinition, registry );
    }

    private AbstractBeanDefinition registerHandlerBeanDefinition( BeanDefinitionRegistry registry, T configuration ) {
        Assert.notNull( registry, "BeanDefinitionRegistry must not be null!" );
        Assert.notNull( configuration, "configuration must not be null!" );
        AbstractBeanDefinition beanDefinition = getHandlerBeanDefinitionBuilder( configuration ).getBeanDefinition();
        registry.registerBeanDefinition( getHandlerBeanName(), beanDefinition );
        return beanDefinition;
    }
    
    protected BeanDefinition createLazyInitTargetSourceBeanDefinition(String beanAwareRef) {
        BeanDefinitionBuilder targetSourceBuilder = BeanDefinitionBuilder.rootBeanDefinition( LazyInitTargetSource.class );
        targetSourceBuilder.addPropertyValue( "targetBeanName", beanAwareRef );
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition( ProxyFactoryBean.class );
        builder.addPropertyValue( "targetSource", targetSourceBuilder.getBeanDefinition() );
        return builder.getBeanDefinition();
    }

    protected void registerHandlerListenerBeanDefinition( BeanDefinition handlerDefinition,
                                                          BeanDefinitionRegistry registry ) {
        // EMPTY
    }

    /**
     * @param registry, the {@link BeanDefinitionRegistry} to be used to register the
     *          {@link org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect}.
     */
    protected void registerBeanConfigurerAspectIfNecessary(BeanDefinitionRegistry registry) {

        if (registry.containsBeanDefinition(BEAN_CONFIGURER_ASPECT_BEAN_NAME)) {
            return;
        }

        if (!ClassUtils.isPresent(BEAN_CONFIGURER_ASPECT_CLASS_NAME, getClass().getClassLoader())) {
            throw new BeanDefinitionStoreException(BEAN_CONFIGURER_ASPECT_CLASS_NAME + " not found. \n"
                    + "Could not configure Spring Data JPA auditing-feature because"
                    + " spring-aspects.jar is not on the classpath!\n"
                    + "If you want to use auditing please add spring-aspects.jar to the classpath.");
        }

        RootBeanDefinition def = new RootBeanDefinition();
        def.setBeanClassName(BEAN_CONFIGURER_ASPECT_CLASS_NAME);
        def.setFactoryMethodName("aspectOf");
        def.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        registry.registerBeanDefinition(BEAN_CONFIGURER_ASPECT_BEAN_NAME,
                new BeanComponentDefinition(def, BEAN_CONFIGURER_ASPECT_BEAN_NAME).getBeanDefinition());
    }

    protected abstract BeanDefinitionBuilder getHandlerBeanDefinitionBuilder( T configuration );

    protected abstract T getConfiguration( AnnotationMetadata annotationMetadata );

    protected abstract BeanDefinitionBuilder defaultHandlerAttributes( T configuration, BeanDefinitionBuilder builder );

    protected abstract String getHandlerBeanName();

    /**
     * 根据指定注解获取对应配置信息
     * @return 注解对象
     */
    protected abstract Class<? extends Annotation> getAnnotation();
}
