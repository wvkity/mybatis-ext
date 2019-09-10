package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.schema.Table;
import com.wkit.lost.mybatis.sql.mapping.delete.BatchDeleteByPrimaryKeySqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据主键批量删除记录
 * @author DT
 */
public class BatchDeleteByPrimaryKey extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null, table, new BatchDeleteByPrimaryKeySqlBuilder() );
        return addDeleteMappedStatement( mapperInterface, mappedMethod(), createSqlSource( scriptBuilder, entity ) );
    }

    @Override
    public String mappedMethod() {
        return "batchDeleteById";
    }
}
