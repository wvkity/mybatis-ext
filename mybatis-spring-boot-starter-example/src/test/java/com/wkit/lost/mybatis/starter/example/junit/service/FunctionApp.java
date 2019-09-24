package com.wkit.lost.mybatis.starter.example.junit.service;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.core.function.Aggregations;
import com.wkit.lost.mybatis.starter.example.beans.SysUser;
import com.wkit.lost.mybatis.starter.example.service.SysUserService;
import com.wkit.lost.mybatis.starter.example.vo.Score;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

@Log4j2
public class FunctionApp extends RootTestRunner {

    @Inject
    private SysUserService sysUserService;

    @Test
    public void countTest() {
        try {
            CriteriaImpl<SysUser> criteria = sysUserService.getCriteria();
            /*criteria.useAlias( "t1" )
                    //.gt( SysUser::getState, 5 );
            criteria.query( "state" );
            criteria.group( "state" );
            //criteria.count( "count", true, Comparator.GE_AND_LE, "id", 9999, 10025, 49999 );
            criteria.having( "count" ).onlyFunction( true );
            criteria.asc( "state" );*/

            criteria.useAlias( "t2" )
                    .query( "state", "sex", "gmtCreate" )
                    .lt( "score", 150 )
                    .group( "state", "sex" )
                    .count("*" )
                    .min( "score" )
                    .max( "score" )
                    .sum( "score" )
                    .avg( "avg", "score" )
                    .descFunc( "avg" )
                    .asc( "state" ).asc( Aggregations.max( criteria, "score" ) )
                    //.resultType( LinkedHashMap.class );
                    .resultMap( "scoreResultMap" );
            //List<Score> result = sysUserService.listForCustom( criteria );
            //Score score = result.get( 0 );
            //List<Object[]> result = sysUserService.listForArray( criteria );
            //List<Map<String, Object>> result = sysUserService.listForMap( criteria );
            //log.info( "执行结果1：{}", JSON.toJSONString( result, true ) );
            //log.info( "执行结果：{}", JSON.toJSONString( sysUserService.listForObject( criteria ), true ) );
            CriteriaImpl<SysUser> criteria1 = sysUserService.getCriteria("t3");
            criteria1.query( "state", "sex", "gmtCreate" )
                    .lt( "score", 150 )
                    .group( "state", "sex" )
                    .count("*" )
                    .min( "score" )
                    .max( "score" )
                    .sum( "score" )
                    .avg( "avg", "score" )
                    .descFunc( "avg" )
                    .asc( "state" ).asc( Aggregations.max( criteria1, "score" ) )
                    .limit( 0, 8 )
                    .resultType( Score.class );
            List<SysUser> limitResult = sysUserService.list( criteria1 );
            //sysUserService.listForMap( criteria1 );
            //List<Score> scores = sysUserService.listForCustom( criteria1 );
            //log.info( "执行结果2：{}", JSON.toJSONString( scores, true ) );
            //Score score = scores.get( 0 );
            //log.info( "获取其中一条记录：{}", JSON.toJSONString( score, true ) );
        } catch ( Exception e ) {
            log.error( "系统出现异常：", e );
        }
    }

    public static void main( String[] args ) {
        Pattern pattern = Pattern.compile( "^\\+?(0)|([1-9]+\\d?)$" );
        System.out.println( pattern.matcher( "8" ).matches() );
    }
}
