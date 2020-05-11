package com.wkit.lost.paging;

/**
 * 分页类
 * @author wvkity
 */
public class Pager extends AbstractPager {

    private static final long serialVersionUID = -6032523290864412841L;

    /**
     * 构造方法
     */
    public Pager() {
        super();
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public Pager(String page) {
        super(page);
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public Pager(long page) {
        super(page);
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public Pager(String page, String size) {
        super(page, size);
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public Pager(long page, long size) {
        super(page, size);
    }

    /**
     * 创建分页实例
     * @return 分页对象
     */
    public static Pager of() {
        return new Pager();
    }

    /**
     * 创建分页实例
     * @param page 当前页
     * @return 分页对象
     */
    public static Pager of(final String page) {
        return new Pager(page);
    }

    /**
     * 创建分页实例
     * @param page 当前页
     * @return 分页对象
     */
    public static Pager of(final long page) {
        return new Pager(page);
    }

    /**
     * 创建分页实例
     * @param page 当前页
     * @param size 每页显示数目
     * @return 分页对象
     */
    public static Pager of(final long page, final long size) {
        return new Pager(page, size);
    }

    /**
     * 创建分页实例
     * @param page 当前页
     * @param size 每页显示数目
     * @return 分页对象
     */
    public static Pager of(final String page, final String size) {
        return new Pager(page, size);
    }
}
