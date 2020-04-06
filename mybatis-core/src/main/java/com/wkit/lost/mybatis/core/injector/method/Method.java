package com.wkit.lost.mybatis.core.injector.method;

import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * 方法映射注入接口
 * @author wvkity
 */
@FunctionalInterface
public interface Method {

    /**
     * 解析
     * @param assistant       构建对象
     * @param mapperInterface Mapper接口类
     * @param resultType      返回值类型
     * @param table           表包装对象
     */
    void inject( MapperBuilderAssistant assistant, Class<?> mapperInterface, Class<?> resultType, TableWrapper table );
}
