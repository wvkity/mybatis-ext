package com.wkit.lost.mybatis.scripting.xmltags;

import com.wkit.lost.mybatis.scripting.defaults.MyBatisDefaultParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

public class MyBatisXMLLanguageDriver extends XMLLanguageDriver {

    @Override
    public MyBatisDefaultParameterHandler createParameterHandler( MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql ) {
        return new MyBatisDefaultParameterHandler( mappedStatement, parameterObject, boundSql );
    }
}
