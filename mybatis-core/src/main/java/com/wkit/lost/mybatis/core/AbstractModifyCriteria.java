package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.ColumnUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

@Log4j2
@SuppressWarnings( "serial" )
public abstract class AbstractModifyCriteria<T> extends AbstractQueryCriteria<T> implements Modify<T, AbstractModifyCriteria<T>> {

    /**
     * 更新代码片段
     */
    protected String updateSegment;

    /**
     * 更新列(属性)
     */
    protected Map<String, Object> modifies = new ConcurrentSkipListMap<>();

    @Override
    public AbstractModifyCriteria<T> modify( Property<T, ?> property, Object value ) {
        return modify( lambdaToProperty( property ), value );
    }

    @Override
    public AbstractModifyCriteria<T> modify( String property, Object value ) {
        if ( StringUtil.hasText( property ) ) {
            this.modifies.put( property, value );
        }
        return this;
    }

    @Override
    public AbstractModifyCriteria<T> modify( Map<String, Object> map ) {
        if ( CollectionUtil.hasElement( map ) ) {
            for ( Map.Entry<String, Object> entry : map.entrySet() ) {
                modify( entry.getKey(), entry.getValue() );
            }
        }
        return this;
    }

    @Override
    public String getUpdateSegment() {
        if ( CollectionUtil.hasElement( this.modifies ) ) {
            List<String> modifyColumns = new ArrayList<>();
            for ( Map.Entry<String, Object> entry : modifies.entrySet() ) {
                String property = entry.getKey();
                Object value = entry.getValue();
                Column column = searchColumn( property );
                if ( column != null && column.isUpdatable() ) {
                    modifyColumns.add( ColumnUtil.convertToCustomArg( column, defaultPlaceholder( value ), 
                            null, Operator.EQ, null ) );
                } else {
                    log.warn( "The corresponding table field information could not be found based on the `{}` " +
                            "property, which was automatically ignored.", property );
                }
            }
            return String.join( ", ", modifyColumns );
        }
        return "";
    }
}
