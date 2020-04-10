package com.wkit.lost.mybatis.spring.boot.data.auditing;

import com.wkit.lost.mybatis.plugins.data.auditing.MetadataAuditingInterceptor;
import com.wkit.lost.mybatis.spring.boot.data.auditing.config.AnnotationAuditingConfiguration;
import com.wkit.lost.mybatis.spring.boot.data.auditing.config.AuditingConfiguration;
import com.wkit.lost.mybatis.spring.boot.registry.BeanDefinitionRegistrarSupport;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

/**
 * 元数据审计拦截器注册器
 * @author wvkity
 */
@Order(100)
class MetadataAuditingInterceptorRegistrar extends BeanDefinitionRegistrarSupport<AuditingConfiguration> {

    private static final String INTERCEPTOR_BEAN_NAME = "MetadataAuditingInterceptor";
    private static final Class<?> REGISTER_ROOT_CLASS = MetadataAuditingInterceptor.class;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        Assert.notNull(annotationMetadata, "AnnotationMetadata must not be null!");
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");
        registerBeanConfigurerAspectIfNecessary(registry);
        super.registerBeanDefinitions(annotationMetadata, registry);
    }

    @Override
    protected AbstractBeanDefinition registerBeanDefinition(BeanDefinitionRegistry registry,
                                                            AuditingConfiguration configuration) {
        return createBeanDefinition(registry, configuration, getBeanName(), REGISTER_ROOT_CLASS);
    }

    @Override
    protected AuditingConfiguration getConfiguration(AnnotationMetadata annotationMetadata) {
        return new AnnotationAuditingConfiguration(annotationMetadata, getAnnotation());
    }

    @Override
    protected BeanDefinitionBuilder setRegisterBeanAttributes(AuditingConfiguration configuration,
                                                              BeanDefinitionBuilder builder) {
        return builder;
    }

    @Override
    protected String getBeanName() {
        return INTERCEPTOR_BEAN_NAME;
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableMetadataAuditing.class;
    }
}
