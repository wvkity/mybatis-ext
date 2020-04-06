package com.wkit.lost.mybatis.core.snowflake.worker;

/**
 * 默认WorkerId分配器
 * @author wvkity
 */
public class DefaultWorkerAssigner implements WorkerAssigner {

    private long workerId;
    private long dataCenterId;

    public DefaultWorkerAssigner( long workerId, long dataCenterId ) {
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    @Override
    public long workerId() {
        return this.workerId;
    }

    @Override
    public long dataCenterId() {
        return this.dataCenterId;
    }
}
