package com.wvkity.mybatis.code.make.observable;

import com.wvkity.mybatis.code.make.utils.StringUtil;
import javafx.scene.Node;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 样式修改器
 * @author wvkity
 */
@FunctionalInterface
public interface StyleModifier {

    /**
     * 默认error样式选择器
     */
    String ERROR_SELECTOR = "error";

    /**
     * 添加默认error样式
     * @param node 节点对象
     */
    default void addError(final Node node) {
        addClass(node, ERROR_SELECTOR);
    }

    /**
     * 移除默认error样式
     * @param node 节点对象
     */
    default void removeError(final Node node) {
        removeClass(node, ERROR_SELECTOR);
    }

    /**
     * 移除默认error样式
     * @param nodes 节点对象列表
     */
    default void removeErrors(final Node... nodes) {
        if (nodes != null && nodes.length > 0) {
            Arrays.stream(nodes).filter(Objects::nonNull).forEach(this::removeError);
        }
    }

    /**
     * 检查节点是否存在指定样式class选择器
     * @param node          节点对象
     * @param classSelector 指定样式class选择器
     * @return boolean
     */
    default boolean hasClass(final Node node, final String classSelector) {
        return StringUtil.isNotEmpty(classSelector) && Optional.ofNullable(node).map(it ->
                node.getStyleClass().contains(classSelector)).orElse(false);
    }

    /**
     * 指定节点添加样式class选择器
     * @param node          节点对象
     * @param classSelector 指定样式class选择器
     */
    default void addClass(final Node node, final String classSelector) {
        Optional.ofNullable(node).ifPresent(it -> {
            if (!hasClass(node, classSelector)) {
                node.getStyleClass().add(classSelector);
            }
        });
    }

    /**
     * 指定节点移除样式class选择器
     * @param node          节点对象
     * @param classSelector 指定样式class选择器
     */
    default void removeClass(final Node node, final String classSelector) {
        Optional.ofNullable(node).ifPresent(it -> it.getStyleClass().remove(classSelector));
    }

    /**
     * 修改样式
     * @param node   节点
     * @param result 节点值校验结果
     */
    void style(final Node node, final boolean result);
}
