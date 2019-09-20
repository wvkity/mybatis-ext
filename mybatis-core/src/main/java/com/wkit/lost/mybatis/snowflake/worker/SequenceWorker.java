package com.wkit.lost.mybatis.snowflake.worker;

import com.wkit.lost.mybatis.snowflake.factory.MillisSequenceFactory;
import com.wkit.lost.mybatis.snowflake.factory.SequenceFactory;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;

public class SequenceWorker {

    private static final SequenceFactory SEQUENCE_FACTORY = new MillisSequenceFactory();
    private static final Sequence SEQUENCE = SEQUENCE_FACTORY.build();

    /**
     * 生成唯一ID
     * @return 唯一ID
     */
    public static long nextId() {
        return SEQUENCE.nextId();
    }

    /**
     * 解析唯一ID信息
     * @param id 唯一ID
     * @return 唯一ID相关元素信息
     */
    public static String parse(long id) {
        return SEQUENCE.parse( id );
    }
}
