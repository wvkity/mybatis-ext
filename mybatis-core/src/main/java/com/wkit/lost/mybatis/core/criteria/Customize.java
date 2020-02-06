package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.lambda.LambdaConverter;

import java.util.Collection;

/**
 * 自定义条件接口
 * @param <Context> 当前对象
 * @param <R>       Lambda对象
 * @author wvkity
 */
public interface Customize<Context, R> extends LambdaConverter<R> {

    /**
     * 模板条件
     * @param template 模板[需要用{}占位符]
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context template( String template, R property, Object value ) {
        return template( template, lambdaToProperty( property ), value );
    }

    /**
     * 模板条件
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;eg:<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;criteria.template("LEFT({}, 2) = {}", "userName", "张三") &nbsp; =&gt; &nbsp;AND LEFT(USER_NAME, 2) = #{param_0, jdbcType="xx.xxx", javaType="xx.xxx"}
     * </p>
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context template( String template, String property, Object value );

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context template( String template, R property, Object... values ) {
        return template( template, lambdaToProperty( property ), ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context template( String template, String property, Object... values ) {
        return template( template, property, ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context template( String template, R property, Collection<Object> values ) {
        return template( template, lambdaToProperty( property ), values );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    Context template( String template, String property, Collection<Object> values );

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orTemplate( String template, R property, Object value ) {
        return orTemplate( template, lambdaToProperty( property ), value );
    }

    /**
     * 模板条件
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;eg:<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;criteria.orTemplate("LEFT({}, 2) = {}", "userName", "张三") &nbsp; =&gt; &nbsp;OR LEFT(USER_NAME, 2) = #{param_0, jdbcType="xx.xxx", javaType="xx.xxx"}
     * </p>
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context orTemplate( String template, String property, Object value );


    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context orTemplate( String template, R property, Object... values ) {
        return orTemplate( template, lambdaToProperty( property ), ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context orTemplate( String template, String property, Object... values ) {
        return orTemplate( template, property, ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context orTemplate( String template, R property, Collection<Object> values ) {
        return orTemplate( template, lambdaToProperty( property ), values );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    Context orTemplate( String template, String property, Collection<Object> values );

    /**
     * 模板条件
     * <br>
     * <i>注：不受or()或and()方法影响</i>
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;eg:<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;criteria.exactTemplate("AND LEFT({}, 2) = {}", "userName", "张三") &nbsp; =&gt; &nbsp;AND LEFT(USER_NAME, 2) = #{param_0, jdbcType="xx.xxx", javaType="xx.xxx"}
     * </p>
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context exactTemplate( String template, R property, Object value ) {
        return exactTemplate( template, lambdaToProperty( property ), value );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context exactTemplate( String template, String property, Object value );

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context exactTemplate( String template, R property, Object... values ) {
        return exactTemplate( template, lambdaToProperty( property ), ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context exactTemplate( String template, String property, Object... values ) {
        return exactTemplate( template, property, ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context exactTemplate( String template, R property, Collection<Object> values ) {
        return exactTemplate( template, lambdaToProperty( property ), values );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    Context exactTemplate( String template, String property, Collection<Object> values );

    /**
     * 纯SQL条件
     * <p>注：存在SQL注入风险，谨慎使用。如提供的其他条件方法无法满足，请参考template方法，或者采用MyBatis原生XML方式编写</p>
     * @param sql SQL语句
     * @return 当前对象
     * @see #template(String, String, Object)
     * @see #orTemplate(String, String, Object)
     * @see #exactTemplate(String, String, Object)
     */
    Context nativeSql( String sql );
}
