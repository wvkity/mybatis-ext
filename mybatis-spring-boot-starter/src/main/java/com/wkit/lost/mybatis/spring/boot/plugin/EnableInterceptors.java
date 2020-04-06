package com.wkit.lost.mybatis.spring.boot.plugin;

import com.wkit.lost.mybatis.plugins.locking.OptimisticLockingInterceptor;
import com.wkit.lost.mybatis.plugins.paging.PageableInterceptor;
import com.wkit.lost.mybatis.plugins.paging.RangePageableInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用系统默认提供插件注解
 * <p>
 * <em>默认注册以下拦截器: </em>
 *     <ul>
 *         <li>乐观锁拦截器: {@link OptimisticLockingInterceptor}</li>
 *         <li>常规分页拦截器: {@link PageableInterceptor}</li>
 *         <li>范围分页拦截器: {@link RangePageableInterceptor}</li>
 *     </ul>
 * </p>
 * @author wvkity
 * @see MybatisInterceptorRegistrar
 */
@Documented
@Inherited
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Import( { MybatisInterceptorRegistrar.class } )
public @interface EnableInterceptors {

    /**
     * 默认拦截器列表
     * @return 拦截器列表
     */
    Plugin[] value() default {
            @Plugin( value = OptimisticLockingInterceptor.class, order = 24 ),
            @Plugin( value = PageableInterceptor.class, order = 25 ),
            @Plugin( value = RangePageableInterceptor.class, order = 26 )
    };

    /**
     * 拦截器列表(其他拦截器)
     * @return 拦截器列表
     */
    Plugin[] interceptors() default {};
}
