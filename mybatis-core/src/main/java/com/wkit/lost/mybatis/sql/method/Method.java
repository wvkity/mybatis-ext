package com.wkit.lost.mybatis.sql.method;

import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * 注入方法接口
 */
@FunctionalInterface
public interface Method {

    /**
     * SQL注入
     * @param assistant       构建处理对象
     * @param mapperInterface Mapper接口
     */
    void inject( MapperBuilderAssistant assistant, Class<?> mapperInterface );
}
