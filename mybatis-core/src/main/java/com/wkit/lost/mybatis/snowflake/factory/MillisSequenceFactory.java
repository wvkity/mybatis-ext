package com.wkit.lost.mybatis.snowflake.factory;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.concurrent.TimeUnit;

@Setter
@Accessors( chain = true )
public class MillisSequenceFactory extends ElasticSequenceFactory {

    public MillisSequenceFactory() {
        this( DEFAULT_EPOCH_TIMESTAMP );
    }

    public MillisSequenceFactory( long epochTimestamp ) {
        super.timestampBits = 41;
        super.workerBits = 5;
        super.dataCenterBits = 5;
        super.sequenceBits = 12;
        // 默认开始时间：2019-01-01
        super.epochTimestamp = epochTimestamp;
        super.timeUnit = TimeUnit.MILLISECONDS;
    }

}
