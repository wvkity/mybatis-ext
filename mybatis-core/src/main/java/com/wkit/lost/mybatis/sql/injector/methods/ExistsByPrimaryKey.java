package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.schema.Table;
import com.wkit.lost.mybatis.sql.mapping.query.ExistsByPrimaryKeySqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据主键查询记录是否存在
 * @author DT
 */
public class ExistsByPrimaryKey extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        // SQL脚本构建器
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, table.getAlias(), table, new ExistsByPrimaryKeySqlBuilder() );
        return this.addSelectMappedStatement( mapperInterface, Integer.class, mappedMethod(), this.createSqlSource( scriptBuilder, entity ), null );
    }

    @Override
    public String mappedMethod() {
        return "existsById";
    }
}
