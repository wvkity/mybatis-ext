package com.wvkity.mybatis.annotation;

import com.wvkity.mybatis.annotation.extension.GenerationType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键生成注解类
 * @author wvkity
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GeneratedValue {

    /**
     * 主键生成方式
     * @return {@link GenerationType}
     */
    GenerationType strategy() default GenerationType.AUTO;

    /**
     * 生成器|序列名
     * @return 生成器
     */
    String generator() default "";
}
