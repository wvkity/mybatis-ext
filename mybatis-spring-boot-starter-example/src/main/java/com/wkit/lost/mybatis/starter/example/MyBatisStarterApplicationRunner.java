package com.wkit.lost.mybatis.starter.example;

import com.wkit.lost.mybatis.spring.boot.filling.EnableMetaObjectAutoFilling;
import com.wkit.lost.mybatis.spring.boot.worker.EnableSnowflakeSequence;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableSnowflakeSequence( value = true )
@EnableMetaObjectAutoFilling( value = true )
@EnableConfigurationProperties
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy( proxyTargetClass = true )
/*@ComponentScans( {
        @ComponentScan(
                basePackages = { "com.wkit.lost.mybatis.starter.example" },
                excludeFilters = @ComponentScan.Filter( type = FilterType.ANNOTATION, value = { Controller.class, RestController.class } ) ),
        @ComponentScan( { "com.wkit.lost.mybatis.starter.example.controller" } )
} )*/
//@ComponentScan( basePackages = { "com.wkit.lost.mybatis.starter.example" } )
public class MyBatisStarterApplicationRunner implements CommandLineRunner {

    @Override
    public void run( String... args ) throws Exception {
        System.out.println( "mybatis example started success." );
    }

    public static void main( String[] args ) {
        //new SpringApplicationBuilder( MyBatisStarterApplicationRunner.class ).web( WebApplicationType.SERVLET ).run( args );
        SpringApplication.run( MyBatisStarterApplicationRunner.class, args );
    }
}
