package com.wvkity.mybatis.core.wrapper.criteria;

/**
 * 查询接口容器
 * @param <T>     实体类
 * @param <Chain> 子类
 * @author wvkity
 */
public interface QueryWrapper<T, Chain extends QueryWrapper<T, Chain>> extends Query<T, Chain>,
        ForeignCriteriaWrapper<T, Chain>, ForeignSubCriteriaWrapper<T, Chain>,
        GroupWrapper<T, Chain>, OrderWrapper<T, Chain>, FunctionWrapper<T, Chain>, HavingWrapper<T, Chain> {

    /**
     * 获取主条件对象
     * @param <E> 泛型类型
     * @return 条件对象
     */
    <E> AbstractQueryCriteriaWrapper<E> getMaster();

    /**
     * 获取顶级主条件对象
     * @param <E> 泛型类型
     * @return 条件对象
     */
    <E> AbstractQueryCriteriaWrapper<E> getRootMaster();

    /**
     * 查询排除聚合函数
     * @return {@code this}
     */
    Chain excludeFunc();

    /**
     * 只查询聚合函数
     * @return {@code this}
     */
    Chain onlyQueryFunc();

    /**
     * 所有列分组(除聚合函数外)
     * @return {@code this}
     */
    Chain groups();

    /**
     * 使用内置表别名
     * @return {@code this}
     */
    Chain useAs();

    /**
     * 使用表别名
     * @param alias 表别名
     * @return {@code this}
     */
    Chain as(final String alias);

    /**
     * 开启属性名自动映射成字段别名
     * @return {@code this}
     */
    default Chain propertyAutoMappingAlias() {
        return propertyAutoMappingAlias(true);
    }

    /**
     * 开启属性名自动映射成字段别名
     * @param enable 是否开启
     * @return {@code this}
     */
    Chain propertyAutoMappingAlias(boolean enable);

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

    /**
     * 设置自定义resultMap key
     * @param resultMap result key
     * @return {@code this}
     */
    Chain resultMap(String resultMap);

    /**
     * 设置自定义返回值类型
     * @param resultType 返回值类型
     * @return {@code this}
     */
    Chain resultType(Class<?> resultType);

    /**
     * 获取查询字段片段
     * @return SQL字符串
     */
    String getQuerySegment();
}
