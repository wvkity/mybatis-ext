package com.wvkity.mybatis.code.make.observable;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.util.Optional;

/**
 * 检验器
 * @author wvkity
 */
@FunctionalInterface
public interface Validator {

    /**
     * 校验
     * @param node 节点对象
     * @return boolean
     */
    default boolean validating(final Control node) {
        return Optional.ofNullable(node).map(it -> {
            if (it instanceof TextField) {
                return validating(it, ((TextField) it).getText());
            }
            return true;
        }).orElse(true);
    }

    /**
     * 校验
     * @param node  节点对象
     * @param value 值
     * @return boolean
     */
    boolean validating(final Control node, final String value);
}
