package com.wkit.lost.mybatis.data.auditing.date.provider;

import java.time.Instant;
import java.util.Date;

/**
 * {@link Date}时间类型提供者
 * @author wvkity
 */
public class DateProvider extends AbstractProvider {
    
    @Override
    public Date getNow() {
        return Date.from( Instant.now() );
    }
}
