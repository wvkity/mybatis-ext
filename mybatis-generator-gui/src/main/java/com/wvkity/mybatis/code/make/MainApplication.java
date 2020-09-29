package com.wvkity.mybatis.code.make;

import com.wvkity.mybatis.code.make.config.SystemConfiguration;
import com.wvkity.mybatis.code.make.utils.PropertiesUtil;
import com.wvkity.mybatis.code.make.view.MainWindowView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class MainApplication extends Application {

    /**
     * 本地配置缓存
     */
    private static final Map<String, String> LOCAL_CONFIG_CACHE = new HashMap<>();

    /*public static void main(String[] args) {
        launch(args);
    }*/

    @Override
    public void start(Stage stage) throws Exception {
        // 初始化配置
        SystemConfiguration.init();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final URL location = classLoader.getResource("view/Application.fxml");
        final FXMLLoader loader = new FXMLLoader(location);
        final Parent parent = loader.load();
        stage.setScene(new Scene(parent));
        stage.setResizable(true);
        stage.show();
        MainWindowView view = loader.getController();
        view.setPrimaryStage(stage);
        try {
            // 加载配置文件
            merge(loadSystemConfig(classLoader))
                    .merge(loadSpecifyConfig(SystemConfiguration.getGlobalConfigFile()))
                    .merge(loadSpecifyConfig(SystemConfiguration.getSpecifyConfigFile()));
            view.initConfig(LOCAL_CONFIG_CACHE);
        } catch (Exception e) {
            log.error("Failed to load system configuration information: ", e);
        }
    }

    /**
     * 合并配置
     * @param config 配置信息
     */
    private MainApplication merge(final Map<String, String> config) {
        if (config != null && !config.isEmpty()) {
            MainApplication.LOCAL_CONFIG_CACHE.putAll(config);
        }
        return this;
    }

    /**
     * 加载系统配置文件
     * @param classLoader {@link ClassLoader}
     * @return 配置信息
     */
    private Map<String, String> loadSystemConfig(final ClassLoader classLoader) {
        try {
            return PropertiesUtil.load(classLoader.getResourceAsStream(SystemConfiguration.getSystemConfigFile()));
        } catch (Exception e) {
            log.error("Failed to load the system configuration file: ", e);
        }
        return new HashMap<>(0);
    }

    /**
     * 加载指定配置文件
     * @param file 文件路径
     * @return 配置信息
     */
    private Map<String, String> loadSpecifyConfig(final String file) {
        return Optional.ofNullable(file).map(File::new).filter(File::exists).map(PropertiesUtil::load)
                .orElse(new HashMap<>(0));
    }
}
