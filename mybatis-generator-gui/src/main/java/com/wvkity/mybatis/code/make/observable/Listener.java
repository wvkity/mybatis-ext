package com.wvkity.mybatis.code.make.observable;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 监听器
 * @author wvkity
 */
public interface Listener extends ValidationListener {

    /**
     * 添加监听器
     * @param nodes 节点对象列表
     */
    default void addChangedListeners(final TextField... nodes) {
        if (nodes != null && nodes.length > 0) {
            Arrays.stream(nodes).filter(Objects::nonNull).forEach(this::addChangedListener);
        }
    }

    /**
     * 添加监听器
     * @param node 节点对象
     */
    default void addChangedListener(final TextField node) {
        addChangedListener(node, this, this);
    }

    /**
     * 添加监听器
     * @param node   节点对象
     * @param filter 过滤器
     */
    default void addChangedListener(final TextField node, final Filter<String, String> filter) {
        addChangedListener(node, filter, this, this);
    }

    /**
     * 添加监听器
     * @param node      节点对象
     * @param filter    过滤器
     * @param validator 校验器
     * @param modifier  样式修改器
     */
    default void addChangedListener(final TextField node, final Filter<String, String> filter,
                                    final Validator validator, final StyleModifier modifier) {
        Optional.ofNullable(node).ifPresent(it ->
                it.textProperty().addListener(((observable, oldValue, newValue) -> {
                    modifier.style(node, filter.filter(observable, oldValue, oldValue) || validator.validating(node));
                })));
    }

    /**
     * 添加监听器
     * @param node      节点对象
     * @param validator 值校验器
     * @param modifier  样式修改器
     */
    default void addChangedListener(final TextField node, final Validator validator, final StyleModifier modifier) {
        Optional.ofNullable(node).ifPresent(it ->
                it.textProperty().addListener(((__, ___, newValue) -> {
                    this.validated(node, newValue, validator, modifier);
                })));
    }

    /**
     * 添加监听器
     * @param node     节点对象
     * @param listener 监听器
     */
    default void addChangedListener(final TextField node, final ChangeListener<String> listener) {
        Optional.ofNullable(node).ifPresent(it -> {
            if (listener != null) {
                it.textProperty().addListener(listener);
            }
        });
    }

    /**
     * 添加失去焦点监听
     * @param node   节点对象
     * @param filter 过滤器
     */
    default void addFocusOutedListener(final TextField node, final Filter<String, Boolean> filter) {
        addFocusOutedListener(node, filter, this);
    }

    /**
     * 添加失去焦点监听
     * @param node     节点对象
     * @param filter   过滤器
     * @param modifier 样式修改器
     */
    default void addFocusOutedListener(final TextField node, final Filter<String, Boolean> filter,
                                       final StyleModifier modifier) {
        Optional.ofNullable(node).ifPresent(it ->
                it.focusedProperty().addListener(((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        if (filter.filter(observable, oldValue, newValue)) {
                            Optional.ofNullable(modifier).ifPresent(mf -> mf.style(it, true));
                        }
                    }
                })));
    }

    /**
     * 添加失去焦点监听
     * @param filter 过滤器
     * @param nodes  节点对象列表
     */
    default void addFocusOutedListeners(final Filter<String, Boolean> filter, final TextField... nodes) {
        addFocusOutedListeners(filter, this, nodes);
    }

    /**
     * 添加失去焦点监听
     * @param filter   过滤器
     * @param modifier 样式修改器
     * @param nodes    节点对象列表
     */
    default void addFocusOutedListeners(final Filter<String, Boolean> filter, final StyleModifier modifier,
                                        final TextField... nodes) {
        if (nodes != null && nodes.length > 0) {
            for (TextField node : nodes) {
                addFocusOutedListener(node, filter, modifier);
            }
        }
    }

    /**
     * 添加焦点监听
     * @param node     节点对象
     * @param listener 监听器
     */
    default void addFocusedListener(final TextField node, final ChangeListener<Boolean> listener) {
        Optional.ofNullable(node).ifPresent(it -> it.focusedProperty().addListener(listener));
    }
}
