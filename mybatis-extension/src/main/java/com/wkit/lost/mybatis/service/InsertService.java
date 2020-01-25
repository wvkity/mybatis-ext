package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.batch.BatchDataBeanWrapper;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;

/**
 * 保存操作接口
 * @param <T> 泛型类型
 */
interface InsertService<T> {

    int DEFAULT_BATCH_SIZE = 200;

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int save( final T entity );

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int saveNotWithNull( final T entity );

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     */
    @SuppressWarnings( { "unchecked" } )
    default int batchSave( T... entities ) {
        return batchSave( ArrayUtil.toList( entities ), DEFAULT_BATCH_SIZE );
    }

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     */
    default int batchSave( final Collection<T> entities ) {
        return batchSave( entities, DEFAULT_BATCH_SIZE );
    }

    /**
     * 批量保存
     * @param entities  待保存对象集合
     * @param batchSize 批量大小
     * @return 受影响行数
     */
    default int batchSave( final Collection<T> entities, int batchSize ) {
        return batchSave( BatchDataBeanWrapper.wrap( entities, batchSize ) );
    }

    /**
     * 批量保存
     * @param wrapper 包装对象
     * @return 受影响行数
     */
    int batchSave( final BatchDataBeanWrapper<T> wrapper );

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     */
    @SuppressWarnings( { "unchecked" } )
    default int batchSaveNotWithAudit( T... entities ) {
        return batchSaveNotWithAudit( ArrayUtil.toList( entities ), DEFAULT_BATCH_SIZE );
    }

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     */
    default int batchSaveNotWithAudit( final Collection<T> entities ) {
        return batchSaveNotWithAudit( entities, DEFAULT_BATCH_SIZE );
    }

    /**
     * 批量保存
     * @param entities  待保存对象集合
     * @param batchSize 批量大小
     * @return 受影响行数
     */
    default int batchSaveNotWithAudit(final Collection<T> entities, int batchSize) {
        return batchSaveNotWithAudit( BatchDataBeanWrapper.wrap( entities, batchSize ) );
    }

    /**
     * 批量保存
     * @param wrapper 包装对象
     * @return 受影响行数
     */
    int batchSaveNotWithAudit( final BatchDataBeanWrapper<T> wrapper );
    
    /**
     * 批量保存
     * @param entities 待保存对象数组
     * @return 受影响行数
     * @see #embeddedBatchSave(Collection, int)
     */
    @SuppressWarnings( { "unchecked" } )
    default int embeddedBatchSave( T... entities ) {
        return embeddedBatchSave( ArrayUtil.toList( entities ), DEFAULT_BATCH_SIZE );
    }

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     * @see #embeddedBatchSave(Collection, int)
     */
    default int embeddedBatchSave( Collection<T> entities ) {
        return embeddedBatchSave( entities, DEFAULT_BATCH_SIZE );
    }

    /**
     * 批量保存
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@link org.apache.ibatis.session.SqlSession}
     * 是由{@link org.mybatis.spring.SqlSessionUtils}创建的，需要注意以下问题: <br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、在执行批量操作前，当前事务下executorType不为BATCH类型，
     * 则无法获取到{@link org.apache.ibatis.session.SqlSession}出现NPE异常，建议将{@link org.mybatis.spring.SqlSessionTemplate}
     * 中的`SIMPLE`模式修改为`BATCH`模式，或者如果可以的话，将批量操作放置其他增删改操作之前.<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、
     * </p>
     * @param entities  待保存对象集合
     * @param batchSize 批量大小
     * @return 受影响行数
     */
    int embeddedBatchSave( Collection<T> entities, int batchSize );

    /**
     * 批量保存
     * @param entities 待保存对象数组
     * @return 受影响行数
     * @see #embeddedBatchSaveNotWithNull(Collection, int)
     */
    @SuppressWarnings( { "unchecked" } )
    default int embeddedBatchSaveNotWithNull( T... entities ) {
        return embeddedBatchSaveNotWithNull( ArrayUtil.toList( entities ), DEFAULT_BATCH_SIZE );
    }

    /**
     * 批量保存
     * @param entities 待保存对象集合
     * @return 受影响行数
     * @see #embeddedBatchSaveNotWithNull(Collection, int)
     */
    default int embeddedBatchSaveNotWithNull( Collection<T> entities ) {
        return embeddedBatchSaveNotWithNull( entities, DEFAULT_BATCH_SIZE );
    }

    /**
     * 批量保存
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@link org.apache.ibatis.session.SqlSession}
     * 是由{@link org.mybatis.spring.SqlSessionUtils}创建的，需要注意以下问题: <br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、在执行批量操作前，当前事务下executorType不为BATCH类型，
     * 则无法获取到{@link org.apache.ibatis.session.SqlSession}出现NPE异常，建议将{@link org.mybatis.spring.SqlSessionTemplate}
     * 中的`SIMPLE`模式修改为`BATCH`模式，或者如果可以的话，将批量操作放置其他增删改操作之前.<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、
     * </p>
     * @param entities  待保存对象集合
     * @param batchSize 批量大小
     * @return 受影响行数
     */
    int embeddedBatchSaveNotWithNull( Collection<T> entities, int batchSize );

}
