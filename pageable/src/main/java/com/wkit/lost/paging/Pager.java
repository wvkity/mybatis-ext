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
    public Pager( String page ) {
        super( page );
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public Pager( long page ) {
        super( page );
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public Pager( String page, String size ) {
        super( page, size );
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页数目
     */
    public Pager( long page, long size ) {
        super( page, size );
    }
}
