package com.wkit.lost.paging;

import java.io.Serializable;

/**
 * 分页接口
 * @author DT
 */
public interface Pageable extends Serializable {

    /**
     * 获取当前页
     * @return 当前页码
     */
    long getPage();

    /**
     * 设置当前页
     * @param page 当前页码
     */
    void setPage( long page );

    /**
     * 获取每页数目
     * @return 每页数目
     */
    long getSize();

    /**
     * 设置每页数目
     * @param size 每页数目
     */
    void setSize( long size );

    /**
     * 获取记录数
     * @return 记录数
     */
    long getRecord();

    /**
     * 设置记录数
     * @param record 记录数
     */
    void setRecord( long record );

    /**
     * 获取总页数
     * @return 总页数
     */
    long getTotal();

    /**
     * 获取显示页码数
     * @return 数量
     */
    long getVisible();

    /**
     * 设置显示页码数
     * @param visible 页码数
     */
    void setVisible( long visible );

    /**
     * 分页段
     * @return 数量
     */
    long offset();

    /**
     * 获取页码起始位置
     * @return 页码
     */
    long getStart();

    /**
     * 获取页码结束位置
     * @return 页码
     */
    long getEnd();

    /**
     * 检查是否存在下一页
     * @return true: 是, false: 否
     */
    boolean hasNext();
}
