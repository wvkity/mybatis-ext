package com.wkit.lost.mybatis.starter.example.config;

import com.wkit.lost.mybatis.data.auditing.AuditorAware;
import com.wkit.lost.mybatis.plugins.data.auditing.MetadataAuditingInterceptor;
import com.wkit.lost.mybatis.plugins.locker.OptimisticLockerInterceptor;
import com.wkit.lost.mybatis.spring.boot.sequence.SequenceProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan( basePackages = { "com.wkit.lost.mybatis.starter.example.mapper" } )
public class MyBatisConfigurer {

    //@Bean
    public SequenceProperties sequenceConfiguration() {
        SequenceProperties configuration = new SequenceProperties();
        configuration.setWorkerId( 1L ).setDataCenterId( 3L );
        return configuration;
    }
    
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
    
    @Bean
    public AuditorAware getAuditorAware() {
        return new DefaultAuditorAware();
    }
    
    @Bean
    public MetadataAuditingInterceptor auditingInterceptor() {
        return new MetadataAuditingInterceptor();
    }
}
