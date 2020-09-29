package com.wvkity.mybatis.code.make.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
@Getter
@Setter
@Accessors(chain = true)
public class SshConnectionView extends BasicConnectionView {

    /**
     * SSH主机
     */
    @FXML
    private TextField sshHost;
    /**
     * SSH端口
     */
    @FXML
    private TextField sshPort;
    /**
     * SSH用户名
     */
    @FXML
    private TextField sshUserName;
    /**
     * SSH密码
     */
    @FXML
    private TextField sshPassword;
    /**
     * 本地端口
     */
    @FXML
    private TextField localPort;
    /**
     * 目标端口
     */
    @FXML
    private TextField targetPort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
