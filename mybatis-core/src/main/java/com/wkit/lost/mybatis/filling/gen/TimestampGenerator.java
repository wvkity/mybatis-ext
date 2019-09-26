package com.wkit.lost.mybatis.filling.gen;

import java.sql.Timestamp;

/**
 * {@link Timestamp}生成器
 * @author DT
 */
public class TimestampGenerator extends AbstractGenerator {

    @Override
    public Object getValue() {
        return new Timestamp( System.currentTimeMillis() );
    }
}
