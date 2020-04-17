package com.wkit.lost.mybatis.core.metadata;

import com.wkit.lost.mybatis.annotation.extension.Executing;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 主键相关信息
 * @author wvkity
 */
@Getter
@ToString
@EqualsAndHashCode
public final class Unique {

    /**
     * 是否为UUID主键
     */
    private final boolean uuid;

    /**
     * 是否为自增主键
     */
    private final boolean identity;

    /**
     * 是否为雪花算法主键
     */
    private final boolean snowflakeSequence;

    /**
     * 是否为雪花算法字符串主键
     */
    private final boolean snowflakeSequenceString;

    /**
     * 主键生成方式
     */
    private final String generator;

    /**
     * SQL执行时机
     */
    private final Executing executing;

    /**
     * 构造方法
     * @param uuid                    是否为UUID主键
     * @param identity                是否为自增主键
     * @param snowflakeSequence       是否为雪花算法主键
     * @param snowflakeSequenceString 是否为雪花算法字符串主键
     * @param generator               主键生成方式
     * @param executing               SQL执行时机
     */
    public Unique(boolean uuid, boolean identity, boolean snowflakeSequence, boolean snowflakeSequenceString,
                  String generator, Executing executing) {
        this.uuid = uuid;
        this.identity = identity;
        this.snowflakeSequence = snowflakeSequence;
        this.snowflakeSequenceString = snowflakeSequenceString;
        this.generator = generator;
        this.executing = executing;
    }
}
