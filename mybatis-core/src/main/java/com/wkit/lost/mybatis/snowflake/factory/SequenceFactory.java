package com.wkit.lost.mybatis.snowflake.factory;

import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.snowflake.worker.WorkerAssigner;

/**
 * ID序列工厂接口
 * @author DT
 */
public interface SequenceFactory {

    /**
     * 构建ID序列实例
     * @return ID序列实例
     */
    Sequence build();

    /**
     * 构建ID序列实例
     * @param workerAssigner workerId分配器
     * @return ID序列实例
     */
    Sequence build( WorkerAssigner workerAssigner );

    /**
     * 构建ID序列
     * @param workerId     workerId
     * @param dataCenterId 注册中心ID
     * @return ID序列
     */
    Sequence build( long workerId, long dataCenterId );
}
