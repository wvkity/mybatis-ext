package com.wkit.lost.mybatis.filling;

/**
 * 自动填充依赖接口
 * @author wvkity
 */
public interface MetaObjectFillAuxiliary {

    /**
     * 操作人
     * @return 操作人
     */
    String operator();

    /**
     * 操作人ID
     * @return 操作人ID
     */
    Object operationUserId();
}
