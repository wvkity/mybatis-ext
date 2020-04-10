package com.wkit.lost.mybatis.core.snowflake.worker;

import com.wkit.lost.mybatis.core.snowflake.factory.MillisecondsSequenceFactory;
import com.wkit.lost.mybatis.core.snowflake.factory.SequenceFactory;
import com.wkit.lost.mybatis.core.snowflake.sequence.Sequence;

/**
 * ID生成器
 */
public final class SequenceGenerator {

    private static final SequenceFactory SEQUENCE_FACTORY = new MillisecondsSequenceFactory();
    private static final Sequence SEQUENCE = SEQUENCE_FACTORY.build();

    private SequenceGenerator() {
    }

    /**
     * 生成唯一ID
     * @return 唯一ID
     */
    public static long nextValue() {
        return SEQUENCE.nextValue();
    }

    /**
     * 生成唯一ID
     * @return 唯一ID
     */
    public static String nextString() {
        return String.valueOf(nextValue());
    }

    /**
     * 解析唯一ID信息
     * @param id 唯一ID
     * @return 唯一ID相关元素信息
     */
    public static String parse(long id) {
        return SEQUENCE.parse(id);
    }

    /**
     * 解析唯一ID信息
     * @param id 唯一ID
     * @return 唯一ID相关元素信息
     */
    public static String parse(String id) {
        return parse(Long.parseLong(id));
    }
}
