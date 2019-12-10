package com.wkit.lost.mybatis.filling.gen;

import java.time.OffsetTime;

/**
 * {@link OffsetTime}生成器
 * @author wvkity
 */
public class OffsetTimeGenerator extends AbstractGenerator {

    @Override
    public Object getValue() {
        return OffsetTime.now();
    }
}
