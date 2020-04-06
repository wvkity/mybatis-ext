package com.wkit.lost.mybatis.core.snowflake.factory;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.concurrent.TimeUnit;

@Setter
@Accessors( chain = true )
public class SecondsSequenceFactory extends ElasticSequenceFactory {

    public SecondsSequenceFactory() {
        this( SECOND_EPOCH_TIMESTAMP );
    }

    public SecondsSequenceFactory( long epochTimestamp ) {
        super.timestampBits = 38;
        super.workerBits = 5;
        super.dataCenterBits = 5;
        super.sequenceBits = 15;
        super.epochTimestamp = epochTimestamp;
        super.timeUnit = TimeUnit.SECONDS;
    }
}
