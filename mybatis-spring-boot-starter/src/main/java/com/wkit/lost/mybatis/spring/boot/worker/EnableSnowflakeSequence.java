package com.wkit.lost.mybatis.spring.boot.worker;

import com.wkit.lost.mybatis.snowflake.sequence.Level;
import com.wkit.lost.mybatis.snowflake.sequence.Mode;
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
 * @author wvkity
 * @see SnowflakeSequenceRegistrar
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Import( { SnowflakeSequenceRegistrar.class } )
@Documented
@Inherited
public @interface EnableSnowflakeSequence {

    /**
     * 注册的beanName
     * @return bean名称
     */
    String name() default "";

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
     * 启用级别
     * @return 级别
     */
    Level level() default Level.UNKNOWN;

    /**
     * 模式
     * @return 模式
     */
    Mode mode() default Mode.UNKNOWN;

}
