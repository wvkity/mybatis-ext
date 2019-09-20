package com.wkit.lost.mybatis.snowflake.sequence;

import lombok.Getter;

/**
 * 64位唯一ID分配器
 * @author DT
 */
public class BitsAllocator {

    public static final int TOTAL_BITS = 1 << 6;
    @Getter
    private int signBits = 1;
    @Getter
    private final int timestampBits;
    @Getter
    private final int workerIdBits;
    @Getter
    private final int dataCenterIdBits;
    @Getter
    private final int sequenceBits;
    @Getter
    private final long maxDeltaTime;
    @Getter
    private final long maxWorkerId;
    @Getter
    private final long maxDataCenterId;
    @Getter
    private final long maxSequence;
    @Getter
    private final int timestampShift;
    @Getter
    private final int workerIdShift;
    @Getter
    private final int dataCenterIdShift;

    public BitsAllocator( int timestampBits, int workerIdBits, int dataCenterIdBits, int sequenceBits ) {
        int allocateTotalBits = signBits + timestampBits + workerIdBits + dataCenterIdBits + sequenceBits;
        if ( allocateTotalBits > TOTAL_BITS ) {
            throw new SnowFlakeException( "allocate not enough 64 bits." );
        }
        // 初始化
        this.timestampBits = timestampBits;
        this.workerIdBits = workerIdBits;
        this.dataCenterIdBits = dataCenterIdBits;
        this.sequenceBits = sequenceBits;

        // 初始化最大值
        this.maxDeltaTime = ~( -1L << timestampBits );
        this.maxWorkerId = ~( -1L << workerIdBits );
        this.maxDataCenterId = ~( -1L << dataCenterIdBits );
        this.maxSequence = ~( -1L << sequenceBits );

        this.timestampShift = workerIdBits + dataCenterIdBits + sequenceBits;
        this.dataCenterIdShift = sequenceBits + workerIdBits;
        this.workerIdShift = sequenceBits;
    }

    public long allocate( long deltaTime, long workerId, long dataCenterId, long sequence ) {
        return ( deltaTime << timestampShift ) | ( dataCenterId << dataCenterIdShift ) | ( workerId << workerIdShift ) | sequence;
    }
}
