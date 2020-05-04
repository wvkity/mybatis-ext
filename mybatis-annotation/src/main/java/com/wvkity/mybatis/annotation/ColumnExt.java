package com.wvkity.mybatis.annotation;

import com.wvkity.mybatis.annotation.extension.UseJavaType;
import com.wvkity.mybatis.annotation.extension.Validated;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段扩展注解
 * @author wvkity
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ColumnExt {

    /**
     * 字段名
     * @return 字段名
     */
    String column() default "";

    /**
     * 是否为BLOB类型
     * @return boolean
     */
    boolean blob() default false;

    /**
     * JDBC类型
     * @return JDBC类型
     */
    JdbcType jdbcType() default JdbcType.UNDEFINED;

    /**
     * 字符串空值校验
     * @return {@link Validated}
     */
    Validated empty() default Validated.CONFIG;

    /**
     * 使用Java类型
     * @return {@link UseJavaType}
     */
    UseJavaType useJavaType() default UseJavaType.CONFIG;

    /**
     * 类型处理器
     * @return 类型处理器
     */
    Class<? extends TypeHandler<?>> typeHandler() default UnknownTypeHandler.class;
}
