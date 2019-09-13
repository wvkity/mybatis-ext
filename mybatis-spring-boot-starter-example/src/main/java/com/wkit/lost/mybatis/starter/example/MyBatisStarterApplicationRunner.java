package com.wkit.lost.mybatis.starter.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
