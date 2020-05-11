package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 排序(聚合函数对象)
 * @author wvkity
 */
public class FunctionSort extends AbstractSortWrapper<Function> {

    private static final long serialVersionUID = -6042427127886318392L;

    /**
     * 构造方法
     * @param functions 聚合函数集合
     * @param ascending 排序方式(是否为ASC排序)
     */
    private FunctionSort(boolean ascending, Collection<Function> functions) {
        this.ascending = ascending;
        if (CollectionUtil.hasElement(functions)) {
            this.columns = functions.stream().filter(Objects::nonNull)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    @Override
    public String getSegment() {
        if (notEmpty()) {
            String orderMode = ascending ? " ASC" : " DESC";
            return this.columns.stream().map(it -> it.getOrderSegment() + orderMode)
                    .collect(Collectors.joining(", "));
        }
        return "";
    }

    /**
     * ASC排序
     * @param functions 聚合函数
     * @return 排序对象
     */
    public static FunctionSort asc(Function... functions) {
        return asc(ArrayUtil.toList(functions));
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @return 排序对象
     */
    public static FunctionSort asc(Criteria<?> criteria, String... aliases) {
        return asc(criteria, ArrayUtil.toList(aliases));
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @return 排序对象
     */
    public static FunctionSort asc(Criteria<?> criteria, List<String> aliases) {
        if (CollectionUtil.hasElement(aliases)) {
            return asc(aliases.stream().map(criteria::searchFunction).collect(Collectors.toList()));
        }
        return null;
    }

    /**
     * ASC排序
     * @param functions 聚合函数
     * @return 排序对象
     */
    public static FunctionSort asc(List<Function> functions) {
        return new FunctionSort(true, functions);
    }

    /**
     * ASC排序
     * @param functions 聚合函数
     * @return 排序对象
     */
    public static FunctionSort desc(Function... functions) {
        return desc(ArrayUtil.toList(functions));
    }

    /**
     * ASC排序
     * @param functions 聚合函数
     * @return 排序对象
     */
    public static FunctionSort desc(List<Function> functions) {
        return new FunctionSort(false, functions);
    }

    /**
     * DESC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @return 排序对象
     */
    public static FunctionSort desc(Criteria<?> criteria, String... aliases) {
        return desc(criteria, ArrayUtil.toList(aliases));
    }

    /**
     * DESC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @return 排序对象
     */
    public static FunctionSort desc(Criteria<?> criteria, List<String> aliases) {
        if (CollectionUtil.hasElement(aliases)) {
            return desc(aliases.stream().map(criteria::searchFunction).collect(Collectors.toList()));
        }
        return null;
    }

}
