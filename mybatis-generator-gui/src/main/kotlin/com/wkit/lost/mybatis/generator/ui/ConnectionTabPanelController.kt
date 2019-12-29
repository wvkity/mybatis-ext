package com.wkit.lost.mybatis.generator.ui

import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import javafx.fxml.FXML
import javafx.scene.control.TabPane
import javafx.scene.layout.AnchorPane
import org.apache.logging.log4j.LogManager
import java.net.URL
import java.util.*

class ConnectionTabPanelController : AbstractController() {

    companion object {
        private val LOG = LogManager.getLogger(ConnectionTabPanelController)
    }

    /**
     * 选项卡面板
     */
    @FXML
    lateinit var tabPanel: TabPane

    @FXML
    lateinit var basicConnection: AnchorPane

    @FXML
    lateinit var basicConnectionController: BasicConnectionController

    @FXML
    lateinit var sshConnection: AnchorPane

    @FXML
    lateinit var sshConnectionController: SshConnectionController

    /**
     * 主程序控制器
     */
    lateinit var application: ApplicationController

    private var isSshConnection = false

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        this.tabPanel.selectionModel.selectedItemProperty().addListener { observable, _, _ ->
            run {
                isSshConnection = "SSH" == observable.value.text
                this.layer.close()
                this.layer.show()
            }
        }
    }

    fun application(application: ApplicationController) {
        this.application = application
        this.basicConnectionController.application = application
        this.sshConnectionController.application = application
    }

    fun setConnectionConfig(config: ConnectionConfig?) {
        config?.run {
            config.takeIf {
                it.useSsh != null && it.useSsh!!
            }?.run {
                sshConnectionController.setConnectionConfig(this)
            } ?: run {
                basicConnectionController.setConnectionConfig(this)
            }
        }
    }

    @FXML
    fun saveConnectionConfig() {
        if (isSshConnection) {
            sshConnectionController.saveConnectConfig()
        } else {
            basicConnectionController.saveConnectConfig()
        }
    }

    /**
     * 关闭窗口
     */
    @FXML
    fun cancel() {
        this.layer.close()
    }

}