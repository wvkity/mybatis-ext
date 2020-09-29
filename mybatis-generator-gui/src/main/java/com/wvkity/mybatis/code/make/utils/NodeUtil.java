package com.wvkity.mybatis.code.make.utils;

import com.wvkity.mybatis.code.make.constant.Icon;
import com.wvkity.mybatis.code.make.node.TreeData;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

/**
 * 节点工具类
 */
public final class NodeUtil {

    private NodeUtil() {
    }

    public static final double W40D = 40.d;
    public static final double H40D = 40.d;
    public static final double W16D = 16.d;
    public static final double H16D = 16.d;

    /**
     * 创建图片对象
     * @param file   文件路径
     * @param width  宽
     * @param height 高
     * @return {@link ImageView}
     */
    public static ImageView newImageView(final String file, final Double width, final Double height) {
        ImageView iv = new ImageView(file);
        Optional.ofNullable(width).ifPresent(iv::setFitWidth);
        Optional.ofNullable(height).ifPresent(iv::setFitHeight);
        return iv;
    }

    /**
     * 创建小图标对象
     * @param file 文件路径
     * @return {@link ImageView}
     */
    public static ImageView newSmallIconView(final String file) {
        return newImageView(file, W16D, H16D);
    }

    /**
     * 创建图标对象
     * @param dbType   数据库类型
     * @param isClosed 是否为关闭
     * @return {@link Image}
     */
    public static Image newSmallIcon(final String dbType, final boolean isClosed) {
        try {
            final String tmp = StringUtil.isNotEmpty(dbType) ? dbType : "OTHER";
            final Icon icon = Icon.valueOf(tmp);
            return new Image(isClosed ? icon.getClose() : icon.getOpen());
        } catch (IllegalArgumentException ignore) {
            // ignore
            final Icon icon = Icon.valueOf("OTHER");
            return new Image(isClosed ? icon.getClose() : icon.getOpen());
        }
    }

    /**
     * 创建图标对象
     * @param dbType   数据库类型
     * @param isClosed 是否为关闭
     * @return {@link ImageView}
     */
    public static ImageView newIconView(final String dbType, boolean isClosed) {
        try {
            final String tmp = StringUtil.isNotEmpty(dbType) ? dbType : "OTHER";
            final Icon icon = Icon.valueOf(tmp);
            return newImageView(isClosed ? icon.getClose() : icon.getOpen(), W16D, H16D);
        } catch (IllegalArgumentException ignore) {
            // ignore
            final Icon icon = Icon.valueOf("OTHER");
            return newImageView(isClosed ? icon.getClose() : icon.getOpen(), W16D, H16D);
        }
    }

    /**
     * 获取缓存数据
     * @param item {@link TreeItem}
     * @param <T>  泛型类型
     * @return {@link TreeData}
     */
    @SuppressWarnings("unchecked")
    public static <T> TreeData<T> getCacheData(final TreeItem<String> item) {
        return (TreeData<T>) Optional.ofNullable(item).map(it -> {
            final Node node = item.getGraphic();
            if (node != null) {
                return node.getUserData();
            }
            return null;
        }).orElse(null);
    }

    /**
     * 获取缓存数据
     * @param item {@link TreeItem}
     * @param <T>  泛型类型
     * @return 缓存类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T getData(final TreeItem<String> item) {
        return (T) Optional.ofNullable(getCacheData(item)).map(TreeData::getData).orElse(null);
    }

    /**
     * 节点是否已展开
     * @param item {@link TreeItem}
     * @return boolean
     */
    public static boolean isExpanded(final TreeItem<String> item) {
        return Optional.ofNullable(getCacheData(item)).map(TreeData::isExpanded).orElse(false);
    }
}
