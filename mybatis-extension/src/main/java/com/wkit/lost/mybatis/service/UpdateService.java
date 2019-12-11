package com.wkit.lost.mybatis.service;

/**
 * 更细操作接口
 * @param <T> 泛型类型
 * @author wvkity
 */
public interface UpdateService<T> {

    /**
     * 根据指定对象更新记录
     * <p>注: 当实体类中存在乐观锁且实例对象中对应的属性值不为空，则会自动更新版本，更新成功时值会自动保存到实例对象中</p>
     * @param entity 指定对象
     * @return 受影响行数
     */
    int update( final T entity );

    /**
     * 根据指定对象更新记录
     * <p>注: 当实体类中存在乐观锁且实例对象中对应的属性值不为空，则会自动更新版本，更新成功时值会自动保存到实例对象中</p>
     * @param entity 指定对象
     * @return 受影响行数
     */
    int updateSelective( final T entity );

    /**
     * 根据指定对象更新记录(主键为条件，更新所有字段[updatable=true])
     * <p>注: 不主动修改实体对象中@Version字段值</p>
     * @param entity 指定对象
     * @return 受影响行数
     */
    int updateOfNoLock( final T entity );

    /**
     * 根据指定对象更新记录(主键为条件，更新可选字段[updatable=true])
     * <p>注: 不主动修改实体对象中@Version字段值</p>
     * @param entity 指定对象
     * @return 受影响行数
     */
    int updateSelectiveOfNoLock( final T entity );
}
