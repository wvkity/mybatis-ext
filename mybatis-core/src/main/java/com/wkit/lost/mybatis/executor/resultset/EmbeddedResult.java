package com.wkit.lost.mybatis.executor.resultset;

/**
 * 返回类型
 * @author wvkity
 */
public interface EmbeddedResult {

    /**
     * 获取自定义resultMap节点key
     * @return 自定义的resultMap key
     */
    default String resultMap() {
        return null;
    }

    /**
     * 获取自定义返回值类型
     * @return 返回值类型
     */
    default Class<?> resultType() {
        return null;
    }

}
