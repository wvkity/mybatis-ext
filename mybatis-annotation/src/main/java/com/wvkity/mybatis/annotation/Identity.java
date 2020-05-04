package com.wvkity.mybatis.annotation;

import com.wvkity.mybatis.annotation.extension.Dialect;
import com.wvkity.mybatis.annotation.extension.Executing;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自增策略
 * @author wvkity
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Identity {

    /**
     * 使用JDBC方式获取(优先级最高)
     * @return boolean
     */
    boolean useJdbc() default false;

    /**
     * 数据库类型
     * @return {@link Dialect}
     */
    Dialect dialect() default Dialect.UNDEFINED;

    /**
     * 根据SQL获取主键值
     * @return SQL
     */
    String sql() default "";

    /**
     * 获取主键SQL执行时机
     * @return {@link Executing}
     */
    Executing executing() default Executing.NONE;
}
