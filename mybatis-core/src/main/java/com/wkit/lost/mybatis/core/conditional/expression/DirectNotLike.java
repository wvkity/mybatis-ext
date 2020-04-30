package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Match;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * NOT LIKE条件
 * @author wvkity
 */
public class DirectNotLike extends AbstractDirectFuzzyExpression {

    private static final long serialVersionUID = 2951096914408350075L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @param logic      逻辑符号
     */
    DirectNotLike(Criteria<?> criteria, String tableAlias, String column, String value,
                  Match match, Character escape, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.value = value;
        this.match = match;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.NOT_LIKE;
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static DirectNotLike.Builder create() {
        return new DirectNotLike.Builder();
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
         * 值
         */
        private String value;
        /**
         * 匹配模式
         */
        private Match match = Match.ANYWHERE;
        /**
         * 转义字符
         */
        private Character escape;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public DirectNotLike build() {
            if (isEmpty(this.column) || this.value == null) {
                return null;
            }
            return new DirectNotLike(this.criteria, this.alias, this.column, this.value,
                    this.match, this.escape, this.logic);
        }
    }
}
