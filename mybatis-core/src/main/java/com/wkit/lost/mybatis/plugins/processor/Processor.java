package com.wkit.lost.mybatis.plugins.processor;

import com.wkit.lost.mybatis.plugins.filter.Filter;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

/**
 * 拦截器处理器
 * @author wvkity
 */
public abstract class Processor implements Filter {

    /**
     * 属性
     */
    @Getter
    @Setter
    protected Properties properties;

    /**
     * 获取执行方法
     * @param ms {@link MappedStatement}
     * @return 方法名
     */
    protected String execMethod( MappedStatement ms ) {
        String msId = ms.getId();
        return msId.substring( msId.lastIndexOf( "." ) + 1 );
    }

    /**
     * 拦截
     * @param invocation 代理对象
     * @return 结果
     * @throws Throwable 异常信息
     */
    public abstract Object intercept( Invocation invocation ) throws Throwable;

}
