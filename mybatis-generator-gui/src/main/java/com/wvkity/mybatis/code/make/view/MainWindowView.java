package com.wvkity.mybatis.code.make.view;

import com.wvkity.mybatis.code.make.config.ConnectConfiguration;
import com.wvkity.mybatis.code.make.config.JavaTypeRegistry;
import com.wvkity.mybatis.code.make.constant.TreeTier;
import com.wvkity.mybatis.code.make.datasource.LocalDataSource;
import com.wvkity.mybatis.code.make.node.TreeData;
import com.wvkity.mybatis.code.make.utils.NodeUtil;
import com.wvkity.mybatis.code.make.utils.StringUtil;
import com.wvkity.mybatis.code.make.builder.Builder;
import com.wvkity.mybatis.code.make.constant.Icon;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 主窗口
 */
@Log4j2
@Getter
@Setter
@Accessors(chain = true)
public class MainWindowView extends AbstractView {

    /**
     * 鼠标双击事件表示
     */
    private static final int MOUSE_EVENT_DOUBLE_CLICK = 2;
    /**
     * JDBC数据类型配置前缀
     */
    private static final String JDBC_PREFIX = "jdbc.";
    /**
     * 系统配置
     */
    private static final Map<String, String> SYS_CONFIG_CACHE = new ConcurrentHashMap<>();

    /**
     * 数据库连接标签
     */
    @FXML
    private Label connectionLabel;
    /**
     * 表实体映射配置标签
     */
    @FXML
    private Label mappingConfigLabel;
    /**
     * 数据库连接列表
     */
    @FXML
    private TreeView<String> databaseTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // 处理顶部标签
            this.initTopLabelIconAndBindEvent();
            this.initDatabaseConnectionTreeAndBindEvent();
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 初始化配置
     * @param config 配置信息
     */
    public void initConfig(final Map<String, String> config) {
        if (config != null && !config.isEmpty()) {
            SYS_CONFIG_CACHE.putAll(config);
            SYS_CONFIG_CACHE.keySet().stream().filter(it -> it.startsWith(JDBC_PREFIX)).forEach(it -> {
                final String key = it.replaceAll(JDBC_PREFIX, "");
                JavaTypeRegistry.register(key, SYS_CONFIG_CACHE.get(it));
            });
        }
    }

    /**
     * 初始化顶部标签并绑定事件
     */
    private void initTopLabelIconAndBindEvent() {
        initDatabaseConnectIconAndBindEvent();
    }

    /**
     * 初始化数据库连接图标并绑定事件
     */
    private void initDatabaseConnectIconAndBindEvent() {
        try {
            this.connectionLabel.setGraphic(NodeUtil.newImageView("icons/connect.png", NodeUtil.W40D, NodeUtil.H40D));
            this.connectionLabel.setOnMouseClicked(it ->
                    Optional.ofNullable(open(View.CONNECTION, "新建数据库连接", false)).ifPresent(at ->
                            ((ConnectionView) at).apply(this).open()));
        } catch (Exception ignore) {
            // ignore
        }
    }

