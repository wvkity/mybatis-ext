package com.wkit.lost.mybatis.core.mapping.sql;

import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.utils.Constants;

/**
 * SQL构建器
 * @author wvkity
 */
public interface Provider extends Constants {

    /**
     * 构建SQL语句
     * @param table  表映射对象
     * @param entity 实体类
     * @param alias  表别名
     * @return SQL语句
     */
    String build(final TableWrapper table, final Class<?> entity, final String alias);
}
