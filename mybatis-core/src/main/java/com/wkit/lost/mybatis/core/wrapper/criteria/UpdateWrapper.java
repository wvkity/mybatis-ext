package com.wkit.lost.mybatis.core.wrapper.criteria;

/**
 * 更新操作包装接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 */
public interface UpdateWrapper<T, Chain extends UpdateWrapper<T, Chain>> {

    /**
     * 修改版本
     * @param version 版本号
     * @return 当前对象
     */
    Chain updateVersion(Object version);
}
