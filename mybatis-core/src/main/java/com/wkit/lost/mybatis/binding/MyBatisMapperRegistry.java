package com.wkit.lost.mybatis.binding;

import com.wkit.lost.mybatis.builder.annotation.MyBatisMapperAnnotationBuilder;
import com.wkit.lost.mybatis.session.MyBatisConfiguration;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.SqlSession;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyBatisMapperRegistry extends MapperRegistry {

    private final Map<Class<?>, MyBatisMapperProxyFactory<?>> knownMappers = new HashMap<>();
    private final MyBatisConfiguration config;

    public MyBatisMapperRegistry( MyBatisConfiguration config ) {
        super( config );
        this.config = config;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public <T> T getMapper( Class<T> type, SqlSession sqlSession ) {
        final MyBatisMapperProxyFactory<T> mapperProxyFactory = ( MyBatisMapperProxyFactory<T> ) knownMappers.get( type );
        if ( mapperProxyFactory == null ) {
            throw new BindingException( "Type " + type + " is not known to the MapperRegistry." );
        }
        try {
            return mapperProxyFactory.newInstance( sqlSession );
        } catch ( Exception e ) {
            throw new BindingException( "Error getting mapper instance. Cause: " + e, e );
        }
    }

    @Override
    public <T> boolean hasMapper( Class<T> type ) {
        return this.knownMappers.containsKey( type );
    }

    @Override
    public <T> void addMapper( Class<T> type ) {
        if ( type.isInterface() ) {
            // 检查是否已注册
            if ( this.hasMapper( type ) ) {
                return;
            }
            boolean loadCompleted = false;
            try {
                this.knownMappers.put( type, new MyBatisMapperProxyFactory<>( type ) );
                // It's important that the type is added before the parser is run
                // otherwise the binding may automatically be attempted by the
                // mapper parser. If the type is already known, it won't try.
                MyBatisMapperAnnotationBuilder parser = new MyBatisMapperAnnotationBuilder( config, type );
                parser.parse();
                loadCompleted = true;
            } finally {
                if ( !loadCompleted ) {
                    this.knownMappers.remove( type );
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * @return 接口集合
     * @see 3.2.2
     */
    @Override
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection( this.knownMappers.keySet() );
    }

}
