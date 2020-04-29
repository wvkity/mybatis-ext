package com.wkit.lost.mybatis.core.wrapper.criteria;


import com.wkit.lost.mybatis.core.constant.Join;
import com.wkit.lost.mybatis.core.wrapper.basic.AbstractQueryWrapper;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 子查询连表包装条件类
 * @param <T> 实体类型
 * @author wvkity
 */
public class ForeignSubCriteria<T> extends ForeignCriteria<T> {

    private static final long serialVersionUID = 5741374317463942878L;

    /**
     * 子查询条件包装对象
     */
    @Getter(AccessLevel.MODULE)
    private final SubCriteria<T> subCriteria;

    /**
     * 构造方法
     * @param sc     子查询条件包装对象
     * @param master 主表条件包装对象
     * @param join   连接模式
     * @param <E>    实体类型
     */
    public <E> ForeignSubCriteria(SubCriteria<T> sc, AbstractQueryCriteriaWrapper<E> master, Join join) {
        super(null, master, join);
        this.subCriteria = sc;
    }

    /**
     * 构造方法
     * @param sc     子查询条件包装对象
     * @param master 主表条件包装对象
     * @param join   连接模式
     * @param alias  别名
     * @param <E>    实体类型
     */
    public <E> ForeignSubCriteria(SubCriteria<T> sc, AbstractQueryCriteriaWrapper<E> master,
                                  Join join, String alias) {
        super(null, master, join, alias);
        this.subCriteria = sc;
    }

    @Override
    protected ForeignSubCriteria<T> newInstance() {
        ForeignSubCriteria<T> instance = new ForeignSubCriteria<>(this.subCriteria, this.master, this.join);
        copy(instance, this);
        return instance;
    }

    @Override
    protected Set<String> getQueryColumns() {
        if (this.fetch) {
            Set<String> result = new LinkedHashSet<>();
            List<AbstractQueryWrapper<?, ?>> queries;
            if (this.queryManager.hasQueries()) {
                queries = this.queryManager.getQueries();
            } else {
                queries = Optional.ofNullable(this.subCriteria).map(it -> it.queryManager.getQueries()).orElse(null);
            }
            Optional.ofNullable(queries).ifPresent(it -> {
                for (AbstractQueryWrapper<?, ?> temp : it) {
                    if (StringUtil.hasText(temp.alias())) {
                        result.add(temp.alias());
                    } else if (StringUtil.hasText(temp.columnName())) {
                        result.add(temp.columnName());
                    }
                }
            });
            if (CollectionUtil.hasElement(this.FOREIGN_CRITERIA_SET)) {
                for (ForeignCriteria<?> foreign : this.FOREIGN_CRITERIA_SET) {
                    Set<String> tempQueries = foreign.getQueryColumns();
                    if (CollectionUtil.hasElement(tempQueries)) {
                        result.addAll(tempQueries);
                    }
                }
            }
            return result;
        }
        return super.getQueryColumns();
    }
}
