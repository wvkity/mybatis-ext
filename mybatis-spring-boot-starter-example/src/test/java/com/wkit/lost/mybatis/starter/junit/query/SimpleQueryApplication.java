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
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.idEq( 1L ).orIdEq( 4L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void equalTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.eq( Grade::getId, 1L ).orEq( "id", 4L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );

    }

    @Test
    public void immediateEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateEq( "ID", 2L ).orImmediateEq( "id", 4L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void notEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.ne( Grade::getId, 1L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateNotEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateNe( "ID", 1L );
        List<GradeVo> list = gradeService.list( criteria.useAlias() );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void lessThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.lt( Grade::getId, 3L ).orGt( "id", 6L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateLessThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateLt( "id", 3L ).orImmediateGe( "ID", 6L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void lessThanOrEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.le( Grade::getId, 3L ).orGe( "id", 6L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateLessOrEqualThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateGe( "id", 5L ).orImmediateLe( "ID", 2L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void greaterThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.gt( Grade::getId, 5L ).orLt( "id", 3L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateGreaterThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateGt( "id", 5L ).orImmediateLt( "ID", 3L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void greaterThanOrEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.ge( Grade::getId, 4L ).orLe( "id", 2L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateGreaterOrEqualThanTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateGe( "id", 4L ).orImmediateLe( "ID", 2L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void isNullTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.isNull( Grade::getName ).orIsNull( "id" );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateIsNullTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateIsNull( "name" ).orImmediateIsNull( "ID" );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void inTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.in( Grade::getId, 2L, 3L, 4L ).orIn( "id", 7L, 8L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void notInTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.notIn( Grade::getId, 2L, 3L, 4L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateInTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateIn( "id", 2L, 3L, 4L )
                .orImmediateIn( "ID", 7L, 8L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateNotInTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateNotIn( "id", 2L, 3L, 4L )
                .orImmediateNotIn( "ID", 7L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void betweenTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.between( Grade::getId, 2L, 4L )
                .orBetween( "id", 7L, 9L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateBetweenTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateBetween( "id", 2L, 4L )
                .orImmediateBetween( "id", 7L, 9L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void notBetweenTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.notBetween( Grade::getId, 2L, 4L )
                .notBetween( "id", 7L, 9L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateNotBetweenTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateNotBetween( "id", 2L, 4L )
                .immediateNotBetween( "ID", 7L, 9L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void likeTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.like( Grade::getName, "x2" )
                .orLikeLeft( "name", "x2" )
                .orLikeRight( "name", "x2" );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void notLikeTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.notLike( Grade::getName, "X2" )
                .orNotLikeLeft( "name", "X2" )
                .orNotLikeRight( "name", "X2" );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateLikeTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateLike( "name", "x2" )
                .orImmediateLikeLeft( "NAME", "X2" )
                .orImmediateLikeRight( "name", "X2" );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void immediateNotLikeTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateNotLike( "name", "x2" );
        //.orImmediateNotLikeLeft( "NAME", "X2" )
        //.orImmediateNotLikeRight( "name", "X2" );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void singleTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        String template = "LEFT({@@}, 2) = {}";
        criteria.template( template, Grade::getName, "33" );
        List<GradeVo> list = gradeService.list( criteria.useAlias() );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void multiTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        String template = "LEFT({@@}, {}) = {}";
        criteria.template( template, Grade::getName, 2, "33" );
        List<GradeVo> list = gradeService.list( criteria.useAlias() );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void mapTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        String template = "LEFT({@@}, ${left}) = ${realName}";
        Map<String, Object> params = new HashMap<>( 2 );
        params.put( "left", 2 );
        params.put( "realName", "33" );
        criteria.template( template, Grade::getName, params );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void singleImmediateTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        String template = "LEFT({@@}, 2) = {}";
        criteria.immediateTemplate( template, "NAME", "33" )
                .orImmediateTemplate( template, "name", "44" );
        List<GradeVo> list = gradeService.list( criteria.useAlias() );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void singleImmediateTemplateNotWithTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        String template = "LEFT(NAME, 2) = {}";
        criteria.immediateTemplate( template, "33" )
                .orImmediateTemplate( template, "44" );
        List<GradeVo> list = gradeService.list( criteria.useAlias() );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void multiImmediateTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        String template = "LEFT({@@}, {}) = {}";
        criteria.immediateTemplate( template, "NAME", 2, "33" );
        List<GradeVo> list = gradeService.list( criteria.useAlias() );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void multiImmediateTemplateNotWithTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        String template = "LEFT(name, {}) = {}";
        criteria.immediateTemplate( template, 2, "33" );
        List<GradeVo> list = gradeService.list( criteria.useAlias() );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void mapImmediateTemplateTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        String template = "LEFT({@@}, ${left}) = ${realName}";
        Map<String, Object> params = new HashMap<>( 2 );
        params.put( "left", 2 );
        params.put( "realName", "33" );
        criteria.immediateTemplate( template, "name", params );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void mapImmediateTemplateNotWithTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        String template = "LEFT(NAME, ${left}) = ${realName}";
        Map<String, Object> params = new HashMap<>( 2 );
        params.put( "left", 2 );
        params.put( "realName", "33" );
        criteria.immediateTemplate( template, params );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }
}
