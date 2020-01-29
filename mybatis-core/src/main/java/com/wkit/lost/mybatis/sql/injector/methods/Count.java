package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.metadata.Table;
import com.wkit.lost.mybatis.sql.mapping.query.CountSqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据指定对象查询记录数
 * @author wvkity
 */
public class Count extends AbstractMethod {
    
    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, table.getAlias(), table, new CountSqlBuilder() );
        return addSelectMappedStatement( mapperInterface, Long.class, mappedMethod(), createSqlSource( scriptBuilder, entity ), null );
    }

    @Override
    public String mappedMethod() {
        return "count";
    }
}
