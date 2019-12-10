package com.wkit.lost.mybatis.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 包含多个{@link MapperScan}注解的容器注解
 * @author wvkity
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Inherited
@Import( MapperScannerRegistrar.RepeatingScannerRegistrar.class )
public @interface MapperScans {

    /**
     * 接口扫描器
     * @return 扫描器列表
     */
    MapperScan[] value() default {};
}
