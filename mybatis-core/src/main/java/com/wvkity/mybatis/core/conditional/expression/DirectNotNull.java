package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * IS NOT NULL条件
 * @author wvkity
 */
public class DirectNotNull extends AbstractDirectEmptyExpression {

    private static final long serialVersionUID = 1242960702230360913L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param logic      逻辑符号
     */
    DirectNotNull(Criteria<?> criteria, String tableAlias, String column, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.symbol = Symbol.NOT_NULL;
        this.logic = logic;
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static DirectNotNull.Builder create() {
        return new DirectNotNull.Builder();
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
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public DirectNotNull build() {
            if (isEmpty(this.column)) {
                return null;
            }
            return new DirectNotNull(this.criteria, this.alias, this.column, this.logic);
        }
    }
}
