package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.sql.mapping.query.SelectOneSqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据主键查询记录
 * @author DT
 */
public class SelectOne extends AbstractMethod {
    
    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, table.getAlias(), table, new SelectOneSqlBuilder() );
        return addSelectMappedStatement( mapperInterface, resultType, mappedMethod(), createSqlSource( scriptBuilder, entity ), table );
    }

    @Override
    public String mappedMethod() {
        return "selectOne";
    }
}
