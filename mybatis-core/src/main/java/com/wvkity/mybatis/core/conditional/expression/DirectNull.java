package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * IS NULL条件
 * @author wvkity
 */
public class DirectNull extends AbstractDirectEmptyExpression {

    private static final long serialVersionUID = -4663604933639152009L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param logic      逻辑符号
     */
    DirectNull(Criteria<?> criteria, String tableAlias, String column, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.symbol = Symbol.NULL;
        this.logic = logic;
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static DirectNull.Builder create() {
        return new DirectNull.Builder();
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
        public DirectNull build() {
            if (isEmpty(this.column)) {
                return null;
            }
            return new DirectNull(this.criteria, this.alias, this.column, this.logic);
        }
    }
}
