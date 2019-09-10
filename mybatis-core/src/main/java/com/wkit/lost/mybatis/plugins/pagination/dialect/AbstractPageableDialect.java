package com.wkit.lost.mybatis.plugins.pagination.dialect;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.plugins.pagination.config.ThreadLocalPageable;
import com.wkit.lost.mybatis.utils.MetaObjectUtil;
import com.wkit.lost.paging.Pageable;
import com.wkit.lost.paging.WrapPager;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * 抽象分页方言
 * @author DT
 */
public abstract class AbstractPageableDialect extends AbstractDialect {

    /**
     * 获取分页对象
     * @return {@link Pageable}
     */
    public Pageable getPageable() {
        return ThreadLocalPageable.getPageable();
    }

    @Override
    public boolean filter( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return true;
    }

    @Override
    public boolean beforeOfQueryRecord( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return Optional.ofNullable( ThreadLocalPageable.getPageable() ).map( pageable -> pageable.getSize() > 0 ).orElse( false );
    }

    @Override
    public boolean afterOfQueryRecord( long records, Object parameter, RowBounds rowBounds ) {
        Pageable pageable = getPageable();
        pageable.setRecord( records );
        // size = 0 执行查询但不分页
        if ( pageable.getSize() < 0 ) {
            return false;
        }
        // 检查是否存在下一页
        return records > ( ( pageable.getPage() - 1 ) * pageable.getSize() );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public Object processParameter( MappedStatement statement, BoundSql boundSql, Object parameter, CacheKey cacheKey ) {
        Map<String, Object> paramMap;
        if ( parameter == null ) {
            paramMap = new HashMap<>();
        } else if ( parameter instanceof Map ) {
            paramMap = new HashMap<>( ( Map<? extends String, ?> ) parameter );
        } else {
            paramMap = new HashMap<>();
            boolean hasTypeHandler = statement.getConfiguration().getTypeHandlerRegistry().hasTypeHandler( parameter.getClass() );
            MetaObject metaObject = MetaObjectUtil.forObject( parameter );
            if ( !hasTypeHandler ) {
                for ( String name : metaObject.getGetterNames() ) {
                    paramMap.put( name, metaObject.getValue( name ) );
                }
            }
            if ( CollectionUtil.hasElement( boundSql.getParameterMappings() ) ) {
                for ( ParameterMapping mapping : boundSql.getParameterMappings() ) {
                    String property = mapping.getProperty();
                    if ( !property.equals( OFFSET_PARAMETER )
                            && !property.equals( LIMIT_PARAMETER )
                            && paramMap.get( property ) != null ) {
                        if ( hasTypeHandler || mapping.getJavaType().equals( parameter.getClass() ) ) {
                            paramMap.put( property, parameter );
                            break;
                        }
                    }
                }
            }
        }
        return processPageableParameter( statement, paramMap, boundSql, cacheKey, getPageable() );
    }

    /**
     * 处理分页参数
     * @param statement {@link MappedStatement}
     * @param parameter 接口参数
     * @param boundSql  SQL绑定对象
     * @param cacheKey  缓存Key
     * @param pageable  分页对象
     * @return 参数
     */
    public abstract Object processPageableParameter( MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql, CacheKey cacheKey, Pageable pageable );

    @Override
    public boolean executePagingOnBefore( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return Optional.ofNullable( getPageable() ).map( pageable -> pageable.getSize() > 0 ).orElse( false );
    }

    @Override
    public String generatePageableSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey ) {
        return generateCorrespondPageableSql( boundSql.getSql(), getPageable(), cacheKey );
    }

    /**
     * 生成对应数据库分页SQL语句
     * @param sql      原SQL语句
     * @param pageable 分页对象
     * @param cacheKey 缓存Key
     * @return 分页SQL
     */
    public abstract String generateCorrespondPageableSql( String sql, Pageable pageable, CacheKey cacheKey );

    @SuppressWarnings( "unchecked" )
    @Override
    public Object executePagingOnAfter( List result, Object parameter, RowBounds rowBounds ) {
        Pageable pageable = getPageable();
        if ( pageable == null ) {
            return result;
        }
        // 检查是否自动填充数据到分页对象中
        if ( pageable instanceof WrapPager ) {
            WrapPager wrapPager = ( WrapPager ) pageable;
            if ( wrapPager.autoFill() ) {
                wrapPager.setElements( result );
            }
        }
        return result;
    }

    @Override
    public void completed() {

    }

    @Override
    public void setProperties( Properties props ) {

    }

    /**
     * 参数处理
     * @param statement {@link MappedStatement}
     * @param boundSql  SQL绑定对象
     * @param pageable  分页对象
     */
    protected void handleParameter( MappedStatement statement, BoundSql boundSql, Pageable pageable ) {
        if ( boundSql.getParameterMappings() != null ) {
            List<ParameterMapping> newMappings = new ArrayList<>( boundSql.getParameterMappings() );
            if ( pageable == null || pageable.getPage() > 0 ) {
                newMappings.add( new ParameterMapping.Builder( statement.getConfiguration(), OFFSET_PARAMETER, Long.class ).build() );
                newMappings.add( new ParameterMapping.Builder( statement.getConfiguration(), LIMIT_PARAMETER, Long.class ).build() );
            } else {
                newMappings.add( new ParameterMapping.Builder( statement.getConfiguration(), OFFSET_PARAMETER, Long.class ).build() );
            }
            MetaObject metaObject = MetaObjectUtil.forObject( boundSql );
            metaObject.setValue( "parameterMappings", newMappings );
        }
    }
}
