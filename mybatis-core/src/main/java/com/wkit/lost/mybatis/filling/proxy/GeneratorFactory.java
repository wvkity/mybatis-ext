package com.wkit.lost.mybatis.filling.proxy;

import com.wkit.lost.mybatis.filling.gen.AbstractGenerator;
import com.wkit.lost.mybatis.filling.gen.GeneratedValue;

import java.util.Optional;

/**
 * 生成器工厂类
 * @author wvkity
 */
public class GeneratorFactory {

    /**
     * 构建值
     * @param clazz 指定类型
     * @return 值
     */
    public static Object build( Class<? extends AbstractGenerator> clazz ) {
        return getProxy( clazz ).map( GeneratedValue::getValue ).orElse( null );
    }

    private static Optional<GeneratedValue> getProxy( Class<? extends AbstractGenerator> clazz ) {
        return Optional.ofNullable( instance( clazz ) ).map( value -> new GeneratorJdkProxy( value ).getProxy() );
    }

    private static <T> T instance( Class<T> clazz ) {
        if ( clazz != null ) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch ( Exception e ) {
                // ignore
            }
        }
        return null;
    }
}
