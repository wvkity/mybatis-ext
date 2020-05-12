package com.wkit.lost.mybatis.generator.ui

import com.wkit.lost.mybatis.generator.bean.Column
import com.wkit.lost.mybatis.generator.bean.Table
import javafx.beans.property.ListProperty
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints
import javafx.scene.layout.VBox
import org.apache.logging.log4j.LogManager
import java.net.URL
import java.util.*

class CustomMadeSelectTableController : AbstractController() {

    companion object {
        private val LOG = LogManager.getLogger(CustomMadeSelectTableController)
        private const val TABLE_NAME_LABEL_TEXT = "表名: "
        private const val TABLE_MAPPING_ENTITY_LABEL_TEXT = "实体类名: "
        private const val TABLE_COMMENT_LABEL_TEXT = "类文档注释: "
        private const val TABLE_COLUMN_TEXT_CHECKED = "是否选择"
        private const val TABLE_COLUMN_TEXT_NAME = "列名"
        private const val TABLE_COLUMN_TEXT_JDBC_TYPE = "JDBC类型"
        private const val TABLE_COLUMN_TEXT_PROPERTY_NAME = "属性名"
        private const val TABLE_COLUMN_TEXT_JAVA_TYPE = "Java类型"
        private const val TABLE_COLUMN_TEXT_IMPORT_JAVA_TYPE = "导入Java类型"
        private const val TABLE_COLUMN_TEXT_COMMENT = "注释"
        private const val TABLE_COLUMN_TEXT_TYPE_HANDLE = "类型处理器"
        private const val TABLE_COLUMN_PROP_CHECKED = "checked"
        private const val TABLE_COLUMN_PROP_NAME = "columnName"
        private const val TABLE_COLUMN_PROP_JDBC_TYPE = "jdbcType"
        private const val TABLE_COLUMN_PROP_PROPERTY_NAME = "propertyName"
        private const val TABLE_COLUMN_PROP_JAVA_TYPE = "javaType"
        private const val TABLE_COLUMN_PROP_IMPORT_JAVA_TYPE = "importJavaType"
        private const val TABLE_COLUMN_PROP_COMMENT = "comment"
        private const val TABLE_COLUMN_PROP_TYPE_HANDLE = "typeHandle"
    }

    /**
     * 选项卡面板
     */
    @FXML
    lateinit var tableTabPanel: TabPane

    /**
     * table
     */
    lateinit var tableData: ListProperty<Table>

