package com.wkit.lost.mybatis.annotation;

/**
 * 雪花算法主键
 * @author DT
 */
public @interface Worker {
    /**
     * 是否为字符串(默认为数字)
     * @return true: 是 false: 否
     */
    boolean value() default false;
}
