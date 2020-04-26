package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 字段相等条件
 * @param <T>
 */
public class DirectNormalEqual<T> extends DirectExpressionWrapper<T> {

    private static final long serialVersionUID = 4647292574240862335L;

    /**
     * 其他条件包装对象
     */
    private final Criteria<?> otherCriteria;

    /**
     * 其他条件包装对象
     */
    private final ColumnWrapper otherColumnWrapper;

    /**
     * 其他表别名
     */
    private final String otherTableAlias;

    /**
     * 其他表字段
     */
    private final String otherColumn;

    /**
     * 构造方法
     * @param criteria           条件包装对象
     * @param tableAlias         表别名
     * @param column             字段
     * @param otherCriteria      其他条件包装对象
     * @param otherTableAlias    其他表别名
     * @param otherColumnWrapper 其他字段包装对象
     * @param otherColumn        其他字段
     * @param logic              逻辑符号
     */
    <E> DirectNormalEqual(Criteria<T> criteria, String tableAlias, String column, Criteria<E> otherCriteria,
                          String otherTableAlias, ColumnWrapper otherColumnWrapper, String otherColumn, Logic logic) {
        this.criteria = criteria;
        this.tableAlias = tableAlias;
        this.column = column;
        this.otherCriteria = otherCriteria;
        this.otherTableAlias = otherTableAlias;
        this.otherColumn = otherColumn;
        this.otherColumnWrapper = otherColumnWrapper;
        this.logic = logic;
        this.symbol = Symbol.EQ;
    }

    /**
     * 创建构建器
     * @param <T> 实体类型
     * @return 构建器
     */
    public static <T> DirectNormalEqual.Builder<T> create() {
        return new DirectNormalEqual.Builder<>();
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
        protected Criteria<T> criteria;
        /**
         * 其他包装对象
         */
        private Criteria<?> otherCriteria;
        /**
         * 其他字段包装对象
         */
        private ColumnWrapper otherColumnWrapper;
        /**
         * 其他表别名
         */
        private String otherTableAlias;
        /**
         * 其他字段
         */
        private String otherColumn;
        /**
         * 表别名
         */
        private String tableAlias;
        /**
         * 字段名
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
        public DirectNormalEqual<T> build() {
            if (isEmpty(this.column) ||
                    (this.otherColumnWrapper == null && isEmpty(this.otherColumn)) ||
                    (this.otherCriteria == null && isEmpty(this.otherTableAlias))) {
                return null;
            }
            return new DirectNormalEqual<>(this.criteria, this.tableAlias, this.column,
                    this.otherCriteria, this.otherTableAlias, this.otherColumnWrapper, this.otherColumn, this.logic);
        }
    }
}
