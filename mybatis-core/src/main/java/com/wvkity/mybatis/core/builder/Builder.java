package com.wvkity.mybatis.core.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 构建器
 * @param <T> 泛型类型
 * @author wvkity
 */
public final class Builder<T> {

    /**
     * {@link Supplier}对象
     */
    private final Supplier<T> supplier;
    /**
     * {@link Consumer}对象集合
     */
    private final List<Consumer<T>> consumers;

    private Builder(Supplier<T> supplier) {
        this.supplier = supplier;
        this.consumers = new ArrayList<>();
    }

    /**
     * Consumer接口
     * @param <T> 泛型类型
     * @param <V> 值
     */
    @FunctionalInterface
    public interface SimpleConsumer<T, V> {
        void accept(final T t, final V v);
    }

    /**
     * 设置值
     * @param consumer {@link SimpleConsumer}
     * @param v        值
     * @param <V>      值类型
     * @return {@link Builder}
     */
    public <V> Builder<T> with(final SimpleConsumer<T, V> consumer, V v) {
        Optional.ofNullable(consumer).ifPresent(it ->
                this.consumers.add(instance -> consumer.accept(instance, v)));
        return this;
    }

    /**
     * 构建指定类型对象
     * @return 对象
     */
    public T build() {
        final T instance = this.supplier.get();
        if (!this.consumers.isEmpty()) {
            this.consumers.forEach(it -> it.accept(instance));
        }
        return instance;
    }

    /**
     * 构建指定类型对象，并清除{@link Consumer}参数
     * @return 对象
     */
    public T release() {
        final T it = build();
        this.clear();
        return it;
    }

    /**
     * 清除{@link Consumer}参数
     * @return 构建器
     */
    public Builder<T> clear() {
        if (!this.consumers.isEmpty()) {
            this.consumers.clear();
        }
        return this;
    }

    /**
     * 创建构建器
     * @param supplier {@link Supplier}
     * @param <T>      泛型类型
     * @return 构建器
     */
    public static <T> Builder<T> of(final Supplier<T> supplier) {
        return new Builder<>(supplier);
    }
}
