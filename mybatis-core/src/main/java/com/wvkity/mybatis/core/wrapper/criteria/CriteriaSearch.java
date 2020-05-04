package com.wvkity.mybatis.core.wrapper.criteria;

/**
 * 条件对象搜索接口
 * @author wvkity
 */
public interface CriteriaSearch {

    /**
     * 搜索子查询条件对象
     * @param tableAlias 子查询别名
     * @param <E>        泛型类型
     * @return {@link SubCriteria}
     */
    <E> SubCriteria<E> searchSub(String tableAlias);

    /**
     * 搜索子查询条件对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link SubCriteria}
     */
    <E> SubCriteria<E> searchSub(Class<E> entity);

    /**
     * 搜索子查询条件对象
     * @param subTempTabAlias 子查询别名
     * @param entity          实体类
     * @param <E>             泛型类型
     * @return {@link SubCriteria}
     */
    <E> SubCriteria<E> searchSub(String subTempTabAlias, Class<E> entity);

}
