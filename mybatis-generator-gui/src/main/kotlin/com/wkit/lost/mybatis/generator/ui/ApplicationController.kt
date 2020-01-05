package com.wkit.lost.mybatis.generator.ui

import com.alibaba.fastjson.JSON
import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import com.wkit.lost.mybatis.generator.bean.GeneratorConfig
import com.wkit.lost.mybatis.generator.bean.Table
import com.wkit.lost.mybatis.generator.constants.*
import com.wkit.lost.mybatis.generator.jdbc.LocalDataSource
import com.wkit.lost.mybatis.generator.utils.DatabaseUtil
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
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
        private const val MOUSE_EVENT_DOUBLE_CLICK = 2
        private val tableObservable = FXCollections.observableArrayList<Table>()
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
     * 定制
     */
    @FXML
    lateinit var customMade: Button

    /**
     * 项目名称
     */
    @FXML
    lateinit var moduleName: TextField

    /**
     * groupId(maven、gradle用)
     */
    @FXML
    lateinit var uniqueGroupId: TextField

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
     * Entity基类
     */
    @FXML
    lateinit var baseEntity: TextField

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
     * 实体类使用swagger注解
     */
    @FXML
    lateinit var entityUseSwaggerAnnotation: CheckBox

    /**
     * DTO类使用swagger注解
     */
    @FXML
    lateinit var dtoUseSwaggerAnnotation: CheckBox

    /**
     * 数据库连接列表
     */
    @FXML
    lateinit var databaseItemView: TreeView<String>

    /////////
    private var tableNamePrefixTempVariable = SimpleStringProperty("")
    ////////

    /**
     * 选中表缓存
     */
    private val selectTableCache = SimpleListProperty<Table>(tableObservable)

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        try {
            this.databaseConnectionPanelInit()
            this.entityMappingConfigurationPanelInit()
            this.otherOptionsInit()
            this.databaseConnectionViewInit()
            this.addValueChangeListener()
            this.addFocusEventListener()
            this.addFocusRemoveErrorListener()
            this.buttonBindEventListener()
            // 选择表添加事件监听
            selectTableCache.addListener { _, _, tables ->
                tableName.text = tables.joinToString { it.getName() }
            }
        } catch (e: Exception) {
            // ignore
        }
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
    private fun otherOptionsInit() {
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
                        addContextMenu(treeCell, treeItem)
                        // 判断是否为双击事件
                        if (event.button == MouseButton.PRIMARY && event.clickCount == MOUSE_EVENT_DOUBLE_CLICK
                                && !treeItemIsActive(treeItem)) {
                            // 不同节点，加载不同数据
                            when (level) {
                                TreeItemNodeLevel.CONNECT.value -> loadSchemaTree(treeItem)
                                TreeItemNodeLevel.SCHEMA.value -> loadTableTree(treeItem)
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
                    data[TreeItemCacheKey.CONFIG.key] = it
                    data[TreeItemCacheKey.ACTIVE.key] = false
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
                buildTreeItemChild(treeItem, schemaList, TreeItemIcon.DB_CLOSE.icon, TreeItemNodeLevel.SCHEMA.value)
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
                buildTreeItemChild(treeItem, tables, TreeItemIcon.TABLE.icon, TreeItemNodeLevel.TABLE.value)
                imageView.image = buildSmallIcon(TreeItemIcon.DB.icon)
                // 添加监听
                tables.forEach { 
                    tableNamePrefixTempVariable.bindBidirectional(it.getTablePrefixBridgeProperty())
                }
                treeItemExpandedToggle(treeItem)
            }
        }
    }

    private fun loadColumnTree(treeItem: TreeItem<String>?) {
        treeItem?.run {
            loadColumnTree(treeItem, getConfig(treeItem)!!, (userData(treeItem)[TreeItemCacheKey.SCHEMA.key]) as String,
                    (userData(treeItem)[TreeItemCacheKey.TABLE.key] as Table))
        }
    }

    private fun loadColumnTree(treeItem: TreeItem<String>?, config: ConnectionConfig, defaultSchema: String?, table: Table) {
        treeItem?.run {
            val columns = DatabaseUtil.getColumns(config, defaultSchema, table.getName())
            columns.takeIf {
                it.isNotEmpty()
            }?.run {
                treeItem.children.clear()
                columns.forEach {
                    val value = "${it.getColumnName()} (${it.getJdbcType().toLowerCase()})"
                    val treeItemChild = TreeItem(value)
                    val smallIcon = if (it.isPrimary()) {
                        buildSmallImage(TreeItemIcon.PRIMARY.icon)
                    } else {
                        buildSmallImage(TreeItemIcon.COLUMN.icon)
                    }
                    val data = HashMap<String, Any>()
                    data[TreeItemCacheKey.COLUMN.key] = it
                    data[TreeItemCacheKey.ACTIVE.key] = true
                    smallIcon.userData = data
                    //val tooltip = Tooltip(it.getComment())
                    treeItemChild.graphic = smallIcon
                    treeItem.children.add(treeItemChild)
                    treeItem.isExpanded = false
                }
                table.columns = columns
                LOG.info("table column info: {}", JSON.toJSONString(table, true))
            }
        }
    }

    private fun buildTreeItemChild(treeItem: TreeItem<String>?, list: List<*>,
                                   icon: String, level: Int) {
        treeItem?.run {
            treeItem.children.clear()
            list.takeIf {
                it.isNotEmpty()
            }?.run {
                val config = getConfig(treeItem)!!
                var schema = ""
                if (level == TreeItemNodeLevel.TABLE.value) {
                    schema = treeItem.value
                }
                list.forEach {
                    val treeItemChild: TreeItem<String> = if (level == TreeItemNodeLevel.TABLE.value) {
                        TreeItem((it as Table).getName())
                    } else {
                        TreeItem(it as String)
                    }
                    val smallIcon = buildSmallImage(icon)
                    val data = HashMap<String, Any>()
                    data[TreeItemCacheKey.CONFIG.key] = config
                    data[TreeItemCacheKey.ACTIVE.key] = false
                    data[TreeItemCacheKey.SCHEMA.key] = schema
                    smallIcon.userData = data
                    treeItemChild.graphic = smallIcon
                    // 加载表字段
                    if (level == TreeItemNodeLevel.TABLE.value) {
                        data[TreeItemCacheKey.TABLE.key] = it
                        // 标记是否选择添加生成代码
                        data[TreeItemCacheKey.SELECTION.key] = false
                        loadColumnTree(treeItemChild, config, schema, it as Table)
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
                    if (level == TreeItemNodeLevel.CONNECT.value) {
                        // 数据库连接节点下的菜单
                        if (text == ContextMenuText.CONNECT_CLOSE.text) {
                            menu.isDisable = !isExpanded
                        } else {
                            menu.isDisable = isExpanded
                        }
                    } else if (level == TreeItemNodeLevel.SCHEMA.value) {
                        // 数据库节点下的菜单
                        if (text == ContextMenuText.DATABASE_CLOSE.text || text == ContextMenuText.DATABASE_RELOAD.text) {
                            menu.isDisable = !isExpanded
                        } else if (text == ContextMenuText.ADD_DB_FOR_GENERATE.text) {
                            // 检查是否已全选
                            if (isExpanded) {
                                menu.isDisable = !isSelectionAll(treeItem) { item ->
                                    !isSelection(item)
                                }
                            }
                        } else if (text == ContextMenuText.REMOVE_DB_FROM_GENERATE.text) {
                            if (isExpanded) {
                                menu.isDisable = !isSelectionAll(treeItem) { item ->
                                    isSelection(item)
                                }
                            }
                        } else {
                            menu.isDisable = isExpanded
                        }
                    } else if (level == TreeItemNodeLevel.TABLE.value) {
                        // 表节点下的菜单
                        if (text == ContextMenuText.ADD_TABLE_FOR_GENERATE.text) {
                            menu.isDisable = isSelection(treeItem)
                        } else if (text == ContextMenuText.REMOVE_TABLE_FROM_GENERATE.text) {
                            menu.isDisable = !isSelection(treeItem)
                        }
                    }
                } ?: run {
                    // 其他菜单不可见
                    menu.isVisible = false
                }
            }
        }
    }

    private fun isSelection(treeItem: TreeItem<String>?): Boolean {
        return treeItem.takeIf {
            it != null
        }?.run {
            val value = userData(treeItem)[TreeItemCacheKey.SELECTION.key]
            value != null && (value as Boolean)
        } ?: run {
            true
        }
    }

    private fun isSelectionAll(treeItem: TreeItem<String>, filter: (treeItem: TreeItem<String>) -> Boolean): Boolean {
        val children = treeItem.children
        return children.takeIf {
            it.isNotEmpty()
        }?.run {
            var result = false
            for (child in children) {
                if (filter(child)) {
                    if (!result) {
                        result = true
                    }
                }
            }
            result
        } ?: run {
            false
        }
    }

    /**
     * 清除选中表缓存
     */
    private fun clearSelectCache() {
        selectTableCache.clear()
    }

    /**
     * 添加鼠标右键菜单
     */
    private fun addContextMenu(treeCell: TreeCell<String>, treeItem: TreeItem<String>?) {
        treeItem?.run {
            val contextMenu = ContextMenu()
            contextMenu.items.addAll(addContextMenuForConnect(treeItem, TreeItemNodeLevel.CONNECT.value))
            contextMenu.items.addAll(addContextMenuForSchema(treeItem, TreeItemNodeLevel.SCHEMA.value))
            contextMenu.items.addAll(addContextMenuForTable(treeItem, TreeItemNodeLevel.TABLE.value))
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
        val openMenuItem = MenuItem(ContextMenuText.CONNECT_OPEN.text)
        openMenuItem.isVisible = false
        openMenuItem.userData = level
        openMenuItem.setOnAction { loadSchemaTree(treeItem) }

        // 关闭数据库连接
        val closeMenuItem = MenuItem(ContextMenuText.CONNECT_CLOSE.text)
        closeMenuItem.isVisible = false
        closeMenuItem.userData = level
        closeMenuItem.setOnAction {
            try {
                treeItem.children.clear()
                (treeItem.graphic as ImageView).image = buildImageIconNode(getConfig(treeItem)?.dbType,
                        true)
                treeItemExpandedToggle(treeItem)
                clearSelectCache()
            } catch (e: Exception) {
                // ignore
            }
        }

        // 编辑连接
        val editMenuItem = MenuItem(ContextMenuText.CONNECT_EDIT.text)
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
        val removeMenuItem = MenuItem(ContextMenuText.CONNECT_REMOVE.text)
        removeMenuItem.userData = level
        removeMenuItem.isVisible = false
        removeMenuItem.setOnAction {
            try {
                val config = getConfig(treeItem)!!
                LocalDataSource.delete(config)
                loadDatabaseConnectionTree()
                clearSelectCache()
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
        val openMenuItem = MenuItem(ContextMenuText.DATABASE_OPEN.text)
        openMenuItem.userData = level
        openMenuItem.isVisible = false
        openMenuItem.setOnAction { loadTableTree(treeItem) }

        // 关闭数据库
        val closeMenuItem = MenuItem(ContextMenuText.DATABASE_CLOSE.text)
        closeMenuItem.userData = level
        closeMenuItem.isVisible = false
        closeMenuItem.setOnAction {
            try {
                treeItem.children.clear()
                (treeItem.graphic as ImageView).image = buildSmallIcon(TreeItemIcon.DB_CLOSE.icon)
                treeItemExpandedToggle(treeItem)
                clearSelectCache()
            } catch (e: Exception) {
                // ignore
            }
        }

        // 重新加载数据库
        val reloadMenuItem = MenuItem(ContextMenuText.DATABASE_RELOAD.text)
        reloadMenuItem.userData = level
        reloadMenuItem.isVisible = false
        reloadMenuItem.isDisable = true
        reloadMenuItem.setOnAction {
            loadTableTree(treeItem)
            // 避免右键菜单无效
            treeItemExpandedToggle(treeItem)
            clearSelectCache()
        }
        // 选择所有表
        val addAllTableMenuItem = MenuItem(ContextMenuText.ADD_DB_FOR_GENERATE.text)
        addAllTableMenuItem.userData = level
        addAllTableMenuItem.isVisible = false
        addAllTableMenuItem.isDisable = true
        addAllTableMenuItem.setOnAction {
            treeItem.children.forEach { child ->
                // 判断是否已添加
                if (!isSelection(child)) {
                    selectTableCache.add((userData(child)[TreeItemCacheKey.TABLE.key] as Table))
                    // 更新选择状态
                    cacheUpdate(child, TreeItemCacheKey.SELECTION.key, true)
                }
            }
        }
        // 移除所有表
        val removeAllTableMenuItem = MenuItem(ContextMenuText.REMOVE_DB_FROM_GENERATE.text)
        removeAllTableMenuItem.userData = level
        removeAllTableMenuItem.isVisible = false
        removeAllTableMenuItem.isDisable = true
        removeAllTableMenuItem.setOnAction {
            treeItem.children.forEach { child ->
                if (isSelection(child)) {
                    selectTableCache.remove((userData(child)[TreeItemCacheKey.TABLE.key] as Table))
                    // 更新选择状态
                    cacheUpdate(child, TreeItemCacheKey.SELECTION.key, false)
                }
            }
        }
        return ArrayList(listOf(openMenuItem, closeMenuItem, reloadMenuItem, addAllTableMenuItem, removeAllTableMenuItem))
    }

    private fun addContextMenuForTable(treeItem: TreeItem<String>, level: Int): MutableList<MenuItem> {
        // 重新加载字段信息
        val refreshMenu = MenuItem(ContextMenuText.COLUMN_RELOAD.text)
        refreshMenu.isVisible = false
        refreshMenu.userData = level
        refreshMenu.setOnAction {
            try {
                val isExpanded = treeItem.isExpanded
                loadColumnTree(treeItem)
                treeItem.isExpanded = isExpanded
            } catch (e: Exception) {
                // ignore
            }
        }
        // 选择当前表
        val addCurTableMenuItem = MenuItem(ContextMenuText.ADD_TABLE_FOR_GENERATE.text)
        addCurTableMenuItem.userData = level
        addCurTableMenuItem.isVisible = false
        addCurTableMenuItem.isDisable = true
        addCurTableMenuItem.setOnAction {
            // 判断是否已添加
            if (!isSelection(treeItem)) {
                selectTableCache.add((userData(treeItem)[TreeItemCacheKey.TABLE.key] as Table))
                // 更新选择状态
                cacheUpdate(treeItem, TreeItemCacheKey.SELECTION.key, true)
            }
        }
        // 移除当前表
        val removeCurTableMenuItem = MenuItem(ContextMenuText.REMOVE_TABLE_FROM_GENERATE.text)
        removeCurTableMenuItem.userData = level
        removeCurTableMenuItem.isVisible = false
        removeCurTableMenuItem.isDisable = true
        removeCurTableMenuItem.setOnAction {
            if (isSelection(treeItem)) {
                selectTableCache.remove((userData(treeItem)[TreeItemCacheKey.TABLE.key] as Table))
                // 更新选择状态
                cacheUpdate(treeItem, TreeItemCacheKey.SELECTION.key, false)
            }
        }
        return ArrayList(listOf(refreshMenu, addCurTableMenuItem, removeCurTableMenuItem))
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
        return userData[TreeItemCacheKey.CONFIG.key]?.run {
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
        return userData[TreeItemCacheKey.ACTIVE.key]?.run {
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
            userData[TreeItemCacheKey.ACTIVE.key].takeIf {
                it != null
            }?.run {
                val isExpanded = !(this as Boolean)
                treeItem.isExpanded = isExpanded
                cacheUpdate(treeItem, TreeItemCacheKey.ACTIVE.key, isExpanded)
            } ?: run {
                treeItem.isExpanded = true
                cacheUpdate(treeItem, TreeItemCacheKey.ACTIVE.key, true)
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
                TreeItemIcon.DB_CLOSE.icon
            } else {
                TreeItemIcon.DB.icon
            }
        } ?: run {
            if (isClose) {
                imgIcon = when (dbType) {
                    "MySQL" -> TreeItemIcon.MYSQL_CLOSE.icon
                    "MYSQL_8" -> TreeItemIcon.MYSQL_CLOSE.icon
                    "ORACLE" -> TreeItemIcon.ORACLE_CLOSE.icon
                    "POSTGRESQL" -> TreeItemIcon.POSTGRESQL_CLOSE.icon
                    "SQLITE" -> TreeItemIcon.SQLITE_CLOSE.icon
                    "SQL_SERVER" -> TreeItemIcon.SQLSERVER_CLOSE.icon
                    else -> TreeItemIcon.DB_CLOSE.icon
                }
            } else {
                imgIcon = when (dbType) {
                    "MySQL" -> TreeItemIcon.MYSQL.icon
                    "MYSQL_8" -> TreeItemIcon.MYSQL.icon
                    "ORACLE" -> TreeItemIcon.ORACLE.icon
                    "POSTGRESQL" -> TreeItemIcon.POSTGRESQL.icon
                    "SQLITE" -> TreeItemIcon.SQLITE.icon
                    "SQL_SERVER" -> TreeItemIcon.SQLSERVER.icon
                    else -> TreeItemIcon.DB.icon
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

    ////////// button event bind ///////////

    private fun isNotEmpty(value: String?): Boolean {
        return !value.isNullOrBlank()
    }

    private fun buttonBindEventListener() {
        try {
            // 自定义定制实体类
            this.customMade.setOnAction {
                tableName.text.takeIf {
                    it.isNullOrBlank()
                }?.run {
                    error(tableName)
                } ?: run {
                    removeError(tableName)
                    val customController = loadWindow(Page.SELECT_TABLE_COLUMN, "定制实体类信息", true) as CustomMadeSelectTableController
                    customController.initData(this, selectTableCache)
                    customController.show()
                    // 关闭窗口回调
                    this.layer.setOnCloseRequest {
                        // 取消修改
                        selectTableCache.forEach { it.rollback() }
                    }
                }
            }
        } catch (e: Exception) {
            // ignore
        }
    }

    private fun isSelected(vararg nodes: CheckBox): Boolean {
        return nodes.takeIf {
            it.isNotEmpty()
        }?.run {
            var result = false
            for (node in nodes) {
                if (node.isSelected) {
                    result = true
                    break
                }
            }
            result
        } ?: run {
            false
        }
    }

    private fun validateGenerateConfig(): Boolean {
        // 必须项
        var result = validate(this.tableName, this.moduleName, this.sourceCodeTargetFolder,
                this.testCodeTargetFolder, this.resourcesTargetFolder, this.entityTargetPackage, this.entityClassName)
        // DTO包依赖项检查
        if (!validate(this.dtoEntityTargetPackage, this.dtoImplSerializable, this.dtoUseSwaggerAnnotation,
                        this.needDtoEntity, this.useDtoAsReturnValue)) {
            result = false
        } else {
            removeError(this.dtoEntityTargetPackage)
        }
        // DTO类依赖项检查
        if (!validate(this.dtoEntityClassName, this.dtoImplSerializable, this.dtoUseSwaggerAnnotation,
                        this.needDtoEntity, this.useDtoAsReturnValue)) {
            result = false
        } else {
            removeError(this.dtoEntityClassName)
        }
        // DAO依赖检查
        if (!validate(this.needDaoInterface, this.mapperTargetPackage, this.mapperClassName,
                        this.mappingFileName, this.mappingTargetFolder)) {
            result = false
        } else {
            removeErrors(this.mapperTargetPackage, this.mapperClassName,
                    this.mappingFileName, this.mappingTargetFolder)
        }
        // Service依赖检查
        if (!validate(this.needServiceInterface, this.serviceTargetPackage, this.serviceClassName,
                        this.serviceImplTargetPackage, this.serviceImplClassName)) {
            result = false
        } else {
            removeErrors(this.serviceTargetPackage, this.serviceClassName,
                    this.serviceImplTargetPackage, this.serviceImplClassName)
        }
        // Controller依赖检查
        if (!validate(this.needController, this.controllerTargetPackage, this.controllerClassName)) {
            result = false
        } else {
            removeErrors(this.controllerTargetPackage, this.controllerClassName)
        }
        return result
    }

    private fun validate(node: TextField, vararg references: CheckBox): Boolean {
        val isSelected = isSelected(*references)
        return takeIf {
            isSelected
        }?.run {
            super.validate(node)
        } ?: run {
            true
        }
    }

    private fun validate(reference: CheckBox, vararg nodes: TextField): Boolean {
        var result = reference.isSelected
        return takeIf {
            result
        }?.run {
            nodes.forEach {
                if (!super.validate(it)) {
                    result = false
                }
            }
            result
        } ?: run {
            true
        }
    }

    private fun addValueChangeListener() {
        try {
            valueChangedListener(this.tableName) { _, _, newValue ->
                newValue.takeIf {
                    it.isBlank()
                }?.run {
                    error(tableName)
                    customMade.isVisible = false
                } ?: run {
                    removeError(tableName)
                    customMade.isVisible = true
                }
            }
            // 必填项
            this.valueChangedListeners(this.moduleName, this.sourceCodeTargetFolder, this.testCodeTargetFolder,
                    this.resourcesTargetFolder, this.entityTargetPackage, this.entityClassName)
            // DAO选填
            valueChangeListener(arrayOf(this.mappingFileName, this.mapperTargetPackage, mapperClassName, mappingTargetFolder)) { _, _, _ ->
                needDaoInterface.isSelected
            }
            // DTO选填
            valueChangeListener(arrayOf(this.dtoEntityTargetPackage, this.dtoEntityClassName)) { _, _, _ ->
                isSelected(this.dtoImplSerializable, this.dtoUseSwaggerAnnotation,
                        this.needDtoEntity, this.useDtoAsReturnValue)
            }
            // Service选填
            valueChangeListener(arrayOf(this.serviceTargetPackage, this.serviceClassName, this.serviceImplTargetPackage,
                    this.serviceImplClassName)) { _, _, _ ->
                needServiceInterface.isSelected
            }
            // Controller选填
            valueChangeListener(arrayOf(this.controllerTargetPackage, this.controllerClassName)) { _, _, _ ->
                needController.isSelected
            }
        } catch (e: Exception) {
            // ignore
        }
    }

    private fun valueChangeListener(nodes: Array<TextField>,
                                    filter: ((observable: ObservableValue<out String>,
                                              oldPropertyValue: String, newPropertyValue: String) -> Boolean)?) {
        filter?.run {
            nodes.forEach {
                it.textProperty().addListener { observable, oldValue, newValue ->
                    if (filter(observable, oldValue, newValue)) {
                        super.validate(it, newValue)
                    }
                }
            }
        } ?: run {
            nodes.forEach {
                super.validate(it)
            }
        }
    }

    private fun addFocusEventListener() {
        try {
            this.tableNamePrefix.focusedProperty().addListener { _, _, isFocused ->
                if (!isFocused) {
                    val tablePrefixValue = tableNamePrefix.text
                    // 比较临时变量值是否一致
                    if (tableNamePrefixTempVariable.get() != tablePrefixValue) {
                        tableNamePrefixTempVariable.set(tablePrefixValue)
                    }
                }
            }
        } catch (e: Exception) {
            // ignore
        }
    }

    private fun addFocusRemoveErrorListener() {
        try {
            // DAO项获取焦点操作
            focusedClearErrorListeners(this.mapperTargetPackage, this.mapperClassName, mappingTargetFolder, mappingFileName) { _, _, _ ->
                !needDaoInterface.isSelected
            }
            // DTO项获取焦点
            focusedClearErrorListeners(this.dtoEntityTargetPackage, this.dtoEntityClassName) { _, _, _ ->
                !isSelected(needDtoEntity, useDtoAsReturnValue, dtoUseSwaggerAnnotation, dtoImplSerializable)
            }
            // Service项
            focusedClearErrorListeners(this.serviceTargetPackage, this.serviceClassName, this.serviceImplTargetPackage,
                    this.serviceImplClassName) { _, _, _ ->
                !needServiceInterface.isSelected
            }
            // Controller项
            focusedClearErrorListeners(this.controllerTargetPackage, this.controllerClassName) { _, _, _ ->
                !needController.isSelected
            }
        } catch (e: Exception) {
            // ignore
        }
    }

    /**
     * 生成代码
     */
    @FXML
    fun generateCode() {
        try {
            val validateResult = validateGenerateConfig()
            if (validateResult) {
                val config = extractGeneratorConfig()
                LOG.info("{}", JSON.toJSONString(config))
                LOG.info("Table info => {}", JSON.toJSONString(this.selectTableCache, true))
            }
        } catch (e: Exception) {
            LOG.error("Source code generation failed: {}", e.message, e)
        }
    }

    private fun extractGeneratorConfig(): GeneratorConfig {
        val config = GeneratorConfig()
        config.projectFolder = projectFolder.text
        config.moduleName = moduleName.text
        config.uniqueGroupId = uniqueGroupId.text
        config.tableNamePrefix = tableNamePrefix.text
        config.primaryKey = primaryKey.text
        config.needPrimaryKeyType = needPrimaryKeyType.value
        config.sourceCodeTargetFolder = sourceCodeTargetFolder.text
        config.testCodeTargetFolder = testCodeTargetFolder.text
        config.resourcesTargetFolder = resourcesTargetFolder.text
        config.rootTargetPackage = rootTargetPackage.text
        config.baseDaoInterface = baseDaoInterface.text
        config.baseServiceInterface = baseServiceInterface.text
        config.baseServiceImpl = baseServiceImpl.text
        config.entityClassName = entityClassName.text
        config.entityTargetPackage = entityTargetPackage.text
        config.dtoEntityTargetPackage = dtoEntityTargetPackage.text
        config.dtoEntityClassName = dtoEntityClassName.text
        config.mapperClassName = mapperClassName.text
        config.mapperTargetPackage = mapperTargetPackage.text
        config.mappingFileName = mappingFileName.text
        config.mappingTargetFolder = mappingTargetFolder.text
        config.serviceClassName = serviceClassName.text
        config.serviceTargetPackage = serviceTargetPackage.text
        config.serviceImplClassName = serviceImplClassName.text
        config.serviceImplTargetPackage = serviceImplTargetPackage.text
        config.controllerClassName = controllerClassName.text
        config.controllerTargetPackage = controllerTargetPackage.text
        config.testTargetPackage = testTargetPackage.text
        config.testClassName = testClassName.text
        config.jpaAnnotationChoice = jpaAnnotationChoice.value
        config.fileEncodingChoice = fileEncodingChoice.value
        config.needDtoEntity = needDtoEntity.isSelected
        config.needDaoInterface = needDaoInterface.isSelected
        config.needServiceInterface = needServiceInterface.isSelected
        config.needController = needController.isSelected
        config.entityImplSerializable = entityImplSerializable.isSelected
        config.dtoImplSerializable = dtoImplSerializable.isSelected
        config.useSchemaPrefix = useSchemaPrefix.isSelected
        config.replaceWithTablePrefix = replaceWithTablePrefix.isSelected
        config.overrideXml = overrideXml.isSelected
        config.needComment = needComment.isSelected
        config.useLombokPlugin = useLombokPlugin.isSelected
        config.needToStringHashCodeEquals = needToStringHashCodeEquals.isSelected
        config.useRepositoryAnnotation = useRepositoryAnnotation.isSelected
        config.useDtoAsReturnValue = useDtoAsReturnValue.isSelected
        config.propertyMappingRemoveIsPrefix = propertyMappingRemoveIsPrefix.isSelected
        config.entityUseJpaAnnotation = entityUseJpaAnnotation.isSelected
        config.entityUseSwaggerAnnotation = entityUseSwaggerAnnotation.isSelected
        config.dtoUseSwaggerAnnotation = dtoUseSwaggerAnnotation.isSelected
        return config
    }
    ///////////////////////////////////////
}