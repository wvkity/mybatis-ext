package com.wkit.lost.mybatis.generator.bean;

/**
 * 连接配置信息
 */
class ConnectionConfig {
    var id = -1
    var dbType: String? = null
    var name: String? = null
    var host: String? = null
    var port: String? = null
    var schema: String? = null
    var userName: String? = null
    var password: String? = null
    var encoding: String? = null
    var url: String? = null
    var sshHost: String? = null
    var sshPort: String? = null
    var sshUserName: String? = null
    var sshPassword: String? = null
    var localPort: String? = null
    var targetPort: String? = null
}
