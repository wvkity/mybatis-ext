package com.wvkity.mybatis.spring.boot.plugin;

import org.apache.ibatis.plugin.Interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 插件注解
 * @author wvkity
 */
@Documented
@Inherited
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

    /**
     * 拦截器类
     * @return 拦截器类
     */
    Class<? extends Interceptor> value();

    /**
     * Bean名称
     * @return bean名称
     */
    String beanName() default "";

    /**
     * 注册顺序
     * @return 序号
     */
    int order() default 0;

}
