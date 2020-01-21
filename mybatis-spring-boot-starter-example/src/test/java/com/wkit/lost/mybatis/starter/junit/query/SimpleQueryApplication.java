package com.wkit.lost.mybatis.starter.junit.query;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.core.Order;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.service.ResultService;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.vo.ResultVo;
import com.wkit.lost.mybatis.starter.example.vo.StudentVo;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class SimpleQueryApplication extends RootTestRunner {

    @Inject
    ResultService resultService;
    
    @Inject
    StudentService studentService;

    @Test
    public void eqTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria( "RS" );
        resultCriteria.eq( Result::getScore, 89 ).asc( "id" ).autoMappingColumnAlias();
        List<ResultVo> results = resultService.list( resultCriteria );
        log.info( "查询结果：{}", results );
    }

    @Test
    public void neTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria( "RS" );
        resultCriteria.ne( Result::getScore, 89 ).asc( "id" );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }

    @Test
    public void ltTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria( "RS" );
        resultCriteria.lt( Result::getScore, 89 ).asc( "id" );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }

    @Test
    public void leTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria( "RS" );
        resultCriteria.le( Result::getScore, 89 ).asc( "id" );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }

    @Test
    public void gtTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria( "RS" );
        resultCriteria.gt( Result::getScore, 89 ).asc( "id" );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }

    @Test
    public void geTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria( "RS" );
        resultCriteria.ge( Result::getScore, 89 )
                .asc( "id" )
                .desc( "score", "subjectId" )
                .asc( "examDate" )
                .asc( "studentId" );
                //.addOrder( Order.aliasAsc( "RS", "STUDENT_ID" ) );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }
    
    @Test
    public void templateTest() {
        CriteriaImpl<Student> criteria = studentService.getCriteria();
        String template = "DATE_FORMAT({}, '%Y-%m-%d') = {}";
        criteria.template( template, Student::getBirthday, "1994-12-05" )
                .orTemplate( template, "birthday", "1995-09-10" );
        List<StudentVo> result = studentService.list( criteria );
        log.info( "查询结果：{}", result );
    }
}
