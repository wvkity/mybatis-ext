package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Case选择查询
 * @author wvkity
 */
public class CaseQuery extends AbstractQueryWrapper<Case> {

    private static final long serialVersionUID = 871576175266620060L;

    /**
     * 构造方法
     * @param column {@link Case}
     */
    public CaseQuery(Case column) {
        this.column = column;
        this.columnAlias = column.as();
    }

    @Override
    public String getSegment(boolean applyQuery) {
        return applyQuery ? this.column.getSegment() : this.column.getConditionSegment();
    }

    @Override
    public String columnName() {
        return null;
    }

    @Override
    public String getSegment() {
        return getSegment(true);
    }

    /**
     * 单个Case选择查询
     */
    public static final class Single {

        private Single() {
        }

        /**
         * Case选择查询
         * @param _case {@link Case}
         * @return {@link CaseQuery}
         */
        public static CaseQuery query(Case _case) {
            if (_case != null) {
                return new CaseQuery(_case);
            }
            return null;
        }
    }

    /**
     * 多个Case选择查询
     */
    public static final class Multi {
        private Multi() {
        }

        /**
         * 多个Case选择查询
         * @param cases {@link Case}数组
         * @return {@link CaseQuery}集合
         */
        public static List<CaseQuery> queries(Case... cases) {
            return queries(ArrayUtil.toList(cases));
        }

        /**
         * 多个Case选择查询
         * @param cases {@link Case}集合
         * @return {@link CaseQuery}集合
         */
        public static List<CaseQuery> queries(Collection<Case> cases) {
            if (CollectionUtil.hasElement(cases)) {
                return cases.stream().filter(Objects::nonNull).map(CaseQuery.Single::query)
                        .collect(Collectors.toList());
            }
            return new ArrayList<>(0);
        }
    }
}
