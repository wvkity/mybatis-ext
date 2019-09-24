package com.wkit.lost.mybatis.starter.example.junit.service;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.starter.example.beans.UserVisitRecord;
import com.wkit.lost.mybatis.starter.example.service.UserVisitRecordService;
import com.wkit.lost.paging.Pager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

@Log4j2
public class UserVisitRecordServiceApp extends RootTestRunner {

    @Inject
    private UserVisitRecordService userVisitRecordService;

    //@Test
    public void test1() {
        Optional<UserVisitRecord> optional = userVisitRecordService.selectOne( "vjznlns00mwek53c07r0" );
        log.info( "结果：{}", JSON.toJSONString( optional.orElse( null ) ) );
    }

    //@Test
    public void test2() {
        Criteria<UserVisitRecord> ct1 = userVisitRecordService.getCriteria();
        userVisitRecordService.list( new Pager( 4 ), ct1 );
        Criteria<UserVisitRecord> ct2 = userVisitRecordService.getCriteria();
        ct2.limit( 40, 60 );
        userVisitRecordService.list( ct2 );
        Criteria<UserVisitRecord> ct3 = userVisitRecordService.getCriteria();
        ct3.limit( 1, 4, 20 );
        userVisitRecordService.list( ct3 );
    }
}
