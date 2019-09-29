package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;
import com.wkit.lost.mybatis.utils.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * 更新数据操作接口
 * @param <T> 泛型类
 */
@EnableMapper
public interface UpdateMapper<T> {

    /**
     * 根据指定对象更新记录(主键为条件，更新所有字段[updatable=true])
     * @param entity 指定对象
     * @return 受影响行数
     */
    int update( @Param( Constants.PARAM_ENTITY ) T entity );

    /**
     * 根据指定对象更新记录(主键为条件，更新可选字段[updatable=true])
     * @param entity 指定对象
     * @return 受影响行数
     */
    int updateSelective( @Param( Constants.PARAM_ENTITY ) T entity );
}
