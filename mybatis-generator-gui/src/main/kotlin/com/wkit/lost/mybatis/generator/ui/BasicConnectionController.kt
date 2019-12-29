package com.wkit.lost.mybatis.generator.ui

import com.alibaba.fastjson.JSON
import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import com.wkit.lost.mybatis.generator.bean.ConnectionConfigInfo
import com.wkit.lost.mybatis.generator.constants.DatabaseType
import com.wkit.lost.mybatis.generator.jdbc.LocalDataSource
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.apache.logging.log4j.LogManager
import java.net.URL
import java.time.OffsetDateTime
import java.util.*

/**
 * TCP/IP连接方式
 * @author wvkity
 */
open class BasicConnectionController : AbstractController() {

    companion object {
        private val LOG = LogManager.getLogger(BasicConnectionController)
    }

    /**
     * 保存的连接名称
     */
    @FXML
    protected lateinit var connectionName: TextField

    /**
     * 主机(HOST)
     */
    @FXML
    protected lateinit var connectionHost: TextField

    /**
     * 端口
     */
    @FXML
    protected lateinit var connectionPort: TextField

    /**
     * 数据库类型
     */
    @FXML
    protected lateinit var databaseType: ComboBox<String>

    /**
     * 用户名
     */
    @FXML
    protected lateinit var userName: TextField

    /**
     * 密码
     */
    @FXML
    protected lateinit var password: PasswordField

    /**
     * 数据库名称
     */
    @FXML
    protected lateinit var schema: TextField

    /**
     * 编码
     */
    @FXML
    protected lateinit var encoding: ComboBox<String>

    /**
     * 驱动信息
     */
    @FXML
    protected lateinit var connectionDriver: TextField

    /**
     *
     */
    lateinit var application: ApplicationController

    /**
     * 检查是否为更新操作
     */
    protected var executeUpdate = false
    protected var primaryKey = -1
    
    protected fun isNotSqlite(): Boolean {
        return databaseType.takeIf { 
            databaseType.value != null
        } ?.run {
            DatabaseType.valueOf(databaseType.value.toUpperCase(Locale.ENGLISH)) != DatabaseType.SQLITE
        } ?: run {
            true
        }
    }
    
    open protected fun needRequired(node: TextField?, value: String?) {
        if (isNotSqlite()) {
            validate(node, value)
        } else {
            removeError(node)
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        try {
            // 事件监听
            valueChangedListener(this.connectionHost)
            valueChangedListener(this.connectionPort) { _, _, value -> needRequired(connectionPort, value)}
            valueChangedListener(this.userName) { _, _, value -> needRequired(userName, value)}
            valueChangedListener(this.password) { _, _, value -> needRequired(password, value)}
            // 获取焦点时清除错误
            focusedClearErrorListener(connectionPort, userName, password) {_, _, _ -> !isNotSqlite() }
        } catch (e: Exception) {
            LOG.error("The database connection window failed to open: {}", e.message, e)
        }
    }

    open fun saveConnectionConfigBefore(): Boolean {
        return dataValidate()
    }

    open fun setConnectionConfig(config: ConnectionConfig?) {
        val that = this
        config?.run {
            executeUpdate = true
            primaryKey = config.id
            that.databaseType.value = config.dbType
            connectionName.text = config.name
            connectionHost.text = config.host
            connectionPort.text = config.port
            that.userName.text = config.userName
            that.password.text = config.password
            that.encoding.value = config.encoding
            that.schema.text = config.schema
            that.connectionDriver.text = config.url
        }
    }

    @FXML
    fun saveConnectConfig() {
        try {
            // 校验数据
            if (this.saveConnectionConfigBefore()) {
                val config = extractConnectionConfig()
                val connection = ConnectionConfigInfo()
                connection.connectName = config.name
                connection.gmtCreate = OffsetDateTime.now()
                connection.connectValue = JSON.toJSONString(config)
                val result: Int
                if (config.id > 0) {
                    connection.id = config.id
                    result = LocalDataSource.update(connection)
                } else {
                    result = LocalDataSource.save(connection)
                }
                if (result > 0) {
                    this.application.close()
                    // 重新加载
                    this.application.loadDatabaseConnectionTree()
                }
            }
        } catch (e: Exception) {
            LOG.error("数据库连接配置信息保存失败: ", e)
        }
    }

    open fun dataValidate(): Boolean {
        var result = validate(this.connectionHost)
        if (isNotSqlite() && !validate(this.connectionPort)) {
            result = false
        } else {
            removeError(connectionPort)
        }
        if (isNotSqlite() && !validate(this.userName)) {
            result = false
        } else {
            removeError(this.userName)
        }
        if (isNotSqlite() && !validate(this.password)) {
            result = false
        } else {
            removeError(password)
        }
        return result
    }
    
    open fun extractConnectionConfig(): ConnectionConfig {
        val name = connectionName.text
        val host = connectionHost.text
        val port = connectionPort.text
        val dbType = databaseType.value
        val username = userName.text
        val passwordValue = password.text
        val encodingValue = encoding.value
        val schemaValue = schema.text
        val url = connectionDriver.text
        val config = ConnectionConfig()
        config.useSsh = false
        config.name = name
        config.host = host
        config.port = port
        config.userName = username
        config.password = passwordValue
        dbType.takeIf {
            !dbType.isNullOrBlank()
        }?.run {
            config.dbType = this.toUpperCase()
        }
        config.encoding = encodingValue
        config.url = url
        config.schema = schemaValue
        if (executeUpdate) {
            config.id = primaryKey
        }
        return config
    }
}
