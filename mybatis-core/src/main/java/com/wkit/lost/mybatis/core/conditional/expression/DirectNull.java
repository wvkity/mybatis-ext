package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * IS NULL条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class DirectNull<T> extends AbstractDirectEmptyExpression<T> {

    private static final long serialVersionUID = -4663604933639152009L;

    /**
     * 构造方法
     * @param criteria   条件包装对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param logic      逻辑符号
     */
    DirectNull(Criteria<T> criteria, String tableAlias, String column, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.symbol = Symbol.NULL;
        this.logic = logic;
    }

    /**
     * 创建条件构建器
     * @param <T> 实体类型
     * @return 构建器
     */
    public static <T> DirectNull.Builder<T> create() {
        return new DirectNull.Builder<>();
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
        public DirectNull<T> build() {
            if (isEmpty(this.column)) {
                return null;
            }
            return new DirectNull<>(this.criteria, this.alias, this.column, this.logic);
        }
    }
}
