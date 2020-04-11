package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.conditional.utils.Formatter;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.TemplateMatch;
import com.wkit.lost.mybatis.core.lambda.Property;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

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
 * @param <T> 实体类型
 * @author wvkity
 */
public class Template<T> extends ColumnExpressionWrapper<T> {

    private static final long serialVersionUID = -7379608873522056093L;

    /**
     * 字段占位符标识
     */
    private static final String COLUMN_PLACEHOLDER = "{@@}";

    /**
     * 匹配模式
     */
    private TemplateMatch match;

    /**
     * 模板
     */
    @Getter
    private String template;

    /**
     * 值
     */
    @Getter
    @Setter
    private Collection<Object> values;

    @Getter
    @Setter
    private Map<String, Object> mapValues;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param template 模板
     * @param logic    逻辑符号
     */
    Template(Criteria<T> criteria, ColumnWrapper column, Object value, String template, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.template = template;
        this.logic = logic;
        this.match = TemplateMatch.SINGLE;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     */
    Template(Criteria<T> criteria, ColumnWrapper column, Collection<Object> values, String template, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.values = values;
        this.template = template;
        this.logic = logic;
        this.match = TemplateMatch.MULTIPLE;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     */
    Template(Criteria<T> criteria, ColumnWrapper column, Map<String, Object> values, String template, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.mapValues = values;
        this.template = template;
        this.logic = logic;
        this.match = TemplateMatch.MAP;
    }

    @Override
    public String getSegment() {
        StringBuilder builder = new StringBuilder(60);
        builder.append(this.logic.getSegment()).append(Constants.SPACE);
        String realAlias = this.criteria.getAlias();
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
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, Property<T, ?> property,
                                         Object value, String template) {
        return create(criteria, property, value, template, Logic.AND);
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, Property<T, ?> property,
                                         Object value, String template, Logic logic) {
        if (criteria != null && property != null) {
            return create(criteria, criteria.searchColumn(property), value, template, logic);
        }
        return null;
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, String property, Object value, String template) {
        return create(criteria, property, value, template, Logic.AND);
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, String property,
                                         Object value, String template, Logic logic) {
        if (criteria != null && hasText(property)) {
            return create(criteria, criteria.searchColumn(property), value, template, logic);
        }
        return null;
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, ColumnWrapper column,
                                         Object value, String template) {
        return create(criteria, column, value, template, Logic.AND);
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, ColumnWrapper column,
                                         Object value, String template, Logic logic) {
        if (criteria != null && column != null && hasText(template) && template.contains(COLUMN_PLACEHOLDER)) {
            return new Template<>(criteria, column, value, template, logic);
        }
        return null;
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, Property<T, ?> property,
                                         Collection<Object> values, String template) {
        return create(criteria, property, values, template, Logic.AND);
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, Property<T, ?> property,
                                         Collection<Object> values, String template, Logic logic) {
        if (criteria != null && property != null) {
            return create(criteria, criteria.searchColumn(property), values, template, logic);
        }
        return null;
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, String property,
                                         Collection<Object> values, String template) {
        return create(criteria, property, values, template, Logic.AND);
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, String property,
                                         Collection<Object> values, String template, Logic logic) {
        if (criteria != null && hasText(property)) {
            return create(criteria, criteria.searchColumn(property), values, template, logic);
        }
        return null;
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, ColumnWrapper column,
                                         Collection<Object> values, String template) {
        return create(criteria, column, values, template, Logic.AND);
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, ColumnWrapper column,
                                         Collection<Object> values, String template, Logic logic) {
        if (criteria != null && column != null && hasText(template) && template.contains(COLUMN_PLACEHOLDER)) {
            return new Template<>(criteria, column, values, template, logic);
        }
        return null;
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, Property<T, ?> property,
                                         Map<String, Object> values, String template) {
        return create(criteria, property, values, template, Logic.AND);
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, Property<T, ?> property,
                                         Map<String, Object> values, String template, Logic logic) {
        if (criteria != null && property != null) {
            return create(criteria, criteria.searchColumn(property), values, template, logic);
        }
        return null;
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, String property,
                                         Map<String, Object> values, String template) {
        return create(criteria, property, values, template, Logic.AND);
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, String property,
                                         Map<String, Object> values, String template, Logic logic) {
        if (criteria != null && hasText(property)) {
            return create(criteria, criteria.searchColumn(property), values, template, logic);
        }
        return null;
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, ColumnWrapper column,
                                         Map<String, Object> values, String template) {
        return create(criteria, column, values, template, Logic.AND);
    }

    /**
     * 创建模板条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> create(Criteria<T> criteria, ColumnWrapper column,
                                         Map<String, Object> values, String template, Logic logic) {
        if (criteria != null && column != null && hasText(template) && template.contains(COLUMN_PLACEHOLDER)) {
            return new Template<>(criteria, column, values, template, logic);
        }
        return null;
    }

}
