package com.wkit.lost.mybatis.filling.gen;

import java.time.OffsetDateTime;

/**
 * {@link OffsetDateTime}生成器
 * @author wvkity
 */
public class OffsetDateTimeGenerator extends AbstractGenerator {

    @Override
    public Object getValue() {
        return OffsetDateTime.now();
    }
}
