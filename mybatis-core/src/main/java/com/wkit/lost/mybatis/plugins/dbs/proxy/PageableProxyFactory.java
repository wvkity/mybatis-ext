package com.wkit.lost.mybatis.plugins.dbs.proxy;

import com.wkit.lost.mybatis.plugins.dbs.dialect.PageableDialect;
import com.wkit.lost.mybatis.plugins.executor.Argument;

import java.util.List;
import java.util.Properties;

public class PageableProxyFactory extends LimitProxyFactory implements PageableDialect {

    @Override
    public void setProperties( Properties props ) {
        super.setProperties( props );
    }

    @Override
    public boolean filter( Argument arg ) {
        return false;
    }

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
