package com.wkit.lost.mybatis.snowflake.factory;

import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.snowflake.sequence.SnowflakeSequence;
import com.wkit.lost.mybatis.snowflake.worker.SequenceUtil;
import com.wkit.lost.mybatis.snowflake.worker.WorkerAssigner;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

@Log4j2
@Setter
@NoArgsConstructor
@Accessors( chain = true )
public class ElasticSequenceFactory implements SequenceFactory {

    /**
     * 默认开始时间戳(2019-01-01)
     */
    protected static final long DEFAULT_EPOCH_TIMESTAMP = 1546272000000L;
    protected int timestampBits;
    protected int workerBits;
    protected int dataCenterBits;
    protected int sequenceBits;
    protected long epochTimestamp;
    protected TimeUnit timeUnit;

    @Override
    public Sequence build() {
        return build( getDefaultWorkerId(), getDefaultDataCenterId() );
    }

    @Override
    public Sequence build( WorkerAssigner workerAssigner ) {
        return build( workerAssigner.workerId(), workerAssigner.dataCenterId() );
    }

    @Override
    public Sequence build( long workerId, long dataCenterId ) {
        return new SnowflakeSequence( timeUnit, timestampBits, workerBits, dataCenterBits, sequenceBits, epochTimestamp, workerId, dataCenterId );
    }

    public long getMaxWorkerId() {
        return SequenceUtil.getMaxWorkerId( workerBits );
    }

    public long getMaxDataCenterId() {
        return SequenceUtil.getMaxDataCenterId( dataCenterBits );
    }

    public long getDefaultDataCenterId() {
        return SequenceUtil.getDefaultDataCenterId( dataCenterBits );
    }

    public long getDefaultWorkerId() {
        return SequenceUtil.getDefaultWorkerId( workerBits, dataCenterBits );
    }
}
