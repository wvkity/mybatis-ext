package com.wkit.lost.mybatis.filling.gen;

import java.sql.Date;

/**
 * {@link Date}生成器
 * @author DT
 */
public class SqlDateGenerator extends AbstractGenerator {

    @Override
    public Object getValue() {
        return new Date( System.currentTimeMillis() );
    }
}
