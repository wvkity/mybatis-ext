package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 查询数据操作接口
 * @param <T> 泛型类
 * @param <V> 返回值类型
 */
public interface CriteriaMapper<T, V> {

    /**
     * 根据指定条件对象更新记录
     * @param criteria 条件对象
     * @return 受影响行数
     */
    int updateByCriteria(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

    /**
     * 根据指定条件对象、实体对象更新记录
     * @param entity   实体对象(更新值部分)
     * @param criteria 条件对象(条件部分)
     * @return 受影响行数
     */
    int mixinUpdateNotWithNull(@Param(Constants.PARAM_ENTITY) T entity,
                               @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

    /**
     * 根据条件对象删除记录
     * @param criteria 条件对象
     * @return 受影响行数
     */
    int deleteByCriteria(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

    /**
     * 根据条件对象逻辑删除记录
     * @param criteria 条件对象
     * @return 受影响行数
     */
    int logicDeleteByCriteria(@Param(Constants.PARAM_CRITERIA) final Criteria<T> criteria);

    /**
     * 查询记录是否存在
     * @param criteria 条件对象
     * @return 记录数
     */
    int existsByCriteria(@Param(Constants.PARAM_CRITERIA) final Criteria<T> criteria);

    /**
     * 根据条件对象查询记录数
     * @param criteria 条件对象
     * @return 记录数
     */
    long countByCriteria(@Param(Constants.PARAM_CRITERIA) final Criteria<T> criteria);

    /**
     * 查询列表
     * @param criteria 条件对象
     * @return 列表
     */
    List<V> listByCriteria(@Param(Constants.PARAM_CRITERIA) final Criteria<T> criteria);

}
