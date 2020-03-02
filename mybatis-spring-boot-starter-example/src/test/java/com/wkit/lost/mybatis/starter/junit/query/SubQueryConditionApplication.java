package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.criteria.CriteriaImpl;
import com.wkit.lost.mybatis.core.criteria.SubCriteria;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.vo.StudentVo;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import com.wkit.lost.paging.Pager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

/**
 * 子查询作为条件测试
 */
@Log4j2
public class SubQueryConditionApplication extends RootTestRunner {

    @Inject
    private StudentService studentService;

    @Test
    @DisplayName( "测试子查询-1" )
    public void test1() {
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "t1" );
        SubCriteria<Grade> gradeSubCriteria = studentCriteria.createSub( Grade.class, "t2",
                "gd", ctx -> ctx.eq( Grade::getName, "S2" ) );
        gradeSubCriteria.query( Grade::getId );
        studentCriteria.notIn( Student::getGradeId, gradeSubCriteria ).desc( "name" ).autoMappingColumnAlias( true );
        List<StudentVo> result = studentService.list( studentCriteria, new Pager( 1, 20 ) );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }

    @Test
    @DisplayName( "测试子查询-2" )
    public void test2() {
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria();
        SubCriteria<Grade> gradeSubCriteria = studentCriteria.createSub( Grade.class,
                "gd", ctx -> ctx.eq( Grade::getName, "S2" ) );
        gradeSubCriteria.query( Grade::getId );
        studentCriteria.eq( Student::getGradeId, gradeSubCriteria );
        List<StudentVo> result = studentService.list( studentCriteria.range( 1, 2, 8 ) );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }
}
