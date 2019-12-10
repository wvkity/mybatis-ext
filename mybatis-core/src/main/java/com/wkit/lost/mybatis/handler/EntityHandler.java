package com.wkit.lost.mybatis.handler;

import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.utils.ClassUtil;
import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.meta.DefaultEntityResolver;
import com.wkit.lost.mybatis.resolver.EntityResolver;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.builder.MapperBuilderAssistant;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体-表映射处理器
 * @author wvkity
 */
@Log4j2
public class EntityHandler {

    /**
     * 表映射信息缓存
     */
    private static final Map<Class<?>, Table> TABLE_CACHE = new ConcurrentHashMap<>( 128 );

    /**
     * 实体-表映射解析器
     */
    private static EntityResolver resolver;

    /**
     * 拦截解析实体-表信息
     * @param assistant 构建XML映射处理对象
     * @param entity    实体类
     * @return {@link Table}(表信息)
     */
    public synchronized static Table intercept( final MapperBuilderAssistant assistant, final Class<?> entity ) {
        MyBatisCustomConfiguration customConfiguration = MyBatisConfigCache.getCustomConfiguration( assistant.getConfiguration() );
        // 初始化实体解析器
        if ( EntityHandler.resolver == null ) {
            if ( customConfiguration == null || customConfiguration.getEntityResolver() == null ) {
                EntityHandler.resolver = new DefaultEntityResolver( customConfiguration );
            } else {
                EntityHandler.resolver = customConfiguration.getEntityResolver();
            }
        }
        if ( entity != null ) {
            Table table = TABLE_CACHE.get( entity );
            if ( table == null ) {
                log.debug( "Resolve entity class - table mapping information：`{}`", entity.getCanonicalName() );
                // 解析&缓存
                table = resolver.resolve( entity );
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
        return entity == null ? null : Optional.ofNullable( TABLE_CACHE.get( entity ) ).orElse( TABLE_CACHE.get( ClassUtil.getRealClass( entity ) ) );
    }
}
