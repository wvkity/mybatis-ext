package com.wkit.lost.mybatis.core.condition;

public interface ConditionWrapper<T, R, Context> extends Condition<T>, ConditionBuilder<T, Context, R> {
}
