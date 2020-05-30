package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.List;

/**
 * 分组筛选条件接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface HavingWrapper<T, Chain extends HavingWrapper<T, Chain>> {

    /**
     * 聚合函数筛选条件
     * @param alias 聚合函数别名
     * @return {@code this}
     */
    Chain having(String alias);

    /**
     * 聚合函数筛选条件
     * @param aliases 聚合函数别名数组
     * @return {@code this}
     */
    default Chain having(String... aliases) {
        return having(ArrayUtil.toList(aliases));
    }

    /**
     * 聚合函数筛选条件
     * @param aliases 聚合函数别名集合
     * @return {@code this}
     */
    Chain having(List<String> aliases);

    /**
     * 聚合函数筛选条件
     * @param function 聚合函数对象
     * @return {@code this}
     */
    Chain having(Function function);

    /**
     * 聚合函数筛选条件
     * @param functions 聚合函数对象集合
     * @return {@code this}
     */
    default Chain having(Function... functions) {
        return having(ArrayUtil.toList(functions));
    }

    /**
     * 聚合函数筛选条件
     * @param functions 聚合函数对象集合
     * @return {@code this}
     */
    Chain having(Collection<Function> functions);
}
