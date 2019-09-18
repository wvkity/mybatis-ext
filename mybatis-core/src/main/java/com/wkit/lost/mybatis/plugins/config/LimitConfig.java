package com.wkit.lost.mybatis.plugins.config;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.LimitMode;
import com.wkit.lost.mybatis.plugins.utils.PluginUtil;

public class LimitConfig {

    public Limit getLimit( Object parameter ) {
        // 从线程中获取
        Limit limit = ThreadLocalLimit.getLimit();
        if ( limit != null ) {
            return limit;
        }
        long rowStart, rowEnd, offset;
        // 直接方式
        Criteria<?> criteria = PluginUtil.getParameter( parameter, "criteria" );
        if ( criteria != null ) {
            LimitMode mode = criteria.limitMode();
            if ( mode == LimitMode.IMMEDIATE ) {
                rowStart = criteria.getStart();
                rowEnd = criteria.getEnd();
                offset = rowEnd - rowStart;
                limit = new Limit( rowStart, rowEnd, offset, true );
            } else if ( mode == LimitMode.PAGEABLE ) {
                long pageStart = criteria.getPageStart();
                long pageEnd = criteria.getPageEnd();
                long pageSize = criteria.getPageSize();
                rowStart = Math.max( ( pageStart - 1 ) * pageSize, 0 );
                rowEnd = rowStart + ( pageEnd - pageStart + 1 ) * pageSize;
                offset = rowEnd - rowStart;
                limit = new Limit( rowStart, rowEnd, offset, true );
            }
        }
        if ( limit != null ) {
            ThreadLocalLimit.set( limit );
        }
        return limit;
    }
}
