package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.schema.Table;
import com.wkit.lost.mybatis.sql.mapping.criteria.PageableListSqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

public class PageableListByCriteria extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null, table, new PageableListSqlBuilder() );
        return addSelectMappedStatement( mapperInterface, resultType, mappedMethod(), createSqlSource( scriptBuilder, entity ), null );
    }

    @Override
    public String mappedMethod() {
        return "pageableListByCriteria";
    }
}
