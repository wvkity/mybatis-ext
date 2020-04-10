package com.wkit.lost.mybatis.core.snowflake.factory;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.concurrent.TimeUnit;

@Setter
@Accessors(chain = true)
public class MillisecondsSequenceFactory extends ElasticSequenceFactory {

    public MillisecondsSequenceFactory() {
        this(MILLIS_EPOCH_TIMESTAMP);
    }

    public MillisecondsSequenceFactory(long epochTimestamp) {
        super.timestampBits = 41;
        super.workerBits = 5;
        super.dataCenterBits = 5;
        super.sequenceBits = 12;
        // 默认开始时间：2019-12-12
        super.epochTimestamp = epochTimestamp;
        super.timeUnit = TimeUnit.MILLISECONDS;
    }

}
