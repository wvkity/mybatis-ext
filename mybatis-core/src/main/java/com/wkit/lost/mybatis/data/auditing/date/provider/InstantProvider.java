package com.wkit.lost.mybatis.data.auditing.date.provider;

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
