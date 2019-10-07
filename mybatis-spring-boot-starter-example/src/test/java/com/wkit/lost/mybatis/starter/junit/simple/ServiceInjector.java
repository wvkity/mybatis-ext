package com.wkit.lost.mybatis.starter.junit.simple;

import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class ServiceInjector extends RootTestRunner {

    @Inject
    private StudentService studentService;

    @Test
    public void test() {
        Assert.assertNotNull( "service对象注入失败", studentService );
        log.info( "studentService对象: {}", studentService );
        log.info( "baseMapper对象: {}", studentService.getExecutor() );
    }
}
