package com.wkit.lost.mybatis.spring.boot.data.auditing;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启元数据审计注解
 * <p>注入默认元数据审计处理器</p>
 * @author wvkity
 */
@Documented
@Inherited
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Import( { MetadataAuditingRegistrar.class } )
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
     * 删除更新操作审计
     * @return true: 开启 false: 关闭
     */
    boolean deleted() default true;

    /**
     * 开启自动审计识别
     * @return true: 开启 false: 关闭
     */
    boolean automatic() default false;

    /**
     * {@link com.wkit.lost.mybatis.core.data.auditing.AuditorAware}实例名
     * @return {@link com.wkit.lost.mybatis.core.data.auditing.AuditorAware}实例名
     */
    String auditorAwareRef() default "";
}
