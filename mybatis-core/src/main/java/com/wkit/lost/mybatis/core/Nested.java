package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.segment.Segment;

import java.io.Serializable;
import java.util.Collection;

/**
 * 嵌套条件接口
 * @param <Context> 当前对象
 * @author DT
 */
public interface Nested<Context> extends Segment, Serializable {
    
    /**
     * 嵌套条件
     * <p>AND (A [OR | AND] B [OR | AND] C...)</p>
     * @param conditions 条件数组
     * @return 当前对象
     */
    default Context nested( Criterion<?>... conditions ) {
        return nested( ArrayUtil.toList( conditions ) );
    }
    
    /**
     * 嵌套条件
     * <p>AND (A [OR | AND] B [OR | AND] C...)</p>
     * @param criteria   查询对象
     * @param conditions 条件数组
     * @return 当前对象
     */
    default Context nested( Criteria<?> criteria, Criterion<?>... conditions ) {
        return nested( criteria, ArrayUtil.toList( conditions ) );
    }
    
    /**
     * 嵌套条件
     * <p>AND (A [OR | AND] B [OR | AND] C...)</p>
     * @param conditions 条件数组
     * @return 当前对象
     */
    Context nested( Collection<Criterion<?>> conditions );
    
    /**
     * 嵌套条件
     * <p>AND (A [OR | AND] B [OR | AND] C...)</p>
     * @param criteria   查询对象
     * @param conditions 条件集合
     * @return 当前对象
     */
    Context nested( Criteria<?> criteria, Collection<Criterion<?>> conditions );
    
    /**
     * 嵌套条件
     * <p>OR (A [OR | AND] B [OR | AND] C...)</p>
     * @param conditions 条件数组
     * @return 当前对象
     */
    default Context orNested( Criterion<?>... conditions ) {
        return orNested( ArrayUtil.toList( conditions ) );
    }
    
    /**
     * 嵌套条件
     * <p>OR (A [OR | AND] B [OR | AND] C...)</p>
     * @param criteria   查询对象
     * @param conditions 条件数组
     * @return 当前对象
     */
    default Context orNested( Criteria<?> criteria, Criterion<?>... conditions ) {
        return orNested( criteria, ArrayUtil.toList( conditions ) );
    }
    
    /**
     * 嵌套条件
     * <p>OR (A [OR | AND] B [OR | AND] C...)</p>
     * @param conditions 条件数组
     * @return 当前对象
     */
    Context orNested( Collection<Criterion<?>> conditions );
    
    /**
     * 嵌套条件
     * <p>OR (A [OR | AND] B [OR | AND] C...)</p>
     * @param criteria   查询对象
     * @param conditions 条件集合
     * @return 当前对象
     */
    Context orNested( Criteria<?> criteria, Collection<Criterion<?>> conditions );
}
