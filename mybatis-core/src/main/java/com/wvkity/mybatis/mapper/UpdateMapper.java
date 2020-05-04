package com.wvkity.mybatis.mapper;

import com.wvkity.mybatis.utils.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * 更新数据操作接口
 * @param <T> 泛型类
 */
interface UpdateMapper<T> {

    /**
     * 根据主键更新记录(主键为条件，更新所有字段[updatable=true])
     * <p>注: 当实体类中存在乐观锁且实例对象中对应的属性值不为空，则会自动更新版本，更新成功时值会自动保存到实例对象中</p>
     * @param entity 指定对象
     * @return 受影响行数
     */
    int update(@Param(Constants.PARAM_ENTITY) T entity);

    /**
     * 根据主键非空更新记录(主键为条件，更新可选字段[updatable=true])
     * <p>注: 当实体类中存在乐观锁且实例对象中对应的属性值不为空，则会自动更新版本，更新成功时值会自动保存到实例对象中</p>
     * @param entity 指定对象
     * @return 受影响行数
     */
    int updateNotWithNull(@Param(Constants.PARAM_ENTITY) T entity);

    /**
     * 根据主键不带乐观锁条件更新记录(主键为条件，更新所有字段[updatable=true])
     * @param entity 指定对象
     * @return 受影响行数
     */
    int updateNotWithLocking(@Param(Constants.PARAM_ENTITY) T entity);

    /**
     * 根据主键不带乐观锁条件非空更新记录(主键为条件，更新可选字段[updatable=true])
     * @param entity 指定对象
     * @return 受影响行数
     */
    int updateNotWithNullAndLocking(@Param(Constants.PARAM_ENTITY) T entity);
}
