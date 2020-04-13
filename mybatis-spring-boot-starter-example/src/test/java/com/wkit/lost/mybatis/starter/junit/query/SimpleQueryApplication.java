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
        criteria.directEq("ID", 2L).orDirectEq("id", 4L);
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
        criteria.directNe("ID", 1L);
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
        criteria.directLt("id", 3L).orDirectGe("ID", 6L);
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
        criteria.directGe("id", 5L).orDirectLe("ID", 2L);
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
        criteria.directGt("id", 5L).orDirectLt("ID", 3L);
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
    public void directGreaterOrEqualThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.directGe("id", 4L).orDirectLe("ID", 2L);
        List<GradeVo> list = gradeService.list(criteria);
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
        criteria.directIsNull("name").orDirectIsNull("ID");
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
        criteria.directIn("id", 2L, 3L, 4L)
                .orDirectIn("ID", 7L, 8L);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directNotInTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.directNotIn("id", 2L, 3L, 4L)
                .orDirectNotIn("ID", 7L);
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
        criteria.directBetween("id", 2L, 4L)
                .orDirectBetween("id", 7L, 9L);
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
        criteria.directNotBetween("id", 2L, 4L)
                .directNotBetween("ID", 7L, 9L);
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
        criteria.directLike("name", "x2")
                .orDirectLikeLeft("NAME", "X2")
                .orDirectLikeRight("name", "X2");
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void directNotLikeTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        criteria.directNotLike("name", "x2");
        //.orDirectNotLikeLeft( "NAME", "X2" )
        //.orDirectNotLikeRight( "name", "X2" );
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
    public void singleDirectTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, 2) = {}";
        criteria.directTemplate(template, "NAME", "33")
                .orDirectTemplate(template, "name", "44");
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void singleDirectTemplateNotWithTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT(NAME, 2) = {}";
        criteria.directTemplate(template, "33")
                .orDirectTemplate(template, "44");
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void multiDirectTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT({@@}, {}) = {}";
        criteria.directTemplate(template, "NAME", 2, "33");
        List<GradeVo> list = gradeService.list(criteria.useAs());
        log.info("结果: {}", JSON.toJSONString(list, true));
    }

    @Test
    public void multiDirectTemplateNotWithTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>(Grade.class);
        String template = "LEFT(name, {}) = {}";
        criteria.directTemplate(template, 2, "33");
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
        criteria.directTemplate(template, "name", params);
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
        criteria.directTemplate(template, params);
        List<GradeVo> list = gradeService.list(criteria);
        log.info("结果: {}", JSON.toJSONString(list, true));
    }
}
