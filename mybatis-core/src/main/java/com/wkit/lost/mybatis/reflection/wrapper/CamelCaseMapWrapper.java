package com.wkit.lost.mybatis.reflection.wrapper;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

/**
 * 列名下划线转驼峰
 * @author DT
 */
public class CamelCaseMapWrapper extends MapWrapper {

    public CamelCaseMapWrapper( MetaObject metaObject, Map<String, Object> map ) {
        super( metaObject, map );
    }

    @Override
    public String findProperty( String name, boolean useCamelCaseMapping ) {
        return useCamelCaseMapping ? CaseFormat.UPPER_UNDERSCORE.to( CaseFormat.LOWER_CAMEL, name ) : name;
    }
}
