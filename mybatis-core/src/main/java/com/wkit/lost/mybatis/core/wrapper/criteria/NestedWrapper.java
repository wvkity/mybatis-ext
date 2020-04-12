package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.function.Function;

/**
 * 嵌套条件接口
 * @param <Chain> 子类
 * @author wvkity
 */
public interface NestedWrapper<Chain extends NestedWrapper<Chain>> {

    /**
     * 嵌套条件
     * @param conditions 条件
     * @return {@code this}
     * @see #and(Collection)
     */
    default Chain and(Criterion<?>... conditions) {
        return and(ArrayUtil.toList(conditions));
    }

    /**
     * 嵌套条件
     * <pre>
     *     // Examples
     *     &#64;Inject;
     *     private UserService userService;
     *
     *     QueryCriteria&lt;User&gt; criteria = new QueryCriteria&lt;&gt;(User.class);
     *     criteria.and(ArrayUtil.toList(criteria.eq(User::getUserName, "张三", User::getState, 2)));
     *     criteria.orGt(User::getAge, 25);
     *     userService.list(criteria);
     *
     *     ==> SELECT col1, col2, ... FROM USER WHERE (USER_NAME = ? AND STATE = ?) OR AGE > ?
     * </pre>
     * @param conditions 条件
     * @return {@code this}
     */
    Chain and(Collection<Criterion<?>> conditions);

    /**
     * 嵌套条件
     * @param criteria   条件包装对象
     * @param conditions 条件
     * @return {@code this}
     * @see #and(Criteria, Collection)
     */
    default Chain and(Criteria<?> criteria, Criterion<?>... conditions) {
        return and(criteria, ArrayUtil.toList(conditions));
    }

    /**
     * 嵌套条件
     * @param criteria   条件包装对象
     * @param conditions 条件
     * @return {@code this}
     * @see #and(Collection)
     */
    Chain and(Criteria<?> criteria, Collection<Criterion<?>> conditions);

    /**
     * 嵌套条件
     * @param function {@link Function}
     * @return {@code this}
     */
    Chain and(Function<Chain, Chain> function);
    
    /**
     * 嵌套条件
     * @param conditions 条件
     * @return {@code this}
     * @see #or(Collection)
     */
    default Chain or(Criterion<?>... conditions) {
        return or(ArrayUtil.toList(conditions));
    }

    /**
     * 嵌套条件
     * <pre>
     *     // Examples
     *     &#64;Inject;
     *     private UserService userService;
     *
     *     QueryCriteria&lt;User&gt; criteria = new QueryCriteria&lt;&gt;(User.class);
     *     criteria.orGt(User::getAge, 25);
     *     criteria.and(ArrayUtil.toList(criteria.eq(User::getUserName, "张三", User::getState, 2)));
     *     userService.list(criteria);
     *
     *     ==> SELECT col1, col2, ... FROM USER WHERE (USER_NAME = ? AND STATE = ?) OR AGE > ?
     * </pre>
     * @param conditions 条件
     * @return {@code this}
     */
    Chain or(Collection<Criterion<?>> conditions);

    /**
     * 嵌套条件
     * @param criteria   条件包装对象
     * @param conditions 条件
     * @return {@code this}
     * @see #or(Criteria, Collection)
     */
    default Chain or(Criteria<?> criteria, Criterion<?>... conditions) {
        return or(criteria, ArrayUtil.toList(conditions));
    }

    /**
     * 嵌套条件
     * @param criteria   条件包装对象
     * @param conditions 条件
     * @return {@code this}
     * @see #or(Collection)
     */
    Chain or(Criteria<?> criteria, Collection<Criterion<?>> conditions);

    /**
     * 嵌套条件
     * @param function {@link Function}
     * @return {@code this}
     */
    Chain or(Function<Chain, Chain> function);
}
