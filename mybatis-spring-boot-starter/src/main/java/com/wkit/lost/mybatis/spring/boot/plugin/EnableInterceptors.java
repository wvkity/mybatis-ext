package com.wkit.lost.mybatis.spring.boot.plugin;

import com.wkit.lost.mybatis.plugins.data.auditing.MetadataAuditingInterceptor;
import com.wkit.lost.mybatis.plugins.locking.OptimisticLockingInterceptor;
import com.wkit.lost.mybatis.plugins.paging.PageableInterceptor;
import com.wkit.lost.mybatis.plugins.paging.RangePageableInterceptor;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

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
 *         <li>元数据审计拦截器: {@link MetadataAuditingInterceptor}</li>
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
     * 拦截器列表
     * @return 拦截器列表
     */
    @AliasFor( "interceptors" )
    Plugin[] value() default {
            @Plugin( value = MetadataAuditingInterceptor.class, order = 1 ),
            @Plugin( value = OptimisticLockingInterceptor.class, order = 2 ),
            @Plugin( value = PageableInterceptor.class, order = 3 ),
            @Plugin( value = RangePageableInterceptor.class, order = 4 )
    };

    /**
     * 拦截器列表
     * @return 拦截器列表
     */
    @AliasFor( "value" )
    Plugin[] interceptors() default {
            @Plugin( value = MetadataAuditingInterceptor.class, order = 1 ),
            @Plugin( value = OptimisticLockingInterceptor.class, order = 2 ),
            @Plugin( value = PageableInterceptor.class, order = 3 ),
            @Plugin( value = RangePageableInterceptor.class, order = 4 )
    };
}
