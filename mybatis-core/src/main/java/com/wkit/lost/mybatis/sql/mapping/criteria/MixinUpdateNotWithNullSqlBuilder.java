package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;

public class MixinUpdateNotWithNullSqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        Set<ColumnWrapper> columns = table.updatableColumns();
        StringBuffer buffer = new StringBuffer( 60 );
        buffer.append( "<trim prefix=\"SET\" suffixOverrides=\", \">" ).append( NEW_LINE );
        for ( ColumnWrapper column : columns ) {
            buffer.append( this.convertToIfTagOfNotNull( true, Execute.REPLACE, false, 0,
                    Constants.PARAM_ENTITY, column, ", ", "" ) );
        }
        buffer.append( "</trim>" );
        return update( buffer.toString(), getConditionForUpdateOrDelete() );
    }
}
