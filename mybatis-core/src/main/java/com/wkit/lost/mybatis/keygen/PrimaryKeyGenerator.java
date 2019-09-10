package com.wkit.lost.mybatis.keygen;

/**
 * 主键生成器接口
 * @author DT
 */
@FunctionalInterface
public interface PrimaryKeyGenerator {

    /**
     * 生成GUID值
     * @return 字符串
     */
    String value();
}
