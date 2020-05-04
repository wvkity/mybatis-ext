package com.wvkity.mybatis.keygen;

import java.util.UUID;

/**
 * 默认GUID生成器
 * @author wvkity
 */
public class GuidGenerator implements KeyGenerator {

    /**
     * 构造方法
     */
    public GuidGenerator() {
    }

    @Override
    public String value() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
