package com.wkit.lost.mybatis.filling.gen;

import java.time.ZonedDateTime;

/**
 * {@link ZonedDateTime}生成器
 * @author wvkity
 */
public class ZonedDateTimeGenerator extends AbstractGenerator {

    @Override
    public Object getValue() {
        return ZonedDateTime.now();
    }
}
