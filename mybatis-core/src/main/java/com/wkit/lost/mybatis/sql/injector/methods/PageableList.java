package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.sql.mapping.query.PageableListSqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 分页查询记录
 * @author wvkity
 */
public class PageableList extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, TableWrapper table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, table.getAlias(),
                table, new PageableListSqlBuilder() );
        return addSelectMappedStatement( mapperInterface, resultType, mappedMethod(),
                createSqlSource( scriptBuilder, entity ), table );
    }

    @Override
    public String mappedMethod() {
        return "pageableList";
    }
}
