package com.wvkity.mybatis.core.mapping.sql.query;

import com.wvkity.mybatis.core.mapping.sql.AbstractCreator;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.utils.Constants;

/**
 * 构建查询所有记录数SQL
 * @author wvkity
 */
public class AllCountCreator extends AbstractCreator {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        return select((primaryKey == null ? "COUNT(*) COUNT" : ("COUNT(" + primaryKey.getColumn() + ") COUNT")),
                Constants.EMPTY);
    }
}
