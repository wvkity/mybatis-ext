package com.wvkity.mybatis.core.data.auditing.time.provider;


import java.time.OffsetTime;

/**
 * {@link OffsetTime}时间类型提供者
 * @author wvkity
 */
public class OffsetTimeProvider extends AbstractProvider {

    @Override
    public OffsetTime getNow() {
        return OffsetTime.now();
    }
}
