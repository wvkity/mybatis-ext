package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.conditional.utils.Formatter;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.TemplateMatch;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 模板条件
 * <p>
 * "{&#64;&#64;}" must exist in the template to identify it as a placeholder for a database field,
 * which is automatically replaced when it is converted into an SQL fragment.
 * </p>
 * <pre>
 *     // MYSQL
 *     // Examples:
 *     &#64;Inject
 *     private GradeService gradeService;
 *
 *     QueryCriteria&lt;Grade&gt; criteria = new QueryCriteria&lt;&gt;(Grade.class);
 *
 *     // single parameter:
 *     // NO1.
 *     String template = "LEFT({&#64;&#64;}, 2) = {}";
 *     criteria.template(template, Grade::Name, "S1");
 *     gradeService.list(criteria);
 *     return:
 *     SELECT column1, column2, ... FROM GRADE WHERE LEFT(NAME, 2) = ?
 *
 *     // multiple parameter:
 *     // NO2.
 *     String template = "LEFT({&#64;&#64;}, {}) = {}";
 *     criteria.template(template, Grade::Name, 2, "S1");
 *     gradeService.list(criteria);
 *     return:
 *     SELECT column1, column2, ... FROM GRADE WHERE LEFT(NAME, ?) = ?
 *
 *     // map parameter:
 *     // NO3.
 *     String template = "LEFT({&#64;&#64;}, ${left}) = ${name}";
 *     Map&lt;String, Object&gt; params = new HashMap&lt;&gt;();
 *     params.put("left", 2);
 *     params.put("name", "S1");
 *     criteria.template(template, Grade::Name, params);
 *     gradeService.list(criteria);
 *     return:
 *     SELECT column1, column2, ... FROM GRADE WHERE LEFT(NAME, ?) = ?
 * </pre>
 * @author wvkity
 */
public class Template extends ColumnExpressionWrapper {

    private static final long serialVersionUID = -7379608873522056093L;

    /**
     * 字段占位符标识
     */
    public static final String COLUMN_PLACEHOLDER = "{@@}";

    /**
     * 匹配模式
     */
    @Getter
    private final TemplateMatch match;

    /**
     * 模板
     */
    @Getter
    private final String template;

    /**
     * 多个值
     */
    private final Collection<Object> values;

    /**
     * 多个键值
     */
    private final Map<String, Object> mapValues;

    /**
     * 构造方法
     * @param criteria  条件包装对象
     * @param column    字段包装对象
     * @param template  模板
     * @param value     值
     * @param values    多个值
     * @param mapValues 多个键值
     * @param logic     逻辑符号
     */
    Template(Criteria<?> criteria, ColumnWrapper column, String template, Object value,
             Collection<Object> values, Map<String, Object> mapValues, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.values = values;
        this.mapValues = mapValues;
        this.template = template;
        this.logic = logic;
        this.match = CollectionUtil.hasElement(mapValues) ? TemplateMatch.MAP :
                CollectionUtil.hasElement(values) ? TemplateMatch.MULTIPLE : TemplateMatch.SINGLE;
    }

    @Override
    public String getSegment() {
        StringBuilder builder = new StringBuilder(60);
        builder.append(this.logic.getSegment()).append(Constants.SPACE);
        String realAlias = this.criteria.as();
        String columnName = (StringUtil.hasText(realAlias) ? (realAlias.trim() + ".") : "")
                + this.column.getColumn();
        String realTemplate = this.template.replaceAll("\\" + COLUMN_PLACEHOLDER, columnName);
        switch (this.match) {
            // 单个参数
            case SINGLE:
                builder.append(Formatter.format(realTemplate,
                        ScriptUtil.safeJoint(defaultPlaceholder(this.value))));
                break;
            // 多个参数    
            case MULTIPLE:
                builder.append(Formatter.format(realTemplate,
                        this.values.stream().map(it -> ScriptUtil.safeJoint(defaultPlaceholder(it)))
                                .collect(Collectors.toList())));
                break;
            // Map参数    
            default:
                builder.append(Formatter.format(realTemplate,
                        this.mapValues.entrySet().parallelStream()
                                .collect(Collectors.toMap(Map.Entry::getKey,
                                        it -> ScriptUtil.safeJoint(defaultPlaceholder(it.getValue()))))));
                break;
        }
        return builder.toString();
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static Template.Builder create() {
        return new Template.Builder();
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
         * 条件包装对象
         */
        private ColumnWrapper column;
        /**
         * 属性
         */
        @Setter(AccessLevel.NONE)
        private String property;
        /**
         * 属性
         */
        @Setter(AccessLevel.NONE)
        private Property<?, ?> lambdaProperty;
        /**
         * 模板
         */
        private String template;
        /**
         * 值
         */
        private Object value;
        /**
         * 多个值
         */
        private Collection<Object> values;

        /**
         * Map值
         */
        private Map<String, Object> map;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 属性
         * @param property 属性
         * @return {@link Equal.Builder}
         */
        public Template.Builder property(String property) {
            this.property = property;
            return this;
        }

        /**
         * 属性
         * @param property 属性
         * @param <T>      实体类型
         * @param <V>      属性值类型
         * @return {@link Equal.Builder}
         */
        public <T, V> Template.Builder property(Property<T, V> property) {
            this.lambdaProperty = property;
            return this;
        }

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public Template build() {
            if (this.value == null || CollectionUtil.isEmpty(this.values) 
                    || CollectionUtil.isEmpty(this.map) || StringUtil.isBlank(this.template)) {
                return null;
            }
            if (this.column != null) {
                return new Template(this.criteria, this.column, this.template, this.value,
                        this.values, this.map, this.logic);
            }
            if (this.criteria == null) {
                return null;
            }
            if (hasText(this.property)) {
                ColumnWrapper wrapper = this.criteria.searchColumn(this.property);
                if (wrapper != null) {
                    return new Template(this.criteria, wrapper, this.template, this.value,
                            this.values, this.map, this.logic);
                }
            }
            if (lambdaProperty != null) {
                ColumnWrapper wrapper = this.criteria.searchColumn(lambdaProperty);
                if (wrapper != null) {
                    return new Template(this.criteria, wrapper, this.template, this.value,
                            this.values, this.map, this.logic);
                }
            }
            return null;
        }
    }
}
