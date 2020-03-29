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
import java.util.List;

@Log4j2
public class SimpleQueryApplication extends RootTestRunner {

    @Inject
    private GradeService gradeService;

    @Test
    public void idEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.idEq( 1L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }

    @Test
    public void equalTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.eq( Grade::getId, 1L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );

    }

    @Test
    public void immediateEqualTest() {
        QueryCriteria<Grade> criteria = new QueryCriteria<>( Grade.class );
        criteria.immediateEq( "ID", 2L );
        List<GradeVo> list = gradeService.list( criteria );
        log.info( "结果: {}", JSON.toJSONString( list, true ) );
    }
}
