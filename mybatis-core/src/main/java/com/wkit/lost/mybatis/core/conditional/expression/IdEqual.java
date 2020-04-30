package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 主键等于条件
 * @author wvkity
 */
public class IdEqual extends ColumnExpressionWrapper {

    private static final long serialVersionUID = 529013059984458176L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     */
    IdEqual(Criteria<?> criteria, ColumnWrapper column, Object value, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.logic = logic;
    }

    /**
     * 创建条件构建器
     * @return 构建器
     */
    public static IdEqual.Builder create() {
        return new IdEqual.Builder();
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
         * 值
         */
        private Object value;
        /**
         * 逻辑符号
         */
        private Logic logic;

        /**
         * 构建条件对象
         * @return 条件对象
         */
        public IdEqual build() {
            if (this.value != null) {
                ColumnWrapper wrapper = loadIdColumn(criteria.getEntityClass());
                if (wrapper != null) {
                    return new IdEqual(this.criteria, wrapper, this.value, this.logic);
                }
            }
            return null;
        }
    }
}
