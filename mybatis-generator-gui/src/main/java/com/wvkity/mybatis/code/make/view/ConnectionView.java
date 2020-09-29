package com.wvkity.mybatis.code.make.view;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 数据库连接窗口
 */
@Log4j2
@Getter
@Setter
@Accessors(chain = true)
public class ConnectionView extends AbstractView {

    /**
     * 选项卡面板
     */
    @FXML
    private TabPane tabPanel;
    /**
     * 基本连接选项卡面板
     */
    @FXML
    private AnchorPane basicConnection;
    /**
     * SSH连接选项卡面板
     */
    @FXML
    private AnchorPane sshConnection;
    /**
     * 基本连接窗口对象
     */
    @FXML
    private BasicConnectionView basicConnectionController;
    /**
     * SSH连接窗口对象
     */
    @FXML
    private SshConnectionView sshConnectionController;
    /**
     * 主窗口对象
     */
    private MainWindowView mainWindowView;
    /**
     * 是否为SSH连接类型
     */
    private boolean useSshConnection = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.tabPanel.getSelectionModel().selectedItemProperty().addListener((observable, __, ___) -> {
                this.useSshConnection = "SSH".equals(observable.getValue().getText());
                this.getLayer().close();
                this.getLayer().show();
            });
        } catch (Exception ignore) {
            // ignore
        }
    }

    public ConnectionView apply(final MainWindowView mainWindowView) {
        this.mainWindowView = mainWindowView;
        this.basicConnectionController.setMainWindowView(mainWindowView);
        this.sshConnectionController.setMainWindowView(mainWindowView);
        return this;
    }

    /**
     * 保存配置
     */
    @FXML
    public void saveConnectConfig() {
        if (this.useSshConnection) {
            this.sshConnectionController.saveConnectConfig();
        } else {
            this.basicConnectionController.saveConnectConfig();
        }
    }

    /**
     * 取消(按钮事件绑定)
     */
    @FXML
    public void cancel() {
        this.close();
    }
}
