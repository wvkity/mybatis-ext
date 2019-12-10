package com.wkit.lost.mybatis.filling.gen;

import java.util.Date;

/**
 * {@link Date}生成器
 * @author wvkity
 */
public class DateGenerator extends AbstractGenerator {

    @Override
    public Object getValue() {
        Date date = new Date();
        date.setTime( System.currentTimeMillis() );
        return date;
    }
}
