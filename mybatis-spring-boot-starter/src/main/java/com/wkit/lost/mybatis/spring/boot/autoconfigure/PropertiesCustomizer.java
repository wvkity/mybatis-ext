package com.wkit.lost.mybatis.spring.boot.autoconfigure;

@FunctionalInterface
public interface PropertiesCustomizer {

    void customize(MyBatisProperties properties);
}
