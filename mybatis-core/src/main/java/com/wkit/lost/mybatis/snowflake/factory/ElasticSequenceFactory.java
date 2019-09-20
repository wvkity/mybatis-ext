package com.wkit.lost.mybatis.snowflake.factory;

import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.snowflake.sequence.SnowFlakeSequence;
import com.wkit.lost.mybatis.snowflake.worker.WorkerAssigner;
import com.wkit.lost.mybatis.utils.Ascii;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.TimeUnit;

@Log4j2
@Setter
@NoArgsConstructor
@Accessors( chain = true )
public class ElasticSequenceFactory implements SequenceFactory {

    /**
     * 默认开始时间戳(2019-01-01)
     */
    protected static final long DEFAULT_EPOCH_TIMESTAMP = 1546272000L;
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
        return new SnowFlakeSequence( timeUnit, timestampBits, workerBits, dataCenterBits, sequenceBits, epochTimestamp, workerId, dataCenterId );
    }

    public long getMaxWorkerId() {
        return ~( -1L << workerBits );
    }

    public long getMaxDataCenterId() {
        return ~( -1L << dataCenterBits );
    }

    public long getDefaultDataCenterId() {
        long id = 0L;
        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress( address );
            if ( network == null ) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if ( mac != null ) {
                    id = ( ( 0x000000FF & ( long ) mac[ mac.length - 1 ] ) | ( 0x0000FF00 & ( ( ( long ) mac[ mac.length - 2 ] ) << 8 ) ) ) >> 6;
                    id = id % ( getMaxDataCenterId() + 1 );
                }
            }
        } catch ( Exception e ) {
            log.warn( "Failed to get the corresponding worker id according to MAC address: ", e );
        }
        return id;
    }

    public long getDefaultWorkerId() {
        StringBuilder builder = new StringBuilder();
        builder.append( getDefaultDataCenterId() );
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if ( Ascii.isNullOrEmpty( name ) ) {
            builder.append( name.split( "@" )[ 0 ] );
        }
        return ( builder.toString().hashCode() & 0xffff ) % ( getMaxWorkerId() + 1 );
    }
}
