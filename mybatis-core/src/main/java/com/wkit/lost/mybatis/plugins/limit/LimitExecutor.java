package com.wkit.lost.mybatis.plugins.limit;

import com.wkit.lost.mybatis.plugins.executor.AbstractQueryExecutor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LimitExecutor extends AbstractQueryExecutor {

    @Override
    protected Object doIntercept( Argument argument ) throws Exception {
        log.info( "执行原查询" );
        return executeOriginalQuery( argument );
    }

    @Override
    public boolean filter( Argument argument ) {
        String methodName = argument.targetMethodName();
        log.info( "我是limit查询执行器执行的目标方法：{}", methodName );
        return "listByLimit".equals( methodName );
    }
}
