package com.wkit.lost.mybatis.plugins.dbs.dialect;

import com.wkit.lost.mybatis.plugins.executor.Argument;

import java.util.List;
import java.util.Properties;

/**
 * 抽象分页方言
 * @author DT
 */
public abstract class AbstractPageableDialect extends AbstractDialect implements PageableDialect {
    @Override
    public boolean beforeOfQueryRecord( Argument arg ) {
        return false;
    }

    @Override
    public String generateQueryRecordSql( Argument arg ) {
        return null;
    }

    @Override
    public boolean afterOfQueryRecord( long records, Argument arg ) {
        return false;
    }

    @Override
    public boolean executePagingOnBefore( Argument arg ) {
        return false;
    }

    @Override
    public void setProperties( Properties props ) {

    }

    @Override
    public boolean filter( Argument arg ) {
        return false;
    }

    @Override
    public Object processParameter( Argument arg ) {
        return null;
    }

    @Override
    public String generatePageableSql( Argument arg ) {
        return null;
    }

    @Override
    public Object executePagingOnAfter( List result, Argument arg ) {
        return null;
    }

    @Override
    public void completed() {

    }
}
