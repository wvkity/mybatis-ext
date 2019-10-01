package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 抽象SQL片段类
 * @author DT
 */
@SuppressWarnings( "serial" )
public abstract class AbstractSegment implements Segment {

    /**
     * SQL片段集合
     */
    @Getter
    protected List<Segment> segments = new CopyOnWriteArrayList<>();

    /**
     * 添加SQL片段
     * @param collection SQL片段集合
     */
    public void addAll( Collection<? extends Segment> collection ) {
        if ( CollectionUtil.hasElement( collection ) ) {
            this.segments.addAll( collection );
        }
    }

    /**
     * 检测SQL片段是否存在元素
     * @return true: 是 | false: 否
     */
    public boolean isNotEmpty() {
        return CollectionUtil.hasElement( this.segments );
    }

}
