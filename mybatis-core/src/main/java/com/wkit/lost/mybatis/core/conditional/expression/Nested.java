package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 嵌套条件
 * @author wvkity
 */
public class Nested extends ExpressionWrapper<Criterion> {

    private static final long serialVersionUID = -230495355562719147L;
    private static final String AND_OR_REGEX = "^(?i)(\\s*and\\s+|\\s*or\\s+)(.*)";
    private static final Pattern AND_OR_PATTERN = Pattern.compile(AND_OR_REGEX, Pattern.CASE_INSENSITIVE);

    /**
     * 条件
     */
    private final Set<Criterion> conditions = new LinkedHashSet<>(8);

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param logic      逻辑符号
     * @param conditions 条件
     */
    Nested(Criteria<?> criteria, Collection<Criterion> conditions, Logic logic) {
        this.criteria = criteria;
        this.logic = logic;
        this.add(conditions);
    }

    /**
     * 添加条件
     * @param conditions 条件集合
     * @return {@code this}
     */
    public Nested add(Criterion... conditions) {
        return add(ArrayUtil.toList(conditions));
    }

    /**
     * 添加条件
     * @param conditions 条件集合
     * @return {@code this}
     */
    public Nested add(Collection<Criterion> conditions) {
        return add(this.criteria, conditions);
    }

    /**
     * 添加条件
     * @param criteria   条件包装对象
     * @param conditions 条件集合
     * @return {@code this}
     */
    public Nested add(Criteria<?> criteria, Criterion... conditions) {
        return add(criteria, ArrayUtil.toList(conditions));
    }

    /**
     * 添加条件
     * @param criteria   条件包装对象
     * @param conditions 条件集合
     * @return {@code this}
     */
    public Nested add(Criteria<?> criteria, Collection<Criterion> conditions) {
        final Criteria<?> realCriteria = criteria == null ? this.criteria : getCriteria();
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
     * 创建条件构建器
     * @return 构建器
     */
    public static Nested.Builder create() {
        return new Nested.Builder();
    }

    /**
     * 条件对象构建器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        /**
         * 条件包装对象
         */
        private Criteria<?> criteria;
        /**
         * 条件集合
         */
        private Collection<Criterion> conditions;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public Nested build() {
            if (CollectionUtil.isEmpty(this.conditions)) {
                return null;
            }
            return new Nested(this.criteria, this.conditions, this.logic);
        }
    }
}
