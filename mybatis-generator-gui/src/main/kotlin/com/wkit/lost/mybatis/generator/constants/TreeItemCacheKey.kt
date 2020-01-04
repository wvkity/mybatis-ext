package com.wkit.lost.mybatis.generator.constants

/**
 * 树缓存key枚举
 * @author wvkity
 */
enum class TreeItemCacheKey(val key: String) {

    CONFIG("config"),
    ACTIVE("active"),
    SCHEMA("schema"),
    TABLE("table"),
    COLUMN("column"),
    SELECTION("selection")
}