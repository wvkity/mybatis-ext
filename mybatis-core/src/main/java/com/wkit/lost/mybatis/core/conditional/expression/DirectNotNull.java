package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * IS NOT NULL条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class DirectNotNull<T> extends AbstractDirectEmptyExpression<T> {

    private static final long serialVersionUID = 1242960702230360913L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param logic      逻辑符号
     */
    DirectNotNull(Criteria<T> criteria, String tableAlias, String column, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.symbol = Symbol.NOT_NULL;
        this.logic = logic;
    }

    /**
     * 创建条件构建器
     * @param <T> 实体类型
     * @return 构建器
     */
    public static <T> DirectNotNull.Builder<T> create() {
        return new DirectNotNull.Builder<>();
    }

    /**
     * 条件构建器
     * @param <T> 实体类型
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder<T> {
        /**
         * 条件包装对象
         */
        private Criteria<T> criteria;
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
        public DirectNotNull<T> build() {
            if (isEmpty(this.column)) {
                return null;
            }
            return new DirectNotNull<>(this.criteria, this.alias, this.column, this.logic);
        }
    }
}
