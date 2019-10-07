package com.wkit.lost.mybatis.starter.junit.simple;

import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class SessionFactoryInjector extends RootTestRunner {

    @Inject
    private SqlSessionFactory factory;

    @Test
    @DisplayName( "测试SqlSessionFactory对象是否注入成功" )
    public void test() {
        Assert.assertNotNull( "SqlSessionFactory对象注入失败", factory );
        log.info( "注入的对象: {}", factory );
    }
}
