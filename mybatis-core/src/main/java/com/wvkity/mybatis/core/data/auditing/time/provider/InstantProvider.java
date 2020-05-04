package com.wvkity.mybatis.core.data.auditing.time.provider;

import java.time.Instant;

/**
 *
 */
public class InstantProvider extends AbstractProvider {

    @Override
    public Instant getNow() {
        return Instant.now();
    }
}
