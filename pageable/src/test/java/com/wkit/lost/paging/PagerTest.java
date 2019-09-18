package com.wkit.lost.paging;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

/**
 * @author DT
 */
public class PagerTest {

    private void println( Pageable pageable ) {
        if ( pageable != null ) {
            System.out.println( JSON.toJSONString( pageable, true ) );
        }
    }

    @Test
    public void test() {
        Pageable pager = new Pager(4L);
        pager.setRecord( 173 );
        println( pager );
        System.out.println(pager.offset());
    }
}
