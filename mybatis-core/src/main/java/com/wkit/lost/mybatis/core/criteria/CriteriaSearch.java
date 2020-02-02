package com.wkit.lost.mybatis.core.criteria;

/**
 * 条件对象搜索接口
 * @author wvkity
 */
public interface CriteriaSearch {

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias 别名
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign( String alias );

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign( Class<E> entity );

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias  别名
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign( String alias, Class<E> entity );

    /**
     * 搜索子查询条件对象
     * @param subTempTabAlias 子查询别名
     * @param <E>             泛型类型
     * @return {@link SubCriteria}
     */
    <E> SubCriteria<E> searchSubCriteria( String subTempTabAlias );

    /**
     * 搜索子查询条件对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link SubCriteria}
     */
    <E> SubCriteria<E> searchSubCriteria( Class<E> entity );

    /**
     * 搜索子查询条件对象
     * @param subTempTabAlias 子查询别名
     * @param entity          实体类
     * @param <E>             泛型类型
     * @return {@link SubCriteria}
     */
    <E> SubCriteria<E> searchSubCriteria( String subTempTabAlias, Class<E> entity );

}
