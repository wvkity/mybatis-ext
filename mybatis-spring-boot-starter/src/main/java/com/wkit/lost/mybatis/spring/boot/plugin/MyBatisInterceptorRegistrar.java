package com.wkit.lost.mybatis.spring.boot.plugin;

import com.wkit.lost.mybatis.plugins.interceptor.LimitInterceptor;
import com.wkit.lost.mybatis.plugins.interceptor.MetaObjectFillingInterceptor;
import com.wkit.lost.mybatis.plugins.interceptor.OptimisticLockerInterceptor;
import com.wkit.lost.mybatis.plugins.interceptor.PageableInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

/**
 * 注册系统提供的插件
 * <ul>
 *     <li>自动填充值插件: {@link MetaObjectFillingInterceptor}</li>
 *     <li>乐观锁插件: {@link OptimisticLockerInterceptor}</li>
 *     <li>常规分页插件: {@link PageableInterceptor}</li>
 *     <li>范围查询插件: {@link LimitInterceptor}</li>
 * </ul>
 */
public class MyBatisInterceptorRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions( AnnotationMetadata metadata, BeanDefinitionRegistry registry ) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes( EnableInterceptors.class.getName() ) );
        boolean primary = Optional.ofNullable( attributes )
                .map( attr -> attr.getBoolean( "primary" ) ).orElse( true );
        boolean filling = Optional.ofNullable( attributes )
                .map( attr -> attr.getBoolean( "filling" ) ).orElse( true );
        boolean pageable = Optional.ofNullable( attributes )
                .map( attr -> attr.getBoolean( "pageable" ) ).orElse( true );
        boolean limit = Optional.ofNullable( attributes )
                .map( attr -> attr.getBoolean( "limit" ) ).orElse( true );
        boolean locker = Optional.ofNullable( attributes )
                .map( attr -> attr.getBoolean( "locker" ) ).orElse( true );
        registerBean( registry, primary, filling, pageable, limit, locker );
    }

    private void registerBean( BeanDefinitionRegistry registry, boolean primary, boolean filling,
                               boolean pageable, boolean limit, boolean locker ) {
        // 自动填充值
        if ( filling ) {
            registerBean( registry, primary, MetaObjectFillingInterceptor.class, "metaObjectFillingInterceptor" );
        }
        // 乐观锁
        if ( locker ) {
            registerBean( registry, primary, OptimisticLockerInterceptor.class, "optimisticLockerInterceptor" );
        }
        // 常规分页
        if( pageable ) {
            registerBean( registry, primary, PageableInterceptor.class, "pageableInterceptor" );
        }
        // Limit分页
        if ( limit ) {
            registerBean( registry, primary, LimitInterceptor.class, "limitInterceptor" );
        }
    }

    private <T extends Interceptor> void registerBean( BeanDefinitionRegistry registry, boolean primary, Class<T> targetClazz, String beanName ) {
        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition( targetClazz );
        GenericBeanDefinition definition = ( GenericBeanDefinition ) definitionBuilder.getRawBeanDefinition();
        definition.setBeanClass( targetClazz );
        definition.setSynthetic( true );
        definition.setScope( SCOPE_SINGLETON );
        definition.setPrimary( primary );
        definition.setAutowireCandidate( true );
        definition.setAutowireMode( AbstractBeanDefinition.AUTOWIRE_BY_TYPE );
        registry.registerBeanDefinition( beanName, definition );
    }

    @Override
    public void setBeanFactory( BeanFactory beanFactory ) throws BeansException {
        this.beanFactory = ( DefaultListableBeanFactory ) beanFactory;
    }
}
