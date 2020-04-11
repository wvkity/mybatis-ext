package com.wkit.lost.paging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型分页
 * @author wvkity
 */
public class PagerWrap<E> extends Pager {

    private static final long serialVersionUID = 3752141957648406085L;

    /**
     * 元素
     */
    private List<E> data;

    /**
     * 是否为空
     */
    private boolean empty = true;

    /**
     * 分页查询时是否自动填充元素
     */
    private boolean autoFilling = true;

    /**
     * 构造方法
     */
    public PagerWrap() {
        super();
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public PagerWrap(String page) {
        super(page);
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public PagerWrap(long page) {
        super(page);
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public PagerWrap(String page, String size) {
        super(page, size);
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public PagerWrap(long page, long size) {
        super(page, size);
    }

    /**
     * 添加元素
     * @param elements 元素数组
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    public PagerWrap<E> add(E... elements) {
        if (elements == null || elements.length == 0) {
            this.data = null;
            this.empty = true;
        } else {
            if (this.data == null) {
                this.data = new ArrayList<>(elements.length);
            }
            this.data.addAll(Arrays.asList(elements));
            this.empty = false;
        }
        return this;
    }

    /**
     * 清空元素
     * @return {@code this}
     */
    public PagerWrap<E> clear() {
        if (this.data != null && !this.data.isEmpty()) {
            this.data.clear();
            this.empty = true;
        }
        return this;
    }

    public List<E> getData() {
        return this.data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        this.empty = this.data == null || this.data.isEmpty();
        return this.empty;
    }

    public boolean autoFilling() {
        return autoFilling;
    }

    public void autoFilling(boolean autoFilling) {
        this.autoFilling = autoFilling;
    }
}
