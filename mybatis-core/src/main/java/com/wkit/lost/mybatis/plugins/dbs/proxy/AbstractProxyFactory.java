package com.wkit.lost.mybatis.plugins.dbs.proxy;

import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractProxyFactory {

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 属性
     */
    protected Properties properties;

    /**
     * 数据库方言
     */
    protected String dbDialect;

    /**
     * 是否自动获取数据库方言
     */
    protected boolean autoRuntimeDialect;

    /**
     * 设置属性
     * @param props 属性
     */
    public void setProperties( Properties props ) {
        this.properties = props;
    }
}
