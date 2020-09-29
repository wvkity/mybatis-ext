package com.wvkity.mybatis.code.make.view;

import com.alibaba.fastjson.JSON;
import com.wvkity.mybatis.code.make.bean.ConnectConfig;
import com.wvkity.mybatis.code.make.builder.Builder;
import com.wvkity.mybatis.code.make.config.ConnectConfiguration;
import com.wvkity.mybatis.code.make.datasource.LocalDataSource;
import com.wvkity.mybatis.code.make.encrypt.AESEncrypt;
import com.wvkity.mybatis.code.make.observable.Filter;
import com.wvkity.mybatis.code.make.observable.SqLiteFilter;
import com.wvkity.mybatis.code.make.utils.KeyUtil;
import com.wvkity.mybatis.code.make.utils.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Log4j2
@Getter
@Setter
@Accessors(chain = true)
public class BasicConnectionView extends AbstractView {

    /**
     * 连接名称
     */
    @FXML
    protected TextField connectName;
    /**
     * 主机地址
     */
    @FXML
    protected TextField host;
    /**
     * 端口
     */
    @FXML
    protected TextField port;
    /**
     * 数据库类型
     */
    @FXML
    protected ComboBox<String> databaseType;
    /**
     * 用户名
     */
    @FXML
    protected TextField userName;
    /**
     * 密码
     */
    @FXML
    protected TextField password;
    /**
     * 数据库名称
     */
    @FXML
    protected TextField schema;
    /**
     * 连接地址
     */
    @FXML
    protected TextField driver;
    /**
     * 编码
     */
    @FXML
    protected ComboBox<String> encoding;

    /**
     * 主窗口
     */
    protected MainWindowView mainWindowView;

    /**
     * 过滤器
     */
    protected Filter<String, String> filter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (filter == null) {
                this.filter = Builder.of(SqLiteFilter<String>::new)
                        .with(SqLiteFilter::setNode, this.databaseType).release();
            }
            addChangedListener(this.host);
            addChangedListener(this.port, this.filter);
            addChangedListener(this.userName, this.filter);
            addChangedListener(this.password, this.filter);
            addFocusOutedListeners(
                    Builder.of(SqLiteFilter<Boolean>::new).with(SqLiteFilter::setNode, this.databaseType).release(),
                    this.port, this.userName, this.password);
        } catch (Exception e) {
            log.error("系统出现异常: ", e);
        }
    }

    /**
     * 保存配置
     */
    public void saveConnectConfig() {
        if (saveBefore()) {
            try {
                final ConnectConfiguration configuration = extract();
                final LocalDateTime time = LocalDateTime.now();
                final String key = KeyUtil.generated(time);
                if (StringUtil.isNotEmpty(configuration.getPassword())) {
                    configuration.setPassword(AESEncrypt.encrypt(key, configuration.getPassword()));
                }
                if (StringUtil.isNotEmpty(configuration.getUserName())) {
                    configuration.setUserName(AESEncrypt.encrypt(key, configuration.getUserName()));
                }
                final ConnectConfig config = ConnectConfig.builder()
                        .name(configuration.getName())
                        .config(JSON.toJSONString(configuration))
                        .gmtCreated(time)
                        .gmtLastModified(time)
                        .build();
                // 保存
                final int result = LocalDataSource.save(config);
                if (result > 0) {
                    this.mainWindowView.close();
                }
            } catch (Exception ignore) {
                // ignore
            }
        }
    }

    public boolean saveBefore() {
        boolean result = validated(this.host);
        if (!this.filter.filter()) {
            result = validated(this.port, this.userName, this.password);
        } else {
            removeErrors(this.port, this.userName, this.password);
        }
        return result;
    }

    /**
     * 提取数据
     * @return {@link ConnectConfiguration}
     */
    public ConnectConfiguration extract() {
        return ConnectConfiguration.builder()
                .name(this.connectName.getText())
                .useSsh(false)
                .dbType(this.databaseType.getValue())
                .host(this.host.getText())
                .port(this.port.getText())
                .userName(this.userName.getText())
                .password(this.password.getText())
                .encoding(this.encoding.getValue())
                .schema(this.schema.getText())
                .jdbcUrl(this.driver.getText())
                .build();
    }
}
