package com.wvkity.mybatis.core.handler;

import com.wvkity.mybatis.core.metadata.FieldWrapper;
import com.wvkity.mybatis.core.parser.FieldParser;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 实体属性处理器
 * @author wvkity
 */
public final class FieldHandler {

    private FieldHandler() {
    }

    /**
     * 解析实体类属性
     * @param parser 属性解析器
     * @param entity 实体类
     * @return 属性列表
     */
    public static List<FieldWrapper> parse(final FieldParser parser, final Class<?> entity) {
        return parser.parse(entity);
    }

    /**
     * 从BeanInfo中解析实体属性
     * @param parser 属性解析器
     * @param entity 实体类
     * @return 属性列表
     */
    public static List<FieldWrapper> parseFromBeanInfo(final FieldParser parser, final Class<?> entity) {
        return parser.parseFromBeanInfo(entity);
    }

    /**
     * 合并属性
     * @param parser 属性解析器
     * @param entity 实体类
     * @return 属性列表
     */
    public static List<FieldWrapper> merge(final FieldParser parser, final Class<?> entity) {
        List<FieldWrapper> fields = parse(parser, entity);
        List<FieldWrapper> beanFields = parseFromBeanInfo(parser, entity);
        Set<FieldWrapper> merged = new HashSet<>();
        Set<FieldWrapper> wrappers = fields.stream()
                .peek(it ->
                        beanFields.stream()
                                .filter(_it -> !merged.contains(_it)
                                        && StringUtil.equals(_it.getName(), it.getName()))
                                .findFirst().ifPresent(_it -> {
                            it.copy(_it);
                            merged.add(_it);
                        })).collect(Collectors.toCollection(LinkedHashSet::new));
        if (!beanFields.isEmpty()) {
            wrappers.addAll(beanFields.stream().filter(it -> !merged.contains(it)).collect(Collectors.toList()));
        }
        return new ArrayList<>(wrappers);
    }
}
