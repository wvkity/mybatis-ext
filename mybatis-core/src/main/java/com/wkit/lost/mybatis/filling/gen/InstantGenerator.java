package com.wkit.lost.mybatis.filling.gen;

import java.time.Instant;

/**
 * {@link Instant}生成器
 * @author DT
 */
public class InstantGenerator extends AbstractGenerator {

    @Override
    public Object getValue() {
        return Instant.now();
    }
}
