package com.wkit.lost.mybatis.handler;

import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.metadata.DefaultEntityResolver;
import com.wkit.lost.mybatis.core.metadata.Table;
import com.wkit.lost.mybatis.core.metadata.TableBuilder;
import com.wkit.lost.mybatis.core.parser.DefaultEntityParser;
import com.wkit.lost.mybatis.core.parser.EntityParser;
import com.wkit.lost.mybatis.resolver.EntityResolver;
import com.wkit.lost.mybatis.utils.ClassUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体-表映射处理器
 * @author wvkity
 */
@Deprecated
@Log4j2
public final class EntityHandler {

    private EntityHandler() {
    }

    /**
     * 表映射信息缓存
     */
    private static final Map<Class<?>, Table> TABLE_CACHE = new ConcurrentHashMap<>( 64 );
    private static final Map<Class<?>, Configuration> TABLE_CONFIGURATION_CACHE = new ConcurrentHashMap<>( 64 );


    /**
     * 实体-表映射解析器
     */
    @Deprecated
    private static EntityResolver resolver;

    /**
     * 拦截解析实体-表信息
     * @param assistant 构建XML映射处理对象
     * @param entity    实体类
     * @return {@link Table}(表信息)
     */
    public synchronized static Table intercept( final MapperBuilderAssistant assistant, final Class<?> entity ) {
        if ( entity != null ) {
            Configuration configuration = assistant.getConfiguration();
            MyBatisCustomConfiguration customConfiguration =
                    MyBatisConfigCache.getCustomConfiguration( configuration );
            // 初始化实体解析器
            EntityResolver resolverVariable = customConfiguration.getEntityResolver();
            if ( resolverVariable == null ) {
                if ( EntityHandler.resolver == null ) {
                    EntityHandler.resolver = new DefaultEntityResolver( customConfiguration );
                }
                resolverVariable = EntityHandler.resolver;
            }
            Table table = TABLE_CACHE.get( entity );
            if ( table == null ) {
                log.debug( "Resolve entity class - table mapping information：`{}`", entity.getCanonicalName() );
                // 解析&缓存
                table = resolverVariable.resolve( entity, assistant.getCurrentNamespace() );
                Optional.ofNullable( table ).ifPresent( __ ->
                        TABLE_CONFIGURATION_CACHE.putIfAbsent( entity, assistant.getConfiguration() ) );
                TABLE_CACHE.put( entity, table );
            }
            return table;
        }
        return null;
    }

    /**
     * 根据实体类获取表映射信息
     * @param entity 实体类
     * @return {@link Table}(表信息)
     */
    public static Table getTable( final Class<?> entity ) {
        return entity == null ? null : Optional.ofNullable( TABLE_CACHE.get( entity ) )
                .orElse( TABLE_CACHE.get( ClassUtil.getRealClass( entity ) ) );
    }

    /**
     * 获取{@link Configuration}
     * @param entity 实体类
     * @return {@link Configuration}
     */
    public static Configuration getConfiguration( final Class<?> entity ) {
        if ( TABLE_CACHE.containsKey( entity ) ) {
            return Optional.ofNullable( TABLE_CONFIGURATION_CACHE.getOrDefault( entity, null ) )
                    .orElse( null );
        }
        return null;
    }

    /**
     * 获取{@link Configuration}
     * @param table 实体-表映射对象
     * @return {@link Configuration}
     */
    public static Configuration getConfiguration( final Table table ) {
        return Optional.ofNullable( table )
                .map( it -> TABLE_CONFIGURATION_CACHE.getOrDefault( it.getEntity(), null ) )
                .orElse( null );
    }

}
