package com.wvkity.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解
 * @author wvkity
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Column {

    /**
     * 字段名
     * @return 字段名
     */
    String name() default "";

    /**
     * 执行insert操作的时候是否包含此字段
     * @return boolean
     */
    boolean insertable() default true;

    /**
     * 执行updatable操作的时候是否包含此字段
     * @return boolean
     */
    boolean updatable() default true;
}
