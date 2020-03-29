package com.wkit.lost.mybatis.core.wrapper.criteria;

public interface Modify<T, Chain extends Modify<T, Chain>> {

    /**
     * 修改版本
     * @param version 版本号
     * @return 当前对象
     */
    Chain updateVersion( Object version );
}
