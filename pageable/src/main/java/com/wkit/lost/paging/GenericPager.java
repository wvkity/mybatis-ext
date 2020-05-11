package com.wkit.lost.paging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型分页
 * @author wvkity
 */
public class GenericPager<E> extends AbstractPager {

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
    public GenericPager() {
        super();
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public GenericPager(String page) {
        super(page);
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public GenericPager(long page) {
        super(page);
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public GenericPager(String page, String size) {
        super(page, size);
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public GenericPager(long page, long size) {
        super(page, size);
    }

    /**
     * 添加元素
     * @param elements 元素数组
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    public GenericPager<E> add(E... elements) {
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
    public GenericPager<E> clear() {
        if (this.data != null && !this.data.isEmpty()) {
            this.data.clear();
            this.empty = true;
        }
        return this;
    }

    /**
     * 获取数据
     * @return 数据列表
     */
    public List<E> getData() {
        return this.data;
    }

    /**
     * 设置数据
     * @param data 数据列表
     */
    public void setData(List<E> data) {
        this.data = data;
    }

    /**
     * 数据是否为空
     * @return boolean
     */
    public boolean isEmpty() {
        this.empty = this.data == null || this.data.isEmpty();
        return this.empty;
    }

    /**
     * 是否自动填充数据
     * @return boolean
     */
    public boolean autoFilling() {
        return autoFilling;
    }

    /**
     * 设置是否自动填充数据
     * @param autoFilling 是否自动填充数据
     * @return {@code this}
     */
    public GenericPager<E> autoFilling(boolean autoFilling) {
        this.autoFilling = autoFilling;
        return this;
    }


    /**
     * 创建分页实例
     * @param page 当前页
     * @param <E>  泛型类型
     * @return 分页对象
     */
    public static <E> GenericPager<E> of(final String page) {
        return new GenericPager<>(page);
    }

    /**
     * 创建分页实例
     * @param page 当前页
     * @param <E>  泛型类型
     * @return 分页对象
     */
    public static <E> GenericPager<E> of(final long page) {
        return new GenericPager<>(page);
    }

    /**
     * 创建分页实例
     * @param page 当前页
     * @param size 每页显示数目
     * @param <E>  泛型类型
     * @return 分页对象
     */
    public static <E> GenericPager<E> of(final long page, final long size) {
        return new GenericPager<>(page, size);
    }

    /**
     * 创建分页实例
     * @param page 当前页
     * @param size 每页显示数目
     * @param <E>  泛型类型
     * @return 分页对象
     */
    public static <E> GenericPager<E> of(final String page, final String size) {
        return new GenericPager<>(page, size);
    }
}
