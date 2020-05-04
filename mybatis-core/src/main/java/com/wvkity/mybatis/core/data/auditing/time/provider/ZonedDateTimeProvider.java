package com.wvkity.mybatis.core.data.auditing.time.provider;

import java.time.ZonedDateTime;

/**
 * {@link ZonedDateTime}时间类型提供者
 * @author wvkity
 */
public class ZonedDateTimeProvider extends AbstractProvider {

    @Override
    public ZonedDateTime getNow() {
        return ZonedDateTime.now();
    }
}
