package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.invoke.SerializedLambda;
import com.wkit.lost.mybatis.lambda.Property;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings( "serial" )
@Log4j2
public abstract class AbstractChainCriteriaWrapper<T, Context extends AbstractChainCriteriaWrapper<T, Context>>
        extends AbstractCriteriaWrapper<T, Property<T, ?>, Context> {

    /**
     * 实体属性-数据库字段映射缓存
     */
    private Map<String, Column> columnMappingCache = null;
    /**
     * 映射缓存是否标识已初始化
     */
    @Getter
    private boolean initialized = false;

    @Override
    public Column searchColumn( Property<T, ?> property ) {
        return getColumn( PropertyMappingForLambda.resolve( property ) );
    }

    @Override
    public Column searchColumn( String property ) {
        return getColumn( property );
    }

    @Override
    public String lambdaToProperty( Property<T, ?> lambda ) {
        return getPropertyNameFromMethodName( PropertyMappingForLambda.resolve( lambda ).getImplMethodName() );
    }

    @Override
    public String methodToProperty( Property<?, ?> property ) {
        return getPropertyNameFromMethodName( PropertyMappingForLambda.resolve( property ).getImplMethodName() );
    }

    /**
     * 初始化表映射
     * @param entityName 类名
     * @param printing   是否打印警告信息
     */
    protected void initMappingCache( String entityName, boolean printing ) {
        if ( StringUtil.hasText( entityName ) ) {
            this.columnMappingCache = PropertyMappingForLambda.getColumnCache( entityName );
            if ( CollectionUtil.isEmpty( this.columnMappingCache ) ) {
                if ( printing )
                    log.warn( ( "The corresponding table mapping information cannot be found in the cache according to the specified entity class name -- `[" + entityName + "]`, " +
                            "please check the configuration is correct!" ) );
            } else {
                this.initialized = true;
            }
        }
    }

    /**
     * 获取字段名
     * @param lambda Lambda
     * @return 字段名
     */
    private Column getColumn( SerializedLambda lambda ) {
        if ( !this.initialized ) {
            String entityName = lambda.getImplClass().replace( "/", "." );
            initMappings( entityName );
        }
        return getColumn( getPropertyNameFromMethodName( lambda.getImplMethodName() ) );
    }

    /**
     * 获取字段名
     * @param property 属性名
     * @return 字段名
     */
    private Column getColumn( String property ) {
        if ( !this.initialized ) {
            String entityName = this.entity.getName();
            initMappings( entityName );
        }
        /*return Optional.ofNullable( this.columnMappingCache.get( property ) )
                .orElseThrow( () -> new MyBatisException( "The field mapping information for the entity class cannot be found based on the `" + property + "` attribute. " +
                        "Check to see if the attribute exists or is decorated using the @transient annotation." ) );*/
        Column column = this.columnMappingCache.get( property );
        if ( column == null ) {
            log.warn( "The field mapping information for the entity class cannot be found based on the `" + property + "` attribute. " +
                    "Check to see if the attribute exists or is decorated using the @transient annotation." );
        }
        return column;
    }

    /**
     * 根据方法名获取属性(getXX|isXX)
     * @param methodName 方法名
     * @return 属性名
     */
    private String getPropertyNameFromMethodName( String methodName ) {
        return PropertyMappingForLambda.getPropertyNameFromMethodName( methodName );
    }

    private void initMappings( String entityName ) {
        if ( !initialized ) {
            initMappingCache( entityName, false );
            if ( CollectionUtil.isEmpty( this.columnMappingCache ) ) {
                throw new MyBatisException( "The table field mapping information for the specified entity ['" + entityName + "'] could not be found" );
            }
        }
    }

}
