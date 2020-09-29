package com.wvkity.mybatis.code.make.constant;

import lombok.Getter;

/**
 * TreeItem节点层级
 * @author wvkity
 */
@Getter
public enum TreeTier {

    /**
     * 连接节点
     */
    CONNECT(1),
    /**
     * 数据库节点
     */
    SCHEMA(2),
    /**
     * 表节点
     */
    TABLE(3),
    /**
     * 未知
     */
    NONE(-1);
    private final int value;

    TreeTier(int value) {
        this.value = value;
    }

    /**
     * 值转成枚举类
     * @param value 值
     * @return 枚举
     */
    public static TreeTier parse(final int value) {
        switch (value) {
            case 1:
                return CONNECT;
            case 2:
                return SCHEMA;
            case 3:
                return TABLE;
            default:
                return NONE;
        }
    }
}
