package com.wkit.lost.mybatis.plugins.pagination.executor;

import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.Properties;

/**
 * 抽象SQL执行器
 * @author DT
 */
@NoArgsConstructor
public abstract class AbstractPageableExecutor {

    /**
     * 属性
     */
    @Getter
    protected Properties properties;

    /**
     * RowBounds参数offset是否作为Pages使用
     */
    @Getter
    protected boolean offsetAsPage = false;

    /**
     * 是否自动获取数据库方言
     */
    @Getter
    protected boolean autoRuntimeDialect;

    /**
     * 数据库方言
     */
    @Getter
    protected String dialect;

    /**
     * 数据库方言类
     */
    @Getter
    protected String dialectClass;


    /**
     * 设置属性
     * @param properties {@link Properties}对象
     */
    public void setProperties( Properties properties ) {
        this.properties = properties;
        this.dialect = Optional.ofNullable( properties.getProperty( "dialect" ) ).orElse( null );
        this.dialectClass = Optional.ofNullable( properties.getProperty( "dialectClazz" ) ).orElse( null );
        this.autoRuntimeDialect = StringUtil.toBoolean( properties.getProperty( "autoRuntimeDialect" ) );
        this.offsetAsPage = StringUtil.toBoolean( properties.getProperty( "offsetAsPage" ) );
    }

}
