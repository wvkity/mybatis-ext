package com.wkit.lost.mybatis.keygen;

import java.util.UUID;

/**
 * 默认GUID生成器
 * @author DT
 */
public class GuidGenerator implements PrimaryKeyGenerator {

    /**
     * 构造方法
     */
    public GuidGenerator() {
    }

    @Override
    public String value() {
        return UUID.randomUUID().toString().replaceAll( "-", "" );
    }
}
