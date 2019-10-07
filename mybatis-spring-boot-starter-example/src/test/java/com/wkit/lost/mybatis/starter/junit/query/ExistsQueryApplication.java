package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.core.SubCriteria;
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
 * 子查询实现EXISTS用法测试
 */
@Log4j2
public class ExistsQueryApplication extends RootTestRunner {

    @Inject
    private StudentService studentService;

    @Test
    @DisplayName( "测试子查询实现exists-1" )
    public void test1() {
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "t1" );
        SubCriteria<Grade> gradeSubCriteria = studentCriteria.createSub( Grade.class, "t2", "gd", ctx -> ctx.eq( Grade::getName, "S2" ).eq( "id", studentCriteria, "gradeId" ) );
        gradeSubCriteria.query( Grade::getId );
        studentCriteria.exists( gradeSubCriteria ).desc( "name" ).autoMappingColumnAlias( true );
        List<StudentVo> result = studentService.list( new Pager( 1, 20 ), studentCriteria );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }

    @Test
    @DisplayName( "测试子查询实现exists-2" )
    public void test2() {
        // 主表
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "t1" );
        // 子查询
        SubCriteria<Grade> gradeSubCriteria = studentCriteria.createSub( Grade.class, "t2", "gd", ctx -> ctx.eq( Grade::getName, "S2" ).propertyEq( "id", "gradeId" ) );
        gradeSubCriteria.query( Grade::getId );
        studentCriteria.exists( gradeSubCriteria ).desc( "name" ).autoMappingColumnAlias( true );
        List<StudentVo> result = studentService.list( new Pager( 1, 20 ), studentCriteria );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }

    /**
     * @see ForeignQueryApplication#test4()
     */
    @Test
    @DisplayName( "测试子查询实现exists-3" )
    public void test3() {
        // 主表
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "t1" );
        // 子查询
        SubCriteria<Grade> gradeSubCriteria = studentCriteria.createSub( Grade.class, "t2", "gd", ctx -> ctx.eq( Grade::getName, "S2" ).propertyEq( "id", "gradeId" ) );
        gradeSubCriteria.query( Grade::getId );
        studentCriteria.eq( Student::getSex, 1 ).exists( gradeSubCriteria ).desc( "name" ).autoMappingColumnAlias( false );
        List<StudentVo> result = studentService.list( studentCriteria );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }
}