    /**
     * 主程序控制器
     */
    lateinit var application: ApplicationController

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        try {

        } catch (e: Exception) {
            LOG.error(e)
        }
    }

    fun initData(application: ApplicationController, tables: ListProperty<Table>) {
        this.application = application
        this.tableData = tables
        initTabPanel()
    }

    private fun initTabPanel() {
        try {
            this.tableData.takeIf {
                it.isNotEmpty()
            }?.run {
                val tabNodes = tableTabPanel.tabs
                this.forEach {
                    tabNodes.add(buildTabNode(it))
                }
            }
        } catch (e: Exception) {
            LOG.error("Table TAB initialization failed: {}", e.message, e)
        }
    }

    private fun buildTabNode(table: Table): Tab {
        val tabNode = Tab()
        tabNode.text = table.getName()
        // HBox
        val vBox = VBox()
        vBox.children.add(buildGridPanel(table))
        vBox.children.add(buildTableView(table))
        tabNode.content = vBox
        tabNode.userData = table
        return tabNode
    }

    private fun buildGridPanel(table: Table): GridPane {
        val gridPanel = GridPane()
        gridPanel.prefWidth = 960.0
        gridPanel.prefHeight = 40.0
        // 行
        val row = RowConstraints()
        row.prefHeight = 34.0
        gridPanel.rowConstraints.add(row)
        // 列
        val textColumn = ColumnConstraints()
        textColumn.halignment = HPos.RIGHT
        textColumn.prefWidth = 60.0
        val secondColumn = ColumnConstraints()
        secondColumn.halignment = HPos.LEFT
        secondColumn.prefWidth = 140.0
        val fourColumn = ColumnConstraints()
        fourColumn.halignment = HPos.LEFT
        fourColumn.prefWidth = 130.0
        val firthColumn = ColumnConstraints()
        firthColumn.halignment = HPos.RIGHT
        firthColumn.prefWidth = 100.0
        val sixthColumn = ColumnConstraints()
        sixthColumn.halignment = HPos.LEFT
        sixthColumn.prefWidth = 220.0
        gridPanel.columnConstraints.addAll(textColumn, secondColumn, textColumn, fourColumn, firthColumn, sixthColumn)
        // 内容
        val insets = Insets(20.0, 0.0, 5.0, 0.0)
        val tableLabel = Label(TABLE_NAME_LABEL_TEXT)
        gridPanel.add(tableLabel, 0, 0)
        GridPane.setMargin(tableLabel, insets)
        val tableNameLabel = Label(table.getName())
        gridPanel.add(tableNameLabel, 1, 0)
        GridPane.setMargin(tableNameLabel, insets)
        val entityNameLabel = Label(TABLE_MAPPING_ENTITY_LABEL_TEXT)
        gridPanel.add(entityNameLabel, 2, 0)
        GridPane.setMargin(entityNameLabel, insets)
        val entityNameInput = TextField()
        entityNameInput.prefWidth = 128.0
        entityNameInput.prefHeight = 28.0
        entityNameInput.text = table.getClassName()
        // 添加监听事件
        entityNameInput.textProperty().addListener { _, _, newValue ->
            table.setClassName(newValue)
        }
        entityNameInput.focusedProperty().addListener { _, _, isFocused ->
            // 失去焦点时
            if (!isFocused && !isNotEmpty(table.getClassName())) {
                entityNameInput.text = table.getClassNameValue()
            }
        }
        gridPanel.add(entityNameInput, 3, 0)
        GridPane.setMargin(entityNameInput, insets)
        val commentLabel = Label(TABLE_COMMENT_LABEL_TEXT)
        gridPanel.add(commentLabel, 4, 0)
        GridPane.setMargin(commentLabel, insets)
        val commentInput = TextField()
        commentInput.prefWidth = 210.0
        commentInput.prefHeight = 28.0
        commentInput.text = table.getComment()
        commentInput.textProperty().addListener { _, _, newComment ->
            table.setComment(newComment)
        }
        gridPanel.add(commentInput, 5, 0)
        GridPane.setMargin(commentInput, insets)
        gridPanel.vgap = 10.0
        return gridPanel
    }

    private fun buildTableView(table: Table): TableView<Column> {
        val tableView = TableView<Column>()
        tableView.id = "columnListView"
        tableView.prefWidth = 960.0
        tableView.prefHeight = 385.0
        tableView.isEditable = true
        tableView.columnResizePolicy = TableView.UNCONSTRAINED_RESIZE_POLICY
        // 是否选择
        val checkedColumn = TableColumn<Column, Boolean>(TABLE_COLUMN_TEXT_CHECKED)
        checkedColumn.prefWidth = 70.0
        checkedColumn.isSortable = false
        checkedColumn.cellValueFactory = PropertyValueFactory<Column, Boolean>(TABLE_COLUMN_PROP_CHECKED)
        checkedColumn.cellFactory = CheckBoxTableCell.forTableColumn(checkedColumn)
        // 列名
        val columnNameColumn = TableColumn<Column, String>(TABLE_COLUMN_TEXT_NAME)
        columnNameColumn.prefWidth = 160.0
        columnNameColumn.isSortable = false
        columnNameColumn.cellValueFactory = PropertyValueFactory<Column, String>(TABLE_COLUMN_PROP_NAME)
        // Jdbc类型
        val jdbcTypeColumn = TableColumn<Column, String>(TABLE_COLUMN_TEXT_JDBC_TYPE)
        jdbcTypeColumn.prefWidth = 135.0
        jdbcTypeColumn.isSortable = false
        jdbcTypeColumn.cellValueFactory = PropertyValueFactory<Column, String>(TABLE_COLUMN_PROP_JDBC_TYPE)
        // 属性名
        val propertyNameColumn = TableColumn<Column, String>(TABLE_COLUMN_TEXT_PROPERTY_NAME)
        propertyNameColumn.prefWidth = 125.0
        propertyNameColumn.isSortable = false
        propertyNameColumn.cellValueFactory = PropertyValueFactory<Column, String>(TABLE_COLUMN_PROP_PROPERTY_NAME)
        propertyNameColumn.cellFactory = TextFieldTableCell.forTableColumn()
        propertyNameColumn.setOnEditCommit {
            it.tableView.items[it.tablePosition.row].propertyNameProperty().set(it.newValue.trim())
        }
        // Java类型
        val javaTypeColumn = TableColumn<Column, String>(TABLE_COLUMN_TEXT_JAVA_TYPE)
        javaTypeColumn.prefWidth = 110.0
        javaTypeColumn.isSortable = false
        javaTypeColumn.cellValueFactory = PropertyValueFactory<Column, String>(TABLE_COLUMN_PROP_JAVA_TYPE)
        javaTypeColumn.cellFactory = TextFieldTableCell.forTableColumn()
        javaTypeColumn.setOnEditCommit {
            it.tableView.items[it.tablePosition.row].javaTypeProperty().set(it.newValue.trim())
        }
        // 导入Java类型
        val importJavaTypeColumn = TableColumn<Column, String>(TABLE_COLUMN_TEXT_IMPORT_JAVA_TYPE)
        importJavaTypeColumn.prefWidth = 160.0
        importJavaTypeColumn.isSortable = false
        importJavaTypeColumn.cellValueFactory = PropertyValueFactory<Column, String>(TABLE_COLUMN_PROP_IMPORT_JAVA_TYPE)
        importJavaTypeColumn.cellFactory = TextFieldTableCell.forTableColumn()
        importJavaTypeColumn.setOnEditCommit {
            it.tableView.items[it.tablePosition.row].importJavaTypeProperty().set(it.newValue.trim())
        }
        // 注释
        val commentColumn = TableColumn<Column, String>(TABLE_COLUMN_TEXT_COMMENT)
        commentColumn.prefWidth = 180.0
        commentColumn.isSortable = false
        commentColumn.cellValueFactory = PropertyValueFactory<Column, String>(TABLE_COLUMN_PROP_COMMENT)
        commentColumn.cellFactory = TextFieldTableCell.forTableColumn()
        commentColumn.setOnEditCommit {
            it.tableView.items[it.tablePosition.row].commentProperty().set(it.newValue)
        }
        // 类型处理
        val typeHandleColumn = TableColumn<Column, String>(TABLE_COLUMN_TEXT_TYPE_HANDLE)
        typeHandleColumn.prefWidth = 200.0
        typeHandleColumn.isSortable = false
        typeHandleColumn.cellValueFactory = PropertyValueFactory<Column, String>(TABLE_COLUMN_PROP_TYPE_HANDLE)
        typeHandleColumn.cellFactory = TextFieldTableCell.forTableColumn()
        typeHandleColumn.setOnEditCommit {
            it.tableView.items[it.tablePosition.row].typeHandleProperty().set(it.newValue.trim())
        }
        // 添加到表格中
        tableView.columns.addAll(checkedColumn, columnNameColumn, jdbcTypeColumn, propertyNameColumn, javaTypeColumn,
                importJavaTypeColumn, commentColumn, typeHandleColumn)
        val columns = FXCollections.observableArrayList(table.columns)
        tableView.items = columns
        return tableView
    }

    /**
     * 取消修改
     */
    @FXML
    fun cancelChanged() {
        tableTabPanel.tabs.forEach {
            val userData = it.userData
            userData.takeIf { tableBean ->
                tableBean != null
            }?.run {
                (userData as Table).rollback()
            }
        }
        close()
    }

    /**
     * 确认修改
     */
    @FXML
    fun confirmChanged() {
        tableTabPanel.tabs.forEach {
            val userData = it.userData
            userData.takeIf { tableBean ->
                tableBean != null
            }?.run {
                (userData as Table).confirmChange()
            }
        }
        close()
    }

    private fun isNotEmpty(value: String?): Boolean {
        return !value.isNullOrBlank()
    }
}