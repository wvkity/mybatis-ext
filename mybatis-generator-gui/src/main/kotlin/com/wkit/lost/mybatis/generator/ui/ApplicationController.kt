package com.wkit.lost.mybatis.generator.ui

import com.alibaba.fastjson.JSON
import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import com.wkit.lost.mybatis.generator.constants.DatabaseType
import com.wkit.lost.mybatis.generator.constants.Page
import com.wkit.lost.mybatis.generator.jdbc.LocalDataSource
import com.wkit.lost.mybatis.generator.utils.DatabaseUtil
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.TextFieldTreeCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.DirectoryChooser
import org.apache.logging.log4j.LogManager
import java.io.File
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ApplicationController : AbstractController() {

    companion object {
        private val LOG = LogManager.getLogger(ApplicationController)
        private const val ICON_MYSQL = "icons/mysql.png"
        private const val ICON_MYSQL_CLOSE = "icons/mysql_close.png"
        private const val ICON_ORACLE = "icons/oracle.png"
        private const val ICON_ORACLE_CLOSE = "icons/oracle_close.png"
        private const val ICON_SQL_SERVER = "icons/sqlserver.png"
        private const val ICON_SQL_SERVER_CLOSE = "icons/sqlserver_close.png"
        private const val ICON_POSTGRESQL = "icons/postgresql.png"
        private const val ICON_POSTGRESQL_CLOSE = "icons/postgresql_close.png"
        private const val ICON_SQLITE = "icons/sqlite.png"
        private const val ICON_SQLITE_CLOSE = "icons/sqlite_close.png"
        private const val ICON_DB = "icons/db.png"
        private const val ICON_DB_CLOSE = "icons/db_close.png"
        private const val ICON_TABLE = "icons/table.png"
        private const val ICON_COLUMN = "icons/column.png"
        private const val ICON_PRIMARY = "icons/primary.png"
        private const val TREE_ITEM_KEY_CONFIG = "config"
        private const val TREE_ITEM_KEY_ACTIVE = "active"
        private const val TREE_ITEM_KEY_SCHEMA = "schema"
        private const val TREE_ITEM_KEY_COLUMN = "column"
        private const val TREE_ITEM_NODE_LEVEL_CONNECT = 1
        private const val TREE_ITEM_NODE_LEVEL_SCHEMA = 2
        private const val TREE_ITEM_NODE_LEVEL_TABLE = 3
        private const val CONTEXT_MENU_TEXT_CONNECT_OPEN = "打开连接"
        private const val CONTEXT_MENU_TEXT_CONNECT_CLOSE = "关闭连接"
        private const val CONTEXT_MENU_TEXT_CONNECT_EDIT = "编辑连接"
        private const val CONTEXT_MENU_TEXT_CONNECT_REMOVE = "删除连接"
        private const val CONTEXT_MENU_TEXT_DATABASE_OPEN = "打开数据库"
        private const val CONTEXT_MENU_TEXT_DATABASE_CLOSE = "关闭数据库"
        private const val CONTEXT_MENU_TEXT_DATABASE_RELOAD = "重新加载"
        private const val CONTEXT_MENU_TEXT_REFRESH = "刷新"
        private const val MOUSE_EVENT_DOUBLE_CLICK = 2

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
     * 主键类型
     */
    @FXML
    lateinit var needPrimaryKeyType: ComboBox<String>

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
     * 自定义Dao通用接口
     */
    @FXML
    lateinit var baseDaoInterface: TextField

    /**
     * 自定义Service通用接口
     */
    @FXML
    lateinit var baseServiceInterface: TextField

    /**
     * 自定义ServiceImpl通用接口
     */
    @FXML
    lateinit var baseServiceImpl: TextField

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
     * 生成DTO类
     */
    @FXML
    lateinit var needDtoEntity: CheckBox

    /**
     * 生成Dao接口
     */
    @FXML
    lateinit var needDaoInterface: CheckBox

    /**
     * 生成Service接口
     */
    @FXML
    lateinit var needServiceInterface: CheckBox

    /**
     * 生成Controller类
     */
    @FXML
    lateinit var needController: CheckBox

    /**
     * 实现序列化接口
     */
    @FXML
    lateinit var entityImplSerializable: CheckBox

    /**
     * DTO类实现序列化接口
     */
    lateinit var dtoImplSerializable: CheckBox

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
     * 类属性映射自动移除IS前缀
     */
    @FXML
    lateinit var propertyMappingRemoveIsPrefix: CheckBox

    /**
     * 实体类使用JPA注解
     */
    @FXML
    lateinit var entityUseJpaAnnotation: CheckBox

    /**
     * 数据库连接列表
     */
    @FXML
    lateinit var databaseItemView: TreeView<String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        this.databaseConnectionPanelInit()
        this.entityMappingConfigurationPanelInit()
        this.closeOtherOptionsInit()
        this.databaseConnectionViewInit()
    }

    /**
     * 数据库连接标签初始化(图标初始化、绑定点击事件)
     */
    private fun databaseConnectionPanelInit() {
        val databaseImage = ImageView("icons/connect.png")
        databaseImage.fitWidth = 40.0
        databaseImage.fitHeight = 40.0
        this.connectionLabel.graphic = databaseImage
        // 绑定事件
        this.connectionLabel.setOnMouseClicked {
            run {
                val that = this
                val tabPanel = loadWindow(Page.CONNECTION_TAB, "新建数据库连接", false)
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

    /**
     * 关闭其他配置选项面板
     */
    private fun closeOtherOptionsInit() {
        this.closeOtherOptionsLabel.setOnMouseClicked {
            run {
                //AnchorPane.setBottomAnchor(this.otherOptionsPanel, -160.0)
                this.toggle(this.otherOptionsPanel)
            }
        }
    }

    /**
     * 数据库连接初始化
     */
    @Suppress("UNCHECKED_CAST")
    private fun databaseConnectionViewInit() {
        this.databaseItemView.isShowRoot = false
        this.databaseItemView.root = TreeItem()
        // 加载连接列表(先加载tree，后绑定鼠标事件)
        this.loadDatabaseConnectionTree()
        // 事件处理
        val defaultCellFactory = TextFieldTreeCell.forTreeView()
        this.databaseItemView.setCellFactory {
            val cell = defaultCellFactory.call(it)
            // 绑定点击事件
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED) { event ->
                val treeNode = event.pickResult.intersectedNode
                if (treeNode is Text || ((treeNode is TreeCell<*>) && treeNode.text != null && treeNode.text.isNotBlank())) {
                    val treeItem = databaseItemView.selectionModel.selectedItem
                    treeItem?.run {
                        val level = databaseItemView.getTreeItemLevel(treeItem)
                        val treeCell = event.source as TreeCell<String>
                        // 创建右键菜单
                        addContextMenu(treeCell, treeItem, level)
                        // 判断是否为双击事件
                        if (event.button == MouseButton.PRIMARY && event.clickCount == MOUSE_EVENT_DOUBLE_CLICK
                                && !treeItemIsActive(treeItem)) {
                            // 不同节点，加载不同数据
                            when (level) {
                                TREE_ITEM_NODE_LEVEL_CONNECT -> loadSchemaTree(treeItem)
                                TREE_ITEM_NODE_LEVEL_SCHEMA -> loadTableTree(treeItem)
                                // TREE_ITEM_NODE_LEVEL_TABLE -> openColumnTree(treeItem)
                            }
                        }
                    }
                }
            }
            // 处理显示/隐藏/禁用/启用
            cell.setOnMousePressed { event ->
                val treeNode = event.pickResult.intersectedNode
                if (treeNode is Text || ((treeNode is TreeCell<*>) && treeNode.text != null && treeNode.text.isNotBlank())) {
                    val treeItem = databaseItemView.selectionModel.selectedItem
                    treeItem?.run {
                        val level = databaseItemView.getTreeItemLevel(treeItem)
                        val treeCell = event.source as TreeCell<String>
                        if (event.button == MouseButton.SECONDARY) {
                            // 右键菜单
                            contextMenuDisableAndVisible(treeCell, treeItem, level)
                        }
                    }
                }
            }
            cell
        }
    }

    /**
     * 加载数据库连接树
     */
    fun loadDatabaseConnectionTree() {
        try {
            val treeRoot = this.databaseItemView.root
            treeRoot.children.clear()
            val connectionConfigs = LocalDataSource.loadConnectConfigList()
            connectionConfigs.takeIf {
                it.isNotEmpty()
            }?.run {
                this.forEach {
                    val dbType = it.dbType
                    val treeItem = TreeItem(it.name)
                    val imageView = buildImageViewNode(dbType, true)
                    val data = HashMap<String, Any>()
                    data[TREE_ITEM_KEY_CONFIG] = it
                    data[TREE_ITEM_KEY_ACTIVE] = false
                    imageView.userData = data
                    treeItem.graphic = imageView
                    treeRoot.children.add(treeItem)
                }
            }
        } catch (e: Exception) {
            LOG.error("The database connection list failed to load: {}", e.message, e)
        }
    }

    /**
     * 加载数据库树
     */
    private fun loadSchemaTree(treeItem: TreeItem<String>?) {
        treeItem?.run {
            val imageView = treeItem.graphic as ImageView
            val connectionConfig = getConfig(treeItem)
            connectionConfig?.run {
                var schemaList: List<String> = ArrayList()
                connectionConfig.dbType.takeIf {
                    !(it.isNullOrBlank()) && DatabaseType.valueOf(it) == DatabaseType.SQLITE
                }?.run {
                    schemaList = listOf("sqlite_master")
                } ?: run {
                    schemaList = DatabaseUtil.getSchemaList(connectionConfig)
                }
                // 构建数据库节点
                buildTreeItemChild(treeItem, schemaList, ICON_DB_CLOSE, TREE_ITEM_NODE_LEVEL_SCHEMA)
                imageView.image = buildImageIconNode(connectionConfig.dbType, false)
                treeItemExpandedToggle(treeItem)
            }
        }
    }

    private fun loadTableTree(treeItem: TreeItem<String>?) {
        treeItem?.run {
            val imageView = treeItem.graphic as ImageView
            val connectionConfig = getConfig(treeItem)
            // 获取数据库名称
            connectionConfig?.run {
                val tables = DatabaseUtil.getTables(connectionConfig, treeItem.value)
                buildTreeItemChild(treeItem, tables, ICON_TABLE, TREE_ITEM_NODE_LEVEL_TABLE)
                imageView.image = buildSmallIcon(ICON_DB)
                treeItemExpandedToggle(treeItem)
            }
        }
    }

    private fun loadColumnTree(treeItem: TreeItem<String>?) {
        treeItem?.run {
            loadColumnTree(treeItem, getConfig(treeItem)!!, (userData(treeItem)[TREE_ITEM_KEY_SCHEMA]) as String, treeItem.value)
        }
    }

    private fun loadColumnTree(treeItem: TreeItem<String>?, config: ConnectionConfig, defaultSchema: String?, tableName: String) {
        treeItem?.run {
            val columns = DatabaseUtil.getColumns(config, defaultSchema, tableName)
            columns.takeIf {
                it.isNotEmpty()
            }?.run {
                treeItem.children.clear()
                LOG.info("table column info: {}", JSON.toJSONString(columns, true))
                columns.forEach {
                    val value = "${it.getColumnName()} (${it.getJdbcType().toLowerCase()})"
                    val treeItemChild = TreeItem(value)
                    val smallIcon = if (it.isPrimary()) {
                        buildSmallImage(ICON_PRIMARY)
                    } else {
                        buildSmallImage(ICON_COLUMN)
                    }
                    val data = HashMap<String, Any>()
                    data[TREE_ITEM_KEY_COLUMN] = it
                    data[TREE_ITEM_KEY_ACTIVE] = true
                    smallIcon.userData = data
                    //val tooltip = Tooltip(it.getComment())
                    treeItemChild.graphic = smallIcon
                    treeItem.children.add(treeItemChild)
                    treeItem.isExpanded = false
                }
            }
        }
    }

    private fun buildTreeItemChild(treeItem: TreeItem<String>?, list: List<String>,
                                   icon: String, level: Int) {
        treeItem?.run {
            treeItem.children.clear()
            list.takeIf {
                it.isNotEmpty()
            }?.run {
                val config = getConfig(treeItem)!!
                var schema = ""
                if (level == TREE_ITEM_NODE_LEVEL_TABLE) {
                    schema = treeItem.value
                }
                list.forEach {
                    val treeItemChild = TreeItem(it)
                    val smallIcon = buildSmallImage(icon)
                    val data = HashMap<String, Any>()
                    data[TREE_ITEM_KEY_CONFIG] = config
                    data[TREE_ITEM_KEY_ACTIVE] = false
                    data[TREE_ITEM_KEY_SCHEMA] = schema
                    smallIcon.userData = data
                    treeItemChild.graphic = smallIcon
                    // 加载表字段
                    if (level == TREE_ITEM_NODE_LEVEL_TABLE) {
                        loadColumnTree(treeItemChild, config, schema, it)
                    }
                    treeItem.children.add(treeItemChild)
                }
            }
        }
    }

    /**
     * 右键菜单显示/隐藏/启用/禁用
     */
    private fun contextMenuDisableAndVisible(treeCell: TreeCell<String>, treeItem: TreeItem<String>, level: Int) {
        val menuItems = treeCell.contextMenu?.items
        menuItems.takeIf {
            it.isNullOrEmpty()
        } ?: run {
            // 数据库连接节点
            val isExpanded = treeItemIsActive(treeItem)
            menuItems?.forEach { menu ->
                menu.takeIf {
                    (menu.userData as Int) == level
                }?.run {
                    val text = menu.text
                    menu.isVisible = true
                    if (level == TREE_ITEM_NODE_LEVEL_CONNECT) {
                        // 数据库连接节点下的菜单
                        if (text == CONTEXT_MENU_TEXT_CONNECT_CLOSE) {
                            menu.isDisable = !isExpanded
                        } else {
                            menu.isDisable = isExpanded
                        }
                    } else if (level == TREE_ITEM_NODE_LEVEL_SCHEMA) {
                        // 数据库节点下的菜单
                        if (text == CONTEXT_MENU_TEXT_DATABASE_CLOSE || text == CONTEXT_MENU_TEXT_DATABASE_RELOAD) {
                            menu.isDisable = !isExpanded
                        } else {
                            menu.isDisable = isExpanded
                        }
                    }
                } ?: run {
                    // 其他菜单不可见
                    menu.isVisible = false
                }
            }
        }
    }

    /**
     * 添加鼠标右键菜单
     */
    private fun addContextMenu(treeCell: TreeCell<String>, treeItem: TreeItem<String>?, level: Int) {
        treeItem?.run {
            val contextMenu = ContextMenu()
            contextMenu.items.addAll(addContextMenuForConnect(treeItem, TREE_ITEM_NODE_LEVEL_CONNECT))
            contextMenu.items.addAll(addContextMenuForSchema(treeItem, TREE_ITEM_NODE_LEVEL_SCHEMA))
            val refreshMenu = MenuItem(CONTEXT_MENU_TEXT_REFRESH)
            refreshMenu.isVisible = false
            refreshMenu.userData = TREE_ITEM_NODE_LEVEL_TABLE
            refreshMenu.setOnAction {
                try {
                    val isExpanded = treeItem.isExpanded
                    loadColumnTree(treeItem)
                    treeItem.isExpanded = isExpanded
                } catch (e: Exception) {
                    // ignore
                }
            }
            contextMenu.items.add(refreshMenu)
            treeCell.contextMenu = contextMenu
        }
    }

    /**
     * 数据库连接节点鼠标右键菜单
     * @param treeItem TreeItem对象
     * @param level 级别
     */
    private fun addContextMenuForConnect(treeItem: TreeItem<String>, level: Int): MutableList<MenuItem> {
        // 打开数据库连接
        val openMenuItem = MenuItem(CONTEXT_MENU_TEXT_CONNECT_OPEN)
        openMenuItem.isVisible = false
        openMenuItem.userData = level
        openMenuItem.setOnAction { loadSchemaTree(treeItem) }

        // 关闭数据库连接
        val closeMenuItem = MenuItem(CONTEXT_MENU_TEXT_CONNECT_CLOSE)
        closeMenuItem.isVisible = false
        closeMenuItem.userData = level
        closeMenuItem.setOnAction {
            try {
                treeItem.children.clear()
                (treeItem.graphic as ImageView).image = buildImageIconNode(getConfig(treeItem)?.dbType,
                        true)
                treeItemExpandedToggle(treeItem)
            } catch (e: Exception) {
                // ignore
            }
        }

        // 编辑连接
        val editMenuItem = MenuItem(CONTEXT_MENU_TEXT_CONNECT_EDIT)
        editMenuItem.userData = level
        editMenuItem.isVisible = false
        val self = this
        editMenuItem.setOnAction {
            try {
                val config = getConfig(treeItem)
                val tabPanel = loadWindow(Page.CONNECTION_TAB, "修改数据库连接", false)
                tabPanel?.run {
                    val controller = (this as ConnectionTabPanelController)
                    controller.application(self)
                    controller.setConnectionConfig(config)
                    // 判断是否使用SSH
                    config?.run {
                        if (config.useSsh!!) {
                            controller.tabPanel.selectionModel.select(1)
                        } else {
                            controller.tabPanel.selectionModel.select(0)
                        }
                    }
                    this.show()
                }
            } catch (e: Exception) {
                // ignore
            }
        }
        // 删除连接
        val removeMenuItem = MenuItem(CONTEXT_MENU_TEXT_CONNECT_REMOVE)
        removeMenuItem.userData = level
        removeMenuItem.isVisible = false
        removeMenuItem.setOnAction {
            try {
                val config = getConfig(treeItem)!!
                LocalDataSource.delete(config)
                loadDatabaseConnectionTree()
            } catch (e: Exception) {
                LOG.error("Database connection deletion failed: {}", e.message, e)
            }
        }
        return ArrayList(listOf(openMenuItem, closeMenuItem, editMenuItem, removeMenuItem))
    }

    /**
     * 表节点鼠标右键菜单
     * @param treeItem TreeItem对象
     * @param level 级别
     */
    private fun addContextMenuForSchema(treeItem: TreeItem<String>, level: Int): MutableList<MenuItem> {

        // 打开数据库
        val openMenuItem = MenuItem(CONTEXT_MENU_TEXT_DATABASE_OPEN)
        openMenuItem.userData = level
        openMenuItem.isVisible = false
        openMenuItem.setOnAction { loadTableTree(treeItem) }

        // 关闭数据库
        val closeMenuItem = MenuItem(CONTEXT_MENU_TEXT_DATABASE_CLOSE)
        closeMenuItem.userData = level
        closeMenuItem.isVisible = false
        closeMenuItem.setOnAction {
            try {
                treeItem.children.clear()
                (treeItem.graphic as ImageView).image = buildSmallIcon(ICON_DB_CLOSE)
                treeItemExpandedToggle(treeItem)
            } catch (e: Exception) {
                // ignore
            }
        }

        // 重新加载数据库
        val reloadMenuItem = MenuItem(CONTEXT_MENU_TEXT_DATABASE_RELOAD)
        reloadMenuItem.userData = level
        reloadMenuItem.isVisible = false
        reloadMenuItem.isDisable = true
        reloadMenuItem.setOnAction {
            loadTableTree(treeItem)
            // 避免右键菜单无效
            treeItemExpandedToggle(treeItem)
        }
        return ArrayList(listOf(openMenuItem, closeMenuItem, reloadMenuItem))
    }

    /**
     * 获取treeItem上的缓存数据
     * @param treeItem TreeItem对象
     */
    @Suppress("UNCHECKED_CAST")
    private fun userData(treeItem: TreeItem<String>?): MutableMap<String, Any?> {
        return treeItem?.run {
            val imageView = treeItem.graphic as ImageView
            val userData = imageView.userData
            userData?.run {
                userData as HashMap<String, Any?>
            } ?: run {
                HashMap<String, Any?>()
            }
        } ?: run {
            return HashMap()
        }
    }

    /**
     * 更新TreeItem上的数据
     * @param treeItem TreeItem对象
     * @param value 值
     */
    private fun cacheUpdate(treeItem: TreeItem<String>?, value: Any) {
        treeItem?.run {
            treeItem.graphic.userData = value
        }
    }

    /**
     * 更新TreeItem上的数据
     * @param treeItem TreeItem对象
     * @param key 键
     * @param value 值
     */
    private fun cacheUpdate(treeItem: TreeItem<String>?, key: String, value: Any) {
        treeItem?.run {
            val userData = userData(treeItem)
            userData[key] = value
            cacheUpdate(treeItem, userData)
        }
    }

    /**
     * 获取数据库连接配置信息
     * @param treeItem TreeItem对象
     */
    private fun getConfig(treeItem: TreeItem<String>?): ConnectionConfig? {
        val userData = userData(treeItem)
        return userData[TREE_ITEM_KEY_CONFIG]?.run {
            this as ConnectionConfig
        } ?: run {
            null
        }
    }

    /**
     * 检查TreeItem是否展开
     * @param treeItem TreeItem对象
     */
    private fun treeItemIsActive(treeItem: TreeItem<String>?): Boolean {
        val userData = userData(treeItem)
        return userData[TREE_ITEM_KEY_ACTIVE]?.run {
            this as Boolean
        } ?: run {
            return false
        }
    }

    /**
     * TreeItem展开切换
     * @param treeItem TreeItem对象
     */
    private fun treeItemExpandedToggle(treeItem: TreeItem<String>?) {
        treeItem?.run {
            val userData = userData(treeItem)
            userData[TREE_ITEM_KEY_ACTIVE].takeIf {
                it != null
            }?.run {
                val isExpanded = !(this as Boolean)
                treeItem.isExpanded = isExpanded
                cacheUpdate(treeItem, TREE_ITEM_KEY_ACTIVE, isExpanded)
            } ?: run {
                treeItem.isExpanded = true
                cacheUpdate(treeItem, TREE_ITEM_KEY_ACTIVE, true)
            }
        }
    }

    private fun buildImageViewNode(dbType: String?, isClose: Boolean): ImageView {
        val imageView = ImageView(buildImageIconNode(dbType, isClose))
        imageView.fitWidth = 16.0
        imageView.fitHeight = 16.0
        return imageView
    }

    private fun buildImageIconNode(dbType: String?, isClose: Boolean): Image {
        var imgIcon: String? = null
        dbType.takeIf {
            it.isNullOrBlank()
        }?.run {
            imgIcon = if (isClose) {
                ICON_DB_CLOSE
            } else {
                ICON_DB
            }
        } ?: run {
            if (isClose) {
                imgIcon = when (dbType) {
                    "MySQL" -> ICON_MYSQL_CLOSE
                    "MYSQL_8" -> ICON_MYSQL_CLOSE
                    "ORACLE" -> ICON_ORACLE_CLOSE
                    "POSTGRESQL" -> ICON_POSTGRESQL_CLOSE
                    "SQLITE" -> ICON_SQLITE_CLOSE
                    "SQL_SERVER" -> ICON_SQL_SERVER_CLOSE
                    else -> ICON_DB_CLOSE
                }
            } else {
                imgIcon = when (dbType) {
                    "MySQL" -> ICON_MYSQL
                    "MYSQL_8" -> ICON_MYSQL
                    "ORACLE" -> ICON_ORACLE
                    "POSTGRESQL" -> ICON_POSTGRESQL
                    "SQLITE" -> ICON_SQLITE
                    "SQL_SERVER" -> ICON_SQL_SERVER
                    else -> ICON_DB
                }
            }
        }
        return Image(imgIcon)
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

    /**
     * 打开其他选项面板
     */
    @FXML
    fun openOtherOptionsPanel() {
        this.toggle(this.otherOptionsPanel)
    }

    private fun buildSmallIcon(icon: String): Image {
        return Image(icon)
    }

    private fun buildSmallImage(icon: String): ImageView {
        val imageView = ImageView(buildSmallIcon(icon))
        imageView.fitWidth = 16.0
        imageView.fitHeight = 16.0
        return imageView
    }
}