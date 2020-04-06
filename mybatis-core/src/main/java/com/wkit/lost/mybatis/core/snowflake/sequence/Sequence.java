package com.wkit.lost.mybatis.core.snowflake.sequence;

/**
 * ID序列
 * @author wvkity
 */
public interface Sequence {

    /**
     * 生成唯一ID
     * @return ID
     */
    long nextValue();

    /**
     * 将唯一ID解析成生成ID的相关元素
     * @param id ID
     * @return 相关元素信息
     */
    String parse( long id );

    /**
     * 生成唯一ID
     * @return 唯一ID
     */
    default String nextString() {
        return String.valueOf( nextValue() );
    }

    /**
     * 将唯一ID解析成生成ID的相关元素
     * @param id ID
     * @return 相关元素信息
     */
    default String parse( String id ) {
        return parse( Long.parseLong( id ) );
    }
}
