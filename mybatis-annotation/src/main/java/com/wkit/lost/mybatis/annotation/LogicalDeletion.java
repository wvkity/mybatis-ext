package com.wkit.lost.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 逻辑删除注解
 * @author wvkity
 */
@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Inherited
public @interface LogicalDeletion {

    /**
     * 是
     * @return 标识已删除值
     */
    String value() default "";

    /**
     * 否
     * @return 标识未删除值
     */
    String not() default "";
}
