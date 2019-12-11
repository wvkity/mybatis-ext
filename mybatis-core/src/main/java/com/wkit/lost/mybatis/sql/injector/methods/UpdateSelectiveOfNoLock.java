package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.mapping.update.UpdateSelectiveOfNoLockSqlBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

public class UpdateSelectiveOfNoLock extends AbstractMethod {
    
    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null, table, new UpdateSelectiveOfNoLockSqlBuilder() );
        return this.addUpdateMappedStatement( mapperInterface, entity, mappedMethod(), this.createSqlSource( scriptBuilder, entity ) );
    }

    @Override
    public String mappedMethod() {
        return "updateSelectiveOfNoLock";
    }
}
