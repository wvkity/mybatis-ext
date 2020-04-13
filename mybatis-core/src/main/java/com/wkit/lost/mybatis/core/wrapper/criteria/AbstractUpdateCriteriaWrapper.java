package com.wkit.lost.mybatis.core.wrapper.criteria;

/**
 * 抽象更新条件包装器
 * @param <T> 实体类型
 * @author wvkity
 */
@SuppressWarnings({"serial"})
public abstract class AbstractUpdateCriteriaWrapper<T> extends AbstractCriteriaWrapper<T>
        implements UpdateWrapper<T, AbstractUpdateCriteriaWrapper<T>> {

    @Override
    public AbstractUpdateCriteriaWrapper<T> updateVersion(Object version) {
        return null;
    }
}
