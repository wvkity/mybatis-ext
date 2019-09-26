package com.wkit.lost.mybatis.spring.boot.filling;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启自动填充值
 * <p>交由拦截器处理</p>
 * @author DT
 * @see MateObjectAutoFillingRegistrar
 * @see com.wkit.lost.mybatis.plugins.interceptor.MetaObjectFillingInterceptor
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Import( { MateObjectAutoFillingRegistrar.class } )
@Documented
@Inherited
public @interface EnableMetaObjectFilling {

    /**
     * 是否开启@Primary(默认为false)
     * @return true: 是 false: 否
     */
    @AliasFor( "primary" )
    boolean value() default false;

    /**
     * 是否开启@Primary
     * @return true: 是 false: 否
     * @see org.springframework.context.annotation.Primary
     */
    @AliasFor( "value" )
    boolean primary() default false;

    /**
     * 是否开启自动匹配模式
     * @return true: 是 false: 否
     */
    boolean autoMatching() default true;

    /**
     * 是否开启保存操作自动填充值
     * @return true: 是 false: 否
     */
    boolean insert() default true;

    /**
     * 是否开启更新操作自动填充值
     * @return true: 是 false: 否
     */
    boolean update() default true;

    /**
     * 是否开启逻辑删除操作自动填充值
     * @return true: 是 false: 否
     */
    boolean delete() default true;

}
