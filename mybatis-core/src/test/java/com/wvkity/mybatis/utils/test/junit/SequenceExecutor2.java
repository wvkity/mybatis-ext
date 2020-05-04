package com.wvkity.mybatis.utils.test.junit;

import com.wvkity.mybatis.core.snowflake.factory.MillisecondsSequenceFactory;
import com.wvkity.mybatis.core.snowflake.sequence.Sequence;

public class SequenceExecutor2 {

    public static void main(String[] args) {
        try {
            Sequence sequence = new MillisecondsSequenceFactory().build(1L, 0L);
            int size = 0;
            for (int i = 0; i < 1000; i++) {
                Thread.sleep(1);
                long id = sequence.nextValue();
                if (id % 2 == 0) {
                    size++;
                }
                System.out.println("第" + (i + 1) + "次: " + id);
                Thread.sleep(10);
            }
            System.out.println("偶数：" + size + "奇数：" + (1000 - size));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
