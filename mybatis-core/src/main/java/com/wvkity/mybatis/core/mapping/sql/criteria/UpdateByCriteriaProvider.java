package com.wvkity.mybatis.core.mapping.sql.criteria;

import com.wvkity.mybatis.core.mapping.sql.AbstractCriteriaProvider;
import com.wvkity.mybatis.utils.Constants;

/**
 * 构建根据包装条件对象更新记录SQL
 * @author wvkity
 */
public class UpdateByCriteriaProvider extends AbstractCriteriaProvider {

    @Override
    public String build() {
        return update(" SET " + Constants.DOLLAR_OPEN_BRACE + Constants.PARAM_CRITERIA + Constants.DOT +
                "updateSegment" + Constants.CLOSE_BRACE, getUpdateCondition());
    }
}
