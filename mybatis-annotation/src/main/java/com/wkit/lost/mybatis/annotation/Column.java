package com.wkit.lost.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解
 * @author DT
 */
@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Inherited
public @interface Column {

    /**
     * 字段
     * @return 字段名
     */
    String name() default "";

    /**
     * 是否可插入
     * @return boolean
     */
    boolean insertable() default true;

    /**
     * 是否可更新
     * @return boolean
     */
    boolean updatable() default true;
}
