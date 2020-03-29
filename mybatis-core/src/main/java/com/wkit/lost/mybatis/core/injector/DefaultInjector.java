package com.wkit.lost.mybatis.core.injector;

import com.wkit.lost.mybatis.core.injector.execute.ListByCriteria;
import com.wkit.lost.mybatis.core.injector.method.Method;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 系统系统默认SQL注入器
 * @author wvkity
 */
public class DefaultInjector extends AbstractInjector {

    @Override
    public Collection<Method> getMethodsForInject( Class<?> mapperInterface ) {
        return Stream.of(
                new ListByCriteria()
        ).collect( Collectors.toList());
    }
}
