package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.lambda.LambdaConverter;

/**
 * 子查询条件接口
 * @param <Chain> 子类
 * @param <P>     Lambda类
 * @author wvkity
 */
public interface SubQueryWrapper<Chain extends SubQueryWrapper<Chain, P>, P> extends LambdaConverter<P> {

    /**
     * 主键等于
     * @param sc 子查询条件包装对象
     * @return {@code this}
     */
    Chain idEq(SubCriteria<?> sc);

    /**
     * 或主键等于
     * @param sc 子查询条件包装对象
     * @return {@code this}
     */
    Chain orIdEq(SubCriteria<?> sc);

    /**
     * 等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain eq(P property, SubCriteria<?> sc) {
        return eq(lambdaToProperty(property), sc);
    }

    /**
     * 等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain eq(String property, SubCriteria<?> sc);

    /**
     * 或等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orEq(P property, SubCriteria<?> sc) {
        return orEq(lambdaToProperty(property), sc);
    }

    /**
     * 或等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orEq(String property, SubCriteria<?> sc);

    /**
     * 不等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain ne(P property, SubCriteria<?> sc) {
        return ne(lambdaToProperty(property), sc);
    }

    /**
     * 不等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain ne(String property, SubCriteria<?> sc);

    /**
     * 或不等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orNe(P property, SubCriteria<?> sc) {
        return orNe(lambdaToProperty(property), sc);
    }

    /**
     * 或不等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orNe(String property, SubCriteria<?> sc);

    /**
     * 小于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain lt(P property, SubCriteria<?> sc) {
        return lt(lambdaToProperty(property), sc);
    }

    /**
     * 小于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain lt(String property, SubCriteria<?> sc);

    /**
     * 或小于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orLt(P property, SubCriteria<?> sc) {
        return orLt(lambdaToProperty(property), sc);
    }

    /**
     * 或小于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orLt(String property, SubCriteria<?> sc);

    /**
     * 小于等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain le(P property, SubCriteria<?> sc) {
        return le(lambdaToProperty(property), sc);
    }

    /**
     * 小于等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain le(String property, SubCriteria<?> sc);

    /**
     * 或小于等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orLe(P property, SubCriteria<?> sc) {
        return orLe(lambdaToProperty(property), sc);
    }

    /**
     * 或小于等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orLe(String property, SubCriteria<?> sc);

    /**
     * 大于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain gt(P property, SubCriteria<?> sc) {
        return gt(lambdaToProperty(property), sc);
    }

    /**
     * 大于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain gt(String property, SubCriteria<?> sc);

    /**
     * 或大于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orGt(P property, SubCriteria<?> sc) {
        return orGt(lambdaToProperty(property), sc);
    }

    /**
     * 或大于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orGt(String property, SubCriteria<?> sc);

    /**
     * 大于等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain ge(P property, SubCriteria<?> sc) {
        return ge(lambdaToProperty(property), sc);
    }

    /**
     * 大于等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain ge(String property, SubCriteria<?> sc);

    /**
     * 或大于等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orGe(P property, SubCriteria<?> sc) {
        return orGe(lambdaToProperty(property), sc);
    }

    /**
     * 或大于等于
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orGe(String property, SubCriteria<?> sc);

    /**
     * LIKE
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain like(P property, SubCriteria<?> sc) {
        return like(lambdaToProperty(property), sc);
    }

    /**
     * LIKE
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain like(String property, SubCriteria<?> sc);

    /**
     * 或LIKE
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orLike(P property, SubCriteria<?> sc) {
        return orLike(lambdaToProperty(property), sc);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orLike(String property, SubCriteria<?> sc);

    /**
     * NOT LIKE
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain notLike(P property, SubCriteria<?> sc) {
        return notLike(lambdaToProperty(property), sc);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain notLike(String property, SubCriteria<?> sc);

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orNotLike(P property, SubCriteria<?> sc) {
        return orNotLike(lambdaToProperty(property), sc);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orNotLike(String property, SubCriteria<?> sc);
    
    /**
     * IN
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain in(P property, SubCriteria<?> sc) {
        return in(lambdaToProperty(property), sc);
    }

    /**
     * IN
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain in(String property, SubCriteria<?> sc);

    /**
     * 或IN
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orIn(P property, SubCriteria<?> sc) {
        return orIn(lambdaToProperty(property), sc);
    }

    /**
     * 或IN
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orIn(String property, SubCriteria<?> sc);

    /**
     * NOT IN
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain notIn(P property, SubCriteria<?> sc) {
        return notIn(lambdaToProperty(property), sc);
    }

    /**
     * NOT IN
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain notIn(String property, SubCriteria<?> sc);

    /**
     * 或NOT IN
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    default Chain orNotIn(P property, SubCriteria<?> sc) {
        return orNotIn(lambdaToProperty(property), sc);
    }

    /**
     * 或NOT IN
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return {@code this}
     */
    Chain orNotIn(String property, SubCriteria<?> sc);
}
