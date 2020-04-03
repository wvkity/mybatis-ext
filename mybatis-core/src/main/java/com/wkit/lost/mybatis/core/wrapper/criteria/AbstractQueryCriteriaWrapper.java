package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.constant.Range;
import com.wkit.lost.mybatis.core.wrapper.basic.ImmediateQuery;
import com.wkit.lost.mybatis.core.wrapper.basic.Query;
import com.wkit.lost.mybatis.core.wrapper.basic.QueryManager;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


/**
 * 抽象查询条件包装器
 * @param <T> 实体类
 */
@Log4j2
@SuppressWarnings( { "serial" } )
public abstract class AbstractQueryCriteriaWrapper<T> extends AbstractCriteriaWrapper<T>
        implements QueryWrapper<T, AbstractQueryCriteriaWrapper<T>> {

    // region fields

    /**
     * 是否开启自动映射列别名(自动映射属性名)
     */
    @Getter
    protected boolean columnAliasAutoMapping = false;

    /**
     * 查询SQL片段
     */
    private String querySegment;

    /**
     * 返回值映射Map
     */
    protected String resultMap;

    /**
     * 返回值类型
     */
    protected Class<?> resultType;

    /**
     * 开始行
     */
    @Getter
    protected long rowStart;

    /**
     * 结束行
     */
    @Getter
    protected long rowEnd;

    /**
     * 开始页
     */
    @Getter
    protected long pageStart;

    /**
     * 结束页
     */
    @Getter
    protected long pageEnd;

    /**
     * 每页数目
     */
    @Getter
    protected long pageSize;

    /**
     * 查询管理器
     */
    @Getter
    protected QueryManager queryManager;

    @Override
    protected void inits() {
        super.inits();
        this.queryManager = new QueryManager( this );
    }

    // endregion

    // region query column

    @Override
    public AbstractQueryCriteriaWrapper<T> query( String property ) {
        this.queryManager.add( Query.Single.query( this, property ) );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> query( String property, String columnAlias ) {
        this.queryManager.add( Query.Single.query( this, property, columnAlias ) );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> immediateQuery( String column ) {
        this.queryManager.add( ImmediateQuery.Single.query( this, column, null ) );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> immediateQuery( String column, String columnAlias ) {
        this.queryManager.add( ImmediateQuery.Single.query( column, columnAlias ) );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> immediateQuery( String tableAlias, String column, String columnAlias ) {
        this.queryManager.add( ImmediateQuery.Single.query( tableAlias, column, columnAlias ) );
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subQuery( SubCriteria<E> criteria, String property ) {
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subQuery( SubCriteria<E> criteria, String property, String columnAlias ) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> queries( Collection<String> properties ) {
        this.queryManager.add( Query.Multi.query( this, properties ) );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> queries( Map<String, String> properties ) {
        this.queryManager.add( Query.Multi.query( this, properties ) );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> immediateQueries( Collection<String> columns ) {
        this.queryManager.add( ImmediateQuery.Multi.query( this, columns ) );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> immediateQueries( Map<String, String> columns ) {
        this.queryManager.add( ImmediateQuery.Multi.query( this, columns ) );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> immediateQueries( String tableAlias, Map<String, String> columns ) {
        this.queryManager.add( ImmediateQuery.Multi.query( tableAlias, columns ) );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> immediateQueries( String tableAlias, Collection<String> columns ) {
        this.queryManager.add( ImmediateQuery.Multi.query( tableAlias, columns ) );
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subQueries( SubCriteria<E> criteria, Collection<String> properties ) {
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subQueries( SubCriteria<E> criteria, Map<String, String> properties ) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> excludes( Collection<String> properties ) {
        this.queryManager.excludes( properties );
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> immediateExcludes( Collection<String> columns ) {
        this.queryManager.excludes( columns );
        return this;
    }

    protected <E> AbstractQueryCriteriaWrapper<T> ifPresent( Criteria<E> criteria,
                                                             Consumer<? super Criteria<E>> consumer ) {
        Optional.ofNullable( criteria ).ifPresent( consumer );
        return this;
    }

    @Override
    public String getQuerySegment() {
        return this.queryManager.getSegment();
    }

    // endregion

    // region foreign criteria
    @Override
    public <E> ForeignCriteria<E> searchForeign( String alias ) {
        return null;
    }

    @Override
    public <E> ForeignCriteria<E> searchForeign( Class<E> entity ) {
        return null;
    }

    @Override
    public <E> ForeignCriteria<E> searchForeign( String alias, Class<E> entity ) {
        return null;
    }
    // endregion

    // region get/set methods

    @Override
    public boolean isEnableAlias() {
        return this.enableAlias;
    }

    @Override
    public AbstractCriteriaWrapper<T> enableAlias( boolean enabled ) {
        this.enableAlias = enabled;
        return this;
    }

    @Override
    public boolean isRange() {
        return ( rowStart >= 0 && rowEnd > 0 ) || ( pageStart > 0 && pageEnd > 0 );
    }

    @Override
    public Range range() {
        if ( rowStart >= 0 && rowEnd > 0 ) {
            return Range.IMMEDIATE;
        } else if ( pageStart > 0 && pageEnd > 0 ) {
            return Range.PAGEABLE;
        }
        return super.range();
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> range( long start, long end ) {
        this.rowStart = start;
        this.rowEnd = end;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> range( long pageStart, long pageEnd, long size ) {
        this.pageStart = pageStart;
        this.pageEnd = pageEnd;
        this.pageSize = size;
        return this;
    }

    @Override
    public boolean isOnly() {
        return this.onlyFunctionForQuery;
    }

    @Override
    public boolean isInclude() {
        return this.includeFunctionForQuery;
    }

    @Override
    public String resultMap() {
        return this.resultMap;
    }

    @Override
    public Class<?> resultType() {
        return this.resultType;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> resultMap( String resultMap ) {
        this.resultMap = resultMap;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> resultType( Class<?> resultType ) {
        this.resultType = resultType;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> useAlias() {
        this.enableAlias = true;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> useAlias( String alias ) {
        this.enableAlias = true;
        this.tableAlias = alias;
        return this;
    }

    // endregion


    @Override
    public String getSegment() {
        return this.segmentManager.getSegment( isGroupAll() ? getGroupSegment() : null );
    }

    // region abstract methods
    public String getGroupSegment() {
        return "";
    }
    // endregion
}
