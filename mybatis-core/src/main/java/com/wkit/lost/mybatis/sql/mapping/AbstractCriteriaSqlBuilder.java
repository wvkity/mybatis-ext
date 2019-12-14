package com.wkit.lost.mybatis.sql.mapping;

/**
 * Criteria抽象SQL构建器
 * @author wvkity
 */
public abstract class AbstractCriteriaSqlBuilder extends AbstractLogicDeletionSqlBuilder {

    /**
     * 获取查询列
     * @return SQL字符串
     */
    protected String getQueryColumns() {
        return null;
    }

    /**
     * 获取查询条件
     * @return XML-SQL字符串
     */
    protected String getConditionForQuery() {
        return "${criteria.foreignSegment} \n<if test=\"criteria != null and criteria.hasCondition\">\n" +
                " ${criteria.whereSqlSegment}" +
                "</if>\n";
    }

    /**
     * 获取更新或删除条件
     * @return XML-SQL字符串
     */
    protected String getConditionForUpdateOrDelete() {
        return "<if test=\"criteria != null and criteria.hasCondition\">\n" +
                " ${criteria.whereSqlSegment}\n" +
                "</if>\n";
    }
}
