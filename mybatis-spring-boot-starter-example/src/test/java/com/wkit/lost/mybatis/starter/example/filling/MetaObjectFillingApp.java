package com.wkit.lost.mybatis.starter.example.filling;

import com.wkit.lost.mybatis.filling.MetaObjectFillingHandler;
import com.wkit.lost.mybatis.starter.example.junit.service.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class MetaObjectFillingApp extends RootTestRunner {

    @Autowired( required = false )
    private MetaObjectFillingHandler metaObjectFillingHandler;

    @Test
    public void test() {
        log.info( "自动填充实例：{}", metaObjectFillingHandler );
    }
}
