package com.wkit.lost.mybatis.sql.mapping;

import com.wkit.lost.mybatis.core.schema.Table;

/**
 * SQL语句构建器
 * @author DT
 */
@FunctionalInterface
public interface SqlBuilder {

    /**
     * 构建SQL语句字符串
     * @param table  表映射信息
     * @param entity 实体类
     * @param alias  别名
     * @return SQL语句
     */
    String buildSqlString( final Class<?> entity, Table table, String alias );
}