    /**
     * 初始化数据库连接列表并绑定事件
     */
    private void initDatabaseConnectionTreeAndBindEvent() {
        try {
            this.databaseTreeView.setShowRoot(false);
            this.databaseTreeView.setRoot(new TreeItem<>());
            this.loadDatabaseConnectionTree();
            // 事件处理
            final Callback<TreeView<String>, TreeCell<String>> factory = TextFieldTreeCell.forTreeView();
            this.databaseTreeView.setCellFactory(it -> {
                final TreeCell<String> cell = factory.call(it);
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                    final Node node = event.getPickResult().getIntersectedNode();
                    if (node instanceof Text || (node instanceof TreeCell
                            && StringUtil.isNotEmpty(((TreeCell<?>) node).getText()))) {
                        Optional.ofNullable(this.databaseTreeView.getSelectionModel().getSelectedItem())
                                .ifPresent(item -> {
                                    final int level = this.databaseTreeView.getTreeItemLevel(item);
                                    // 创建右键菜单
                                    // 双击事件
                                    if (event.getButton() == MouseButton.PRIMARY
                                            && event.getClickCount() == MOUSE_EVENT_DOUBLE_CLICK
                                            && !NodeUtil.isExpanded(item)) {
                                        final TreeTier treeTier = TreeTier.parse(level);
                                        switch (treeTier) {
                                            case CONNECT:
                                                loadSchemaTree(item);
                                                break;
                                            case SCHEMA:
                                                break;
                                            case TABLE:
                                                break;
                                            default:
                                                // ignore
                                                break;
                                        }
                                    }
                                });
                    }
                });
                return cell;
            });
        } catch (Exception ignore) {
            // ignore
        }
    }

    /**
     * 加载数据库连接列表
     */
    private void loadDatabaseConnectionTree() {
        try {
            final TreeItem<String> parent = this.databaseTreeView.getRoot();
            parent.getChildren().clear();
            final List<ConnectConfiguration> configs = LocalDataSource.loadConnections();
            if (!configs.isEmpty()) {
                for (ConnectConfiguration it : configs) {
                    final TreeItem<String> item = new TreeItem<>(it.getName());
                    final Icon icon = Icon.convert(it.getDbType());
                    final ImageView iv = NodeUtil.newSmallIconView(icon.getClose());
                    iv.setUserData(Builder.of(TreeData<ConnectConfiguration>::new)
                            .with(TreeData::setExpanded, false).with(TreeData::setData, it)
                            .with(TreeData::setIcon, icon).release());
                    item.setGraphic(iv);
                    parent.getChildren().add(item);
                }
            }
        } catch (Exception e) {
            log.error("The database connection list failed to load: ", e);
        }
    }

    /**
     * 加载数据库树
     * @param parent 父节点
     */
    private void loadSchemaTree(final TreeItem<String> parent) {
        Optional.ofNullable(NodeUtil.getCacheData(parent)).ifPresent(it -> {
            final Object value = it.getData();
            if (value != null) {
                final ConnectConfiguration configuration = (ConnectConfiguration) value;
                try {
                    final List<String> schemas = LocalDataSource.loadSchemas(configuration);
                    buildChild(parent, it, schemas, TreeTier.SCHEMA, Icon.DATABASE);
                    expandedToggle(parent);
                } catch (SQLException e) {
                    log.error("The schema list failed to load: ", e);
                }
            }
        });
    }

    /**
     * 构建子节点
     * @param parent 父节点
     * @param data   父节点数据对象
     * @param items  节点数据
     * @param tier   级别
     * @param icon   图标
     */
    private void buildChild(final TreeItem<String> parent, final TreeData<?> data, final List<?> items,
                            final TreeTier tier, final Icon icon) {
        Optional.ofNullable(parent).ifPresent(it -> {
            it.getChildren().clear();
            if (items != null && !items.isEmpty()) {
                String schema = "";
                boolean isSchemaTier = tier == TreeTier.SCHEMA;
                if (isSchemaTier) {
                    schema = parent.getValue();
                }
                for (Object item : items) {
                    final TreeItem<String> node;
                    final TreeData<?> cache;
                    if (isSchemaTier) {
                        node = new TreeItem<>(item.toString());
                        cache = Builder.of(TreeData<String>::new).with(TreeData::setExpanded, false)
                                .with(TreeData::setParent, data).with(TreeData::setData, item.toString()).release();
                    } else {
                        node = new TreeItem<>(String.valueOf(item));
                        cache = null;
                    }
                    cache.setIcon(icon);
                    cache.setExpanded(false);
                    final ImageView iv = NodeUtil.newSmallIconView(icon.getClose());
                    iv.setUserData(cache);
                    node.setGraphic(iv);
                    it.getChildren().add(node);
                }
            }
        });
    }

    /**
     * 展开父节点
     * @param parent 父节点对象
     */
    private void expandedToggle(final TreeItem<String> parent) {
        Optional.ofNullable(NodeUtil.getCacheData(parent)).ifPresent(it -> {
            final boolean isExpanded = !it.isExpanded();
            it.setExpanded(isExpanded);
            parent.setExpanded(isExpanded);
            final Icon icon = it.getIcon();
            // 更改图标
            final ImageView iv = (ImageView) parent.getGraphic();
            iv.setImage(new Image(isExpanded ? icon.getOpen() : icon.getClose()));
        });
    }
}
