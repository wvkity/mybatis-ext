package com.wkit.lost.mybatis.core.snowflake.factory;

import com.wkit.lost.mybatis.core.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.core.snowflake.sequence.SnowflakeSequence;
import com.wkit.lost.mybatis.core.snowflake.worker.SequenceUtil;
import com.wkit.lost.mybatis.core.snowflake.worker.WorkerAssigner;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

@Log4j2
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ElasticSequenceFactory implements SequenceFactory {

    /**
     * 默认开始时间(毫秒: 2020-01-25 00:00:00)
     */
    public static final long MILLIS_EPOCH_TIMESTAMP = 1579881600000L;
    /**
     * 默认开始时间(秒: 2020-01-25 00:00:00)
     */
    public static final long SECOND_EPOCH_TIMESTAMP = 1579881600L;
    protected int timestampBits;
    protected int workerBits;
    protected int dataCenterBits;
    protected int sequenceBits;
    protected long epochTimestamp;
    protected TimeUnit timeUnit;

    @Override
    public Sequence build() {
        return build(getDefaultWorkerId(), getDefaultDataCenterId());
    }

    @Override
    public Sequence build(WorkerAssigner workerAssigner) {
        return build(workerAssigner.workerId(), workerAssigner.dataCenterId());
    }

    @Override
    public Sequence build(long workerId, long dataCenterId) {
        return new SnowflakeSequence(timeUnit, timestampBits, workerBits, dataCenterBits, sequenceBits, epochTimestamp, workerId, dataCenterId);
    }

    public long getMaxWorkerId() {
        return SequenceUtil.getMaxWorkerId(workerBits);
    }

    public long getMaxDataCenterId() {
        return SequenceUtil.getMaxDataCenterId(dataCenterBits);
    }

    public long getDefaultDataCenterId() {
        return SequenceUtil.getDefaultDataCenterId(dataCenterBits);
    }

    public long getDefaultWorkerId() {
        return SequenceUtil.getDefaultWorkerId(workerBits, dataCenterBits);
    }
}
