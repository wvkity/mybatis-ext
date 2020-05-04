package com.wvkity.mybatis.spring.boot.plugin;

import com.wvkity.mybatis.spring.boot.plugin.config.AnnotationInterceptorConfiguration;
import com.wvkity.mybatis.spring.boot.plugin.config.InterceptorConfiguration;
import com.wvkity.mybatis.spring.boot.plugin.config.InterceptorDefinition;
import com.wvkity.mybatis.spring.boot.registry.BeanDefinitionRegistrarSupport;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Mybatis拦截器注册器
 * @author wvkity
 */
public class MybatisInterceptorRegistrar extends BeanDefinitionRegistrarSupport<InterceptorConfiguration> {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        Assert.notNull(annotationMetadata, "AnnotationMetadata must not be null!");
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");
        registerBeanConfigurerAspectIfNecessary(registry);
        super.registerBeanDefinitions(annotationMetadata, registry);
    }

    @Override
    protected AbstractBeanDefinition registerBeanDefinition(BeanDefinitionRegistry registry,
                                                            InterceptorConfiguration configuration) {
        List<InterceptorDefinition> plugins = configuration.interceptors();
        if (!plugins.isEmpty()) {
            for (InterceptorDefinition plugin : plugins) {
                createBeanDefinition(registry, configuration, plugin.getBeanName(), plugin.getInterceptor());
            }
        }
        return null;
    }

    @Override
    protected InterceptorConfiguration getConfiguration(AnnotationMetadata annotationMetadata) {
        return new AnnotationInterceptorConfiguration(annotationMetadata, getAnnotation());
    }

    @Override
    protected BeanDefinitionBuilder setRegisterBeanAttributes(InterceptorConfiguration __,
                                                              BeanDefinitionBuilder builder) {
        return builder;
    }

    @Override
    protected String getBeanName() {
        return null;
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableInterceptors.class;
    }
}
