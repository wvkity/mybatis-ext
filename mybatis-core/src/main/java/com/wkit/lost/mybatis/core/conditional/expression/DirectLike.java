package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Match;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * LIKE条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class DirectLike<T> extends AbstractDirectFuzzyExpression<T> {

    private static final long serialVersionUID = 7581578653676661619L;

    /**
     * 构造方法
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     */
    DirectLike(String column, String value, Logic logic) {
        this.column = column;
        this.value = value;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     */
    DirectLike(String tableAlias, String column, String value, Logic logic) {
        this.tableAlias = tableAlias;
        this.column = column;
        this.value = value;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     */
    DirectLike(Criteria<T> criteria, String column, String value, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param column 字段
     * @param value  值
     * @param match  匹配模式
     * @param logic  逻辑符号
     */
    DirectLike(String column, String value, Match match, Logic logic) {
        this.column = column;
        this.value = value;
        this.match = match;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param logic      逻辑符号
     */
    DirectLike(String tableAlias, String column, String value, Match match, Logic logic) {
        this.tableAlias = tableAlias;
        this.column = column;
        this.value = value;
        this.match = match;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     */
    DirectLike(Criteria<T> criteria, String column, String value, Match match, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.match = match;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @param logic  逻辑符号
     */
    DirectLike(String column, String value, Character escape, Logic logic) {
        this.column = column;
        this.value = value;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @param logic      逻辑符号
     */
    DirectLike(String tableAlias, String column, String value, Character escape, Logic logic) {
        this.tableAlias = tableAlias;
        this.column = column;
        this.value = value;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     */
    DirectLike(Criteria<T> criteria, String column, String value, Character escape, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param column 字段
     * @param value  值
     * @param match  匹配模式
     * @param escape 转义字符
     * @param logic  逻辑符号
     */
    DirectLike(String column, String value, Match match, Character escape, Logic logic) {
        this.column = column;
        this.value = value;
        this.match = match;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @param logic      逻辑符号
     */
    DirectLike(String tableAlias, String column, String value, Match match, Character escape, Logic logic) {
        this.tableAlias = tableAlias;
        this.column = column;
        this.value = value;
        this.match = match;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     */
    DirectLike(Criteria<T> criteria, String column, String value, Match match, Character escape, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.match = match;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.LIKE;
    }


    /**
     * 创建LIKE条件对象
     * @param column 字段
     * @param value  值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String column, String value) {
        return create(column, value, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String column, String value, Logic logic) {
        if (hasText(column)) {
            return new DirectLike<>(column, value, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param column 字段
     * @param value  值
     * @param match  匹配模式
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String column, String value, Match match) {
        return create(column, value, match, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param column 字段
     * @param value  值
     * @param match  匹配模式
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String column, String value, Match match, Logic logic) {
        if (hasText(column)) {
            return new DirectLike<>(column, value, match, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String column, String value, Character escape) {
        return create(column, value, escape, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String column, String value, Character escape, Logic logic) {
        if (hasText(column)) {
            return new DirectLike<>(column, value, escape, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param column 字段
     * @param value  值
     * @param match  匹配模式
     * @param escape 转义字符
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String column, String value, Match match, Character escape) {
        return create(column, value, match, escape, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param column 字段
     * @param value  值
     * @param match  匹配模式
     * @param escape 转义字符
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String column, String value, Match match,
                                           Character escape, Logic logic) {
        if (hasText(column)) {
            return new DirectLike<>(column, value, match, escape, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String tableAlias, String column, String value) {
        return create(tableAlias, column, value, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String tableAlias, String column, String value, Logic logic) {
        if (hasText(column)) {
            return new DirectLike<>(tableAlias, column, value, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String tableAlias, String column, String value, Match match) {
        return create(tableAlias, column, value, match, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String tableAlias, String column, String value,
                                           Match match, Logic logic) {
        if (hasText(column)) {
            return new DirectLike<>(tableAlias, column, value, match, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String tableAlias, String column, String value,
                                           Character escape) {
        return create(tableAlias, column, value, escape, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String tableAlias, String column, String value,
                                           Character escape, Logic logic) {
        if (hasText(column)) {
            return new DirectLike<>(tableAlias, column, value, escape, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String tableAlias, String column, String value,
                                           Match match, Character escape) {
        return create(tableAlias, column, value, match, escape, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(String tableAlias, String column, String value,
                                           Match match, Character escape, Logic logic) {
        if (hasText(column)) {
            return new DirectLike<>(tableAlias, column, value, match, escape, logic);
        }
        return null;
    }


    /**
     * 创建LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(Criteria<T> criteria, String column, String value) {
        return create(criteria, column, value, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(Criteria<T> criteria, String column, String value, Logic logic) {
        if (criteria != null && hasText(column)) {
            return new DirectLike<>(criteria, column, value, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(Criteria<T> criteria, String column, String value, Match match) {
        return create(criteria, column, value, match, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(Criteria<T> criteria, String column, String value,
                                           Match match, Logic logic) {
        if (criteria != null && hasText(column)) {
            return new DirectLike<>(criteria, column, value, match, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(Criteria<T> criteria, String column, String value,
                                           Character escape) {
        return create(criteria, column, value, escape, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(Criteria<T> criteria, String column, String value,
                                           Character escape, Logic logic) {
        if (criteria != null && hasText(column)) {
            return new DirectLike<>(criteria, column, value, escape, logic);
        }
        return null;
    }

    /**
     * 创建LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(Criteria<T> criteria, String column, String value,
                                           Match match, Character escape) {
        return create(criteria, column, value, match, escape, Logic.AND);
    }

    /**
     * 创建LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> create(Criteria<T> criteria, String column, String value,
                                           Match match, Character escape, Logic logic) {
        if (criteria != null && hasText(column)) {
            return new DirectLike<>(criteria, column, value, match, escape, logic);
        }
        return null;
    }
}
