package com.wkit.lost.mybatis.generator.ui

import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import javafx.fxml.FXML
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.apache.logging.log4j.LogManager
import java.net.URL
import java.util.*

/**
 * SSH连接方式
 */
open class SshConnectionController : BasicConnectionController() {

    companion object {
        private val LOG = LogManager.getLogger(SshConnectionController)
    }

    /**
     * SSH主机
     */
    @FXML
    protected lateinit var sshConnectionHost: TextField

    /**
     * SSH端口
     */
    @FXML
    protected lateinit var sshConnectionPort: TextField

    /**
     * SSH用户名
     */
    @FXML
    protected lateinit var sshUserName: TextField

    /**
     * SSH密码
     */
    @FXML
    protected lateinit var sshPassword: PasswordField

    /**
     * 本地端口
     */
    @FXML
    protected lateinit var localPort: TextField

    /**
     * 目标端口
     */
    @FXML
    protected lateinit var targetPort: TextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        super.initialize(location, resources)
        try {
            valueChangedListeners(sshConnectionHost, sshConnectionPort, sshUserName, sshPassword, localPort)
        } catch (e: Exception) {
            LOG.error("The database connection window failed to open: {}", e.message, e)
        }
    }

    override fun extractConnectionConfig(): ConnectionConfig {
        val config = super.extractConnectionConfig()
        config.useSsh = true
        val sshHost = sshConnectionHost.text
        val sshPort = sshConnectionPort.text
        val sshUsername = sshUserName.text
        val sshPasswordValue = sshPassword.text
        val localPortValue = localPort.text
        val targetPortValue = targetPort.text
        config.sshHost = sshHost
        config.sshPort = sshPort
        config.sshUserName = sshUsername
        config.sshPassword = sshPasswordValue
        config.targetPort = targetPortValue
        config.localPort = localPortValue
        return config
    }

    override fun saveConnectionConfigBefore(): Boolean {
        val result = super.saveConnectionConfigBefore()
        val rst = validate(sshConnectionHost, sshConnectionPort, sshUserName, sshPassword, localPort)
        return result && rst
    }

    override fun setConnectionConfig(config: ConnectionConfig?) {
        super.setConnectionConfig(config)
        val that = this
        config?.run {
            sshConnectionHost.text = config.sshHost
            sshConnectionPort.text = config.sshPort
            that.sshUserName.text = config.sshUserName
            that.sshPassword.text = config.sshPassword
            that.localPort.text = config.localPort
            that.targetPort.text = config.targetPort
        }
    }
}