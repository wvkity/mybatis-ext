package com.wkit.lost.mybatis.scripting;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

public class XmlLanguageDriver extends XMLLanguageDriver {

    @Override
    public ParameterHandler createParameterHandler( MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql ) {
        return super.createParameterHandler( mappedStatement, parameterObject, boundSql );
    }
}
