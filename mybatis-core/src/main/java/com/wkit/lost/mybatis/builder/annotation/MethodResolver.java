package com.wkit.lost.mybatis.builder.annotation;

import java.lang.reflect.Method;

public class MethodResolver extends org.apache.ibatis.builder.annotation.MethodResolver {

    private final MapperAnnotationBuilder annotationBuilder;
    private final Method method;

    public MethodResolver( org.apache.ibatis.builder.annotation.MapperAnnotationBuilder annotationBuilder, Method method ) {
        super( annotationBuilder, method );
        this.annotationBuilder = ( MapperAnnotationBuilder ) annotationBuilder;
        this.method = method;
    }

    @Override
    public void resolve() {
        annotationBuilder.parseStatement( method );
    }
}
