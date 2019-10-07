package com.wkit.lost.mybatis.starter.junit.simple;

import com.wkit.lost.mybatis.starter.example.mapper.StudentMapper;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class MapperInjector extends RootTestRunner {

    @Inject
    private StudentMapper studentMapper;

    @Test
    public void test() {
        try {
            Assert.assertNotNull( "mapper对象注入失败", studentMapper );
            log.info( "studentMapper对象: {}", studentMapper );
        } catch ( Exception e ) {
            log.error( "系统出错了：", e );
            throw new RuntimeException( e );
        }
    }
}
