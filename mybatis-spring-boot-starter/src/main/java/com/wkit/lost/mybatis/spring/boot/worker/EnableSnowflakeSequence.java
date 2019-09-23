package com.wkit.lost.mybatis.spring.boot.worker;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用雪花算法ID序列
 * @author DT
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Import( { SnowflakeSequenceRegistrar.class } )
@Documented
@Inherited
public @interface EnableSnowflakeSequence {

    /**
     * 是否开启秒级(默认为false)
     * @return true: 是 false: 否
     */
    @AliasFor( "secondEnable" )
    boolean value() default false;

    /**
     * 是否开启秒级(默认为false)
     * @return true: 是 false: 否
     */
    @AliasFor( "value" )
    boolean secondEnable() default false;

    /**
     * 是否通过mac地址自动分配worker、dataCenter
     * @return true: 是 false: 否
     */
    boolean macEnable() default false;
}
