package com.wkit.lost.mybatis.core.criteria;

import java.io.Serializable;

/**
 * 实例复制接口
 * @param <Context> 当前对象
 * @author wvkity
 */
@FunctionalInterface
public interface InstanceClone<Context> extends Serializable {
    
    /**
     * 深拷贝实例
     * @return 实例
     */
    Context deepClone();
}
