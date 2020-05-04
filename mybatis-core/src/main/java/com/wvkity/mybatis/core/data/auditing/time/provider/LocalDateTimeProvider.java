package com.wvkity.mybatis.core.data.auditing.time.provider;

import java.time.LocalDateTime;

/**
 * {@link LocalDateTime}时间类型提供者
 * @author wvkity
 */
public class LocalDateTimeProvider extends AbstractProvider {

    @Override
    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
