package com.wkit.lost.mybatis.sql.mapping;

/**
 * Criteria抽象SQL构建器
 * @author DT
 */
public abstract class AbstractCriteriaSqlBuilder extends AbstractSqlBuilder {

    /**
     * 获取查询列
     * @return SQL字符串
     */
    protected String getQueryColumns() {
        return null;
    }

    /**
     * 获取条件
     * @return XML-SQL字符串
     */
    protected String getCondition() {
        return "${criteria.foreignSegment} \n<if test=\"criteria != null and criteria.hasCondition\">\n" +
                "  ${criteria.sqlSegment}\n" +
                "</if>\n";
    }
}
