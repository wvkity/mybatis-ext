package com.wkit.lost.mybatis.starter.example;

import com.wkit.lost.mybatis.plugins.data.auditing.SystemBuiltinAuditingInterceptor;
import com.wkit.lost.mybatis.spring.boot.data.auditing.EnableMetadataAuditing;
import com.wkit.lost.mybatis.spring.boot.plugin.EnableInterceptors;
import com.wkit.lost.mybatis.spring.boot.plugin.Plugin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableMetadataAuditing( automatic = true )
@EnableInterceptors
//( interceptors = { @Plugin( value = SystemBuiltinAuditingInterceptor.class, order = 33 ) } )
@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy( proxyTargetClass = true )
public class MyBatisStarterApplicationRunner implements CommandLineRunner {

    @Override
    public void run( String... args ) throws Exception {
        System.out.println( "mybatis example started success." );
    }

    public static void main( String[] args ) {
        SpringApplication.run( MyBatisStarterApplicationRunner.class, args );
    }
}
