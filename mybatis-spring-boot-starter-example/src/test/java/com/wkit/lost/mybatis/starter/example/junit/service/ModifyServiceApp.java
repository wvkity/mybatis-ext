package com.wkit.lost.mybatis.starter.example.junit.service;

import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.starter.example.beans.SysUser;
import com.wkit.lost.mybatis.starter.example.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class ModifyServiceApp extends RootTestRunner {

    @Inject
    private SysUserService sysUserService;

    @Test
    public void updateTest() {
        SysUser sysUser = new SysUser();
        sysUser.setId( 17L ).setUserName( "测试更新" ).setScore( 77 ).setSex( 1 ).setDeleted( 0 ).setPassword( "123456" );
        int result = sysUserService.update( sysUser );
        log.info( "执行结果：{}", result );
    }

    @Test
    public void updateSelectiveTest1() {
        SysUser sysUser = new SysUser();
        sysUser.setId( 15L ).setUserName( "6789" ).setScore( 81 ).setSex( 1 );
        int result = sysUserService.updateSelective( sysUser );
        log.info( "执行结果：{}", result );
    }

    @Test
    public void updateByCriteriaTest() {
        CriteriaImpl<SysUser> criteria = sysUserService.getCriteria();
        criteria.modify( SysUser::getState, 4 )
                .modify( SysUser::getUserName, "我是criteria更新操作" )
                .idEq( 2L )
                .eq( SysUser::getState, 7 );
        int result = sysUserService.update( criteria );
        log.info( "执行结果：{}", result );
    }

    @Test
    public void updateSelectiveTest2() {
        SysUser sysUser = new SysUser();
        sysUser.setState( 8 ).setPassword( "123456b" ).setDeleted( 0 ).setScore( 86 );
        CriteriaImpl<SysUser> criteria = sysUserService.getCriteria();
        criteria.idEq( 14L ).eq( SysUser::getState, 1 );
        int result = sysUserService.updateSelective( sysUser, criteria );
        log.info( "执行结果：{}", result );
    }
}
