package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.sql.mapping.insert.InsertSqlBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractInsertMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 保存记录
 * @author DT
 */
public class Insert extends AbstractInsertMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        /*KeyGenerator keyGenerator = createKeyGenerator( table, ( mapperInterface.getName() + "." + mappedMethod() ) );
        Column primary = table.getPrimaryKey();
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null, table, new InsertSqlBuilder() );
        return addInsertMappedStatement( mapperInterface, entity, mappedMethod(), createSqlSource( scriptBuilder, entity ), keyGenerator, primary.getProperty(), primary.getColumn() );*/
        return addInsertMappedStatement( mapperInterface, resultType, table, new InsertSqlBuilder() );
    }

    @Override
    public String mappedMethod() {
        return "insert";
    }
}
