package com.wvkity.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键注解类
 * @author wvkity
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Id {

    /**
     * prefix别名{@link #prefix()}
     * @return 前缀
     */
    String value() default "";

    /**
     * 主键前缀
     * @return 前缀
     */
    String prefix() default "";
}
