package com.wkit.lost.mybatis.starter.junit.query;

import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.core.ForeignCriteria;
import com.wkit.lost.mybatis.core.JoinMode;
import com.wkit.lost.mybatis.core.SubCriteria;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.entity.Subject;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.vo.StudentVo;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import com.wkit.lost.paging.Pager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class PageableQueryApplication extends RootTestRunner {

    @Inject
    private StudentService studentService;

    @Test
    @DisplayName( "测试分页查询1--常规分页" )
    public void test() {
        CriteriaImpl<Student> criteria = studentService.getCriteria();
        criteria.asc( "id" );
        List<StudentVo> result = studentService.list( new Pager( 1, 10 ), criteria );
        log.info( "查询结果：{}", result );
    }

    @Test
    @DisplayName( "测试分页查询2--指定范围" )
    public void limitTest() {
        CriteriaImpl<Student> criteria = studentService.getCriteria();
        criteria.asc( "id" ).limit( 10, 30 );
        List<StudentVo> result = studentService.list( criteria );
        log.info( "查询结果：{}", result );
    }

    @Test
    @DisplayName( "测试分页查询3--指定页码、每页显示数目" )
    public void limitTest2() {
        CriteriaImpl<Student> criteria = studentService.getCriteria();
        criteria.asc( "id" ).limit( 2, 6, 6 );
        List<StudentVo> result = studentService.list( criteria );
        log.info( "查询结果：{}", result );
    }

    @Test
    @DisplayName( "测试分页查询4--联表分页" )
    public void foreignPageableTest() {
        CriteriaImpl<Student> criteria = studentService.getCriteria();
        ForeignCriteria<Result> foreignCriteria = criteria.createForeign( Result.class, "RS", Student::getId, Result::getStudentId, JoinMode.LEFT );
        foreignCriteria.query( "score" );
        criteria.addForeign( foreignCriteria ).asc( "id" );
        List<StudentVo> result = studentService.list( new Pager( 2, 10 ), criteria );
        log.info( "查询结果：{}", result );
    }

    @Test
    @DisplayName( "测试分页查询5--联表、子查询" )
    public void foreignSubPageableTest() {
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "SD" );
        // 创建子查询
        SubCriteria<Subject> subjectSubCriteria = studentCriteria.createSub( Subject.class, "SJ" );
        subjectSubCriteria.createForeign( Grade.class, null, Subject::getGradeId, Grade::getId,
                ctx -> ctx.in( Grade::getName, "S2", "Y2" ) ).appendTo();
        // 关联子查询
        studentCriteria.addForeign( subjectSubCriteria, Student::getGradeId, Subject::getGradeId,
                ctx -> ctx.setRelation( true ).ge( Subject::getHours, 55 ) );
        // 关联分数查询
        studentCriteria.addForeign( Result.class, "RS", null, Student::getId, Result::getStudentId,
                ctx -> ctx.eq( Result::getSubjectId, subjectSubCriteria, Subject.Fields.id )
                        .ge( Result::getScore, 84 ).query( Result::getScore ) ).asc( "id" );
        List<StudentVo> result = studentService.list( new Pager( 1, 10 ), studentCriteria );
        log.info( "查询结果：{}", result );
    }

    @Test
    @DisplayName( "测试分页查询6--联表、子查询" )
    public void foreignSubPageableTest2() {
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "SD" );
        // 创建子查询
        SubCriteria<Subject> subjectSubCriteria = studentCriteria.createSub( Subject.class, "SJ" );
        subjectSubCriteria.createForeign( Grade.class, null, Subject::getGradeId, Grade::getId,
                ctx -> ctx.in( Grade::getName, "S2", "Y2" ) ).appendTo();
        // 关联子查询
        studentCriteria.addForeign( subjectSubCriteria, Student::getGradeId, Subject::getGradeId,
                ctx -> ctx.ge( Subject::getHours, 55 ) );
        // 关联分数查询
        studentCriteria.addForeign( Result.class, "RS", null, Student::getId, Result::getStudentId,
                ctx -> ctx.eq( Result::getSubjectId, subjectSubCriteria, Subject.Fields.id )
                        .ge( Result::getScore, 84 ).query( Result::getScore ) )
                .asc( "id" ).limit( 1, 3, 2 );
        List<StudentVo> result = studentService.list( studentCriteria );
        log.info( "查询结果：{}", result );
    }

    @Test
    @DisplayName( "测试分页查询2--常规分页" )
    public void test2() {
        List<StudentVo> result = studentService.list(
                new Pager( 1, 10 ),
                new CriteriaImpl<>(Student.class).desc( "id" ) );
        log.info( "查询结果：{}", result );
    }
}
