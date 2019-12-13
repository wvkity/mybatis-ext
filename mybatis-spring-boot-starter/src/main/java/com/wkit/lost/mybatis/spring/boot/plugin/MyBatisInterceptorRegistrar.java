package com.wkit.lost.mybatis.spring.boot.plugin;

import com.wkit.lost.mybatis.plugins.interceptor.LimitInterceptor;
import com.wkit.lost.mybatis.plugins.interceptor.MetaObjectFillingInterceptor;
import com.wkit.lost.mybatis.plugins.interceptor.OptimisticLockerInterceptor;
import com.wkit.lost.mybatis.plugins.interceptor.PageableInterceptor;
import com.wkit.lost.mybatis.spring.boot.registry.AbstractBeanDefinitionRegistry;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注册系统提供的插件
 * <ul>
 *     <li>自动填充值插件: {@link MetaObjectFillingInterceptor}</li>
 *     <li>乐观锁插件: {@link OptimisticLockerInterceptor}</li>
 *     <li>常规分页插件: {@link PageableInterceptor}</li>
 *     <li>范围查询插件: {@link LimitInterceptor}</li>
 * </ul>
 */
public class MyBatisInterceptorRegistrar extends AbstractBeanDefinitionRegistry
        implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions( AnnotationMetadata metadata, BeanDefinitionRegistry registry ) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes( EnableInterceptors.class.getName() ) );
        boolean primary = getValue( attributes, "primary", true );
        boolean filling = getValue( attributes, "filling", true );
        boolean pageable = getValue( attributes, "pageable", true );
        boolean limit = getValue( attributes, "limit", true );
        boolean locker = getValue( attributes, "locker", true );
        registerBean( registry, primary, filling, pageable, limit, locker );
    }

    private void registerBean( BeanDefinitionRegistry registry, boolean primary, boolean filling,
                               boolean pageable, boolean limit, boolean locker ) {
        // 自动填充值
        if ( filling ) {
            registerBean( registry, MetaObjectFillingInterceptor.class, null, primary );
        }
        // 乐观锁
        if ( locker ) {
            registerBean( registry, OptimisticLockerInterceptor.class, null, primary );
        }
        // 常规分页
        if ( pageable ) {
            registerBean( registry, PageableInterceptor.class, null, primary );
        }
        // Limit分页
        if ( limit ) {
            registerBean( registry, LimitInterceptor.class, null, primary );
        }
    }
}
