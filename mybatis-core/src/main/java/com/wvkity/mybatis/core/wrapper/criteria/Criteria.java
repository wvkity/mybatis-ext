package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.conditional.criterion.Criterion;
import com.wvkity.mybatis.core.converter.PlaceholderConverter;
import com.wvkity.mybatis.core.converter.PropertyConverter;
import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.core.wrapper.aggreate.Aggregation;

import java.util.Collection;

/**
 * 条件包装接口
 * @param <T> 泛型类型
 * @author wvkity
 */
public interface Criteria<T> extends Search<T>, PlaceholderConverter, PropertyConverter<T>, Segment {

    /**
     * 获取WHERE条件SQL片段
     * @return SQL片段
     */
    String getWhereSegment();

    /**
     * 获取实体类
     * @return 实体类
     */
    Class<T> getEntityClass();

    /**
     * 获取表别名
     * @return 别名
     */
    String as();

    /**
     * 是否启用别名
     * @return true: 是 | false: 否
     */
    default boolean isEnableAlias() {
        return false;
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
     * 添加多个条件
     * @param array 条件对象数组
     * @return 当前对象
     */
    Criteria<T> where(Criterion... array);

    /**
     * 添加多个条件
     * @param list 条件对象集合
     * @return 当前对象
     */
    Criteria<T> where(Collection<Criterion> list);

    /**
     * 添加子查询条件对象
     * @param array 子查询条件对象数组
     * @return 当前对象
     */
    Criteria<T> where(SubCriteria<?>... array);

    /**
     * 添加子查询条件对象
     * @param list 子查询条件对象集合
     * @return 当前对象
     */
    Criteria<T> addSub(Collection<SubCriteria<?>> list);

    /**
     * 联表查询副表引用属性
     * @return 联表查询副表引用属性
     */
    default String getReference() {
        return null;
    }

    /**
     * 开启自动映射列别名(针对查询自动映射属性名)
     * @return true: 是, false: 否
     */
    default boolean isPropertyAutoMappingAlias() {
        return false;
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
        return null;
    }

    /**
     * 获取乐观锁条件值
     * @return 乐观锁值
     */
    default Object getConditionVersionValue() {
        return null;
    }

    /**
     * 获取聚合函数对象
     * @param alias 聚合函数别名
     * @return 聚合函数对象
     */
    default Aggregation getAggregate(String alias) {
        return null;
    }

    /**
     * 是否启用别名
     * @param enabled 是否启用
     * @return 当前对象
     */
    Criteria<T> as(boolean enabled);

}
