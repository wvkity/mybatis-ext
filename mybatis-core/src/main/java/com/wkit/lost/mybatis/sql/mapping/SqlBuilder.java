package com.wkit.lost.mybatis.sql.mapping;

import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.utils.Constants;

/**
 * SQL语句构建器
 * @author wvkity
 */
@FunctionalInterface
public interface SqlBuilder {

    /**
     * 换行符
     */
    String NEW_LINE = Constants.NEW_LINE;

    /**
     * 构建SQL语句字符串
     * @param table  表映射信息
     * @param entity 实体类
     * @param alias  别名
     * @return SQL语句
     */
    String buildSqlString( final Class<?> entity, TableWrapper table, String alias );
}
