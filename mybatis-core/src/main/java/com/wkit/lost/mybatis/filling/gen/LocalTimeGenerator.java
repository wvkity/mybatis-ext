package com.wkit.lost.mybatis.filling.gen;

import java.time.LocalTime;

/**
 * {@link LocalTime}生成器
 */
public class LocalTimeGenerator extends AbstractGenerator {

    @Override
    public Object getValue() {
        return LocalTime.now();
    }
}
