package com.wkit.lost.mybatis.snowflake.sequence;

/**
 * ID序列
 * @author DT
 */
public interface Sequence {

    /**
     * 生成唯一ID
     * @return ID
     */
    long nextId();

    /**
     * 将唯一ID解析成生成ID的相关元素
     * @param id ID
     * @return 相关元素信息
     */
    String parse( long id );
}
