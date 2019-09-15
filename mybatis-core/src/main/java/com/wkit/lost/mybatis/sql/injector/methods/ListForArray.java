package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.schema.Table;
import com.wkit.lost.mybatis.sql.mapping.criteria.CriteriaQuerySqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractCriteriaMethod;
import com.wkit.lost.mybatis.sql.method.AbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;

public class ListForArray extends AbstractCriteriaMethod {

    @Override
    public String mappedMethod() {
        return "listForArray";
    }

    @Override
    public Class<?> getResultType() {
        return Object[].class;
    }
}
