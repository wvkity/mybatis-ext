package com.wkit.lost.mybatis.plugins.paging.dbs.dialect;

import com.wkit.lost.mybatis.plugins.paging.config.RangePageable;
import com.wkit.lost.mybatis.plugins.paging.config.ThreadLocalPageable;
import com.wkit.lost.mybatis.plugins.paging.config.ThreadLocalRangePageable;
import com.wkit.lost.mybatis.plugins.paging.dbs.sql.OriginalSqlForCountParser;
import com.wkit.lost.mybatis.utils.CollectionUtil;
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
 * 抽象数据库方言
 * @author wvkity
 */
public abstract class AbstractDialect implements Dialect {

    /**
     * SQL解析器
     */
    protected OriginalSqlForCountParser countSqlParser = new OriginalSqlForCountParser();

    @Override
    public boolean filter( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return false;
    }

    @Override
    public String generateQueryRecordSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey ) {
        return countSqlParser.smartTransform( boundSql.getSql() );
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
        if ( isLimit() ) {
            RangePageable range = ThreadLocalRangePageable.getRange();
            return processPageableParameter( statement, paramMap, boundSql, cacheKey, range.getStart(), 
                    range.getEnd(), range.getOffset() );
        } else {
            Pageable pageable = ThreadLocalPageable.getPageable();
            return processPageableParameter( statement, paramMap, boundSql, cacheKey, pageable.offset(), 
                    pageable.getSize() + pageable.offset(), pageable.getSize() );
        }
    }

    @Override
    public String generatePageableSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey ) {
        long rowStart = 0;
        long rowEnd = 0;
        long pageSize = 0;
        if ( isLimit() ) {
            RangePageable range = ThreadLocalRangePageable.getRange();
            rowStart = range.getStart();
            rowEnd = range.getEnd();
            pageSize = range.getOffset();
        } else {
            Pageable pageable = ThreadLocalPageable.getPageable();
            if ( pageable != null ) {
                pageSize = pageable.getSize();
                rowStart = pageable.offset();
                rowEnd = rowStart + pageSize;
            }
        }
        return generateCorrespondPageableSql( boundSql.getSql(), cacheKey, rowStart, rowEnd, pageSize );
    }

    @Override
    public boolean executePagingOnBefore( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return true;
    }

    @SuppressWarnings( { "unchecked" } )
    @Override
    public <E> Object executePagingOnAfter( List<E> result, Object parameter, RowBounds rowBounds ) {
        Pageable pageable = ThreadLocalPageable.getPageable();
        if ( pageable instanceof WrapPager ) {
            WrapPager<E> pager = ( WrapPager<E> ) pageable;
            if ( pager.autoFill() ) {
                pager.setElements( result );
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

    protected boolean isLimit() {
        RangePageable range = ThreadLocalRangePageable.getRange();
        Pageable pageable = ThreadLocalPageable.getPageable();
        return pageable == null && Optional.ofNullable( range ).map( RangePageable::isApply ).orElse( false );
    }

    /**
     * 参数处理
     * @param statement {@link MappedStatement}
     * @param boundSql  SQL绑定对象
     * @param rowStart  分页开始位置
     * @param rowEnd    分页结束位置
     */
    protected void handleParameter( MappedStatement statement, BoundSql boundSql, long rowStart, long rowEnd ) {
        if ( boundSql.getParameterMappings() != null ) {
            List<ParameterMapping> newMappings = new ArrayList<>( boundSql.getParameterMappings() );
            if ( rowStart >= 0 ) {
                newMappings.add( new ParameterMapping.Builder( statement.getConfiguration(), OFFSET_PARAMETER, Long.class ).build() );
                newMappings.add( new ParameterMapping.Builder( statement.getConfiguration(), LIMIT_PARAMETER, Long.class ).build() );
            } else {
                newMappings.add( new ParameterMapping.Builder( statement.getConfiguration(), OFFSET_PARAMETER, Long.class ).build() );
            }
            MetaObject metaObject = MetaObjectUtil.forObject( boundSql );
            metaObject.setValue( "parameterMappings", newMappings );
        }
    }

    /**
     * 处理分页参数
     * @param statement {@link MappedStatement}
     * @param parameter 接口参数
     * @param boundSql  SQL绑定对象
     * @param cacheKey  缓存Key
     * @param rowStart  分页开始位置
     * @param rowEnd    分页结束位置
     * @param offset    偏移量
     * @return 参数
     */
    public abstract Object processPageableParameter( MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long offset );

    /**
     * 生成对应数据库分页SQL语句
     * @param sql      原SQL语句
     * @param cacheKey 缓存Key
     * @param rowStart 分页开始位置
     * @param rowEnd   分页结束位置
     * @param pageSize 每页显示数目
     * @return 分页SQL
     */
    public abstract String generateCorrespondPageableSql( String sql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long pageSize );

}
