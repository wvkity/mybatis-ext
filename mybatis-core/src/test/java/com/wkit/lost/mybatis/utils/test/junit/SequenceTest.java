package com.wkit.lost.mybatis.utils.test.junit;

import com.wkit.lost.mybatis.snowflake.factory.ElasticSequenceFactory;
import com.wkit.lost.mybatis.snowflake.factory.MillisSequenceFactory;
import com.wkit.lost.mybatis.snowflake.factory.SecondsSequenceFactory;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.snowflake.worker.DefaultWorkerAssigner;
import com.wkit.lost.mybatis.snowflake.worker.WorkerAssigner;

import java.util.concurrent.TimeUnit;

public class SequenceTest {

    public static void main( String[] args ) {


        Sequence secondsIdSequence = IdSequenceHolder.secondsIdSequence;
        for ( int i = 0; i < 10; i++ ) {
            long uid = secondsIdSequence.nextId();
            String jsonText = secondsIdSequence.parse( uid );
            System.out.println( jsonText );
        }
        System.out.println( "######################################################" );

        Sequence millisIdSequence = IdSequenceHolder.millisIdSequence;
        for ( int i = 0; i < 10; i++ ) {
            long uid = millisIdSequence.nextId();
            String jsonText = millisIdSequence.parse( uid );
            System.out.println( jsonText );
        }

    }

    public static class IdSequenceHolder {

        private static Sequence secondsIdSequence;

        private static Sequence millisIdSequence;

        private static Sequence elasticIdSequence;

        static {
            secondsIdSequence = new SecondsSequenceFactory().build( 2L, 0L );
            millisIdSequence = new MillisSequenceFactory().build( 2L, 0L );

            ElasticSequenceFactory elasticFactory = new ElasticSequenceFactory();

            // TimeBits + WorkerBits + SeqBits = 64 -1
            elasticFactory.setTimestampBits( 41 );
            elasticFactory.setWorkerBits( 10 );
            elasticFactory.setSequenceBits( 12 );
            elasticFactory.setTimeUnit( TimeUnit.MILLISECONDS );
            elasticFactory.setEpochTimestamp( 1483200000000L );

            // for complex , you can implements the WorkerIdAssigner to create worker id.
            // e.g. use the simple implement in here.
            WorkerAssigner workerIdAssigner = new DefaultWorkerAssigner( 1L, 0L );
            // or set workerId directly
//            elasticSequence = elasticFactory.create(1L);
            elasticIdSequence = elasticFactory.build( workerIdAssigner );
        }

    }

}
