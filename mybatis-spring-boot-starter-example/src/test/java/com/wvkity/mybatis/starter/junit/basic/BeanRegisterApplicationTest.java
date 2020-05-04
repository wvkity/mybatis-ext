package com.wvkity.mybatis.starter.junit.basic;

import com.wvkity.mybatis.plugins.paging.PageableInterceptor;
import com.wvkity.mybatis.core.snowflake.sequence.Sequence;
import com.wvkity.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class BeanRegisterApplicationTest extends RootTestRunner {

    @Autowired
    Sequence sequence;

    @Autowired
    PageableInterceptor pageableInterceptor;

    @Test
    public void sequenceTest() {
        long id = sequence.nextValue();
        log.info("ID = {}", id);
        log.info("ID 信息: {}", sequence.parse(id));
    }

    @Test
    public void interceptorTest() {
        log.info("pageable interceptor info: {}", pageableInterceptor);
    }
}
