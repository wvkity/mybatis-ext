package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.config.MyBatisConfigCache;
import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.handler.TableHandler;
import com.wvkity.mybatis.core.immutable.ImmutableLinkedSet;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.metadata.PropertyMappingCache;
import com.wvkity.mybatis.exception.MyBatisException;
import com.wvkity.mybatis.invoke.SerializedLambda;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.StringUtil;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 抽象条件包装器
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
@Log4j2
@SuppressWarnings({"serial"})
abstract class AbstractChainCriteriaWrapper<T, Chain extends AbstractChainCriteriaWrapper<T, Chain>> extends
        AbstractBasicCriteriaWrapper<T, Chain> {

    /**
     * 属性-字段包装对象缓存(只读)
     */
    private Map<String, ColumnWrapper> _PROPERTY_COLUMN_CACHE;

    /**
     * 可更新字段名缓存(小写)
     */
    private Set<String> _UPDATABLE_COLUMN_NAME_CACHE;

    /**
     * 映射缓存是否标识已初始化
     */
    private boolean initialized = false;

    @Override
    protected void inits() {
        super.inits();
        this.initMappings(this.entityClass);
    }

    @Override
    public ColumnWrapper searchColumn(Property<?, ?> property) {
        if (property == null) {
            return null;
        }
        return getColumn(PropertyMappingCache.parse(property));
    }

    @Override
    public ColumnWrapper searchColumn(String property) {
        return StringUtil.hasText(property) ? getColumn(property) : null;
    }

    @Override
    public <V> String convert(Property<T, V> property) {
        if (property == null) {
            return null;
        }
        return methodToProperty(PropertyMappingCache.parse(property).getImplMethodName());
    }

    @Override
    public <E, V> String methodToProperty(Property<E, V> property) {
        if (property == null) {
            return null;
        }
        return methodToProperty(PropertyMappingCache.parse(property).getImplMethodName());
    }

    /**
     * 获取字段名
     * @param lambda Lambda
     * @return 字段名
     */
    private ColumnWrapper getColumn(SerializedLambda lambda) {
        return getColumn(methodToProperty(lambda.getImplMethodName()));
    }

    /**
     * 获取字段名
     * @param property 属性名
     * @return 字段名
     */
    protected ColumnWrapper getColumn(String property) {
        if (!initialized) {
            initMappings(this.entityClass);
        }
        ColumnWrapper column = this._PROPERTY_COLUMN_CACHE.get(property);
        if (column == null) {
            if (MyBatisConfigCache.isNotMatchingWithThrows()) {
                throw new MyBatisException("The field mapping information for the entity class(" +
                        this.entityClass.getCanonicalName() + ") cannot be found based on the `" + property + "` " +
                        "attribute. Check to see if the attribute exists or is decorated using the @transient " +
                        "annotation.");
            } else {
                log.warn("The field mapping information for the entity class({}) cannot be found based on the `{}` " +
                        "attribute. Check to see if the attribute exists or is decorated using the @transient " +
                        "annotation.", this.entityClass.getCanonicalName(), property);
            }
        }
        return column;
    }

    /**
     * 根据方法名获取属性(getXX|isXX)
     * @param methodName 方法名
     * @return 属性名
     */
    private String methodToProperty(String methodName) {
        return PropertyMappingCache.methodToProperty(methodName);
    }

    /**
     * 初始化表映射
     * @param klass    实体类
     * @param printing 是否打印警告信息
     */
    protected final void initMappingCache(Class<?> klass, boolean printing) {
        if (klass != null && !this.initialized) {
            this._PROPERTY_COLUMN_CACHE = PropertyMappingCache.columnCache(klass);
            if (CollectionUtil.isEmpty(this._PROPERTY_COLUMN_CACHE)) {
                if (printing)
                    log.warn(("The corresponding table mapping information cannot be found in the cache according " +
                            "to the specified entity class name -- `[" + klass.getCanonicalName() + "]`, please check " +
                            "the configuration is correct!"));
            } else {
                this.initialized = true;
                Set<String> tmp = TableHandler.getTable(klass).updatableColumns().stream().map(it ->
                        it.getColumn().toLowerCase(Locale.ENGLISH)).collect(Collectors.toCollection(LinkedHashSet::new));
                this._UPDATABLE_COLUMN_NAME_CACHE = ImmutableLinkedSet.of(tmp);
            }
        }
    }

    /**
     * 初始化属性-字段包装对象缓存
     * @param klass 实体类
     */
    private void initMappings(Class<?> klass) {
        if (!initialized) {
            initMappingCache(klass, false);
            if (CollectionUtil.isEmpty(this._PROPERTY_COLUMN_CACHE)) {
                throw new MyBatisException("The table field mapping information for the specified " +
                        "entity ['" + klass.getCanonicalName() + "'] could not be found");
            }
        }
    }

    /**
     * 检查字段是否可更新
     * @param column 字段名
     * @return boolean
     */
    final boolean checkCanUpdatable(final String column) {
        if (StringUtil.isBlank(column)) {
            return false;
        }
        if (CollectionUtil.isEmpty(this._UPDATABLE_COLUMN_NAME_CACHE)) {
            return true;
        }
        final String realColumn = column.toLowerCase(Locale.ENGLISH);
        return Optional.ofNullable(this._UPDATABLE_COLUMN_NAME_CACHE).filter(it ->
                it.contains(realColumn)).isPresent();
    }

    /**
     * 检查是否存在包装对象
     * @return boolean
     */
    protected final boolean hasColumnWrapperCache() {
        return CollectionUtil.hasElement(this._PROPERTY_COLUMN_CACHE);
    }

    /**
     * 选择包装字段
     * @param predicate {@link Predicate}
     * @return 包装字段对象集合
     */
    protected final List<ColumnWrapper> filtration(Predicate<ColumnWrapper> predicate) {
        if (!this.initialized) {
            initMappingCache(this.entityClass, true);
        }
        TableHandler.getTable(this.entityClass);
        if (hasColumnWrapperCache()) {
            return this._PROPERTY_COLUMN_CACHE.values().stream().filter(predicate).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }
}
