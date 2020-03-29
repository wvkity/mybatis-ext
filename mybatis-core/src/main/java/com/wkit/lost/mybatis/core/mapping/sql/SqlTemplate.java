package com.wkit.lost.mybatis.core.mapping.sql;

import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;

/**
 * SQL模板
 * @author wvkity
 */
public enum SqlTemplate {

    /**
     * Criteria条件查询
     */
    SELECT_CRITERIA {
        @Override
        protected String getSegment( String tableName ) {
            return "SELECT <![CDATA[%s]]> FROM " +
                    ScriptUtil.unSafeJoint( Constants.PARAM_CRITERIA + ".tableName" ) + " %s";
        }
    };

    /**
     * 获取SQL语句
     * @param table 表包装对象
     * @param __ 表别名
     * @return SQL语句
     */
    public String getSegment( final TableWrapper table, final String __ ) {
        String catalog = table.getCatalog();
        String schema = table.getSchema();
        String tableName = table.getName();
        if ( StringUtil.hasText( schema ) ) {
            return getSegment( schema + "." + tableName );
        } else if ( StringUtil.hasText( catalog ) ) {
            return getSegment( catalog + "." + tableName );
        }
        return getSegment( table.getName() );
    }

    /**
     * 获取SQL语句
     * @param tableName 表名
     * @return SQL语句
     */
    protected abstract String getSegment( final String tableName );
}
