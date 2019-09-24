package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 查询数据操作接口
 * @param <T>  泛型类
 * @param <R>  返回值类型
 */
public interface CriteriaMapper<T, R> {

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
    List<Object> listForObject( @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Object集合
     */
    List<Object[]> listForArray( @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Map
     */
    List<Map<String, Object>> listForMap( @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );

    /**
     * 分页查询列表
     * @param pageable 分页对象
     * @param criteria 条件对象
     * @return 列表
     */
    List<R> pageableListByCriteria( @Param( Constants.PARAM_PAGEABLE ) final Pageable pageable, @Param( Constants.PARAM_CRITERIA ) final Criteria<T> criteria );
}
