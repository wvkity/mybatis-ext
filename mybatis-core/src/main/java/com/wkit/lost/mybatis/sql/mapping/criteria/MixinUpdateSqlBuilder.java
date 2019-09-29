package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;

public class MixinUpdateSqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        Set<Column> columns = table.getUpdatableColumns();
        StringBuffer buffer = new StringBuffer( 60 );
        buffer.append( "<trim prefix=\"SET\" suffixOverrides=\", \">\n" );
        for ( Column column : columns ) {
            buffer.append( this.convertToIfTagOfNotNull( true, Execute.REPLACE, false, 0, Constants.PARAM_ENTITY, column, ", ", "" ) );
        }
        buffer.append( "</trim>" );
        String condition = "\n<if test=\"criteria != null and criteria.hasCondition\">\n ${criteria.sqlSegment}\n</if>\n";
        return update( buffer.toString(), condition );
    }
}
