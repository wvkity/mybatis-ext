package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象查询列包装器
 * @param <E> 字段类型
 * @author wvkity
 */
@SuppressWarnings({"serial"})
public abstract class AbstractQueryWrapper<E> extends AbstractWrapper<E> implements QueryWrapper<E> {

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

    @Override
    public String alias() {
        return this.columnAlias;
    }

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
