package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralUpdateMethod;
import com.wvkity.mybatis.core.mapping.sql.update.UpdateNotWithLockingProvider;

/**
 * 不带乐观锁更新操作方法映射(属性可选更新[updatable=true])
 * @author wvkity
 */
public class UpdateNotWithLocking extends AbstractGeneralUpdateMethod<UpdateNotWithLockingProvider> {

}
