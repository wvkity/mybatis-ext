package com.wkit.lost.mybatis.generator.bean

import java.time.OffsetDateTime

/**
 * 数据库连接配置信息
 */
class ConnectionConfigInfo {
    var id: Int = 0
    var connectName: String? = null
    var connectValue: String? = null
    var gmtCreate: OffsetDateTime? = null
    var gmtModify: OffsetDateTime? = null
}