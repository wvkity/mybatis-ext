package com.wkit.lost.paging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型分页
 * @author wvkity
 */
public class WrapPager<E> extends Pager {

    private static final long serialVersionUID = 3752141957648406085L;

    /**
     * 元素
     */
    private List<E> elements;

    /**
     * 是否为空
     */
    private boolean empty = true;

    /**
     * 分页查询时是否自动填充元素
     */
    private boolean autoFill = false;

    /**
     * 构造方法
     */
    public WrapPager() {
        super();
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public WrapPager(String page) {
        super(page);
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public WrapPager(long page) {
        super(page);
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public WrapPager(String page, String size) {
        super(page, size);
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public WrapPager(long page, long size) {
        super(page, size);
    }

    /**
     * 添加元素
     * @param elements 元素数组
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    public WrapPager<E> add(E... elements) {
        if (elements == null || elements.length == 0) {
            this.elements = null;
            this.empty = true;
        } else {
            if (this.elements == null) {
                this.elements = new ArrayList<>(elements.length);
            }
            this.elements.addAll(Arrays.asList(elements));
            this.empty = false;
        }
        return this;
    }

    /**
     * 清空元素
     * @return {@code this}
     */
    public WrapPager<E> clear() {
        if (this.elements != null && !this.elements.isEmpty()) {
            this.elements.clear();
            this.empty = true;
        }
        return this;
    }

    public List<E> getElements() {
        return this.elements;
    }

    public void setElements(List<E> elements) {
        this.elements = elements;
    }

    public boolean isEmpty() {
        this.empty = this.elements == null || this.elements.isEmpty();
        return this.empty;
    }

    public boolean autoFill() {
        return autoFill;
    }

    public void autoFill(boolean autoFill) {
        this.autoFill = autoFill;
    }
}
