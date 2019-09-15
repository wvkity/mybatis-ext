package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.plugins.dbs.dialect.Dialect;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.Properties;

/**
 * LIMIT查询执行器
 * @author DT
 */
@Log4j2
public class LimitQueryExecutor extends AbstractQueryExecutor {

    @Override
    protected Mode getTarget() {
        return Mode.LIMIT;
    }

    @Override
    protected Object doIntercept( Argument arg ) throws Exception {
        try {
            // 检查代理对象是否存在
            validateDialectExists();
            // 检查是否需要执行
            if ( this.factory.filter( arg ) ) {

            }
        } catch ( Exception e ) {
            log.warn( "Limit query plug-in failed to execute: `{}` -- {}", arg.target(), e );
            throw e;
        } finally {
            Optional.ofNullable( this.factory ).ifPresent( Dialect::completed );
        }
        return executeOriginalQuery( arg );
    }

    @Override
    public boolean filter( Argument arg ) {
        String methodName = arg.targetMethodName();
        log.info( "我是limit查询执行器执行的目标方法：{}", methodName );
        return "rangeList".equals( methodName );
    }

    @Override
    public void setProperties( Properties properties ) {
        super.setProperties( properties );
    }

    @Override
    protected String getDefaultDialect() {
        return "com.wkit.lost.mybatis.plugins.dbs.proxy.LimitProxyFactory";
    }
}
