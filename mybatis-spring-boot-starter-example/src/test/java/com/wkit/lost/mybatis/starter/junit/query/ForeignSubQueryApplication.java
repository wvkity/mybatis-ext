package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.core.SubCriteria;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.entity.Subject;
import com.wkit.lost.mybatis.starter.example.service.GradeService;
import com.wkit.lost.mybatis.starter.example.service.ResultService;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.service.SubjectService;
import com.wkit.lost.mybatis.starter.example.vo.StudentVo;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

/**
 * 子查询作为联表测试
 */
@Log4j2
public class ForeignSubQueryApplication extends RootTestRunner {

    @Inject
    private StudentService studentService;
    @Inject
    private ResultService resultService;
    @Inject
    private GradeService gradeService;
    @Inject
    private SubjectService subjectService;

    /**
     * 搜索科目成绩大于84分、科目课时大于等于55小时、班级在S2、Y2的学生信息、科目信息、成绩
     */
    @Test
    @DisplayName( "测试子查询作为联表-1" )
    public void test1() {
        // 主表
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "sd" );
        // 子查询
        SubCriteria<Subject> subjectSubCriteria = studentCriteria.createSub( Subject.class, "sj" );
        subjectSubCriteria.addForeign( Grade.class, null, Subject::getGradeId, Grade::getId,
                ctx -> ctx.in( Grade::getName, "S2", "Y2" ) );
        // 关联子查询
        studentCriteria.addForeign( subjectSubCriteria, Student::getGradeId, Subject::getGradeId,
                ctx -> ctx.setRelation( true ).ge( Subject::getHours, 55 ) );
        // 关联分数查询
        studentCriteria.addForeign( Result.class, "rs", null, Student::getId, Result::getStudentId,
                ctx -> ctx.eq( Result::getSubjectId, subjectSubCriteria, Subject.Fields.id ).ge( Result::getScore, 84 ).query( Result::getScore ));
        // 结果
        List<StudentVo> result = studentService.list( studentCriteria );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }

    /**
     * 搜索科目成绩大于84分、科目课时大于等于55小时、班级在S2、Y2的学生信息、科目信息、成绩
     */
    @Test
    @DisplayName( "测试子查询作为联表-2" )
    @SuppressWarnings( "unchecked" )
    public void test2() {
        // 主表
        CriteriaImpl<Student> studentCriteria = studentService.getCriteria( "sd" );
        // 子查询
        SubCriteria<Subject> subjectSubCriteria = studentCriteria.createSub( Subject.class, "sj" );
        subjectSubCriteria.addForeign( Grade.class, null, Subject::getGradeId, Grade::getId,
                ctx -> ctx.in( Grade::getName, "S2", "Y2" ) );
        // 关联子查询
        studentCriteria.addForeign( subjectSubCriteria, Student::getGradeId, Subject::getGradeId,
                ctx -> ctx.ge( Subject::getHours, 55 ) );
        studentCriteria.queryFromSub( subjectSubCriteria, Subject::getId, "subjectId" )
                .queryFromSub( subjectSubCriteria, Subject::getName, Subject::getHours, Subject::getGradeId );
        // 关联分数
        studentCriteria.addForeign( Result.class, "rs", null, Student::getId, Result::getStudentId,
                ctx -> ctx.eq( Result::getSubjectId, subjectSubCriteria, Subject.Fields.id )
                        .ge( Result::getScore, 84 ).query( Result::getScore ) );
        List<StudentVo> result = studentService.list( studentCriteria );
        log.info( "查询学生结果: {}", JSON.toJSONString( result, true ) );
    }
}
