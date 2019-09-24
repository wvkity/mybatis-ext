package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.sql.mapping.delete.BatchDeleteSqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据指定对象批量删除记录
 * @author DT
 */
public class BatchDelete extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null, table, new BatchDeleteSqlBuilder() );
        return addDeleteMappedStatement( mapperInterface, mappedMethod(), createSqlSource( scriptBuilder, entity ) );
    }

    @Override
    public String mappedMethod() {
        return "batchDelete";
    }
}
