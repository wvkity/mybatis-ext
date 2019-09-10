package com.wkit.lost.mybatis.binding;

import com.wkit.lost.mybatis.builder.annotation.MapperAnnotationBuilder;
import com.wkit.lost.mybatis.session.Configuration;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

public class MapperRegistry extends org.apache.ibatis.binding.MapperRegistry {

    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();
    private final Configuration config;

    public MapperRegistry( Configuration config ) {
        super( config );
        this.config = config;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public <T> T getMapper( Class<T> type, SqlSession sqlSession ) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get( type );
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
                this.knownMappers.put( type, new MapperProxyFactory<>( type ) );
                // It's important that the type is added before the parser is run
                // otherwise the binding may automatically be attempted by the
                // mapper parser. If the type is already known, it won't try.
                MapperAnnotationBuilder parser = new MapperAnnotationBuilder( config, type );
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

    /**
     * @since 3.2.2
     */
    public void addMappers( String packageName, Class<?> superType ) {
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
        resolverUtil.find( new ResolverUtil.IsA( superType ), packageName );
        Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
        for ( Class<?> mapperClass : mapperSet ) {
            addMapper( mapperClass );
        }
    }

    /**
     * @since 3.2.2
     */
    public void addMappers( String packageName ) {
        addMappers( packageName, Object.class );
    }
}
