package com.wkit.lost.mybatis.starter.junit.simple;

import com.wkit.lost.mybatis.data.auditing.MetadataAuditable;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

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
}
