package com.wvkity.mybatis.code.make.observable;

import com.wvkity.mybatis.code.make.constant.DatabaseType;
import com.wvkity.mybatis.code.make.utils.StringUtil;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

/**
 * SQLite数据库过滤器
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SqLiteFilter<T> implements Filter<String, T> {

    /**
     * 节点
     */
    private ComboBox<String> node;

    @Override
    public boolean filter(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        return filter();
    }

    @Override
    public boolean filter() {
        final String value;
        if (this.node != null && StringUtil.isNotEmpty((value = node.getValue()))) {
            try {
                return DatabaseType.valueOf(value.toUpperCase(Locale.ENGLISH)) == DatabaseType.SQLITE;
            } catch (Exception ignore) {
                // ignore
            }
        }
        return false;
    }
}
