package com.wvkity.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.paging.Pager;
import com.wkit.lost.paging.GenericPager;
import com.wvkity.mybatis.core.constant.Comparator;
import com.wvkity.mybatis.core.wrapper.aggreate.FunctionBuilder;
import com.wvkity.mybatis.core.wrapper.criteria.QueryCriteria;
import com.wvkity.mybatis.starter.example.entity.Exam;
import com.wvkity.mybatis.starter.example.service.ExamService;
import com.wvkity.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class FunctionApplication extends RootTestRunner {

    @Inject
    private ExamService examService;

    @SuppressWarnings("unchecked")
    @Test
    public void test1() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod).sum("score", "total").groups(Exam::getStudentId, Exam::getExamYear);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        List<Map<String, Object>> result = examService.map(criteria);
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test2() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod).sum("score", "total")
                .groups(Exam::getStudentId, Exam::getExamYear).funcDesc("total").desc(Exam::getStudentId);
        List<Map<String, Object>> result = examService.map(criteria);
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test3() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        FunctionBuilder it = FunctionBuilder.create();
        it.criteria(criteria).comparator(Comparator.GE).value(1800).alias("total").property(Exam::getScore);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod).function(it.sum())
                .groups(Exam::getStudentId, Exam::getExamYear).funcDesc("total").desc(Exam::getStudentId);
        // 分组筛选
        criteria.having("total");
        List<Map<String, Object>> result = examService.map(criteria);
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test4() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        FunctionBuilder it = FunctionBuilder.create();
        it.criteria(criteria).comparator(Comparator.LE_OR_GT).minValue(1777).maxValue(1980).alias("total").property(Exam::getScore);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod).function(it.sum())
                .groups(Exam::getStudentId, Exam::getExamYear).funcDesc("total").desc(Exam::getStudentId);
        // 分组筛选
        criteria.having("total");
        List<Map<String, Object>> result = examService.map(criteria);
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test5() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        FunctionBuilder it = FunctionBuilder.create();
        it.criteria(criteria).comparator(Comparator.LE_OR_GT).minValue(1777).maxValue(1980).alias("total").property(Exam::getScore);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod).function(it.sum())
                .groups(Exam::getStudentId, Exam::getExamYear).funcDesc("total").desc(Exam::getStudentId);
        // 分组筛选
        criteria.having("total");
        List<Map<String, Object>> result = examService.map(criteria.range(2, 10));
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test6() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        FunctionBuilder it = FunctionBuilder.create();
        it.criteria(criteria).comparator(Comparator.LE_OR_GT).minValue(1777).maxValue(1980).alias("total").property(Exam::getScore);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod).function(it.sum()).function(it.avg().as("avg").scale(2))
                .groups(Exam::getStudentId, Exam::getExamYear).funcDesc("total").desc(Exam::getStudentId);
        // 分组筛选
        criteria.having("total");
        List<Map<String, Object>> result = examService.map(criteria.range(2, 10));
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test7() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        FunctionBuilder it = FunctionBuilder.create();
        it.criteria(criteria).alias("total").property(Exam::getScore);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod)
                .function(it.sum().min(1777).max(1980).comparator(Comparator.LE_OR_GT))
                .function(it.avg().as("avg").scale(2).value(75.5).comparator(Comparator.GE))
                .groups(Exam::getStudentId, Exam::getExamYear)
                .funcDesc("total")
                .desc(Exam::getStudentId);
        // 分组筛选
        criteria.havings("total", "avg");
        List<Map<String, Object>> result = examService.custom(criteria.resultType(Map.class).range(1, 3, 12));
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test8() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        FunctionBuilder it = FunctionBuilder.create();
        it.criteria(criteria).alias("total").property(Exam::getScore);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod)
                .function(it.sum().min(1777).max(1980).comparator(Comparator.LE_OR_GT))
                .function(it.avg().as("avg").scale(2).value(75.5).comparator(Comparator.GE))
                .groups(Exam::getStudentId, Exam::getExamYear)
                .funcDesc("total")
                .desc(Exam::getStudentId);
        // 分组筛选
        criteria.havings("total", "avg");
        List<Object[]> result = examService.array(criteria.range(1, 3, 12));
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test9() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        criteria.sum(Exam::getScore, "total", Comparator.LE_OR_GT, 1777, 1980);
        criteria.groups(Exam::getStudentId, Exam::getExamYear)
                .funcDesc("total")
                .desc(Exam::getStudentId);
        // 分组筛选
        criteria.havings("total");
        List<Object[]> result = examService.array(criteria.range(1, 3, 12));
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test10() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        criteria.sum(Exam::getScore, "total", Comparator.LE_OR_GT, 1777, 1980);
        criteria.avg(Exam::getScore, "avg", 2, Comparator.GT, 75.5);
        criteria.groups(Exam::getStudentId, Exam::getExamYear)
                .funcDesc("total")
                .desc(Exam::getStudentId);
        // 分组筛选
        criteria.havings("total", "avg");
        List<Object[]> result = examService.array(criteria.range(1, 3, 12));
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test11() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        criteria.sum(Exam::getScore, "total", Comparator.LE_OR_GT, 1777, 1980);
        criteria.avg(Exam::getScore, "avg", 2, Comparator.GT, 75.5);
        criteria.min(Exam::getScore, "min").max(Exam::getScore, "max");
        criteria.groups(Exam::getStudentId, Exam::getExamYear)
                .funcDesc("max", "total")
                .desc(Exam::getStudentId);
        // 分组筛选
        criteria.havings("total", "avg");
        List<Map<String, Object>> result = examService.map(criteria.range(1, 3, 12));
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test12() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        criteria.sum(Exam::getScore, "total", Comparator.LE_OR_GT, 1777, 1980);
        criteria.avg(Exam::getScore, "avg", 2, Comparator.GT, 75.5);
        criteria.min(Exam::getScore, "min").max(Exam::getScore, "max");
        criteria.funcDesc("max", "total").desc(Exam::getStudentId);
        // 分组筛选
        criteria.havings("total", "avg");
        // 分组所有
        criteria.groups().as("ex_");
        // 只查询聚合函数
        // criteria.onlyQueryFunc();
        // 不查询聚合函数
        criteria.excludeFunc();
        List<Map<String, Object>> result = examService.map(criteria.range(1, 3, 12));
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test13() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        criteria.countWithAlias("cs");
        criteria.sumWith("score", "total", Comparator.LE_OR_GT, 1777, 1980);
        criteria.avgWith("score", "avg", 2, Comparator.GT, 75.5);
        criteria.minWith("score", "min").maxWith("score", "max");
        criteria.funcDesc("max", "total").desc(Exam::getStudentId);
        // 分组筛选
        criteria.havings("total", "avg");
        // 分组所有
        criteria.groups().as("ex_");
        // 只查询聚合函数
        // criteria.onlyQueryFunc();
        // 不查询聚合函数
        // criteria.excludeFunc();
        List<Map<String, Object>> result = examService.map(criteria.range(1, 3, 12));
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test14() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        criteria.countWithAlias("cs");
        criteria.sumWith("score", "total", Comparator.LE_OR_GT, 1777, 1980);
        criteria.avgWith("score", "avg", 2, Comparator.GT, 75.5);
        criteria.minWith("score", "min").maxWith("score", "max");
        criteria.funcDesc("max", "total").desc(Exam::getStudentId);
        // 分组筛选
        criteria.havings("total", "avg");
        // 分组所有
        criteria.groups().as("ex_");
        // 只查询聚合函数
        // criteria.onlyQueryFunc();
        // 不查询聚合函数
        // criteria.excludeFunc();
        List<Map<String, Object>> result = examService.map(criteria, new Pager());
        log.info("result: {}", JSON.toJSONString(result, true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test15() {
        QueryCriteria<Exam> criteria = QueryCriteria.from(Exam.class);
        criteria.eq(Exam::getGrade, 2, Exam::getPeriod, 2012, Exam::getSemester, 1);
        criteria.select(Exam::getStudentId, Exam::getExamYear, Exam::getPeriod);
        criteria.countWithAlias("cs");
        criteria.sumWith("score", "total", Comparator.LE_OR_GT, 1777, 1980);
        criteria.avgWith("score", "avg", 2, Comparator.GT, 75.5);
        criteria.minWith("score", "min").maxWith("score", "max");
        criteria.funcDesc("max", "total").desc(Exam::getStudentId);
        // 分组筛选
        criteria.havings("total", "avg");
        // 分组所有
        criteria.groups().as("ex_");
        // 只查询聚合函数
        // criteria.onlyQueryFunc();
        // 不查询聚合函数
        // criteria.excludeFunc();
        GenericPager<LinkedHashMap<String, Object>> pager;
        List<Map<String, Object>> result = examService.custom(criteria.resultType(LinkedHashMap.class), (pager = GenericPager.of(1, 10)));
        log.info("result: {}", JSON.toJSONString(result, true));
        log.info("=================================");
        log.info("pager: {}", JSON.toJSONString(pager, true));
    }
}
