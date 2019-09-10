package com.wkit.lost.mybatis.annotation;

import com.wkit.lost.mybatis.annotation.extension.Dialect;
import com.wkit.lost.mybatis.annotation.extension.Executing;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自增策略
 * @author DT
 */
@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Inherited
public @interface Identity {

    /**
     * 使用JDBC方式获取(优先级最高)
     * @return boolean
     */
    boolean useJdbcGenerated() default false;

    /**
     * 数据库类型
     * @return {@link Dialect}
     */
    Dialect dialect() default Dialect.UNDEFINED;

    /**
     * 根据SQL获取主键值
     * @return SQL
     */
    String identitySql() default "";

    /**
     * 获取主键SQL执行时机
     * @return {@link Executing}
     */
    Executing execution() default Executing.DEFAULT;
}
