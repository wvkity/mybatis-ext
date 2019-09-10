package com.wkit.lost.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类注解
 * @author DT
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Inherited
public @interface Table {

    /**
     * 表名[默认类名]
     * @return 表名
     */
    String name() default "";

    /**
     * 数据库目录[数据库]
     * @return 目录
     */
    String catalog() default "";

    /**
     * 模式
     * @return 模式
     */
    String schema() default "";

    /**
     * 表名前缀
     * @return 字符串
     */
    String prefix() default "";
    
}
