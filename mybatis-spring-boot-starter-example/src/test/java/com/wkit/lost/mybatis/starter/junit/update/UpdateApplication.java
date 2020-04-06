package com.wkit.lost.mybatis.starter.junit.update;

import com.wkit.lost.mybatis.core.wrapper.criteria.QueryCriteria;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;

@Log4j2
public class UpdateApplication extends RootTestRunner {

    @Inject
    private UserService userService;

    @Test
    public void updateTest() {
        User user = new User();
        user.setId( 1L ).setUserName( "李四" ).setPassword( "123456c" )
                .setState( 2 ).setScore( 87 ).setSex( 1 ).setVersion( 1 )
                .setCreatedUserId( DEF_SYS_USER_ID ).setCreatedUserName( DEF_SYS_USER_NAME )
                .setGmtCreated( LocalDateTime.now() ).setDeleted( false );
        int result = userService.update( user );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void updateNotWithNullTest() {
        User user = new User();
        user.setId( 1L ).setUserName( "张三" )
                .setState( 2 ).setScore( 87 ).setVersion( 2 );
        int result = userService.updateNotWithNull( user );
        log.info( "执行结果: {}", result );
    }
    
    @Test
    public void updateNotWithLockingTest() {
        User user = new User();
        user.setId( 1L ).setUserName( "李四" ).setPassword( "123456c" )
                .setState( 2 ).setScore( 87 ).setSex( 1 ).setVersion( 1 )
                .setCreatedUserId( DEF_SYS_USER_ID ).setCreatedUserName( DEF_SYS_USER_NAME )
                .setGmtCreated( LocalDateTime.now() ).setDeleted( false );
        int result = userService.updateNotWithLocking( user );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void updateNotWithNullAndLockingTest() {
        User user = new User();
        user.setId( 1L ).setUserName( "张三" )
                .setState( 1 ).setScore( 99 ).setVersion( 2 ).setVersion( 2 );
        int result = userService.updateNotWithNullAndLocking( user );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void mixinUpdateNotWithNullTest() {
        User user = new User();
        user.setUserName( "张三" )
                .setState( 1 ).setScore( 99 ).setVersion( 2 ).setVersion( 2 );
        QueryCriteria<User> criteria = new QueryCriteria<>( User.class );
        criteria.idEq( 1L ).eq( User::getSex, 1, User::getVersion, 1 );
        int result = userService.updateNotWithNull( user, criteria );
        log.info( "执行结果: {}", result );
    }
}
