package com.wvkity.mybatis.annotation.naming;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命名方式注解类
 * <p>默认采用驼峰转下划线大写</p>
 * @author wvkity
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Naming {

    /**
     * 命名方式
     * @return {@link NamingStrategy}
     */
    NamingStrategy value() default NamingStrategy.CAMEL_HUMP_UPPERCASE;
}
