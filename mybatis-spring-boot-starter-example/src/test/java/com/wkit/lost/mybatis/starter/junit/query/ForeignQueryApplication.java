package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.criteria.CriteriaImpl;
import com.wkit.lost.mybatis.core.criteria.ForeignCriteria;
import com.wkit.lost.mybatis.core.condition.criterion.Restrictions;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.vo.StudentVo;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

/**
 * 联表查询测试
 */
@Log4j2
public class ForeignQueryApplication extends RootTestRunner {

    @Inject
    private StudentService studentService;

    @Test
    @DisplayName( "测试根据年级获取学生列表-1" )
    public void test1() {
        // 主表
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria();
        studentCriteria.useAlias();
        ForeignCriteria<Grade> gradeForeignCriteria = studentCriteria.createForeign( Grade.class, "",
                null, "gradeId", "id", Restrictions.eq( "name", "S2" ) );
        gradeForeignCriteria.useAlias();
        studentCriteria.addForeign( gradeForeignCriteria )
                .autoMappingColumnAlias( false ).eq( Student::getSex, 1 );
        List<StudentVo> result = studentService.list( studentCriteria );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }

    @Test
    @DisplayName( "测试根据年级获取学生列表-2" )
    public void test2() {
        // 主表
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "sd" );
        // 关联表
        ForeignCriteria<Grade> gradeForeignCriteria = studentCriteria.createForeign( Grade.class, "gd",
                null, "gradeId", "id" );
        gradeForeignCriteria.eq( Grade::getName, "S2" );
        studentCriteria.addForeign( gradeForeignCriteria ).autoMappingColumnAlias( false ).eq( Student::getSex, 1 );
        List<StudentVo> result = studentService.list( studentCriteria );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }

    @Test
    @DisplayName( "测试根据年级获取学生列表-3" )
    public void test3() {
        // 主表
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "sd" );
        // 关联表
        ForeignCriteria<Grade> gradeForeignCriteria = studentCriteria.createForeign( Grade.class, "gd",
                null, "gradeId", "id", cxt -> cxt.eq( Grade::getName, "S2" ) );
        studentCriteria.addForeign( gradeForeignCriteria ).notNull( "email" )
                .autoMappingColumnAlias( false ).eq( Student::getSex, 1 );
        List<StudentVo> result = studentService.list( studentCriteria );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }

    @Test
    @DisplayName( "测试根据年级获取学生列表-4" )
    public void test4() {
        // 主表
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "sd" );
        // 关联表
        studentCriteria.addForeign( Grade.class, "gd", null, "gradeId", "id",
                ctx -> ctx.eq( Grade::getName, "S2" ) )
                .eq( Student::getSex, 1 ).desc( "name" ).autoMappingColumnAlias( false );
        List<StudentVo> result = studentService.list( studentCriteria );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }
}
