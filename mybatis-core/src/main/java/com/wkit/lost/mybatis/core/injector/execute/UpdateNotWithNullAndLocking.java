package com.wkit.lost.mybatis.core.injector.execute;

import com.wkit.lost.mybatis.core.injector.method.AbstractGeneralUpdateMethod;
import com.wkit.lost.mybatis.core.mapping.sql.update.UpdateNotWithNullAndLockingProvider;

/**
 * 不带乐观锁非空更新操作方法映射(属性可选更新[updatable=true])
 * @author wvkity
 */
public class UpdateNotWithNullAndLocking extends AbstractGeneralUpdateMethod<UpdateNotWithNullAndLockingProvider> {
}
