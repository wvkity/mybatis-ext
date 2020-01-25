package com.wkit.lost.mybatis.starter.junit.simple;

import com.wkit.lost.mybatis.data.auditing.MetadataAuditable;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class MetadataAuditingApplication extends RootTestRunner {

    @Autowired
    private MetadataAuditable metadataAuditable;

    @Inject
    private Sequence sequence;

    @Inject
    private StudentService studentService;

    @Inject
    private UserService userService;

    int nextValue( int start, int end ) {
        return ( int ) ( Math.random() * ( end - start + 1 ) + start );
    }

    List<User> build( int size ) {
        List<User> list = new ArrayList<>( size );
        for ( int i = 0; i < size; i++ ) {
            User user = new User();
            user.setUserName( "测试批量保存" + ( i + 1 ) )
                    .setSex( 0 )
                    .setState( nextValue( 1, 10 ) )
                    .setPassword( "1234456" )
                    .setScore( nextValue( 1, 100 ) )
                    .setIntegerVersion( 1 );
            list.add( user );
        }
        return list;
    }


    @Test
    public void test() {
        log.info( "{}", metadataAuditable );
        log.info( "auditorAware -> {}", metadataAuditable.enableInsertedAuditable() );
    }

    @Test
    public void sequenceTest() {
        log.info( "{}", sequence );
        long id = sequence.nextValue();
        log.info( "{}", id );
        log.info( "{}", sequence.parse( id ) );
    }

    @Test
    public void saveTest() {
        Student student = new Student();
        student.setName( "测试保存操作审计" ).setSex( 1 )
                .setGradeId( 2L ).setPhone( "123456778" ).setPassword( "123456a" );
        int result = studentService.save( student );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void insertTest() {
        User user = new User();
        user.setUserName( "测试保存操作审计" ).setPassword( "123456a" )
                .setScore( 77 ).setState( 1 ).setSex( 1 );
        int result = userService.save( user );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void updateTest() {
        User user = new User();
        user.setId( 14334062462504961L )
                .setUserName( "测试更新操作审计" )
                .setSex( 0 )
                .setState( 3 )
                .setPassword( "1234456" )
                .setScore( 89 );
        int result = userService.updateNotWithNull( user );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void versionTest() {
        User user = new User();
        user.setId( 14339874132070402L )
                .setUserName( "测试更新操作审计" )
                .setSex( 0 )
                .setState( 3 )
                .setPassword( "1234456" )
                .setScore( 89 )
                .setIntegerVersion( 1 );
        int result = userService.updateNotWithNull( user );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void batchSaveTest() {
        Grade grade = new Grade();
        grade.setId( 4L ).setName( "X2" );
        int result = userService.testSave( grade, build( 2 ) );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void batchSaveTest2() {
        Grade grade = new Grade();
        grade.setId( 7L ).setName( "X5" );
        int result = userService.testSave( grade, build( 3 ) );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void batchSaveTest3() {
        List<User> list =  build( 3 );
        Instant start = Instant.now();
        int result = userService.batchSave( list );
        Instant end = Instant.now();
        log.info( "耗时: {}", ChronoUnit.MILLIS.between( start, end ) );
        log.info( "执行结果: {}", result );
    }

    @Test
    public void batchSaveNotWithNullTest() {
        List<User> list =  build( 1000 );
        Instant start = Instant.now();
        int result = userService.batchSaveNotWithAudit( list );
        Instant end = Instant.now();
        log.info( "耗时: {}", ChronoUnit.MILLIS.between( start, end ) );
        log.info( "执行结果: {}", result );
    }
}
