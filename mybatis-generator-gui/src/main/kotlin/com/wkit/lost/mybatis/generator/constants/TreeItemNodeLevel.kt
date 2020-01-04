package com.wkit.lost.mybatis.generator.constants

/**
 * 树节点级别枚举
 * @author wvkity
 */
enum class TreeItemNodeLevel(val value: Int) {
    CONNECT(1),
    SCHEMA(2),
    TABLE(3)
}