package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.schema.Table;
import com.wkit.lost.mybatis.sql.mapping.criteria.CriteriaQuerySqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

public class ListForArray extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, table.getAlias(), table, new CriteriaQuerySqlBuilder() );
        return addSelectMappedStatement( mapperInterface, Object[].class, mappedMethod(), createSqlSource( scriptBuilder, entity ), table );
    }

    @Override
    public String mappedMethod() {
        return "listForArray";
    }
}
