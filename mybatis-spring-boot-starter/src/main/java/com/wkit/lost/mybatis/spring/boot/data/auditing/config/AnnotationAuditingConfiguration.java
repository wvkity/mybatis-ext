package com.wkit.lost.mybatis.spring.boot.data.auditing.config;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Map;

public class AnnotationAuditingConfiguration implements AuditingConfiguration {

    private static final String MISSING_ANNOTATION_ATTRIBUTES = "Couldn't find annotation attributes for %s in %s!";

    private final AnnotationAttributes attributes;

    public AnnotationAuditingConfiguration(AnnotationMetadata metadata, Class<? extends Annotation> annotation) {
        Assert.notNull(metadata, "AnnotationMetadata must not be null!");
        Assert.notNull(annotation, "Annotation must not be null!");
        Map<String, Object> attributesSource = metadata.getAnnotationAttributes(annotation.getName());

        if (attributesSource == null) {
            throw new IllegalArgumentException(String.format(MISSING_ANNOTATION_ATTRIBUTES, annotation, metadata));
        }

        this.attributes = new AnnotationAttributes(attributesSource);
    }

    @Override
    public boolean enableInserted() {
        return attributes.getBoolean("inserted");
    }

    @Override
    public boolean enableModified() {
        return attributes.getBoolean("modified");
    }

    @Override
    public boolean enableDeleted() {
        return attributes.getBoolean("deleted");
    }
    
    @Override
    public String getAuditorAwareRef() {
        return attributes.getString("auditorAwareRef");
    }
}
