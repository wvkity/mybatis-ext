package com.wvkity.mybatis.core.metadata;

import com.wvkity.mybatis.annotation.naming.NamingStrategy;
import com.wvkity.mybatis.config.MyBatisCustomConfiguration;
import com.wvkity.mybatis.core.naming.DefaultPhysicalNamingStrategy;
import com.wvkity.mybatis.core.naming.PhysicalNamingStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
abstract class BuilderSupport {

    /**
     * 默认命名策略
     */
    protected static final PhysicalNamingStrategy DEFAULT_PHYSICAL_NAMING_STRATEGY = new DefaultPhysicalNamingStrategy();

    /**
     * 自定义配置
     */
    protected MyBatisCustomConfiguration configuration;

    /**
     * 命名策略
     */
    protected NamingStrategy namingStrategy;

    /**
     * 命名处理器
     */
    protected PhysicalNamingStrategy physicalNamingStrategy;

    /**
     * 获取命名策略模式(小写/大写/其他)
     * @return 命名策略模式
     */
    protected int namingMode() {
        return Optional.ofNullable(this.namingStrategy).map(it -> {
            String namingType = it.name().toUpperCase();
            return namingType.contains("LOWERCASE") ? 0 : namingType.contains("UPPERCASE") ? 1 : 2;
        }).orElse(2);
    }

    /**
     * 数据库表名转换
     * @param tableName 数据库表名
     * @return 新数据库表名
     */
    protected String tableNameTransform(String tableName) {
        return this.physicalNamingStrategy.tableName(tableName, this.namingStrategy);
    }

    /**
     * 数据库表字段名转换
     * @param columnName 数据库表字段名
     * @return 新数据库表字段名
     */
    protected String columnNameTransform(String columnName) {
        return this.physicalNamingStrategy.columnName(columnName, this.namingStrategy);
    }

    /**
     * 设置值
     * @param consumer 消费Lambda
     * @param value    值
     * @param supplier 默认供给Lambda
     * @param <T>      泛型值类型
     */
    protected <T> void ifPresent(Consumer<T> consumer, T value, Supplier<T> supplier) {
        ifPresent(consumer, value, supplier.get());
    }

    /**
     * 设置值
     * @param consumer     消费Lambda
     * @param value        值
     * @param defaultValue 默认值
     * @param <T>          泛型值类型
     */
    protected <T> void ifPresent(Consumer<T> consumer, T value, T defaultValue) {
        final T realValue = value == null ? defaultValue : value;
        consumer.accept(realValue);
    }

    /**
     * 设置自定义配置对象
     * @param configuration 自定义配置对象
     * @return {@code this}
     */
    public BuilderSupport configuration(MyBatisCustomConfiguration configuration) {
        this.configuration = configuration;
        if (this.configuration == null) {
            this.physicalNamingStrategy = DEFAULT_PHYSICAL_NAMING_STRATEGY;
        } else {
            this.physicalNamingStrategy = Optional.ofNullable(this.configuration.getPhysicalNamingStrategy())
                    .orElse(DEFAULT_PHYSICAL_NAMING_STRATEGY);
        }
        return this;
    }
}
