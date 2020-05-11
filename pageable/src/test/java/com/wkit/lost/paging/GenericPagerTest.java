package com.wkit.lost.paging;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

/**
 * @author wvkity
 */
public class GenericPagerTest {

    @Test
    public void test() {
        GenericPager<Long> pager = new GenericPager<>();
        pager.add(1L, 2L, 3L, 4L, 5L, 6L, 7L);
        pager.setRecord(336);
        System.out.println(JSON.toJSONString(pager, true));
    }
}
