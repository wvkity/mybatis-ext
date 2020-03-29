package com.wkit.lost.mybatis.core.conditional;

import com.wkit.lost.mybatis.core.conditional.expression.Equal;
import com.wkit.lost.mybatis.core.conditional.expression.IdEqual;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateEqual;
import com.wkit.lost.mybatis.core.conditional.expression.NotEqual;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.lambda.Property;

/**
 * 条件工具类
 * @author wvkity
 */
public final class Restrictions {

    private Restrictions() {
    }

    /**
     * ID等于
     * @param criteria 条件包装对象
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> IdEqual<T> idEq( Criteria<T> criteria, Object value ) {
        return IdEqual.create( criteria, value );
    }

    /**
     * ID等于
     * @param criteria 条件包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> IdEqual<T> idEq( Criteria<T> criteria, Object value, Logic logic ) {
        return IdEqual.create( criteria, value, logic );
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return eq( criteria, property, value, Logic.AND );
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( Criteria<T> criteria, String property, Object value ) {
        return eq( criteria, property, value, Logic.AND );
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        return Equal.create( criteria, property, value, logic );
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return Equal.create( criteria, property, value, logic );
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( String column, Object value ) {
        return immediateEq( column, value, Logic.AND );
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( String column, Object value, Logic logic ) {
        return ImmediateEqual.create( column, value, logic );
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( String tableAlias, String column, Object value ) {
        return immediateEq( tableAlias, column, value, Logic.AND );
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( String tableAlias, String column, Object value, Logic logic ) {
        return ImmediateEqual.create( tableAlias, column, value, logic );
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( Criteria<T> criteria, String column, Object value ) {
        return immediateEq( criteria, column, value, Logic.AND );
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( Criteria<T> criteria, String column, Object value, Logic logic ) {
        return ImmediateEqual.create( criteria, column, value, logic );
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return ne( criteria, property, value, Logic.AND );
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        return NotEqual.create( criteria, property, value, logic );
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( Criteria<T> criteria, String property, Object value ) {
        return ne( criteria, property, value, Logic.AND );
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return NotEqual.create( criteria, property, value, logic );
    }
}
