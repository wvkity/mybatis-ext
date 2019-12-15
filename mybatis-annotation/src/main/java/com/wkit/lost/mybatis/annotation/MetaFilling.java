package com.wkit.lost.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识为自动填充字段
 * <p>优先级比{@link ColumnExt @ColumnExt}中的fill属性高</p>
 * @author wvkity
 * @see ColumnExt
 * @see ColumnExt#fill()
 */
@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Inherited
public @interface MetaFilling {

    /**
     * 保存操作进行填充值
     * @return true: 是 false: 否
     */
    boolean insert() default false;

    /**
     * 更新操作进行填充值
     * @return true: 是 false: 否
     */
    boolean update() default false;

    /**
     * 删除操作进行填充值
     * @return true: 是 false: 否
     */
    boolean delete() default false;
}
