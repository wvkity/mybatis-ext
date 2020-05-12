package com.wkit.lost.mybatis.generator.run

import com.wkit.lost.mybatis.generator.config.Configuration
import com.wkit.lost.mybatis.generator.mapping.JdbcJavaTypeRegistrar
import com.wkit.lost.mybatis.generator.ui.ApplicationController
import com.wkit.lost.mybatis.generator.utils.FileUtil
import com.wkit.lost.mybatis.generator.utils.PropertiesUtil
import com.wkit.lost.mybatis.generator.utils.SystemUtil
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.FileInputStream

class ApplicationRunning : Application() {

    companion object {
        private val LOG = LogManager.getLogger(ApplicationRunning)
        private const val SYS_CONFIG_FILE_NAME = "mybatis_generator.properties"
        private const val SYS_CONFIG_DIR = ".MybatisGenerator/config"
        private val SYS_CONFIGURATION_CACHE = HashMap<String, String>()
    }

    override fun start(stage: Stage) {
        // 初始化配置
        Configuration.initConfig()
        // 
        val location = Thread.currentThread().contextClassLoader.getResource("fxml/application.fxml")
        val fxmlLoader = FXMLLoader(location)
        val root = fxmlLoader.load<Parent>()
        stage.scene = Scene(root)
        stage.isResizable = true
        stage.show()
        val controller = fxmlLoader.getController<ApplicationController>()
        controller.primaryStage = stage
        try {
            // 加载系统全局配置
            val classLoader = Thread.currentThread().contextClassLoader
            val sysConfig = PropertiesUtil.loadProperties(classLoader.getResourceAsStream(SYS_CONFIG_FILE_NAME))
            sysConfig.takeIf {
                it.isNotEmpty()
            }?.run {
                SYS_CONFIGURATION_CACHE.putAll(sysConfig)
            }
            // 加载用户全局配置(用户主目录下)
            val globalFilePath = SystemUtil.userHome() + FileUtil.SLASH + SYS_CONFIG_DIR + FileUtil.SLASH + SYS_CONFIG_FILE_NAME
            globalFilePath.takeIf {
                FileUtil.exists(it)
            }?.run {
                val userGlobalConfig = PropertiesUtil.loadProperties(FileInputStream(File(globalFilePath)))
                userGlobalConfig.takeIf {
                    it.isNotEmpty()
                }?.run {
                    SYS_CONFIGURATION_CACHE.putAll(userGlobalConfig)
                }
            }
            // 加载当前jar同级目录下的配置文件
            val userFilePath = SystemUtil.userDir() + FileUtil.SLASH + SYS_CONFIG_FILE_NAME
            userFilePath.takeIf {
                FileUtil.exists(it)
            }?.run {
                val userCustomConfig = PropertiesUtil.loadProperties(FileInputStream(File(userFilePath)))
                userCustomConfig.takeIf {
                    it.isNotEmpty()
                }?.run {
                    SYS_CONFIGURATION_CACHE.putAll(userCustomConfig)
                }
            }
            // 缓存到JdbcJavaTypeMapping类中
            SYS_CONFIGURATION_CACHE.takeIf {
                it.isNotEmpty()
            }?.run {
                for ((key, value) in SYS_CONFIGURATION_CACHE) {
                    if (!key.contains(".")) {
                        JdbcJavaTypeRegistrar.register(key, value)
                    }
                }
            }
            controller.initSysConfig(SYS_CONFIGURATION_CACHE)
        } catch (e: Exception) {
            LOG.error("The load system configuration failed: {}", e.message, e)
        }
    }

}

fun main(args: Array<String>) = Application.launch(ApplicationRunning::class.java, *args)

