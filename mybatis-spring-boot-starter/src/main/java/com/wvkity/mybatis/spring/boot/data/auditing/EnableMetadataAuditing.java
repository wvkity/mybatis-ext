package com.wvkity.mybatis.spring.boot.data.auditing;

import com.wvkity.mybatis.core.data.auditing.AuditorAware;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启元数据审计注解
 * <ul>
 *     <li>注入默认元数据审计处理器</li>
 *     <li>注入默认元数据审计拦截器</li>
 * </ul>
 * @author wvkity
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MetadataAuditingRegistrar.class, MetadataAuditingInterceptorRegistrar.class})
public @interface EnableMetadataAuditing {

    /**
     * 开启保存操作审计
     * @return true: 开启 false: 关闭
     */
    boolean inserted() default true;

    /**
     * 开启更新操作审计
     * @return true: 开启 false: 关闭
     */
    boolean modified() default true;

    /**
     * 逻辑删除操作审计
     * @return true: 开启 false: 关闭
     */
    boolean deleted() default true;

    /**
     * {@link AuditorAware}实例名
     * @return {@link AuditorAware}实例名
     */
    String auditorAwareRef() default "";
}
