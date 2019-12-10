package com.wkit.lost.mybatis.sql;

import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.meta.Table;

/**
 * SQL模板
 * @author wvkity
 */
public enum SqlTemplate {

    /**
     * 查询
     * <p>
     * &nbsp;&nbsp;SELECT %s FROM TABLE_NAME alias %s
     * </p>
     */
    SELECT {
        @Override
        public String toSqlString( Table table, String alias ) {
            return "SELECT %s FROM " + SqlTemplate.parseTableName( table ) + (StringUtil.isBlank( alias ) ? " " : " " + alias + " ") + "%s";
        }
    },

    /**
     * CRITERIA查询
     * <p>
     * &nbsp;&nbsp;SELECT %s FROM TABLE_NAME alias %s
     * </p>
     */
    CRITERIA_SELECT {
        @Override
        public String toSqlString( Table table, String alias ) {
            return "SELECT <![CDATA[%s]]> FROM ${criteria.tableName} %s";
        }
    },

    /**
     * 添加
     * <p>
     * &nbsp;&nbsp;INSERT INTO TABLE_NAME %s VALUES %s
     * </p>
     */
    INSERT {
        @Override
        public String toSqlString( Table table, String alias ) {
            return "INSERT INTO " + SqlTemplate.parseTableName( table ) + " %s VALUES %s";
        }
    },

    /**
     * 修改
     * <p>
     * &nbsp;&nbsp;UPDATE TABLE_NAME %s%s
     * </p>
     */
    UPDATE {
        @Override
        public String toSqlString( Table table, String alias ) {
            return "UPDATE " + SqlTemplate.parseTableName( table ) + "%s%s";
        }
    },

    /**
     * 删除
     * <p>
     * &nbsp;&nbsp;DELETE FROM TABLE_NAME %s%s
     * </p>
     */
    DELETE {
        @Override
        public String toSqlString( Table table, String alias ) {
            return "DELETE FROM " + SqlTemplate.parseTableName( table ) + "%s%s";
        }
    };

    /**
     * 转SQL语句
     * @param table 表映射信息
     * @param alias 别名
     * @return SQL字符串
     */
    public abstract String toSqlString( Table table, String alias );

    /**
     * 解析表名
     * @param table 表映射对象
     * @return 表名
     */
    private static String parseTableName( final Table table ) {
        String catalog = table.getCatalog();
        String schema = table.getSchema();
        String tableName = table.getName();
        if ( StringUtil.hasText( schema ) ) {
            return schema + "." + tableName;
        } else if ( StringUtil.hasText( catalog ) ) {
            return catalog + "." + tableName;
        }
        return tableName;
    }
}
