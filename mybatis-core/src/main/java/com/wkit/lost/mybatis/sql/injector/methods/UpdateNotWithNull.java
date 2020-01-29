package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.metadata.Table;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.mapping.update.UpdateNotWithNullSqlBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据指定对象更新记录(属性可选更新[updatable=true])
 * @author wvkity
 */
public class UpdateNotWithNull extends AbstractMethod {
    
    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null, table, 
                new UpdateNotWithNullSqlBuilder() );
        return this.addUpdateMappedStatement( mapperInterface, entity, mappedMethod(), 
                this.createSqlSource( scriptBuilder, entity ) );
    }

    @Override
    public String mappedMethod() {
        return "updateNotWithNull";
    }
}
