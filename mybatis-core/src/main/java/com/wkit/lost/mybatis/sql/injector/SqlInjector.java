package com.wkit.lost.mybatis.sql.injector;

import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * SQL注入器接口
 * @author wvkity
 */
public interface SqlInjector {

    /**
     * SQL注入
     * @param assistant       构建对象
     * @param mapperInterface Mapper接口
     */
    void inject( MapperBuilderAssistant assistant, Class<?> mapperInterface );
}
