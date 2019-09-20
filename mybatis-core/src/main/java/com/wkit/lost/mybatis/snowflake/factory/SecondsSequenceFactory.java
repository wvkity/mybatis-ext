package com.wkit.lost.mybatis.snowflake.factory;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.concurrent.TimeUnit;

@Setter
@Accessors( chain = true )
public class SecondsSequenceFactory extends ElasticSequenceFactory {

    public SecondsSequenceFactory() {
        this( DEFAULT_EPOCH_TIMESTAMP );
    }

    public SecondsSequenceFactory( long epochTimestamp ) {
        super.timestampBits = 31;
        super.workerBits = 5;
        super.dataCenterBits = 5;
        super.sequenceBits = 22;
        // 默认开始时间：2019-01-01
        super.epochTimestamp = epochTimestamp;
        super.timeUnit = TimeUnit.SECONDS;
    }
}
