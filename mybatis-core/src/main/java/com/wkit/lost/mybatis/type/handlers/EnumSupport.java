package com.wkit.lost.mybatis.type.handlers;

/**
 * 自定义枚举接口
 * @param <E> 枚举类
 * @param <T> 枚举值类型
 * @author wvkity
 */
public interface EnumSupport<E extends Enum<E>, T> {

    /**
     * 获取映射值
     * @return 枚举值
     */
    T getValue();

    /**
     * 获取描述
     * @return 描述
     */
    String getDescription();
}
