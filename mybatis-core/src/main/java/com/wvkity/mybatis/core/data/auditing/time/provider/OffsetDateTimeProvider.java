package com.wvkity.mybatis.core.data.auditing.time.provider;

import java.time.OffsetDateTime;

/**
 * {@link OffsetDateTime}时间类型提供者
 * @author wvkity
 */
public class OffsetDateTimeProvider extends AbstractProvider {

    @Override
    public OffsetDateTime getNow() {
        return OffsetDateTime.now();
    }
}
