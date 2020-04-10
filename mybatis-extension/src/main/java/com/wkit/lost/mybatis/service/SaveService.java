package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.batch.BatchDataBeanWrapper;

import java.util.Collection;

/**
 * 保存操作接口
 * @param <T> 泛型类型
 */
public interface SaveService<T> {

    int DEFAULT_BATCH_SIZE = 500;

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int save(final T entity);

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int saveNotWithNull(final T entity);

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     */
    @SuppressWarnings({"unchecked"})
    int batchSave(T... entities);

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     */
    int batchSave(final Collection<T> entities);

    /**
     * 批量保存
     * @param entities  待保存对象集合
     * @param batchSize 批量大小
     * @return 受影响行数
     */
    int batchSave(final Collection<T> entities, int batchSize);

    /**
     * 批量保存
     * @param wrapper 包装对象
     * @return 受影响行数
     */
    int batchSave(final BatchDataBeanWrapper<T> wrapper);

    /**
     * 批量保存(不自动审计)
     * @param entities 待保存对象集合
     * @return 受影响行数
     */
    @SuppressWarnings({"unchecked"})
    int batchSaveNotWithAudit(T... entities);

    /**
     * 批量保存(不自动审计)
     * @param entities 待保存对象集合
     * @return 受影响行数
     */
    int batchSaveNotWithAudit(final Collection<T> entities);

    /**
     * 批量保存(不自动审计)
     * @param entities  待保存对象集合
     * @param batchSize 批量大小
     * @return 受影响行数
     */
    int batchSaveNotWithAudit(final Collection<T> entities, int batchSize);

    /**
     * 批量保存(不自动审计)
     * @param wrapper 包装对象
     * @return 受影响行数
     */
    int batchSaveNotWithAudit(final BatchDataBeanWrapper<T> wrapper);

    /**
     * 批量保存
     * @param entities 待保存对象数组
     * @return 受影响行数
     * @see #embeddedBatchSave(Collection, int)
     */
    @SuppressWarnings({"unchecked"})
    int embeddedBatchSave(T... entities);

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     * @see #embeddedBatchSave(Collection, int)
     */
    int embeddedBatchSave(Collection<T> entities);

    /**
     * 批量保存
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@link org.apache.ibatis.session.SqlSession}
     * 是由{@link org.mybatis.spring.SqlSessionUtils}创建的，需要注意以下问题: <br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、在执行批量操作前，当前事务下executorType不为BATCH模式，
     * 由于无法获取到{@link org.apache.ibatis.session.SqlSession}对象则出现NPE异常，建议将{@link org.mybatis.spring.SqlSessionTemplate}
     * 中的`SIMPLE`模式修改为`BATCH`模式，或者如果可以的话，将批量操作放置其他增删改操作之前.<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、非`BATCH`模式可以使用{@link #batchSave(BatchDataBeanWrapper)}、
     * {@link #batchSaveNotWithAudit(BatchDataBeanWrapper)}方法
     * </p>
     * @param entities  待保存对象集合
     * @param batchSize 批量大小
     * @return 受影响行数
     * @see #batchSave(Collection)
     * @see #batchSave(Collection, int)
     * @see #batchSave(BatchDataBeanWrapper)
     * @see #batchSaveNotWithAudit(Collection)
     * @see #batchSaveNotWithAudit(Collection, int)
     * @see #batchSaveNotWithAudit(BatchDataBeanWrapper)
     */
    int embeddedBatchSave(Collection<T> entities, int batchSize);

    /**
     * 批量保存
     * @param entities 待保存对象数组
     * @return 受影响行数
     * @see #embeddedBatchSaveNotWithNull(Collection, int)
     */
    @SuppressWarnings({"unchecked"})
    int embeddedBatchSaveNotWithNull(T... entities);

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     * @see #embeddedBatchSaveNotWithNull(Collection, int)
     */
    int embeddedBatchSaveNotWithNull(Collection<T> entities);

    /**
     * 批量保存
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@link org.apache.ibatis.session.SqlSession}
     * 是由{@link org.mybatis.spring.SqlSessionUtils}创建的，需要注意以下问题: <br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、在执行批量操作前，当前事务下executorType不为BATCH模式，
     * 由于无法获取到{@link org.apache.ibatis.session.SqlSession}对象则出现NPE异常，建议将{@link org.mybatis.spring.SqlSessionTemplate}
     * 中的`SIMPLE`模式修改为`BATCH`模式，或者如果可以的话，将批量操作放置其他增删改操作之前.<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、非`BATCH`模式可以使用{@link #batchSave(BatchDataBeanWrapper)}、
     * {@link #batchSaveNotWithAudit(BatchDataBeanWrapper)}方法
     * </p>
     * @param entities  待保存对象集合
     * @param batchSize 批量大小
     * @return 受影响行数
     * @see #batchSave(Collection)
     * @see #batchSave(Collection, int)
     * @see #batchSave(BatchDataBeanWrapper)
     * @see #batchSaveNotWithAudit(Collection)
     * @see #batchSaveNotWithAudit(Collection, int)
     * @see #batchSaveNotWithAudit(BatchDataBeanWrapper)
     */
    int embeddedBatchSaveNotWithNull(Collection<T> entities, int batchSize);

}
