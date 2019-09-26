package com.wkit.lost.mybatis.filling.gen;

import java.time.LocalDate;

/**
 * {@link LocalDate}生成器
 * @author DT
 */
public class LocalDateGenerator extends AbstractGenerator {

    @Override
    public Object getValue() {
        return LocalDate.now();
    }
}
