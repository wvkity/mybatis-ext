package com.wvkity.mybatis.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * 数组工具类
 */
public class ArrayUtil {

    /**
     * 检测对象是否为数组对象
     * @param target 待检测对象
     * @return boolean
     */
    public static boolean isArray(final Object target) {
        return target != null && target.getClass().isArray();
    }

    /**
     * 检测数组对象是否为空
     * @param args 待检测数组对象
     * @param <T>  类型
     * @return boolean
     */
    @SafeVarargs
    public static <T> boolean isEmpty(final T... args) {
        return args == null || args.length == 0;
    }

    /**
     * 检测数组对象是否为空
     * @param target 待检测数组对象
     * @return boolean
     */
    public static boolean isEmpty(final Object target) {
        if (target == null) {
            return true;
        } else if (isArray(target)) {
            return Array.getLength(target) == 0;
        }
        throw new IllegalArgumentException("The argument provided is not an array object");
    }

    /**
     * 获取数组大小
     * @param target 数组对象
     * @return 数组长度
     */
    public static int length(final Object target) {
        if (isArray(target)) {
            return Array.getLength(target);
        }
        return 0;
    }

    /**
     * 数组转集合
     * @param <T>   泛型
     * @param array 数组
     * @return 集合
     */
    @SafeVarargs
    public static <T> ArrayList<T> toList(T... array) {
        return Optional.ofNullable(array).map(it -> new ArrayList<>(Arrays.asList(it)))
                .orElse(new ArrayList<>(0));
    }
}
