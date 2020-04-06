package com.wkit.lost.mybatis.core.snowflake.sequence;

import com.wkit.lost.mybatis.core.snowflake.clock.MillisecondsClock;
import com.wkit.lost.mybatis.core.snowflake.factory.ElasticSequenceFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 基于雪花算法生成64位ID
 * @author wvkity
 */
public class SnowflakeSequence implements Sequence {

    private TimeUnit timeUnit;
    private long epochTimestamp;
    private long workerId;
    private long dataCenterId;
    private BitsAllocator bitsAllocator;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" );
    private static final String TEMPLATE = "{\"id\": \"%d\", \"timestamp\": \"%s\", \"workerId\": \"%d\", \"dataCenterId\": \"%d\", \"sequence\": \"%d\"}";

    public SnowflakeSequence( int timestampBits, int workerIdBits, int dataCenterIdBits, int sequenceBits, long epochTimestamp, long workerId, long dataCenterId ) {
        bitsAllocator = new BitsAllocator( timestampBits, workerIdBits, dataCenterIdBits, sequenceBits );
        if ( workerId > bitsAllocator.getMaxWorkerId() ) {
            throw new SnowflakeException( "Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId() );
        }
        if ( dataCenterId > bitsAllocator.getMaxDataCenterId() ) {
            throw new SnowflakeException( "DataCenter id " + dataCenterId + " exceeds the max " + bitsAllocator.getMaxDataCenterId() );
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        if ( epochTimestamp > bitsAllocator.getMaxDeltaTime() ) {
            throw new SnowflakeException( "epoch timestamp " + epochTimestamp + " exceeds the max " + bitsAllocator.getMaxDeltaTime() );
        }
        checkTime( epochTimestamp );
    }

    public SnowflakeSequence( TimeUnit timeUnit, int timestampBits, int workerIdBits, int dataCenterIdBits, int sequenceBits, long epochTimestamp, long workerId, long dataCenterId ) {
        this.timeUnit = timeUnit;
        bitsAllocator = new BitsAllocator( timestampBits, workerIdBits, dataCenterIdBits, sequenceBits );
        if ( workerId > bitsAllocator.getMaxWorkerId() ) {
            throw new SnowflakeException( "Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId() );
        }
        if ( dataCenterId > bitsAllocator.getMaxDataCenterId() ) {
            throw new SnowflakeException( "DataCenter id " + dataCenterId + " exceeds the max " + bitsAllocator.getMaxDataCenterId() );
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        checkTime( epochTimestamp );
    }

    public SnowflakeSequence( TimeUnit timeUnit, long epochTimestamp, long workerId, long dataCenterId ) {
        this.timeUnit = timeUnit;
        if ( timeUnit == TimeUnit.MILLISECONDS ) {
            bitsAllocator = new BitsAllocator( 41, 5, 5, 12 );
        } else {
            bitsAllocator = new BitsAllocator( 38, 5, 5, 15 );
        }
        if ( workerId > bitsAllocator.getMaxWorkerId() ) {
            throw new SnowflakeException( "Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId() );
        }
        if ( dataCenterId > bitsAllocator.getMaxDataCenterId() ) {
            throw new SnowflakeException( "DataCenter id " + dataCenterId + " exceeds the max " + bitsAllocator.getMaxDataCenterId() );
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        checkTime( epochTimestamp );
    }

    private void checkTime( long epochTimestamp ) {
        long realEpochTime;
        if ( epochTimestamp <= 0 ) {
            realEpochTime = ( timeUnit == null || timeUnit == TimeUnit.MILLISECONDS ) ? ElasticSequenceFactory.MILLIS_EPOCH_TIMESTAMP : ElasticSequenceFactory.SECOND_EPOCH_TIMESTAMP;
        } else {
            realEpochTime = epochTimestamp;
        }
        this.epochTimestamp = realEpochTime;
    }

    @Override
    public synchronized long nextValue() {
        long currentTime = getTimestamp();
        if ( currentTime < lastTimestamp ) {
            throw new SnowflakeException( "Clock moved backwards. Refusing for %s timeStamp", lastTimestamp - currentTime );
        }
        if ( currentTime == lastTimestamp ) {
            sequence = ( sequence + 1 ) & bitsAllocator.getMaxSequence();
            if ( sequence == 0 ) {
                currentTime = getNextTime( lastTimestamp );
            }
        } else {
            //sequence = 0L;
            // 避免都是从0开始
            sequence = ThreadLocalRandom.current().nextLong( 1, 3 );
        }
        lastTimestamp = currentTime;
        return bitsAllocator.allocate( currentTime - epochTimestamp, workerId, dataCenterId, sequence );
    }

    @Override
    public String parse( long id ) {
        long totalBits = BitsAllocator.TOTAL_BITS;
        long signBits = bitsAllocator.getSignBits();
        long timestampBits = bitsAllocator.getTimestampBits();
        long workerIdBits = bitsAllocator.getWorkerIdBits();
        long dataCenterIdBits = bitsAllocator.getDataCenterIdBits();
        long sequenceBits = bitsAllocator.getSequenceBits();

        // parse id
        long sequence = ( id << ( totalBits - sequenceBits ) ) >>> ( totalBits - sequenceBits );
        long workerId = ( id << ( timestampBits + signBits + dataCenterIdBits ) ) >>> ( totalBits - workerIdBits );
        long dataCenterId = ( id << ( timestampBits + signBits ) ) >>> ( totalBits - dataCenterIdBits );
        long deltaMillis = id >>> ( workerIdBits + dataCenterIdBits + sequenceBits );

        LocalDateTime thatTime = LocalDateTime.ofInstant( Instant.ofEpochMilli( timeUnit.toMillis( this.epochTimestamp + deltaMillis ) ), ZoneId.systemDefault() );
        String thatTimeStr = FORMATTER.format( thatTime );

        // format as string
        return String.format( TEMPLATE, id, thatTimeStr, workerId, dataCenterId, sequence );

    }

    private long getTimestamp() {
        long millis = MillisecondsClock.currentTimeMillis();
        long timestamp = timeUnit.convert( millis, TimeUnit.MILLISECONDS );
        if ( timestamp - this.epochTimestamp > bitsAllocator.getMaxDeltaTime() ) {
            throw new SnowflakeException( "Timestamp bits is exhausted. Refusing UID generate. Now: " + timestamp );
        }
        return timestamp;
    }

    private long getNextTime( long lastTimestamp ) {
        long timestamp = getTimestamp();
        while ( timestamp <= lastTimestamp ) {
            timestamp = getTimestamp();
        }
        return timestamp;
    }
}
