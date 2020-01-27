package com.wkit.lost.mybatis.plugins.paging.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RangePageable {

    /**
     * 开始位置
     */
    private long start;

    /**
     * 结束位置
     */
    private long end;

    /**
     * 偏移量
     */
    private long offset;

    /**
     * 是否应用
     */
    private boolean apply;
}
