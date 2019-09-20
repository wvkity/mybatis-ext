package com.wkit.lost.mybatis.snowflake.sequence;

import com.wkit.lost.mybatis.snowflake.clock.MillisecondClock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 基于雪花算法生成64位ID
 * @author DT
 */
public class SnowFlakeSequence implements Sequence {

    private TimeUnit timeUnit;
    private long epochTimestamp;
    private long workerId;
    private long dataCenterId;
    private BitsAllocator bitsAllocator;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowFlakeSequence( int timestampBits, int workerIdBits, int dataCenterIdBits, int sequenceBits, long epochTimestamp, long workerId, long dataCenterId ) {
        bitsAllocator = new BitsAllocator( timestampBits, workerIdBits, dataCenterIdBits, sequenceBits );
        if ( workerId > bitsAllocator.getMaxWorkerId() ) {
            throw new SnowFlakeException( "Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId() );
        }
        if ( dataCenterId > bitsAllocator.getMaxDataCenterId() ) {
            throw new SnowFlakeException( "DataCenter id " + dataCenterId + " exceeds the max " + bitsAllocator.getMaxDataCenterId() );
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        if ( epochTimestamp > bitsAllocator.getMaxDeltaTime() ) {
            throw new SnowFlakeException( "epoch timestamp " + epochTimestamp + " exceeds the max " + bitsAllocator.getMaxDeltaTime() );
        }
        this.epochTimestamp = epochTimestamp;
    }

    public SnowFlakeSequence( TimeUnit timeUnit, int timestampBits, int workerIdBits, int dataCenterIdBits, int sequenceBits, long epochTimestamp, long workerId, long dataCenterId ) {
        this.timeUnit = timeUnit;
        bitsAllocator = new BitsAllocator( timestampBits, workerIdBits, dataCenterIdBits, sequenceBits );
        if ( workerId > bitsAllocator.getMaxWorkerId() ) {
            throw new SnowFlakeException( "Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId() );
        }
        if ( dataCenterId > bitsAllocator.getMaxDataCenterId() ) {
            throw new SnowFlakeException( "DataCenter id " + dataCenterId + " exceeds the max " + bitsAllocator.getMaxDataCenterId() );
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        this.epochTimestamp = epochTimestamp;
    }

    @Override
    public synchronized long nextId() {
        long currentTime = getTimestamp();
        if ( currentTime < lastTimestamp ) {
            throw new SnowFlakeException( "Clock moved backwards. Refusing for %s timeStamp", lastTimestamp - currentTime );
        }
        if ( currentTime == lastTimestamp ) {
            sequence = ( sequence + 1 ) & bitsAllocator.getMaxSequence();
            if ( sequence == 0 ) {
                currentTime = getNextTime( lastTimestamp );
            }
        } else {
            //sequence = 0L;
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
        long workerId = (id << (timestampBits + signBits + dataCenterIdBits)) >>> (totalBits - workerIdBits);
        long dataCenterId = (id << (timestampBits + signBits)) >>> (totalBits - dataCenterIdBits);
        long deltaMillis = id >>> ( workerIdBits + dataCenterIdBits + sequenceBits );

        Date thatTime = new Date( timeUnit.toMillis( this.epochTimestamp + deltaMillis ) );
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
        String thatTimeStr = simpleDateFormat.format( thatTime );

        // format as string
        return String.format( "{\"id\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\", \"dataCenterId\":\"%d\",\"sequence\":\"%d\"}", id,
                thatTimeStr, workerId, dataCenterId, sequence );

    }

    private long getTimestamp() {
        long millis = MillisecondClock.currentTimeMillis();
        long timestamp = timeUnit.convert( millis, TimeUnit.MILLISECONDS );
        if ( timestamp - this.epochTimestamp > bitsAllocator.getMaxDeltaTime() ) {
            throw new SnowFlakeException( "Timestamp bits is exhausted. Refusing UID generate. Now: " + timestamp );
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
