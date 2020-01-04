package com.wkit.lost.mybatis.generator.constants

/**
 * 右键菜单文本枚举
 * @author wvkity
 */
enum class ContextMenuText(val text: String) {
    CONNECT_OPEN("打开连接"),
    CONNECT_CLOSE("关闭连接"),
    CONNECT_EDIT("编辑连接"),
    CONNECT_REMOVE("删除连接"),
    DATABASE_OPEN("打开数据库"),
    DATABASE_CLOSE("关闭数据库"),
    DATABASE_RELOAD("重新加载表"),
    COLUMN_RELOAD("重新加载列"),
    ADD_DB_FOR_GENERATE("添加所有表(生成代码)"),
    REMOVE_DB_FROM_GENERATE("移除所有表(生成代码)"),
    ADD_TABLE_FOR_GENERATE("添加当前表(生成代码)"),
    REMOVE_TABLE_FROM_GENERATE("移除当前表(生成代码)")
}