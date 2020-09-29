package com.wvkity.mybatis.code.make.builder;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 实体构建器
 * @param <T> 泛型类型
 * @author wvkity
 */
@AllArgsConstructor
public final class Builder<T> {

    @FunctionalInterface
    public interface SimpleConsumer<T, V> {
        void accept(final T t, final V v);
    }

    private final Supplier<T> supplier;
    private final List<Consumer<T>> consumers = new ArrayList<>();

    public <V> Builder<T> with(final SimpleConsumer<T, V> consumer, final V value) {
        this.consumers.add(it -> consumer.accept(it, value));
        return this;
    }

    /**
     * 构建对象
     * @return {@link Supplier}
     */
    public T build() {
        final T instance = this.supplier.get();
        if (!this.consumers.isEmpty()) {
            this.consumers.forEach(it -> it.accept(instance));
        }
        return instance;
    }

    /**
     * 构建对象(清除{@link Consumer})
     * @return {@link Supplier}
     */
    public T release() {
        final T instance = build();
        this.clear();
        return instance;
    }

    /**
     * 清除{@link Consumer}
     * @return {@link Builder}
     */
    public Builder<T> clear() {
        this.consumers.clear();
        return this;
    }

    public static <T> Builder<T> of(final Supplier<T> supplier) {
        return new Builder<>(supplier);
    }
}
