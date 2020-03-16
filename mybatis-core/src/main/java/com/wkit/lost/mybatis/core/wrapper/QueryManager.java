package com.wkit.lost.mybatis.core.wrapper;

import com.wkit.lost.mybatis.core.criteria.AbstractGeneralQueryCriteria;
import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.ForeignCriteria;
import com.wkit.lost.mybatis.core.criteria.ForeignSubCriteria;
import com.wkit.lost.mybatis.core.criteria.SubCriteria;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 查询管理器
 * @author wvkity
 */
public class QueryManager implements Segment {

    private static final long serialVersionUID = -3275773488142492015L;

    private static final int LIST_CAPACITY = 10;
    private static final int MAP_CAPACITY = 16;
    private static final int LIST_CRITICAL = 6;
    private static final int MAP_CRITICAL = 14;

    /**
     * 条件对象
     */
    private AbstractGeneralQueryCriteria<?, ?> criteria;

    /**
     * 查询列容器
     */
    private List<AbstractQueryWrapper<?, ?>> wrappers;

    /**
     * 排除查询属性
     */
    private Set<String> excludeProperties;

    /**
     * 排除查询列
     */
    private Set<String> excludeColumns;

    /**
     * 标记SQL片段已生成
     */
    private boolean cached = false;

    /**
     * SQL片段
     */
    private String sqlSegment = "";

    /**
     * 临时
     */
    private List<AbstractQueryWrapper<?, ?>> _wrappers;

    /**
     * 构造方法
     * @param criteria 条件对象
     */
    public QueryManager( AbstractGeneralQueryCriteria<?, ?> criteria ) {
        this.criteria = criteria;
    }

    /**
     * 添加查询列
     * @param wrappers 查询列包装对象数组
     * @return 当前对象
     */
    public QueryManager add( AbstractQueryWrapper<?, ?>... wrappers ) {
        return add( ArrayUtil.toList( wrappers ) );
    }

    /**
     * 添加查询列
     * @param wrappers 查询列包装对象集合
     * @return 当前对象
     */
    public QueryManager add( Collection<? extends AbstractQueryWrapper<?, ?>> wrappers ) {
        if ( CollectionUtil.hasElement( wrappers ) ) {
            List<? extends AbstractQueryWrapper<?, ?>> its = wrappers.stream().filter( Objects::nonNull )
                    .collect( Collectors.toList() );
            if ( !its.isEmpty() ) {
                if ( this.wrappers == null ) {
                    this.wrappers = new ArrayList<>( calCapacity( its.size(), LIST_CAPACITY, LIST_CRITICAL ) );
                }
                this.wrappers.addAll( its );
                this.cached = false;
            }
        }
        return this;
    }

    /**
     * 排除查询属性
     * @param properties 属性数组
     * @return 当前对象
     */
    public QueryManager excludes( String... properties ) {
        return excludes( ArrayUtil.toList( properties ) );
    }

    /**
     * 排除查询属性
     * @param properties 属性集合
     * @return 当前对象
     */
    public QueryManager excludes( Collection<String> properties ) {
        Set<String> its = distinct( properties );
        if ( !its.isEmpty() ) {
            if ( this.excludeProperties == null ) {
                this.excludeProperties = new HashSet<>( calCapacity( its.size(), MAP_CAPACITY, MAP_CRITICAL ) );
            }
            this.excludeProperties.addAll( its );
            this.cached = false;
        }
        return this;
    }

    /**
     * 排除查询列
     * @param columns 列名数组
     * @return 当前对象
     */
    public QueryManager immediateExcludes( String... columns ) {
        return immediateExcludes( ArrayUtil.toList( columns ) );
    }

    /**
     * 排除查询列
     * @param columns 列名集合
     * @return 当前对象
     */
    public QueryManager immediateExcludes( Collection<String> columns ) {
        Set<String> its = distinct( columns );
        if ( !its.isEmpty() ) {
            if ( this.excludeColumns == null ) {
                this.excludeColumns = new HashSet<>( calCapacity( its.size(), MAP_CAPACITY, MAP_CRITICAL ) );
            }
            this.excludeColumns.addAll( its );
            this.cached = false;
        }
        return this;
    }

    /**
     * 获取所有查询列
     * @return 查询列集合
     */
    public List<AbstractQueryWrapper<?, ?>> getQueries() {
        if ( cached ) {
            return new ArrayList<>( this._wrappers );
        }
        List<AbstractQueryWrapper<?, ?>> queries;
        if ( CollectionUtil.hasElement( wrappers ) ) {
            queries = wrappers.stream().filter( it -> {
                if ( it instanceof ColumnQuery ) {
                    return accept( ( ( ColumnQuery<?> ) it ).getProperty(), this.excludeProperties, false );
                } else if ( it instanceof StringQuery ) {
                    return accept( ( ( StringQuery<?> ) it ).getColumn(), this.excludeColumns, true );
                }
                return true;
            } ).collect( Collectors.toList() );
        } else {
            /*if ( criteria instanceof ForeignSubCriteria || criteria instanceof SubCriteria ) {
                if ( criteria.isFetch() ) {
                    List<AbstractQueryWrapper<?, ?>> items = criteria.getQueries();
                    if ( CollectionUtil.hasElement( items ) ) {
                        queries = items.stream().map( it -> it.transform( criteria ) )
                                .filter( Objects::nonNull ).collect( Collectors.toList() );
                    } else {
                        queries = new ArrayList<>( 0 );
                    }
                } else {
                    queries = new ArrayList<>( 0 );
                }
            } else {
                // 获取所有列
                Set<ColumnWrapper> columnWrappers = TableHandler.getTable( criteria.getEntityClass() ).columns();
                queries = columnWrappers.stream().filter( it ->
                        accept( it.getProperty(), this.excludeProperties, false )
                                && accept( it.getColumn(), this.excludeColumns, true )
                ).map( it -> ColumnQuery.Single.query( criteria, it ) ).collect( Collectors.toList() );
            }*/
            queries = loop( criteria );
        }
        this._wrappers = queries;
        this.cached = true;
        return new ArrayList<>( this._wrappers );
    }

