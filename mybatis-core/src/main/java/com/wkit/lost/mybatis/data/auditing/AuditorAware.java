package com.wkit.lost.mybatis.data.auditing;

/**
 * 审计接口(获取用户信息)
 * @author wvkity
 */
public interface AuditorAware {

    /**
     * 获取当前操作人名称
     * @return 操作人名称
     */
    Object currentUserName();

    /**
     * 获取当前操作人ID
     * @return 操作人ID
     */
    Object currentUserId();
}
