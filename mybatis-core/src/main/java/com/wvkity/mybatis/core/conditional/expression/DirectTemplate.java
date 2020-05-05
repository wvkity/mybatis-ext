package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.conditional.utils.PlaceholderParser;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.TemplateMatch;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.Constants;
import com.wvkity.mybatis.utils.StringUtil;
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
 * ":&#64;" is not mandatory in the template and is used to identify it as a placeholder for a database field,
 * which is automatically replaced with the specified field when it is converted into an SQL fragment.
 * </p>
 * <pre>
 *     // MYSQL
 *     // Examples
 *     &#64;Inject
 *     private GradeService gradeService;
 *
 *     QueryCriteria&lt;Grade&gt; criteria = new QueryCriteria&lt;&gt;(Grade.class);
 *
 *     // single parameter:
 *     // NO1.
 *     String template = "LEFT(NAME, 2) = ?0";
 *     criteria.directTemplate(template, "S1");
 *     gradeService.list(criteria);
 *     return:
 *     SELECT column1, column2, ... FROM GRADE WHERE LEFT(NAME, 2) = ?
 *
 *     // NO2.
 *     String template = "LEFT(:&#64;, 2) = ?0";
 *     criteria.directTemplate(template, "NAME", "S1");
 *     gradeService.list(criteria);
 *     return:
 *     SELECT column1, column2, ... FROM GRADE WHERE LEFT(NAME, 2) = ?
 *
 *     // multiple parameter:
 *     // NO3.
 *     String template = "LEFT(NAME, ?0) = ?1";
 *     criteria.directTemplate(template, 2, "S1");
 *     gradeService.list(criteria);
 *     return:
 *     SELECT column1, column2, ... FROM GRADE WHERE LEFT(NAME, ?) = ?
 *
 *     // NO4.
 *     String template = "LEFT(:&#64;, ?0) = ?1";
 *     criteria.directTemplate(template,"NAME", 2, "S1");
 *     gradeService.list(criteria);
 *     return:
 *     SELECT column1, column2, ... FROM GRADE WHERE LEFT(NAME, ?) = ?
 *
 *     // map parameter:
 *     // NO5.
 *     String template = "LEFT(NAME, :left) = :name";
 *     Map&lt;String, Object&gt; params = new HashMap&lt;&gt;();
 *     params.put("left", 2);
 *     params.put("name", "S1");
 *     criteria.directTemplate(template, params);
 *     gradeService.list(criteria);
 *     return:
 *     SELECT column1, column2, ... FROM GRADE WHERE LEFT(NAME, ?) = ?
 *
 *     // NO6.
 *     String template = "LEFT(:&#64;, :left) = :name";
 *     Map&lt;String, Object&gt; params = new HashMap&lt;&gt;();
 *     params.put("left", 2);
 *     params.put("name", "S1");
 *     criteria.directTemplate(template, "NAME", params);
 *     gradeService.list(criteria);
 *     return:
 *     SELECT column1, column2, ... FROM GRADE WHERE LEFT(NAME, ?) = ?
 * </pre>
 * @author wvkity
 * @see PlaceholderParser
 */
public class DirectTemplate extends DirectExpressionWrapper {

    private static final long serialVersionUID = 5788833497759949457L;

    /**
     * 字段占位符标识
     */
    public static final String COLUMN_PLACEHOLDER = ":@";

    /**
     * 匹配模式
     */
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
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param template   模板
     * @param value      值
     * @param values     多个值
     * @param mapValues  多个键值
     * @param logic      逻辑符号
     */
    DirectTemplate(Criteria<?> criteria, String tableAlias, String column, String template,
                   Object value, Collection<Object> values, Map<String, Object> mapValues, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.template = template;
        this.value = value;
        this.values = values;
        this.mapValues = mapValues;
        this.logic = logic;
        this.match = CollectionUtil.hasElement(mapValues) ? TemplateMatch.MAP :
                CollectionUtil.hasElement(values) ? TemplateMatch.MULTIPLE : TemplateMatch.SINGLE;
    }

    @Override
    public String getSegment() {
        StringBuilder builder = new StringBuilder(60);
        builder.append(this.logic.getSegment()).append(Constants.SPACE);
        String realTemplate;
        if (this.template.contains(COLUMN_PLACEHOLDER)) {
            String realAlias = this.criteria.as();
            String columnName = (StringUtil.hasText(realAlias) ? (realAlias.trim() + ".") : "") + this.column;
            realTemplate = this.template.replaceAll("\\" + COLUMN_PLACEHOLDER, columnName);
        } else {
            realTemplate = this.template;
        }
        switch (this.match) {
            // 单个参数
            case SINGLE:
                builder.append(PlaceholderParser.format(realTemplate,
                        ScriptUtil.safeJoint(defaultPlaceholder(this.value))));
                break;
            // 多个参数    
            case MULTIPLE:
                builder.append(PlaceholderParser.format(realTemplate,
                        this.values.stream().map(it -> ScriptUtil.safeJoint(defaultPlaceholder(it)))
                                .collect(Collectors.toList())));
                break;
            // Map参数    
            default:
                builder.append(PlaceholderParser.format(realTemplate,
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
    public static DirectTemplate.Builder create() {
        return new DirectTemplate.Builder();
    }

    /**
     * 条件构建器
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
         * 表别名
         */
        private String alias;
        /**
         * 条件包装对象
         */
        private String column;
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
         * 构建条件对象
         * @return 条件对象
         */
        public DirectTemplate build() {
            if (this.value == null && CollectionUtil.isEmpty(this.values)
                    && CollectionUtil.isEmpty(this.map) && StringUtil.isBlank(this.template)) {
                return null;
            }
            return new DirectTemplate(this.criteria, this.alias, this.column, this.template,
                    this.value, this.values, this.map, this.logic);
        }
    }
}
