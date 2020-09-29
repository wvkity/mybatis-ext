package com.wvkity.mybatis.code.make.observable;

import com.wvkity.mybatis.code.make.utils.StringUtil;
import javafx.scene.Node;
import javafx.scene.control.Control;

import java.util.Optional;

/**
 * 校验监听器
 * @author wvkity
 */
public interface ValidationListener extends Validator, StyleModifier {

    @Override
    default boolean validating(final Control node, final String value) {
        return node != null && StringUtil.isNotEmpty(value);
    }

    /**
     * 校验
     * @param node 节点对象
     * @return boolean
     */
    default boolean validated(final Control node) {
        return validated(node, this, this);
    }

    /**
     * 校验
     * @param node  节点对象
     * @param value 值
     * @return boolean
     */
    default boolean validated(final Control node, final String value) {
        return validated(node, value, this, this);
    }

    /**
     * 校验
     * @param nodes 节点对象列表
     * @return boolean
     */
    default boolean validated(final Control... nodes) {
        return validated(this, this, nodes);
    }

    /**
     * 校验
     * @param node      节点对象
     * @param validator 校验器
     * @param modifier  样式修改器
     * @return boolean
     */
    default boolean validated(final Control node, final Validator validator, final StyleModifier modifier) {
        final boolean result = validator.validating(node);
        Optional.ofNullable(modifier).ifPresent(it -> it.style(node, result));
        return result;
    }

    /**
     * 校验
     * @param node      节点对象
     * @param value     值
     * @param validator 校验器
     * @param modifier  样式修改器
     * @return boolean
     */
    default boolean validated(final Control node, final String value, final Validator validator,
                              final StyleModifier modifier) {
        final boolean result = validator.validating(node, value);
        Optional.ofNullable(modifier).ifPresent(it -> it.style(node, result));
        return result;
    }

    /**
     * 校验
     * @param nodes     节点对象列表
     * @param validator 校验器
     * @param modifier  样式修改器
     * @return boolean
     */
    default boolean validated(final Validator validator, final StyleModifier modifier, final Control... nodes) {
        if (nodes != null && nodes.length > 0) {
            boolean result = true;
            for (Control it : nodes) {
                if (it != null) {
                    final boolean ret = validated(it, validator, modifier);
                    if (!ret && result) {
                        result = ret;
                    }
                }
            }
            return result;
        }
        return true;
    }

    @Override
    default void style(final Node node, final boolean result) {
        Optional.ofNullable(node).ifPresent(it -> {
            if (result) {
                this.removeError(node);
            } else {
                this.addError(node);
            }
        });
    }
}
