package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.AbstractConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.executor.resultset.EmbeddedResult;

import java.util.Collection;
import java.util.function.Function;

/**
 * Criteria接口
 * @param <T> 泛型类型
 * @author wvkity
 */
public interface Criteria<T> extends Segment, EmbeddedResult, Search<T>, ParamValuePlaceholderConverter {

    /**
     * 获取WHERE条件SQL片段
     * @return SQL片段
     */
    String getWhereSqlSegment();

    /**
     * 获取实体类
     * @return 实体类
     */
    Class<T> getEntityClass();

    /**
     * 获取表别名
     * @return 别名
     */
    String getAlias();

    /**
     * 是否启用别名
     * @return true: 是 | false: 否
     */
    default boolean isEnableAlias() {
        return false;
    }

    /**
     * 是否执行范围查询
     * @return true: 是 false: 否
     */
    default boolean isRange() {
        return false;
    }

    /**
     * Range方式
     * @return {@link RangeMode}
     */
    default RangeMode range() {
        return RangeMode.NONE;
    }

    /**
     * 获取开始位置
     * @return 开始位置
     */
    default long getStart() {
        return 0;
    }

    /**
     * 获取结束位置
     * @return 结束位置
     */
    default long getEnd() {
        return 0;
    }

    /**
     * 获取开始页
     * @return 页数
     */
    default long getPageStart() {
        return 0;
    }

    /**
     * 获取结束页
     * @return 页数
     */
    default long getPageEnd() {
        return 0;
    }

    /**
     * 获取每页数目
     * @return 每页大小
     */
    default long getPageSize() {
        return 0;
    }

    /**
     * 获取主条件对象
     * @param <E> 泛型类型
     * @return 条件对象
     */
    <E> AbstractCriteriaWrapper<E> getMaster();

    /**
     * 获取顶级主条件对象
     * @param <E> 泛型类型
     * @return 条件对象
     */
    <E> AbstractCriteriaWrapper<E> getRootMaster();

    /**
     * 添加条件
     * @param conditionManager 条件管理器
     * @return 当前对象
     */
    Criteria<T> add( AbstractConditionManager<T> conditionManager );

    /**
     * 添加条件
     * @param conditionFunction 条件函数
     * @return 当前对象
     */
    Criteria<T> add( Function<AbstractConditionManager<T>, AbstractConditionManager<T>> conditionFunction );

    /**
     * 添加条件
     * @param condition 条件对象
     * @return 当前对象
     */
    Criteria<T> add( Criterion<?> condition );

    /**
     * 添加多个条件
     * @param conditions 条件对象数组
     * @return 当前对象
     */
    Criteria<T> add( Criterion<?>... conditions );

    /**
     * 添加多个条件
     * @param conditions 条件对象集合
     * @return 当前对象
     */
    Criteria<T> add( Collection<Criterion<?>> conditions );

    /**
     * 添加子查询条件对象
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Criteria<T> addSubCriteria( SubCriteria<?> subCriteria );

    /**
     * 添加子查询条件对象
     * @param subCriteriaArray 子查询条件对象数组
     * @return 当前对象
     */
    Criteria<T> addSubCriteria( SubCriteria<?>... subCriteriaArray );

    /**
     * 添加子查询条件对象
     * @param subCriteriaList 子查询条件对象集合
     * @return 当前对象
     */
    Criteria<T> addSubCriteria( Collection<SubCriteria<?>> subCriteriaList );

    /**
     * 获取聚合函数对象
     * @param alias 聚合函数别名
     * @return 聚合函数对象
     */
    default Aggregation getFunction( String alias ) {
        return null;
    }

    /**
     * 检查是否存在条件(包含WHERE/GROUP BY/HAVING/ORDER BY)
     * @return true: 是 , false: 否
     */
    default boolean isHasCondition() {
        return false;
    }

    /**
     * 是否只查询聚合函数
     * @return true: 是, false: 否
     */
    default boolean isOnly() {
        return false;
    }

    /**
     * 是否包含聚合函数
     * @return true: 是, false: 否
     */
    default boolean isInclude() {
        return true;
    }

    /**
     * 获取乐观锁更新值
     * @return 乐观锁值
     */
    default Object getModifyVersionValue() {
        return 0;
    }

    /**
     * 获取乐观锁条件值
     * @return 乐观锁值
     */
    default Object getConditionVersionValue() {
        return 0;
    }

    /**
     * 是否启用别名
     * @param enabled 是否启用
     * @return 当前对象
     */
    Criteria<T> enableAlias( boolean enabled );

    /**
     * 设置自定义resultMap key
     * @param resultMap result key
     * @return 当前对象
     */
    default Criteria<T> resultMap( String resultMap ) {
        return this;
    }

    /**
     * 设置自定义返回值类型
     * @param resultType 返回值类型
     * @return 当前对象
     */
    default Criteria<T> resultType( Class<?> resultType ) {
        return this;
    }
    
}
