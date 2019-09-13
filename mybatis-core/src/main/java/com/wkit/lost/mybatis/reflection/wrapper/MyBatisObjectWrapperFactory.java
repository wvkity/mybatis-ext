package com.wkit.lost.mybatis.reflection.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

public class MyBatisObjectWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor( Object object ) {
        return object instanceof Map;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public ObjectWrapper getWrapperFor( MetaObject metaObject, Object object ) {
        return new MyBatisMapWrapper( metaObject, ( Map<String, Object> ) object );
    }
}
