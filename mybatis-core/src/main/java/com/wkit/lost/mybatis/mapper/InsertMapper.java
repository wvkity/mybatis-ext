package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.batch.BatchDataBeanWrapper;
import com.wkit.lost.mybatis.utils.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * 保存数据操作接口
 * @param <T> 泛型类
 * @author wvkity
 */
public interface InsertMapper<T> {

    int DEFAULT_BATCH_SIZE = 200;

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int insert( @Param( Constants.PARAM_ENTITY ) T entity );

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int insertNotWithNull( @Param( Constants.PARAM_ENTITY ) T entity );

    /**
     * 批量保存记录
     * <p>保存对象会自动审计</p>
     * @param dataWrapper 待保存包装对象
     * @return 受影响行数
     */
    int batchInsert( @Param( Constants.PARAM_BATCH_BEAN_WRAPPER ) BatchDataBeanWrapper<T> dataWrapper );

    /**
     * 批量保存记录
     * <p>保存对象不会自动审计</p>
     * @param dataWrapper 待保存包装对象
     * @return 受影响行数
     */
    int batchInsertNotWithAudit( @Param( Constants.PARAM_BATCH_BEAN_WRAPPER ) BatchDataBeanWrapper<T> dataWrapper );

}
