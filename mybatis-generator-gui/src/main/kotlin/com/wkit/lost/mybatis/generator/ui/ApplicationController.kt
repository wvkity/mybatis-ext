package com.wkit.lost.mybatis.generator.ui

import com.wkit.lost.mybatis.generator.constants.Page
import javafx.animation.TranslateTransition
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import javafx.util.Duration
import org.apache.logging.log4j.LogManager
import java.io.File
import java.net.URL
import java.util.*

class ApplicationController : AbstractController() {
    
    companion object {
        private val LOG = LogManager.getLogger(ApplicationController)
    }

    /**
     * 数据库连接标签
     */
    @FXML
    lateinit var connectionLabel: Label

    /**
     * 表实体映射配置标签
     */
    @FXML
    lateinit var mappingConfigLabel: Label

    /**
     * 项目名称
     */
    @FXML
    lateinit var projectName: TextField

    /**
     * 项目所在目录
     */
    @FXML
    lateinit var projectFolder: TextField

    /**
     * 数据库表名
     */
    @FXML
    lateinit var tableName: TextField

    /**
     * 表名前缀
     */
    @FXML
    lateinit var tableNamePrefix: TextField

    /**
     * 主键(ID)
     */
    @FXML
    lateinit var primaryKey: TextField

    /**
     * 源码目录
     */
    @FXML
    lateinit var sourceCodeTargetFolder: TextField

    /**
     * 测试源码目录
     */
    @FXML
    lateinit var testCodeTargetFolder: TextField

    /**
     * 配置文件资源目录
     */
    @FXML
    lateinit var resourcesTargetFolder: TextField

    /**
     * 根包名
     */
    @FXML
    lateinit var rootTargetPackage: TextField

    /**
     * 实体类名
     */
    @FXML
    lateinit var entityClassName: TextField

    /**
     * 实体类包名
     */
    @FXML
    lateinit var entityTargetPackage: TextField

    /**
     * DTO类名
     */
    @FXML
    lateinit var dtoEntityClassName: TextField

    /**
     * DTO类包名
     */
    @FXML
    lateinit var dtoEntityTargetPackage: TextField

    /**
     * Mapper接口名
     */
    @FXML
    lateinit var mapperClassName: TextField

    /**
     * Mapper接口包名
     */
    @FXML
    lateinit var mapperTargetPackage: TextField

    /**
     * 映射XML文件名
     */
    @FXML
    lateinit var mappingFileName: TextField

    /**
     * 映射XML文件目录
     */
    @FXML
    lateinit var mappingTargetFolder: TextField

    /**
     * Service接口名
     */
    @FXML
    lateinit var serviceClassName: TextField

    /**
     * Service接口包名
     */
    @FXML
    lateinit var serviceTargetPackage: TextField

    /**
     * ServiceImpl名
     */
    @FXML
    lateinit var serviceImplClassName: TextField

    /**
     * ServiceImpl包名
     */
    @FXML
    lateinit var serviceImplTargetPackage: TextField

    /**
     * Controller类名
     */
    @FXML
    lateinit var controllerClassName: TextField

    /**
     * Controller包名
     */
    @FXML
    lateinit var controllerTargetPackage: TextField

    /**
     * Test类包名
     */
    @FXML
    lateinit var testTargetPackage: TextField

    /**
     * Test类名
     */
    @FXML
    lateinit var testClassName: TextField

    /**
     * JPA注解
     */
    @FXML
    lateinit var jpaAnnotationChoice: ComboBox<String>

    /**
     * 文件编码格式
     */
    @FXML
    lateinit var fileEncodingChoice: ComboBox<String>

    /**
     * 使用Schema前缀
     */
    @FXML
    lateinit var useSchemaPrefix: CheckBox

    /**
     * 表名前缀匹配时，类名自动去除前缀
     */
    lateinit var replaceWithTablePrefix: CheckBox

    /**
     * 覆盖原XML文件
     */
    @FXML
    lateinit var overrideXml: CheckBox

    /**
     * 生成实体类注释
     */
    lateinit var needComment: CheckBox

    /**
     * 使用Lombok插件
     */
    @FXML
    lateinit var useLombokPlugin: CheckBox

    /**
     * 生成toString、hashCode、equals方法
     */
    @FXML
    lateinit var needToStringHashCodeEquals: CheckBox

    /**
     * Mapper接口使用@Repository注解
     */
    @FXML
    lateinit var useRepositoryAnnotation: CheckBox

    /**
     * 使用DTO类作为返回值
     */
    @FXML
    lateinit var useDtoAsReturnValue: CheckBox

    /**
     * 其他选项面板
     */
    @FXML
    lateinit var otherOptionsPanel: VBox

    /**
     * 关闭其他选项标签
     */
    @FXML
    lateinit var closeOtherOptionsLabel: Label

    /**
     * 数据库连接列表
     */
    @FXML
    lateinit var databaseItemView: TreeView<String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        this.databaseConnectionPanelInit()
        this.entityMappingConfigurationPanelInit()
        this.closeOtherOptionsInit()
    }

    /**
     * 数据库连接标签初始化(图标初始化、绑定点击事件)
     */
    private fun databaseConnectionPanelInit() {
        val databaseImage = ImageView("icons/database.png")
        databaseImage.fitWidth = 40.0
        databaseImage.fitHeight = 40.0
        this.connectionLabel.graphic = databaseImage
        // 绑定事件
        this.connectionLabel.setOnMouseClicked {
            run {
                val that = this
                val tabPanel = loadWindow(Page.CONNECTION_TAB, "", false)
                tabPanel?.run {
                    (this as ConnectionTabPanelController).application(that)
                    this.show()
                }
            }
        }
    }

    /**
     * 表实体映射配置标签初始化
     */
    private fun entityMappingConfigurationPanelInit() {
        val mappingImage = ImageView("icons/configuration.png")
        mappingImage.fitWidth = 40.0
        mappingImage.fitHeight = 40.0
        this.mappingConfigLabel.graphic = mappingImage
        // 绑定事件
    }
    
    private fun closeOtherOptionsInit() {
        this.closeOtherOptionsLabel.setOnMouseClicked { 
            run {
                //AnchorPane.setBottomAnchor(this.otherOptionsPanel, -160.0)
                this.toggle(this.otherOptionsPanel)
            }
        }
    }

    /**
     * 选择项目目录
     */
    @FXML
    fun chooseProjectFolder() {
        val directoryChooser = DirectoryChooser()
        val selectFolder: File? = directoryChooser.showDialog(this.primaryStage)
        selectFolder?.run {
            projectFolder.text = selectFolder.absolutePath
        }
    }
    
    @FXML
    fun openOtherOptionsPanel() {
        this.toggle(this.otherOptionsPanel)
    }
    
}