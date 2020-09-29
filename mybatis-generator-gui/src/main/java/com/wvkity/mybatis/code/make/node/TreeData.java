package com.wvkity.mybatis.code.make.node;

import com.wvkity.mybatis.code.make.constant.Icon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 树节点数据
 * @author wvkity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeData<T> {

    /**
     * 是否展开
     */
    private boolean expanded;
    /**
     * 数据
     */
    private T data;
    /**
     * 父节点
     */
    private TreeData<?> parent;
    /**
     * 图标
     */
    private Icon icon;

    public TreeData(boolean expanded) {
        this.expanded = expanded;
    }

    public static <T> TreeData<T> of() {
        return new TreeData<>();
    }

    public static <T> TreeData<T> of(final boolean expanded) {
        return new TreeData<>(expanded);
    }
}
