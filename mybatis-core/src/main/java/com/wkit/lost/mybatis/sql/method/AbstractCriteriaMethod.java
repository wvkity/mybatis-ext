package com.wkit.lost.mybatis.sql.method;

import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.sql.mapping.criteria.CriteriaQuerySqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 抽象注入方法模板
 * @author wvkity
 */
public abstract class AbstractCriteriaMethod extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, Table table ) {
        Class<?> entity = table.getEntity();
        DefaultXmlScriptBuilder scriptBuilder = new DefaultXmlScriptBuilder( entity, table.getAlias(), table, new CriteriaQuerySqlBuilder() );
        Class<?> returnType = getResultType();
        return addSelectMappedStatement( mapperInterface, returnType == null ? resultType : returnType, mappedMethod(), createSqlSource( scriptBuilder, entity ), table );
    }

    /**
     * 获取返回值类型
     * @return 返回值类型
     */
    public abstract Class<?> getResultType();
}
