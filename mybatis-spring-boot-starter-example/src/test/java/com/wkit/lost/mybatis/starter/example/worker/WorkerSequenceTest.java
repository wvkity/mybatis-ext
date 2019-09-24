package com.wkit.lost.mybatis.starter.example.worker;

import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.starter.example.junit.service.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class WorkerSequenceTest extends RootTestRunner {

    @Autowired( required = false )
    private Sequence sequence;

    @Test
    public void test() {
        long id = sequence.nextId();
        log.info( id );
        log.info( id > 96426133406617603L );
        log.info( "{}", sequence.parse( id ) );
        log.info( "{}", sequence );
    }
}
