package com.wkit.lost.mybatis.starter.junit.basic;

import com.wkit.lost.mybatis.plugins.paging.PageableInterceptor;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
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
        log.info( "ID = {}", id );
        log.info( "ID 信息: {}", sequence.parse( id ) );
    }
    
    @Test
    public void interceptorTest() {
        log.info( "pageable interceptor info: {}", pageableInterceptor );
    }
}
