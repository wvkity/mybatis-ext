package com.wkit.lost.mybatis.starter.junit.update;

import com.wkit.lost.mybatis.core.criteria.CriteriaImpl;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class CriteriaUpdate extends RootTestRunner {
    
    @Inject
    private StudentService studentService;
    
    @Test
    public void updateTest1() {
        CriteriaImpl<Student> criteria = studentService.getCriteria();
        criteria.update( Student::getAddress, "广东省广州市天河区岗顶xxx路xxx单元5号" )
                .update( "userName", 5 );
        criteria.idEq( "S1101001" );
        int result = studentService.update( criteria );
        log.info( "执行结果：{}", result );
    }
}
