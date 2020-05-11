package com.wvkity.mybatis.starter.example;

import com.wvkity.mybatis.spring.boot.data.auditing.EnableMetadataAuditing;
import com.wvkity.mybatis.spring.boot.plugin.EnableInterceptors;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Log4j2
@EnableMetadataAuditing
@EnableInterceptors
//( interceptors = { @Plugin( value = SystemBuiltinAuditingInterceptor.class, order = 33 ) } )
@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MyBatisStarterApplicationRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("mybatis example started success.");
    }

    public static void main(String[] args) {
        SpringApplication.run(MyBatisStarterApplicationRunner.class, args);
    }
}
