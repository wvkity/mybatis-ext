package com.wkit.lost.mybatis.core.mapping.sql.delete;

import com.wkit.lost.mybatis.core.mapping.sql.AbstractCriteriaProvider;

/**
 * 构建根据指定条件包装对象删除记录SQL
 * @author wvkity
 */
public class DeleteByCriteriaProvider extends AbstractCriteriaProvider {

    @Override
    public String build() {
        return delete(getUpdateCondition());
    }
}
