package com.wkit.lost.mybatis.core.wrapper.criteria;

/**
 * 查询操作条件包装器
 * @param <T> 实体类型
 * @author wvkity
 */
public class QueryCriteria<T> extends AbstractQueryCriteriaWrapper<T> {

    private static final long serialVersionUID = -5025331186998284119L;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    public QueryCriteria(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.inits();
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     */
    public QueryCriteria(Class<T> entityClass, String alias) {
        this.entityClass = entityClass;
        this.tableAlias = alias;
        this.inits();
    }

    @Override
    protected QueryCriteria<T> newInstance() {
        QueryCriteria<T> instance = new QueryCriteria<>(this.entityClass, this.tableAlias);
        copy(instance, this);
        return instance;
    }

    /**
     * 创建条件包装对象
     * @param entity 实体类
     * @param <T>    泛型类型
     * @return 条件包装对象
     */
    public static <T> QueryCriteria<T> from(final Class<T> entity) {
        return new QueryCriteria<>(entity);
    }
}
