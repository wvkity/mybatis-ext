package com.wkit.lost.mybatis.spring.mapper;

import com.wkit.lost.mybatis.utils.StringUtil;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

public class MapperFactoryBean<T> extends SqlSessionDaoSupport implements FactoryBean<T> {

    /**
     * 指定接口
     */
    private Class<T> mapperInterface;

    /**
     *
     */
    private boolean addToConfig = true;

    /**
     * 接口映射处理器
     */
    //private MapperProcessor mapperProcessor;

    public MapperFactoryBean() {
    }

    public MapperFactoryBean( Class<T> mapperInterface ) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        Assert.notNull( this.mapperInterface, "Property 'mapperInterface' is required" );
        Configuration configuration = getSqlSession().getConfiguration();
        if ( this.addToConfig && !configuration.hasMapper( this.mapperInterface ) ) {
            try {
                configuration.addMapper( this.mapperInterface );
            } catch ( Exception e ) {
                logger.error( StringUtil.format( "Error while adding the mapper '{}' to configuration", this.mapperInterface ), e );
                throw new IllegalArgumentException( e );
            } finally {
                ErrorContext.instance().reset();
            }
        }
        // TODO 接口映射处理
        /*if ( configuration.hasMapper( this.mapperInterface )
                && this.mapperProcessor != null
                && this.mapperProcessor.isInheritMapperInterfaceAndRegister( this.mapperInterface ) ) {
            this.mapperProcessor.processMappingConfiguration( getSqlSession().getConfiguration(), this.mapperInterface );
        }*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getObject() throws Exception {
        return getSqlSession().getMapper( this.mapperInterface );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getObjectType() {
        return this.mapperInterface;
    }

    public boolean isAddToConfig() {
        return addToConfig;
    }

    public void setAddToConfig( boolean addToConfig ) {
        this.addToConfig = addToConfig;
    }

    /*public MapperProcessor getMapperProcessor() {
        return mapperProcessor;
    }

    public void setMapperProcessor( MapperProcessor mapperProcessor ) {
        this.mapperProcessor = mapperProcessor;
    }*/
}
