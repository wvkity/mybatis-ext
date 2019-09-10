package com.wkit.lost.mybatis.core.function;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.segment.Segment;

import java.util.List;

/**
 * 聚合函数接口
 * @author DT
 */
public interface Aggregation extends Segment {

    /**
     * 获取别名
     * @return 函数别名
     */
    String getAlias();

    /**
     * 设置别名
     * @param alias 别名
     * @return 当前对象
     */
    Aggregation setAlias( String alias );

    /**
     * 设置连接方式
     * @param logic 连接枚举
     * @return 当前对象
     */
    Aggregation setLogic( Logic logic );

    /**
     * 设置比较符
     * @param comparator 比较符
     * @return 当前对象
     */
    Aggregation setComparator( Comparator comparator );

    /**
     * 设置条件对象
     * @param criteria 条件对象
     * @return 当前对象
     */
    Aggregation setCriteria( Criteria<?> criteria );

    /**
     * 设置是否去重
     * @param distinct 是否去重
     * @return 当前对象
     */
    Aggregation setDistinct( boolean distinct );

    /**
     * 设置值
     * @param values 值
     * @return 当前对象
     */
    Aggregation setValues( List<Object> values );

    /**
     * 设置值
     * @param values 值
     * @return 当前对象
     */
    Aggregation setValues( Object... values );

    /**
     * 转成HAVING条件SQL片段
     * @return SQL片段
     */
    String toQuerySqlSegment();

    /**
     * 转成ORDER BY排序SQL片段
     * @return SQL片段
     */
    String toOrderSqlSegment();
}
