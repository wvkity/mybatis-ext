package com.wvkity.mybatis.core.snowflake.worker;

/**
 * WorkerId分配器
 * @author wvkity
 */
public interface WorkerAssigner {

    /**
     * 分配机器标识
     * @return 机器标识码
     */
    long workerId();

    /**
     * 分配数据中心ID
     * @return 数据中心ID
     */
    long dataCenterId();
}
