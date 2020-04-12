package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 嵌套条件
 * @param <T> 实体类
 * @author wvkity
 */
public class Nested<T> extends ExpressionWrapper<T, Criterion<?>> {

    private static final long serialVersionUID = -230495355562719147L;
    private static final String AND_OR_REGEX = "^(?i)(\\s*and\\s+|\\s*or\\s+)(.*)";
    private static final Pattern AND_OR_PATTERN = Pattern.compile(AND_OR_REGEX, Pattern.CASE_INSENSITIVE);

    /**
     * 条件
     */
    private final Set<Criterion<?>> conditions = new LinkedHashSet<>(8);

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param logic      逻辑符号
     * @param conditions 条件
     */
    Nested(Criteria<T> criteria, Logic logic, Collection<Criterion<?>> conditions) {
        this.criteria = criteria;
        this.logic = logic;
        this.add(conditions);
    }

    /**
     * 添加条件
     * @param conditions 条件集合
     * @return {@code this}
     */
    public Nested<T> add(Criterion<?>... conditions) {
        return add(ArrayUtil.toList(conditions));
    }

    /**
     * 添加条件
     * @param conditions 条件集合
     * @return {@code this}
     */
    public Nested<T> add(Collection<Criterion<?>> conditions) {
        return add(this.criteria, conditions);
    }

    /**
     * 添加条件
     * @param criteria   条件包装对象
     * @param conditions 条件集合
     * @return {@code this}
     */
    public Nested<T> add(Criteria<T> criteria, Criterion<?>... conditions) {
        return add(criteria, ArrayUtil.toList(conditions));
    }

    /**
     * 添加条件
     * @param criteria   条件包装对象
     * @param conditions 条件集合
     * @return {@code this}
     */
    public Nested<T> add(Criteria<T> criteria, Collection<Criterion<?>> conditions) {
        final Criteria<T> realCriteria = criteria == null ? this.criteria : getCriteria();
        if (CollectionUtil.hasElement(conditions)) {
            this.conditions.addAll(conditions.stream().filter(Objects::nonNull).peek(it -> {
                if (it.getCriteria() == null) {
                    it.criteria(realCriteria);
                }
            }).collect(Collectors.toList()));
        }
        return this;
    }

    @Override
    public String getSegment() {
        if (CollectionUtil.hasElement(this.conditions)) {
            final StringBuilder builder = new StringBuilder(logic.getSegment());
            builder.append(Constants.SPACE).append(Constants.BRACKET_LEFT);
            final String segment = this.conditions.stream().map(Criterion::getSegment)
                    .collect(Collectors.joining(Constants.SPACE));
            if (AND_OR_PATTERN.matcher(segment).matches()) {
                builder.append(segment.replaceFirst(AND_OR_REGEX, "$2"));
            } else {
                builder.append(segment);
            }
            builder.append(Constants.BRACKET_RIGHT);
            return builder.toString();
        }
        return "";
    }

    /**
     * 创建嵌套条件对象
     * @param criteria   条件包装对象
     * @param logic      逻辑符号
     * @param conditions 条件数组
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> Nested<T> create(Criteria<T> criteria, Logic logic, Criterion<?>... conditions) {
        return create(criteria, logic, ArrayUtil.toList(conditions));
    }

    /**
     * 创建嵌套条件对象
     * @param criteria   条件包装对象
     * @param logic      逻辑符号
     * @param conditions 条件集合
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> Nested<T> create(Criteria<T> criteria, Logic logic, Collection<Criterion<?>> conditions) {
        return new Nested<>(criteria, logic, conditions);
    }
}
