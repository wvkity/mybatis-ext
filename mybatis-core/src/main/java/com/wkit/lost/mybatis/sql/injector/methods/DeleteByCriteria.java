package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.sql.mapping.criteria.DeleteSqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据主键删除记录
 * @author wvkity
 */
public class DeleteByCriteria extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, TableWrapper table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null, 
                table, new DeleteSqlBuilder() );
        return this.addDeleteMappedStatement( mapperInterface, mappedMethod(), 
                createSqlSource( scriptBuilder, entity ) );
    }

    @Override
    public String mappedMethod() {
        return "deleteByCriteria";
    }
}
