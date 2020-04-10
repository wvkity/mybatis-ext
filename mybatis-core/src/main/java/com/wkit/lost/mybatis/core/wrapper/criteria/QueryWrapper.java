package com.wkit.lost.mybatis.core.wrapper.criteria;

/**
 * 查询接口容器
 * @param <T>     实体类
 * @param <Chain> 子类
 * @author wvkity
 */
public interface QueryWrapper<T, Chain extends QueryWrapper<T, Chain>> extends Query<T, Chain> {

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias 别名
     * @param <E>   泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign(String alias);

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign(Class<E> entity);

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias  别名
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign(String alias, Class<E> entity);

    /**
     * 使用内置表别名
     * @return {@code this}
     */
    Chain useAlias();

    /**
     * 使用表别名
     * @param alias 表别名
     * @return {@code this}
     */
    Chain useAlias(final String alias);

    /**
     * 设置范围
     * <p>优先级比{@link #range(long, long, long)}高</p>
     * @param end 结束行
     * @return {@code this}
     */
    default Chain range(long end) {
        return range(0, end);
    }

    /**
     * 设置范围
     * <p>优先级比{@link #range(long, long, long)}高</p>
     * @param start 开始行
     * @param end   结束行
     * @return {@code this}
     */
    Chain range(long start, long end);

    /**
     * 设置范围
     * <p>优先级比{@link #range(long, long)}低</p>
     * @param pageStart 开始页
     * @param pageEnd   结束页
     * @param size      每页数目
     * @return {@code this}
     */
    Chain range(long pageStart, long pageEnd, long size);
}
