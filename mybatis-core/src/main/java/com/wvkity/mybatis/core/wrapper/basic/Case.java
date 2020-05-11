package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.conditional.criterion.Criterion;
import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.Constants;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * CASE WHEN condition THEN value ELSE otherValue END
 * @author wvkity
 */
public class Case implements Segment {

    private static final long serialVersionUID = -5463777731782305163L;

    private static final String AND_OR_REGEX = "^(?i)(\\s*and\\s+|\\s*or\\s+)(.*)";
    private static final Pattern AND_OR_PATTERN = Pattern.compile(AND_OR_REGEX, Pattern.CASE_INSENSITIVE);

    /**
     * 别名
     */
    private String alias;

    /**
     * WHEN/ELSE选项
     */
    private final List<When> WHENS = new ArrayList<>(8);

    private Case() {
    }

    /**
     * 创建Case实例
     * @return {@link Case}
     */
    public static Case create() {
        return new Case();
    }

    /**
     * WHEN选项
     * @param condition 条件
     * @return {@link When}
     */
    public When when(String condition) {
        return new When(this, condition);
    }

    /**
     * WHEN选项
     * @param condition 条件
     * @return {@link When}
     */
    public When when(Criterion condition) {
        return new When(this, condition);
    }

    /**
     * WHEN选项
     * @param conditions 条件集合
     * @return {@link When}
     */
    public When when(List<Criterion> conditions) {
        return new When(this, conditions);
    }

    /**
     * ELSE选项(标记结束)
     * @param value 值
     * @return {@code this}
     */
    public Case end(Object value) {
        this.WHENS.add(new When(this, value));
        return this;
    }

    /**
     * 设置别名
     * @param alias 别名
     * @return {@code this}
     */
    public Case as(final String alias) {
        this.alias = alias;
        return this;
    }

    /**
     * 获取别名
     * @return 别名
     */
    public String as() {
        return this.alias;
    }

    /**
     * 添加WHEN对象
     * @param when {@link When}
     * @return {@code this}
     */
    public Case add(When when) {
        if (when != null) {
            this.WHENS.add(when);
        }
        return this;
    }

    @Override
    public String getSegment() {
        String script = getConditionSegment();
        if (StringUtil.hasText(script)) {
            if (StringUtil.hasText(this.alias)) {
                return "(" + script + ") AS " + this.alias;
            }
        }
        return script;
    }

    /**
     * 获取SQL片段(作为条件)
     * @return SQL片段
     */
    public String getConditionSegment() {
        if (!this.WHENS.isEmpty()) {
            String temp = this.WHENS.stream().map(Segment::getSegment).filter(Objects::nonNull)
                    .collect(Collectors.joining(Constants.SPACE));
            if (StringUtil.hasText(temp)) {
                return "CASE " + temp.trim() + " END";
            }
        }
        return Constants.EMPTY;
    }

    /**
     * Case条件
     * @author wvkity
     */
    public static class When implements Segment {

        private static final long serialVersionUID = -913351210913682710L;

        /**
         * Case对象
         */
        private final Case _case;

        /**
         * 单个条件(字符串)
         */
        private final String condition;

        /**
         * 单个条件
         */
        private final Criterion criterion;

        /**
         * 多个条件
         */
        private final List<Criterion> conditions;

        /**
         * 值
         */
        private Object value;

        /**
         * 标记是否为结束
         */
        private final boolean end;

        /**
         * 构造方法
         * @param _case {@link Case}
         * @param value 值
         */
        private When(Case _case, Object value) {
            this._case = _case;
            this.value = value;
            this.condition = null;
            this.criterion = null;
            this.conditions = null;
            this.end = true;
        }

        /**
         * 构造方法
         * @param _case     {@link Case}
         * @param condition 条件
         */
        private When(Case _case, String condition) {
            this._case = _case;
            this.condition = condition;
            this.conditions = null;
            this.criterion = null;
            end = false;
        }

        /**
         * 构造方法
         * @param _case     {@link Case}
         * @param criterion 条件
         */
        private When(Case _case, Criterion criterion) {
            this._case = _case;
            this.criterion = criterion;
            this.condition = null;
            this.conditions = null;
            end = false;
        }

        /**
         * 构造方法
         * @param _case      {@link Case}
         * @param conditions 多个条件对象
         */
        private When(Case _case, List<Criterion> conditions) {
            this._case = _case;
            this.conditions = conditions;
            this.criterion = null;
            this.condition = null;
            end = false;
        }

        /**
         * 设置值
         * @param value 值(常量值/字段名)
         * @return {@link Case}
         */
        public Case then(Object value) {
            this.value = value;
            return this._case.add(this);
        }

        @Override
        public String getSegment() {
            if (this.end) {
                return "ELSE " + this.value;
            } else {
                if (StringUtil.hasText(this.condition)) {
                    return "WHEN " + condition + " THEN " + this.value;
                } else if (this.criterion != null) {
                    String script = this.criterion.getSegment();
                    if (AND_OR_PATTERN.matcher(script).matches()) {
                        return "WHEN " + script.replaceFirst(AND_OR_REGEX, "$2") + " THEN " + this.value;
                    }
                    return "WHEN " + script + " THEN " + this.value;
                } else if (CollectionUtil.hasElement(this.conditions)) {
                    String script = this.conditions.stream().map(Segment::getSegment)
                            .collect(Collectors.joining(Constants.SPACE));
                    if (AND_OR_PATTERN.matcher(script).matches()) {
                        return "WHEN " + script.replaceFirst(AND_OR_REGEX, "$2") + " THEN " + this.value;
                    }
                    return "WHEN " + script + " THEN " + this.value;
                }
            }
            return null;
        }
    }
}
