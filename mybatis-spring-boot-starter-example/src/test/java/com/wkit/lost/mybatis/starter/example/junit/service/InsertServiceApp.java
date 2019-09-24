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
        sysUser.setUserName( "测试主键填充" ).setGmtCreate( Instant.now() );
        sysUser.setCount( 55L );
        sysUser.setScore( 88 );
        sysUser.setCreateUser( "wangzhang" );
        sysUser.setState( 6 ).setPassword( "123456a" );
        int result = sysUserService.save( sysUser );
        log.info( "执行结果：{}", result );
    }
}
