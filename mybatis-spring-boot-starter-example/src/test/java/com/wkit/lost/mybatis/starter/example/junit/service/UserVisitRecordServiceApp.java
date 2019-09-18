package com.wkit.lost.mybatis.starter.example.junit.service;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.starter.example.beans.UserVisitRecord;
import com.wkit.lost.mybatis.starter.example.service.UserVisitRecordService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

@Log4j2
public class UserVisitRecordServiceApp extends RootTestRunner {

    @Inject
    private UserVisitRecordService userVisitRecordService;

    @Test
    public void test1() {
        Optional<UserVisitRecord> optional = userVisitRecordService.selectOne( "vjznlns00mwek53c07r0" );
        log.info( "结果：{}", JSON.toJSONString( optional.orElse( null ) ) );
    }
}
