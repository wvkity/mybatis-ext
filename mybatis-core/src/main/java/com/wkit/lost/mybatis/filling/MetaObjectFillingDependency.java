package com.wkit.lost.mybatis.filling;

/**
 * 自动填充依赖接口
 * @author DT
 */
public interface MetaObjectFillingDependency {

    /**
     * 当前用户名
     * @return 用户名
     */
    String currentUserName();

    /**
     * 当前用户ID
     * @return 用户ID
     */
    Object currentUserId();
}
