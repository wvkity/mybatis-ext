package com.wkit.lost.mybatis.spring.boot.plugin;

import com.wkit.lost.mybatis.config.Plugin;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用系统默认提供的插件
 * @see MyBatisInterceptorRegistrar
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Import( { MyBatisInterceptorRegistrar.class } )
@Documented
@Inherited
public @interface EnableInterceptors {

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
     * 待注册插件列表
     * @return 插件列表
     */
    Plugin[] include() default { Plugin.META_DATA_AUDIT, Plugin.OPTIMISTIC_LOCKER,
            Plugin.PAGEABLE, Plugin.LIMIT };

    /**
     * 过滤无需注册的插件
     * @return 插件列表
     */
    Plugin[] exclude() default {};
    
}
