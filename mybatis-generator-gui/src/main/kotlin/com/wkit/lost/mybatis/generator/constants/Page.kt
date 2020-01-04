package com.wkit.lost.mybatis.generator.constants

/**
 * 页面枚举类
 * @author wvkity
 */
enum class Page(private val url: String) {
    CONNECTION_TAB("fxml/connection_tab.fxml"),
    SELECT_TABLE_COLUMN("fxml/select_table_column.fxml"),
    GENERATOR_CONFIG("fxml/generator_config.fxml");

    /**
     * 获取资源路径
     * @return URL
     */
    fun getLocation() = this.url
}