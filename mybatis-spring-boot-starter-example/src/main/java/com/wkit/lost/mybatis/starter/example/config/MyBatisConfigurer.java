package com.wkit.lost.mybatis.starter.example.config;

import com.wkit.lost.mybatis.filling.MetaObjectFillAuxiliary;
import com.wkit.lost.mybatis.plugins.interceptor.OptimisticLockerInterceptor;
import com.wkit.lost.mybatis.spring.boot.worker.SequenceAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan( basePackages = { "com.wkit.lost.mybatis.starter.example.mapper" } )
public class MyBatisConfigurer {

    //@Bean
    public SequenceAutoConfiguration sequenceConfiguration() {
        SequenceAutoConfiguration configuration = new SequenceAutoConfiguration();
        configuration.setWorkerId( 1L ).setDataCenterId( 3L );
        return configuration;
    }
    
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean( "metaObjectFillAuxiliary" )
    public MetaObjectFillAuxiliary dependency() {
        return new FillingDependency();
    }
}
