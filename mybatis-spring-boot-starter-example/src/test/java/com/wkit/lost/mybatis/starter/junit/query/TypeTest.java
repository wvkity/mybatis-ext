package com.wkit.lost.mybatis.starter.junit.query;

import com.wkit.lost.mybatis.core.criteria.CriteriaImpl;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class TypeTest extends RootTestRunner {

    @Inject
    private StudentService studentService;

    @Test
    public void test() {
        CriteriaImpl<Student> criteria = studentService.getCriteria();
        log.info( "{}", criteria.getEntityClass() );
    }
}
