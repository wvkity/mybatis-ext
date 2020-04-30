package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.wrapper.criteria.QueryCriteria;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.service.GradeService;
import com.wkit.lost.mybatis.starter.example.vo.GradeVo;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class SimpleQueryApplication extends RootTestRunner {

    @Inject
    private GradeService gradeService;

    @Test
    public void test() {
        List<GradeVo> list = gradeService.list(1L, 2L, 3L);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void idEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.idEq(1L).orIdEq(4L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void equalTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.eq(Grade::getId, 1L).orEq("id", 4L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));

    }

    @Test
    public void directEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.eqWith("ID", 2L).orEqWith("id", 4L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void notEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.ne(Grade::getId, 1L);
        List<GradeVo> list = gradeService.list(criteria.as("sel___"));
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directNotEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.neWith("ID", 1L);
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void lessThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.lt(Grade::getId, 3L).orGt("id", 6L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directLessThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.ltWith("id", 3L).orGeWith("ID", 6L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void lessThanOrEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.le(Grade::getId, 3L).orGe("id", 6L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directLessOrEqualThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.geWith("id", 5L).orLeWith("ID", 2L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void greaterThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.gt(Grade::getId, 5L).orLt("id", 3L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directGreaterThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.gtWith("id", 5L).orLtWith("ID", 3L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void greaterThanOrEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.ge(Grade::getId, 4L).orLe("id", 2L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directGreaterThanOrEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.geWith("id", 4L).orLeWith("ID", 2L);
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void isNullTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.isNull(Grade::getName).orIsNull("id");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directIsNullTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.isNullWith("name").orIsNullWith("ID");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void isNotNullTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.notNull(Grade::getName);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directIsNotNullTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.notNullWith("name").orNotNullWith("ID");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void inTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.in(Grade::getId, 2L, 3L, 4L).orIn("id", 7L, 8L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void notInTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.notIn(Grade::getId, 2L, 3L, 4L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directInTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.inWith("id", 2L, 3L, 4L)
                .orInWith("ID", 7L, 8L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directNotInTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.notInWith("id", 2L, 3L, 4L)
                .orNotInWith("ID", 7L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void betweenTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.between(Grade::getId, 2L, 4L)
                .orBetween("id", 7L, 9L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directBetweenTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.betweenWith("id", 2L, 4L)
                .orBetweenWith("id", 7L, 9L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void notBetweenTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.notBetween(Grade::getId, 2L, 4L)
                .notBetween("id", 7L, 9L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directNotBetweenTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.notBetweenWith("id", 2L, 4L)
                .notBetweenWith("ID", 7L, 9L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void likeTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.like(Grade::getName, "x2")
                .orLikeLeft("name", "x2")
                .orLikeRight("name", "x2");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void notLikeTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.notLike(Grade::getName, "X2")
                .orNotLikeLeft("name", "X2")
                .orNotLikeRight("name", "X2");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directLikeTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.likeWith("name", "x2")
                .orLikeLeftWith("NAME", "X2")
                .orLikeRightWith("name", "X2");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directNotLikeTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.notLikeWith("name", "x2")
                .orNotLikeLeftWith("NAME", "X2")
                .orNotLikeRightWith("name", "X2");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void singleTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, 2) = {}";
        criteria.template(template, Grade::getName, "33");
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void multiTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, {}) = {}";
        criteria.template(template, Grade::getName, 2, "33");
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void mapTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, ${left}) = ${realName}";
        Map<String, Object> params = new HashMap<>(2);
        params.put("left", 2);
        params.put("realName", "33");
        criteria.template(template, Grade::getName, params);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void mapTemplateTest2() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, ${left}) = ${realName}";
        criteria.template(template, Grade::getName, "left", 2, "realName", "x2");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void singleDirectTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, 2) = {}";
        criteria.templateWith(template, "NAME", "33")
                .orTemplateWith(template, "name", "44");
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void singleDirectTemplateNotWithTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT(NAME, 2) = {}";
        criteria.templateWith(template, "33")
                .orTemplateWith(template, "44");
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void multiDirectTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, {}) = {}";
        criteria.templateWith(template, "NAME", 2, "33");
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void multiDirectTemplateNotWithTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT(name, {}) = {}";
        criteria.templateWith(template, 2, "33");
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void mapDirectTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, ${left}) = ${realName}";
        Map<String, Object> params = new HashMap<>(2);
        params.put("left", 2);
        params.put("realName", "33");
        criteria.templateWith(template, "name", params);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void mapDirectTemplateTest2() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, ${left}) = ${realName}";
        criteria.templateWith(template, "name", "left", 2, "realName", "x2");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void mapDirectTemplateNotWithTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT(NAME, ${left}) = ${realName}";
        Map<String, Object> params = new HashMap<>(2);
        params.put("left", 2);
        params.put("realName", "33");
        criteria.templateWith(template, params);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void mapDirectTemplateNotWithTest2() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT(NAME, ${left}) = ${realName}";
        criteria.templateWith(template, "left", 2, "realName", "x2");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }
}
