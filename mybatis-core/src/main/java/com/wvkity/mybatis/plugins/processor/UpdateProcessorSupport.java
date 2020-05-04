package com.wvkity.mybatis.plugins.processor;

import com.wvkity.mybatis.utils.PrimitiveRegistry;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;

/**
 * 更新操作处理器
 * @author wvkity
 */
public abstract class UpdateProcessorSupport extends Processor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        return doProceed(invocation, ms, parameter);
    }

    @Override
    public boolean filter(MappedStatement ms, Object parameter) {
        SqlCommandType exec = ms.getSqlCommandType();
        return (exec == SqlCommandType.INSERT || exec == SqlCommandType.UPDATE) && parameter != null
                && !(PrimitiveRegistry.isPrimitiveOrWrapper(parameter) || parameter.getClass() == String.class);
    }

    /**
     * 拦截处理
     * @param invocation 代理对象信息
     * @param ms         {@link MappedStatement}
     * @param parameter  方法参数
     * @return 方法执行结果
     * @throws Throwable 异常信息
     */
    protected abstract Object doProceed(Invocation invocation, MappedStatement ms, Object parameter) throws Throwable;
}
