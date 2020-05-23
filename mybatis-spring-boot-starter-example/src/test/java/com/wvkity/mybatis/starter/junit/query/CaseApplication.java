package com.wvkity.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wvkity.mybatis.core.conditional.Restrictions;
import com.wvkity.mybatis.core.wrapper.aggreate.Aggregator;
import com.wvkity.mybatis.core.wrapper.basic.Case;
import com.wvkity.mybatis.core.wrapper.criteria.QueryCriteria;
import com.wvkity.mybatis.starter.example.entity.Exam;
import com.wvkity.mybatis.starter.example.entity.Student;
import com.wvkity.mybatis.starter.example.service.ExamService;
import com.wvkity.mybatis.starter.junit.RootTestRunner;
import com.wvkity.mybatis.utils.ArrayUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Log4j2
public class CaseApplication extends RootTestRunner {

    @Inject
    private ExamService examService;

    @Test
    public void test1() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        Case it = Case.create()
                .when("subject_id = 6").then("subject_id")
                .when(Restrictions.eq(criteria, Exam::getSubjectId, 8)).then(6)
                .when(Restrictions.eq(criteria, Exam::getSubjectId, 9)).then(7)
                .when(ArrayUtil.toList(Restrictions.eq(criteria, Exam::getSubjectId, 10), Restrictions.eqWith(criteria, "period", 2))).then(7)
                .end(8).as("sub_case");
        log.info(it.getConditionSegment());
        log.info(it.getSegment());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test2() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        criteria.select(Exam::getScore);
        // case查询
        Case it = Case.create();
        it.when(Restrictions.gt(criteria, Exam::getScore, 95)).then(1)
                .when(ArrayUtil.toList(Restrictions.le(criteria, Exam::getScore, 95), Restrictions.ge(criteria, Exam::getScore, 80))).then(2)
                .end(3).as("level");
        criteria.select(it).desc(Exam::getScore);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.eq(Exam::getSubjectId, 9);
        criteria.groups();
        List<Map<String, Object>> result = examService.mapList(criteria);
        //log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test3() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        criteria.select(Exam::getScore);
        // case查询
        Case it = Case.create();
        it.when("score > 95").then(1).when("score >= 80 AND score <= 95").then(2).end(3).as("level");
        criteria.select(it).desc(Exam::getScore);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.eq(Exam::getSubjectId, 9);
        criteria.groups();
        List<Map<String, Object>> result = examService.mapList(criteria);
        //log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test4() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        //criteria.select(Exam::getScore);
        // 统计总分数
        criteria.sum(Exam::getScore, "total_1");
        // 统计总考试次数
        criteria.count(Exam::getId, "total_2");
        // 统计95分以上(包含)的次数
        Case subCase = Case.create();
        subCase.when("SCORE >= 95").then(1).end("NULL");
        criteria.function(Aggregator.count(criteria, subCase).as("total_3"));
        // case查询
        Case it = Case.create();
        it.when("score > 95").then(1).when("score >= 80 AND score <= 95").then(2).end(3).as("level");
        criteria.select(it);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.groups();
        // 根据超过95分次数排序
        criteria.funcDesc("total_3", "total_1");
        List<Map<String, Object>> result = examService.mapList(criteria);
        //log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test5() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.select(Exam::getExamYear, Exam::getPeriod).as("ex");
        criteria.select(Exam::getStudentId, "student.id");
        //criteria.select(Exam::getScore);
        // 统计总分数
        criteria.sum(Exam::getScore, "total_1");
        // 统计总考试次数
        criteria.count(Exam::getId, "total_2");
        // 统计95分以上(包含)的次数
        Case subCase = Case.create();
        subCase.when("ex.SCORE >= 95").then(1).end("NULL");
        criteria.function(Aggregator.count(criteria, subCase).as("total_3"));
        // case查询
        Case it = Case.create();
        it.when("ex.score > 95").then(1).when("ex.score >= 80 AND ex.score <= 95").then(2).end(3).as("level");
        criteria.select(it);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.groups();
        // 根据超过95分次数排序
        criteria.funcDesc("total_3", "total_1");
        // 连表查询学生姓名
        criteria.innerJoin(Student.class).on(st -> st.nq(criteria, Exam::getStudentId))
                .select(Student::getName, "student.name").as("st");
        List<Map<String, Object>> result = examService.mapList(criteria);
        log.info("result: {}", JSON.toJSONString(result, true));
    }
}
