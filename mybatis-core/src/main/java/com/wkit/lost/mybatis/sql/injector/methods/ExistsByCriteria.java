package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.sql.mapping.criteria.ExistsSqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据Criteria对象查询记录是否存在
 * @author DT
 */
public class ExistsByCriteria extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null, table, new ExistsSqlBuilder() );
        return addSelectMappedStatement( mapperInterface, Integer.class, mappedMethod(), createSqlSource( scriptBuilder, entity ), null );
    }

    @Override
    public String mappedMethod() {
        return "existsByCriteria";
    }
}
