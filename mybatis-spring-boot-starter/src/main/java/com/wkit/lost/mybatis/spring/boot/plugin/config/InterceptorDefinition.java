package com.wkit.lost.mybatis.spring.boot.plugin.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.plugin.Interceptor;

import java.util.Objects;

/**
 * 拦截器定义信息
 * @author wvkity
 */
@Getter
@AllArgsConstructor
@ToString
public class InterceptorDefinition {

    /**
     * 拦截器类
     */
    private Class<? extends Interceptor> interceptor;

    /**
     * bean名称
     */
    private String beanName;

    /**
     * 顺序
     */
    private int order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterceptorDefinition)) return false;
        InterceptorDefinition that = (InterceptorDefinition) o;
        return Objects.equals(interceptor, that.interceptor) &&
                Objects.equals(beanName, that.beanName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interceptor, beanName);
    }
}
