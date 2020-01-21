package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.utils.PrimitiveRegistry;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;

public abstract class AbstractUpdateExecutor {
    
    public abstract Object intercept( Invocation invocation ) throws Throwable;

    /**
     * 拦截
     * @param statement       {@link MappedStatement}对象
     * @param parameterObject 参数
     * @return true: 继续下一步 false: 跳过
     */
    protected boolean filter( MappedStatement statement, Object parameterObject ) {
        SqlCommandType exec = statement.getSqlCommandType();
        return ( exec == SqlCommandType.INSERT || exec == SqlCommandType.UPDATE ) && parameterObject != null
                && !( PrimitiveRegistry.isPrimitiveOrWrapper( parameterObject ) || parameterObject.getClass() == String.class );
    }
    
    
}
