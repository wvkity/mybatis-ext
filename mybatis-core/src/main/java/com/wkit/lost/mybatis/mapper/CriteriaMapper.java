package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 查询数据操作接口
 * @param <T> 泛型类
 * @param <R> 返回值类型
 */
interface CriteriaMapper<T, R> {

    /**
     * 根据指定条件对象更新记录
     * @param criteria 条件对象
     * @return 受影响行数
     */
    int updateByCriteria( @Param( Constants.PARAM_CRITERIA ) Criteria<T> criteria );

    /**
     * 根据指定条件对象、实体对象更新记录
     * @param entity   实体对象(更新值部分)
     * @param criteria 条件对象(条件部分)
     * @return 受影响行数
     */
    int mixinUpdateNotWithNull( @Param( Constants.PARAM_ENTITY ) T entity,
                                @Param( Constants.PARAM_CRITERIA ) Criteria<T> criteria );

    /**
     * 根据条件对象删除记录
     * @param criteria 条件对象
     * @return 受影响行数
     */
    int deleteByCriteria( @Param( Constants.PARAM_CRITERIA ) Criteria<T> criteria );

    /**
     * 根据条件对象逻辑删除记录
     * @param criteria 条件对象
     * @return 受影响行数
     */
    int logicDeleteByCriteria( @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );

    /**
     * 查询记录是否存在
     * @param criteria 条件对象
     * @return 记录数
     */
    int existsByCriteria( @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );

    /**
     * 查询列表
     * @param criteria 条件对象
     * @return 列表
     */
    List<R> listByCriteria( @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Object集合
     */
    List<Object> objectList( @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Object集合
     */
    List<Object[]> arrayList( @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Map
     */
    List<Map<String, Object>> mapList( @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );

    /**
     * 分页查询列表
     * @param pageable 分页对象
     * @param criteria 条件对象
     * @return 列表
     */
    List<R> pageableListByCriteria( @Param( Constants.PARAM_PAGEABLE ) final Pageable pageable, 
                                    @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );
}
