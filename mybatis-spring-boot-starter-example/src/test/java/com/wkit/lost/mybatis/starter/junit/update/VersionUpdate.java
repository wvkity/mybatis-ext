package com.wkit.lost.mybatis.starter.junit.update;

import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Log4j2
public class VersionUpdate extends RootTestRunner {

    static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Inject
    private UserService userService;

    @Test
    public void integerVersionTest() {
        User user = new User();
        user.setId( 4L ).setIntegerVersion( 4 ).setUserName( "测试修改" );
        int result = userService.updateNotWithNull( user );
        log.info( "执行结果: {}", result );
        log.info( "执行后的entity信息：{}", user );
    }
    
    @Test
    public void integerVersionNoLockTest() {
        User user = new User();
        user.setId( 14L ).setIntegerVersion( 4 ).setUserName( "测试无锁更新..." );
        int result = userService.updateNotWithNullAndLocking( user );
        log.info( "执行结果: {}", result );
        log.info( "执行后的entity信息：{}", user );
    }

    @Test
    public void versionByCriteriaTest() {
        CriteriaImpl<User> criteria = userService.getCriteria();
        criteria.update( "userName", "我是测试乐观锁" )
                .update( "password", "admin123456" )
                .idEq( 10L )
                .version( 10 );
        int result = userService.update( criteria );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void timeVersionTest() {
        User user = new User();
        LocalDateTime dateTime = transform( TIME_PATTERN, "2019-12-11 14:48:55" );
        user.setId( 11L )
                .setUserName( "我是测试时间乐观锁" )
                .setPassword( "root123456" )
                .setTimeVersion( Date.from( dateTime.atZone( ZoneId.systemDefault() ).toInstant() ) );
        int result = userService.updateNotWithNull( user );
        log.info( "执行结果: {}", result );
    }
    
    @Test
    public void integerVersionUpdateTest() {
        User user = new User();
        user.setId( 15L ).setIntegerVersion( 2 ).setUserName( "updateVersion测试" ).setPassword( "ADMIN" );
        int result = userService.update( user );
        log.info( "执行结果: {}", result );
        log.info( "执行后的entity信息：{}", user );
    }

    @Test
    public void integerVersionUpdateNoLockTest() {
        User user = new User();
        user.setId( 20L ).setIntegerVersion( 2 ).setUserName( "updateVersionNoLock测试" ).setPassword( "ADMIN" );
        int result = userService.updateNotWithLocking( user );
        log.info( "执行结果: {}", result );
        log.info( "执行后的entity信息：{}", user );
    }

    private LocalDateTime transform( String pattern, String time ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
        return LocalDateTime.parse( time, formatter );
    }
}
