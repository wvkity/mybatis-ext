package com.wkit.lost.mybatis.spring.boot.plugin;

import com.wkit.lost.mybatis.config.Plugin;
import com.wkit.lost.mybatis.plugins.paging.config.PluginConvert;
import com.wkit.lost.mybatis.plugins.locking.OptimisticLockingInterceptor;
import com.wkit.lost.mybatis.plugins.paging.PageableInterceptor;
import com.wkit.lost.mybatis.spring.boot.registry.AbstractBeanDefinitionRegistry;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 注册系统提供的插件
 * <ul>
 *     <li>自动填充值插件: {@link com.wkit.lost.mybatis.plugins.data.auditing.MetadataAuditingInterceptor}</li>
 *     <li>乐观锁插件: {@link OptimisticLockingInterceptor}</li>
 *     <li>常规分页插件: {@link PageableInterceptor}</li>
 *     <li>范围查询插件: {@link com.wkit.lost.mybatis.plugins.paging.RangePageableInterceptor}</li>
 * </ul>
 */
public class MyBatisInterceptorRegistrar extends AbstractBeanDefinitionRegistry
        implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions( AnnotationMetadata metadata, BeanDefinitionRegistry registry ) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes( EnableInterceptors.class.getName() ) );
        boolean primary = getValue( attributes, "primary", false );
        Plugin[] includes = getValue( attributes, "include", new Plugin[ 0 ] );
        Plugin[] excludes = getValue( attributes, "exclude", new Plugin[ 0 ] );
        if ( !ArrayUtil.isEmpty( includes ) ) {
            Set<Plugin> ignores = new HashSet<>( ArrayUtil.toList( excludes ) );
            for ( Plugin plugin : new LinkedHashSet<>( ArrayUtil.toList( includes ) ) ) {
                if ( ignores.isEmpty() || !ignores.contains( plugin ) ) {
                    Class<?> beanClass = PluginConvert.getInterceptorClass( plugin );
                    if ( beanClass != null ) {
                        registerBean( registry, beanClass, null, primary );
                    }
                }
            }
        }
    }
}
