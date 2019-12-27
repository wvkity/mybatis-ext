package com.wkit.lost.mybatis.generator.run

import com.wkit.lost.mybatis.generator.config.Configuration
import com.wkit.lost.mybatis.generator.ui.ApplicationController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class ApplicationRunning : Application() {

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
    }
}

fun main(args: Array<String>) = Application.launch(ApplicationRunning::class.java, *args)

