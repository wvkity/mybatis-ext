package com.wkit.lost.mybatis.starter.junit.query;

import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import com.wkit.lost.mybatis.starter.example.service.ResultService;
import com.wkit.lost.mybatis.starter.example.vo.ResultVo;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class SimpleQueryApplication extends RootTestRunner {

    @Inject
    ResultService resultService;

    @Test
    public void eqTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria("RS");
        resultCriteria.eq( Result::getScore, 89 ).asc( "id" ).autoMappingColumnAlias();
        List<ResultVo> results = resultService.list( resultCriteria );
        log.info( "查询结果：{}", results );
    }
    
    @Test
    public void neTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria("RS");
        resultCriteria.ne( Result::getScore, 89 ).asc( "id" );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }
    
    @Test
    public void ltTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria("RS");
        resultCriteria.lt( Result::getScore, 89 ).asc( "id" );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }

    @Test
    public void leTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria("RS");
        resultCriteria.le( Result::getScore, 89 ).asc( "id" );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }

    @Test
    public void gtTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria("RS");
        resultCriteria.gt( Result::getScore, 89 ).asc( "id" );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }

    @Test
    public void geTest() {
        CriteriaImpl<Result> resultCriteria = resultService.getCriteria("RS");
        resultCriteria.ge( Result::getScore, 89 ).asc( "id" );
        List<ResultVo> results = resultService.list( resultCriteria.limit( 0, 10 ) );
        log.info( "查询结果：{}", results );
    }
}
