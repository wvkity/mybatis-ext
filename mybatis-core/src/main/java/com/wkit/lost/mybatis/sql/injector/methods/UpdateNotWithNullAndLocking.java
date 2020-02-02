package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.mapping.update.UpdateNotWithNullAndLockingSqlBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

public class UpdateNotWithNullAndLocking extends AbstractMethod {
    
    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, TableWrapper table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null, table, 
                new UpdateNotWithNullAndLockingSqlBuilder() );
        return this.addUpdateMappedStatement( mapperInterface, entity, mappedMethod(), 
                this.createSqlSource( scriptBuilder, entity ) );
    }

    @Override
    public String mappedMethod() {
        return "updateNotWithNullAndLocking";
    }
}
