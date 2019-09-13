package com.wkit.lost.mybatis.starter.example.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan( basePackages = { "com.wkit.lost.mybatis.starter.example.mapper" } )
public class MyBatisConfigurer {
}
