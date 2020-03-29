package com.wkit.lost.mybatis.core.wrapper.criteria;

public class QueryCriteria<T> extends AbstractQueryCriteriaWrapper<T> {

    private static final long serialVersionUID = -5025331186998284119L;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    public QueryCriteria( Class<T> entityClass ) {
        this.entityClass = entityClass;
        this.inits();
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     */
    public QueryCriteria( Class<T> entityClass, String alias ) {
        this.entityClass = entityClass;
        this.tableAlias = alias;
        this.inits();
    }
}
