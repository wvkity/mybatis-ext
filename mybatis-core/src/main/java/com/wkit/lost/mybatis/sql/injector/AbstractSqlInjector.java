package com.wkit.lost.mybatis.sql.injector;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.sql.method.Method;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.builder.MapperBuilderAssistant;

import java.util.Collection;
import java.util.Set;

/**
 * 抽象SQL注入器
 */
@Log4j2
public abstract class AbstractSqlInjector implements SqlInjector {

    @Override
    public void inject( MapperBuilderAssistant builderAssistant, Class<?> mapperInterface ) {
        String className = mapperInterface.getCanonicalName();
        Set<String> mapperRegistryCache = MyBatisConfigCache.getMapperInterfaceCache( builderAssistant.getConfiguration() );
        if ( !mapperRegistryCache.contains( className ) ) {
            Collection<Method> methods = this.getMethodListOfInjection();
            if ( CollectionUtil.isEmpty( methods ) ) {
                log.warn( "No effective injection method was found" );
                return;
            }
            // 注入
            methods.forEach( method -> method.inject( builderAssistant, mapperInterface ) );
            // 缓存
            mapperRegistryCache.add( className );
        }
    }

    /**
     * 注入方法集合
     * @return 方法集合
     */
    public abstract Collection<Method> getMethodListOfInjection();
}
