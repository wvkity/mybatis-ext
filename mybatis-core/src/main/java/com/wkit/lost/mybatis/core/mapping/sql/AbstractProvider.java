package com.wkit.lost.mybatis.core.mapping.sql;

import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.utils.Constants;
import lombok.Getter;

/**
 * 抽象SQL构建器
 * @author wvkity
 */
public abstract class AbstractProvider implements Provider {

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
     * 添加
     * @param columnSegment 字段SQL片段
     * @param valueSegment  值SQL片段
     * @return SQL语句
     */
    protected String insert( String columnSegment, String valueSegment ) {
        return toSqlString( SqlTemplate.INSERT, columnSegment, valueSegment );
    }

    /**
     * 删除
     * @param condition 条件SQL片段
     * @return SQL语句
     */
    protected String delete( String condition ) {
        return toSqlString( SqlTemplate.DELETE, "", condition );
    }

    /**
     * 更新
     * @param script    脚本SQL片段
     * @param condition 条件SQL片段
     * @return SQL语句
     */
    protected String update( String script, String condition ) {
        return toSqlString( SqlTemplate.UPDATE, script, condition );
    }

    /**
     * 查询
     * @param script    脚本SQL片段
     * @param condition 条件SQL片段
     * @return SQL语句
     */
    protected String select( String script, String condition ) {
        return toSqlString( SqlTemplate.SELECT, script, condition );
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
     * 转换成乐观锁if标签脚本
     * @param column 字段包装对象
     * @return if标签脚本
     */
    protected String convertIfTagForLocking( final ColumnWrapper column ) {
        // 条件
        StringBuilder condition = new StringBuilder( 30 );
        condition.append( Constants.PARAM_OPTIMISTIC_LOCKING_KEY ).append( " != null" );
        if ( column.isCheckNotEmpty() && String.class.isAssignableFrom( column.getJavaType() ) ) {
            condition.append( " and " ).append( Constants.PARAM_OPTIMISTIC_LOCKING_KEY ).append( " != ''" );
        }
        // 脚本
        String script = Constants.CHAR_SPACE + column.getColumn() + " = " +
                ScriptUtil.safeJoint( Constants.PARAM_OPTIMISTIC_LOCKING_KEY,
                        ScriptUtil.concatIntactArg( column.getJavaType(), column.getJdbcType(),
                                column.getTypeHandler(), column.isUseJavaType() ) );
        return ScriptUtil.convertIfTag( condition.toString(),
                script, true );
    }

    /**
     * 构建SQL语句
     * @return SQL语句
     */
    public abstract String build();
}
