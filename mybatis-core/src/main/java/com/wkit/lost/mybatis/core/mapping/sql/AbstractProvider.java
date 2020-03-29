package com.wkit.lost.mybatis.core.mapping.sql;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.utils.Constants;
import lombok.Getter;

import static com.wkit.lost.mybatis.utils.Constants.NEW_LINE;
import static com.wkit.lost.mybatis.utils.Constants.PARAM_CRITERIA;

/**
 * 抽象SQL构建器
 * @author wvkity
 */
public abstract class AbstractProvider implements Provider {

    /**
     * AND连接符
     */
    protected static final String AND = Logic.AND.getSegment();

    /**
     * OR连接符
     */
    protected static final String OR = Logic.OR.getSegment();

    /**
     * 换行符
     */
    protected static final String NEW_LINE = Constants.NEW_LINE;

    /**
     * 实体类
     */
    @Getter
    protected Class<?> entity;

    /**
     * 表映射信息
     */
    @Getter
    protected TableWrapper table;

    /**
     * 别名
     */
    @Getter
    protected String alias;

    @Override
    public String build( TableWrapper table, Class<?> entity, String alias ) {
        this.table = table;
        this.entity = entity;
        this.alias = alias;
        return build();
    }

    /**
     * Criteria条件查询
     * @param conditionSegment 条件片段
     * @return SQL语句
     */
    protected String criteriaQuery( final String conditionSegment ) {
        return criteriaQuery( ScriptUtil.unSafeJoint( Constants.PARAM_CRITERIA, ".querySegment" ),
                conditionSegment );
    }

    /**
     * Criteria条件查询
     * @param requireSegment   必要片段
     * @param conditionSegment 条件片段
     * @return SQL语句
     */
    protected String criteriaQuery( final String requireSegment, final String conditionSegment ) {
        return toSqlString( SqlTemplate.SELECT_CRITERIA, requireSegment, conditionSegment );
    }

    /**
     * SQL模板转成SQL语句
     * @param template         模板
     * @param requireSegment   必要片段
     * @param conditionSegment 条件片段
     * @return SQL语句
     */
    protected String toSqlString( SqlTemplate template, final String requireSegment, final String conditionSegment ) {
        return String.format( template.getSegment( this.table, this.alias ), requireSegment, conditionSegment );
    }

    /**
     * 构建SQL语句
     * @return SQL语句
     */
    public abstract String build();
}
