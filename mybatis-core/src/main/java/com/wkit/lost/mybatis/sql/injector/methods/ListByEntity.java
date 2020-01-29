package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.metadata.Table;
import com.wkit.lost.mybatis.sql.mapping.query.ListByEntitySqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据指定对象查询记录
 * @author wvkity
 */
public class ListByEntity extends AbstractMethod {
    
    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, table.getAlias(), table, new ListByEntitySqlBuilder() );
        return addSelectMappedStatement( mapperInterface, resultType, mappedMethod(), createSqlSource( scriptBuilder, entity ), table );
    }

    @Override
    public String mappedMethod() {
        return "listByEntity";
    }
}
