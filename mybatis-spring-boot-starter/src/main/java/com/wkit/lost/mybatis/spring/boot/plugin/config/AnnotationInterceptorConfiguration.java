package com.wkit.lost.mybatis.spring.boot.plugin.config;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Mybatis拦截器注解配置
 * @author wvkity
 */
public class AnnotationInterceptorConfiguration implements InterceptorConfiguration {

    private static final String MISSING_ANNOTATION_ATTRIBUTES = "Couldn't find annotation attributes for %s in %s!";
    private static final String PLUGIN_ANNOTATION_KEY = "interceptors";
    private static final String PLUGIN_VALUE = "value";
    private static final String PLUGIN_BEAN_NAME = "beanName";
    private static final String PLUGIN_ORDER = "order";

    private final AnnotationAttributes attributes;

    public AnnotationInterceptorConfiguration( AnnotationMetadata metadata, Class<? extends Annotation> annotation ) {
        Map<String, Object> attributesSource = metadata.getAnnotationAttributes( annotation.getName() );

        if ( attributesSource == null ) {
            throw new IllegalArgumentException( String.format( MISSING_ANNOTATION_ATTRIBUTES, annotation, metadata ) );
        }

        this.attributes = new AnnotationAttributes( attributesSource );
    }

    @Override
    public List<InterceptorDefinition> interceptors() {
        List<InterceptorDefinition> interceptors = new ArrayList<>();
        // 默认拦截器
        AnnotationAttributes[] defaults = attributes.getAnnotationArray( PLUGIN_VALUE );
        if ( !ArrayUtil.isEmpty( defaults ) ) {
            Set<InterceptorDefinition> plugins = new HashSet<>();
            for ( AnnotationAttributes attribute : defaults ) {
                plugins.add( new InterceptorDefinition( attribute.getClass( PLUGIN_VALUE ),
                        attribute.getString( PLUGIN_BEAN_NAME ), ( Integer ) attribute.get( PLUGIN_ORDER ) ) );
            }
            interceptors.addAll( plugins );
        }
        // 其他拦截器
        AnnotationAttributes[] others = attributes.getAnnotationArray( PLUGIN_ANNOTATION_KEY );
        if ( !ArrayUtil.isEmpty( others ) ) {
            Set<InterceptorDefinition> plugins = new HashSet<>();
            for ( AnnotationAttributes attribute : others ) {
                plugins.add( new InterceptorDefinition( attribute.getClass( PLUGIN_VALUE ),
                        attribute.getString( PLUGIN_BEAN_NAME ), ( Integer ) attribute.get( PLUGIN_ORDER ) ) );
            }
            interceptors.addAll( plugins );
        }
        if ( !interceptors.isEmpty() ) {
            interceptors.sort( Comparator.comparingInt( InterceptorDefinition::getOrder ) );
        }
        return interceptors;
    }
}
