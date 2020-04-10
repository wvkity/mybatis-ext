package com.wkit.lost.mybatis.builder.annotation;

import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.builder.annotation.MethodResolver;

import java.lang.reflect.Method;

public class MyBatisMethodResolver extends MethodResolver {

    private final MyBatisMapperAnnotationBuilder annotationBuilder;
    private final Method method;

    public MyBatisMethodResolver(MapperAnnotationBuilder annotationBuilder, Method method) {
        super(annotationBuilder, method);
        this.annotationBuilder = (MyBatisMapperAnnotationBuilder) annotationBuilder;
        this.method = method;
    }

    @Override
    public void resolve() {
        annotationBuilder.parseStatement(method);
    }
}
