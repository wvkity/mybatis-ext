package com.wkit.lost.mybatis.plugins.paging.config;

import com.wkit.lost.mybatis.core.constant.Range;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
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
            Range mode = criteria.range();
            if ( mode == Range.IMMEDIATE ) {
                rowStart = Math.max( criteria.getRowStart() - 1, 0 );
                rowEnd = Math.max( criteria.getRowEnd() - rowStart + 1, 0 );
                offset = rowEnd - rowStart;
                range = new RangePageable( rowStart, rowEnd, offset, true );
            } else if ( mode == Range.PAGEABLE ) {
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
