package com.wvkity.mybatis.core.injector;

import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * SQL注入器
 * @author wvkity
 */
public interface Injector {

    /**
     * SQL注入
     * @param assistant       构建对象
     * @param mapperInterface Mapper接口
     */
    void inject(MapperBuilderAssistant assistant, Class<?> mapperInterface);
}
