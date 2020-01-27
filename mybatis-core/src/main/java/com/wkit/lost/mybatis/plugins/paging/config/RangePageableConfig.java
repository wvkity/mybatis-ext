package com.wkit.lost.mybatis.plugins.paging.config;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.RangeMode;
import com.wkit.lost.mybatis.plugins.utils.PluginUtil;
import com.wkit.lost.mybatis.utils.Constants;

public class RangePageableConfig {

    public RangePageable getRangePageable( Object parameter ) {
        // 从线程中获取
        RangePageable range = ThreadLocalRangePageable.getRange();
        if ( range != null ) {
            return range;
        }
        long rowStart, rowEnd, offset;
        // 直接方式
        Criteria<?> criteria = PluginUtil.getParameter( parameter, Constants.PARAM_CRITERIA );
        if ( criteria != null ) {
            RangeMode mode = criteria.range();
            if ( mode == RangeMode.IMMEDIATE ) {
                rowStart = criteria.getStart();
                rowEnd = criteria.getEnd();
                offset = rowEnd - rowStart;
                range = new RangePageable( rowStart, rowEnd, offset, true );
            } else if ( mode == RangeMode.PAGEABLE ) {
                long pageStart = criteria.getPageStart();
                long pageEnd = criteria.getPageEnd();
                long pageSize = criteria.getPageSize();
                rowStart = Math.max( ( pageStart - 1 ) * pageSize, 0 );
                rowEnd = rowStart + ( pageEnd - pageStart + 1 ) * pageSize;
                offset = rowEnd - rowStart;
                range = new RangePageable( rowStart, rowEnd, offset, true );
            }
        }
        if ( range != null ) {
            ThreadLocalRangePageable.set( range );
        }
        return range;
    }
}
