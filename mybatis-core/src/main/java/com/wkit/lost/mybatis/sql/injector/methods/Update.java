package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.mapping.update.UpdateSqlBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据指定对象更新记录(属性全部更新[updatable=true])
 * @author wvkity
 */
public class Update extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, TableWrapper table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, null,
                table, new UpdateSqlBuilder() );
        return this.addUpdateMappedStatement( mapperInterface, entity, mappedMethod(),
                this.createSqlSource( scriptBuilder, entity ) );
    }

    @Override
    public String mappedMethod() {
        return "update";
    }
}
