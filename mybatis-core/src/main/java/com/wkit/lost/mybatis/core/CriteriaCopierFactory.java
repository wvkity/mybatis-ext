package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.core.ForeignCriteria;
import com.wkit.lost.mybatis.core.SubCriteria;
import net.sf.cglib.beans.BeanCopier;

/**
 * Bean实例复制器工厂类
 * @author DT
 */
public final class CriteriaCopierFactory {

    /**
     * {@link CriteriaImpl}实例复制器
     */
    public static final BeanCopier CRITERIA_IMPL_INSTANCE_COPIER = BeanCopier.create( CriteriaImpl.class, CriteriaImpl.class, false );

    /**
     * {@link ForeignCriteria}实例复制器
     */
    public static final BeanCopier FOREIGN_CRITERIA_IMPL_INSTANCE_COPIER = BeanCopier.create( ForeignCriteria.class, ForeignCriteria.class, false );

    /**
     * {@link SubCriteria}实例复制器
     */
    public static final BeanCopier SUB_CRITERIA_IMPL_INSTANCE_COPIER = BeanCopier.create( SubCriteria.class, SubCriteria.class, false );

    /**
     * 深复制实例({@link CriteriaImpl})
     * @param source 源对象
     * @param <T>    类型
     * @return 新实例
     */
    public static <T> CriteriaImpl<T> clone( CriteriaImpl<T> source ) {
        CriteriaImpl<T> instance = new CriteriaImpl<>( source.getEntity() );
        CRITERIA_IMPL_INSTANCE_COPIER.copy( source, instance, null );
        return instance;
    }

    /**
     * 深复制实例({@link ForeignCriteria})
     * @param source 源对象
     * @param <T>    类型
     * @return 新实例
     */
    public static <T> ForeignCriteria<T> clone( ForeignCriteria<T> source ) {
        ForeignCriteria<T> instance = ForeignCriteria.newInstanceForClone( source.getEntity() );
        FOREIGN_CRITERIA_IMPL_INSTANCE_COPIER.copy( source, instance, null );
        return instance;
    }

    /**
     * 深复制实例({@link SubCriteria})
     * @param source 源对象
     * @param <T>    类型
     * @return 新实例
     */
    public static <T> SubCriteria<T> clone( SubCriteria<T> source ) {
        SubCriteria<T> instance = SubCriteria.newInstanceForClone( source.getEntity() );
        SUB_CRITERIA_IMPL_INSTANCE_COPIER.copy( source, instance, null );
        return instance;
    }
}
