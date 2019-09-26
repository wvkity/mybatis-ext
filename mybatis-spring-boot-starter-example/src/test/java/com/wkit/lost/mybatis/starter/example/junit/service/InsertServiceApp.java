package com.wkit.lost.mybatis.starter.example.junit.service;

import com.wkit.lost.mybatis.starter.example.beans.SysUser;
import com.wkit.lost.mybatis.starter.example.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Instant;

@Log4j2
public class InsertServiceApp extends RootTestRunner {

    @Inject
    private SysUserService sysUserService;

    @Test
    public void insert() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName( "测试自动填充" );
        sysUser.setCount( 56L );
        sysUser.setScore( 85 );
        //sysUser.setCreateUser( "我是自动填充" );
        sysUser.setState( 4 ).setPassword( "123456a" );
        int result = sysUserService.saveSelective( sysUser );
        log.info( "执行结果：{}", result );
    }
}
