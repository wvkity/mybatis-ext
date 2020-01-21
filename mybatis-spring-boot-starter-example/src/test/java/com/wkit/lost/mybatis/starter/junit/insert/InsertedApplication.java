package com.wkit.lost.mybatis.starter.junit.insert;

import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class InsertedApplication extends RootTestRunner {

    @Inject
    private StudentService studentService;

    @Test
    public void test() {
        Student student = new Student();
        student.setAddress( "xxx路" ).setName( "咋还跟你说" ).setGradeId( 1L )
                .setEmail( "399923455@qq.com" ).setPassword( "123456c" )
                .setPhone( "13711999887" ).setSex( 1 );
        int result = studentService.save( student );
        log.info( "执行结果: {}", result );
    }
}
