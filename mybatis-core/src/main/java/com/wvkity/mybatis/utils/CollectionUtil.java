package com.wvkity.mybatis.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 * @author wvkity
 */
public abstract class CollectionUtil {

    /**
     * 检测集合是否不包含某个元素
     * @param collection 集合
     * @param target     对象
     * @return boolean
     */
    public static boolean notContains(final Collection<?> collection, final Object target) {
        return collection != null && target != null && !collection.contains(target);
    }

    /**
     * 检测集合是否为空
     * @param target 待检测集合
     * @param <E>    集合类型
     * @return boolean
     */
    public static <E> boolean isEmpty(final Collection<E> target) {
        return target == null || target.isEmpty();
    }

    /**
     * 检测集合是否有值
     * @param target 待检测集合
     * @param <E>    集合类型
     * @return boolean
     */
    public static <E> boolean hasElement(final Collection<E> target) {
        return !isEmpty(target);
    }

    /**
     * 检测{@link Map}集合是否为空
     * @param target 待检测集合
     * @param <K>    键类型
     * @param <V>    值类型
     * @return boolean
     */
    public static <K, V> boolean isEmpty(final Map<K, V> target) {
        return target == null || target.isEmpty();
    }

    /**
     * 检测{@link Map}集合是否有值
     * @param target 待检测集合
     * @param <K>    键类型
     * @param <V>    值类型
     * @return boolean
     */
    public static <K, V> boolean hasElement(final Map<K, V> target) {
        return !isEmpty(target);
    }
}
