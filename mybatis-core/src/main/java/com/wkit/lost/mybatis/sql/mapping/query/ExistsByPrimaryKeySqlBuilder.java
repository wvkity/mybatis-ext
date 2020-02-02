package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;

/**
 * 根据主键查询记录是否存在SQL构建器
 * @author wvkity
 */
public class ExistsByPrimaryKeySqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = "CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END COUNT";
        String condition = "WHERE " + ColumnConvert.convertToArg( table.getPrimaryKey(), Execute.NONE, null, this.alias );
        return select( querySegment, condition );
    }
}
