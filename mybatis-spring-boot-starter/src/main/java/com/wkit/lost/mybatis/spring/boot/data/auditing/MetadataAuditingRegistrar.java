package com.wkit.lost.mybatis.spring.boot.data.auditing;

import com.wkit.lost.mybatis.core.data.auditing.MetadataAuditingHandler;
import com.wkit.lost.mybatis.spring.boot.data.auditing.config.AnnotationAuditingConfiguration;
import com.wkit.lost.mybatis.spring.boot.data.auditing.config.AuditingConfiguration;
import com.wkit.lost.mybatis.spring.boot.registry.BeanDefinitionRegistrarSupport;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * 元数据审计处理器注册器
 * @author wvkity
 */
@Order(101)
class MetadataAuditingRegistrar extends BeanDefinitionRegistrarSupport<AuditingConfiguration> {

    private static final String AUDITOR_AWARE = "auditorAware";
    private static final String INSERTED = "inserted";
    private static final String MODIFIED = "modified";
    private static final String DELETED = "deleted";
    private static final String AUTOMATIC = "automatic";
    private static final String HANDLER_BEAN_NAME = "metadataAuditingHandler";
    private static final Class<?> REGISTER_ROOT_CLASS = MetadataAuditingHandler.class;

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
        if (StringUtils.hasText(configuration.getAuditorAwareRef())) {
            builder.addPropertyValue(AUDITOR_AWARE,
                    createLazyInitTargetSourceBeanDefinition(configuration.getAuditorAwareRef()));
        } else {
            builder.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE);
        }
        builder.addPropertyValue(INSERTED, configuration.enableInserted());
        builder.addPropertyValue(MODIFIED, configuration.enableModified());
        builder.addPropertyValue(DELETED, configuration.enableDeleted());
        builder.addPropertyValue(AUTOMATIC, configuration.enableAutomatic());
        return builder;
    }

    @Override
    protected String getBeanName() {
        return HANDLER_BEAN_NAME;
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableMetadataAuditing.class;
    }
}