    private List<AbstractQueryWrapper<?, ?>> loop( AbstractGeneralQueryCriteria<?, ?> criteria ) {
        if ( criteria instanceof ForeignSubCriteria ) {
            // 子查询联表
            ForeignSubCriteria<?> fsc = ( ForeignSubCriteria<?> ) criteria;
            if ( fsc.hasQueries() ) {
                return fsc.getQueries();
            }
            List<AbstractQueryWrapper<?, ?>> items;
            if ( fsc.isFetch() ) {
                // 重新组装
                items = fsc.getSubCriteria().getQueries();
                if ( CollectionUtil.hasElement( items ) ) {
                    return items.stream().map( it -> it.transform( criteria ) )
                            .filter( Objects::nonNull ).collect( Collectors.toList() );
                } else {
                    return new ArrayList<>( 0 );
                }
            }
            return new ArrayList<>( 0 );
        } else if ( criteria instanceof SubCriteria ) {
            // 子查询
            SubCriteria<?> sc = ( SubCriteria<?> ) criteria;
            if ( sc.hasQueries() ) {
                return sc.getQueries();
            }
            return build( sc );
        } else if ( criteria instanceof ForeignCriteria ) {
            // 联表
            ForeignCriteria<?> fc = ( ForeignCriteria<?> ) criteria;
            if ( fc.hasQueries() ) {
                return fc.getQueries();
            } else if ( fc.isFetch() ) {
                return build( fc );
            }
            return new ArrayList<>( 0 );
        } else {
            return build( criteria );
        }
    }

    private List<AbstractQueryWrapper<?, ?>> build( Criteria<?> criteria ) {
        // 获取所有列
        Set<ColumnWrapper> columnWrappers = TableHandler.getTable( criteria.getEntityClass() ).columns();
        return columnWrappers.stream().filter( it ->
                accept( it.getProperty(), this.excludeProperties, false )
                        && accept( it.getColumn(), this.excludeColumns, true )
        ).map( it -> ColumnQuery.Single.query( criteria, it ) ).collect( Collectors.toList() );
    }

    /**
     * 检查是否存在查询列对象
     * @return true: 是, false: 否
     */
    public boolean hasQueries() {
        return this.wrappers != null && !this.wrappers.isEmpty();
    }

    @Override
    public String getSqlSegment() {
        if ( cached && StringUtil.hasText( this.sqlSegment ) ) {
            return this.sqlSegment;
        }
        this.sqlSegment = getSqlSegment( true );
        return this.sqlSegment;
    }

    /**
     * 获取SQL片段
     * @param applyQuery 是否为查询语句
     * @return SQL片段
     */
    public String getSqlSegment( boolean applyQuery ) {
        return getQueries().stream().map( it -> it.getSqlSegment( applyQuery ) )
                .filter( StringUtil::hasText ).collect( Collectors.joining( ", " ) );
    }

    /**
     * 检查字符串是否在容器中出现
     * @param target     目标字符串
     * @param wrappers   目标容器
     * @param ignoreCase 是否忽略大小写比较
     * @return true: 否, false: 是
     */
    private boolean accept( String target, Collection<String> wrappers, boolean ignoreCase ) {
        if ( CollectionUtil.isEmpty( wrappers ) ) {
            return true;
        }
        if ( ignoreCase ) {
            return wrappers.parallelStream().noneMatch( it -> it.equalsIgnoreCase( target ) );
        } else {
            return wrappers.parallelStream().noneMatch( it -> it.equals( target ) );
        }
    }

    /**
     * 字符串集合去重
     * @param values 字符串集合
     * @return 新的字符串集合
     */
    private Set<String> distinct( Collection<String> values ) {
        return CollectionUtil.isEmpty( values ) ? new HashSet<>( 0 ) :
                values.stream().filter( StringUtil::hasText ).collect( Collectors.toSet() );
    }

    /**
     * 计算集合容器初始化大小
     * @param size            目标大小
     * @param defaultCapacity 默认大小
     * @param critical        临界值
     * @return 容器大小
     */
    private int calCapacity( int size, int defaultCapacity, int critical ) {
        if ( size < defaultCapacity ) {
            int rem = size % defaultCapacity;
            return rem > critical ? ( defaultCapacity * 3 / 2 ) : defaultCapacity;
        } else {
            int multiple = size / defaultCapacity;
            int rem = size % defaultCapacity;
            return multiple * defaultCapacity + ( rem > critical ? ( defaultCapacity * 3 / 2 )
                    : defaultCapacity );
        }
    }
}
