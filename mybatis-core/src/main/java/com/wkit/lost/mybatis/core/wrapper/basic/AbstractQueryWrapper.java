package com.wkit.lost.mybatis.core.wrapper.basic;

import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象查询列包装器
 * @param <T> 实体类型
 * @param <E> 字段类型
 * @author wvkity
 */
@SuppressWarnings({"serial"})
public abstract class AbstractQueryWrapper<T, E> extends AbstractWrapper<T, E> implements QueryWrapper<T> {

    /**
     * 字段
     */
    protected E column;

    /**
     * 字段别名
     */
    protected String columnAlias;

    @Override
    protected boolean notEmpty() {
        return this.column != null;
    }

    /**
     * 将当前查询列包装对象转换成另一个查询列包装对象
     * @param criteria 条件对象
     * @return 新的查询列包装对象
     */
    public abstract AbstractQueryWrapper<?, ?> transform(Criteria<?> criteria);

    /**
     * 获取SQL片段
     * @param applyQuery 是否为查询语句
     * @return SQL片段
     */
    public abstract String getSegment(boolean applyQuery);

    /**
     * 过滤空值元素
     * @param map 字段别名-字段集合
     * @return 字段别名-字段集合
     */
    public static Map<String, String> filterNullValue(Map<String, String> map) {
        if (CollectionUtil.hasElement(map)) {
            return map.entrySet().stream().filter(it -> StringUtil.hasText(it.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (k1, k2) -> k2, LinkedHashMap::new));
        }
        return map;
    }

}
