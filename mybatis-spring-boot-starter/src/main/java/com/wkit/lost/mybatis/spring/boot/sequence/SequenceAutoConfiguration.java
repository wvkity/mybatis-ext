package com.wkit.lost.mybatis.spring.boot.sequence;

import com.wkit.lost.mybatis.snowflake.factory.MillisecondsSequenceFactory;
import com.wkit.lost.mybatis.snowflake.factory.SecondsSequenceFactory;
import com.wkit.lost.mybatis.snowflake.sequence.Level;
import com.wkit.lost.mybatis.snowflake.sequence.Rule;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.snowflake.worker.SequenceUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@EnableConfigurationProperties( SequenceProperties.class )
public class SequenceAutoConfiguration {

    private static final int WORKER_BIT = 5;
    private static final int DATA_CENTER_BIT = 5;

    private final SequenceProperties properties;

    public SequenceAutoConfiguration( SequenceProperties properties ) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public Sequence sequence() {
        return Optional.ofNullable( properties ).map( it -> {
            if ( it.getLevel() == Level.SECONDS ) {
                if ( it.getRule() == Rule.MAC ) {
                    return new SecondsSequenceFactory( it.getEpochTimestamp() )
                            .build( SequenceUtil.getDefaultWorkerId( WORKER_BIT, DATA_CENTER_BIT ),
                                    SequenceUtil.getDefaultDataCenterId( DATA_CENTER_BIT ) );
                } else {
                    return new SecondsSequenceFactory( it.getEpochTimestamp() )
                            .build( it.getWorkerId(), it.getDataCenterId() );
                }
            } else {
                if ( it.getRule() == Rule.MAC ) {
                    return new MillisecondsSequenceFactory( it.getEpochTimestamp() )
                            .build( SequenceUtil.getDefaultWorkerId( WORKER_BIT, DATA_CENTER_BIT ),
                                    SequenceUtil.getDefaultDataCenterId( DATA_CENTER_BIT ) );
                } else {
                    return new MillisecondsSequenceFactory( it.getEpochTimestamp() )
                            .build( it.getWorkerId(), it.getDataCenterId() );
                }
            }
        } ).orElse( new MillisecondsSequenceFactory().build() );
    }
}
