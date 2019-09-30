package com.wkit.lost.mybatis.starter.example.junit.service;

import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.starter.example.beans.SysUser;
import com.wkit.lost.mybatis.starter.example.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class LogicalDeletionApp extends RootTestRunner {

    @Inject
    private SysUserService userService;

    @Test
    public void deletionTest() {
        int result = userService.delete( 16L );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void deletionTest1() {
        CriteriaImpl<SysUser> criteria = userService.getCriteria();
        criteria.idEq( 18L ).eq( SysUser::getState, 7 ).eq( SysUser::getCreateUser, "张三" );
        int result = userService.delete( criteria );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void deletionTest2() {
        int result = userService.delete( new SysUser().setId( 17L ).setState( 8 ) );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void logicDeleteTest() {
        SysUser sysUser = new SysUser();
        sysUser.setId( 11L );
        int result = userService.logicDelete( sysUser );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void logicDeleteTest2() {
        CriteriaImpl<SysUser> criteria = userService.getCriteria();
        criteria.idEq( 13L ).eq( SysUser::getState, 3 );
        int result = userService.logicDelete( criteria );
        log.info( "执行结果: {}", result );
    }
}
