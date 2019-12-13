package com.wkit.lost.mybatis.spring.boot.registry;

import com.wkit.lost.mybatis.utils.Ascii;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.core.annotation.AnnotationAttributes;

import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractBeanDefinitionRegistry implements BeanFactoryAware {

    protected static final ScopeMetadataResolver METADATA_RESOLVER = new AnnotationScopeMetadataResolver();
    protected static final BeanNameGenerator BEAN_NAME_GENERATOR = new AnnotationBeanNameGenerator();
    protected DefaultListableBeanFactory beanFactory;

    /**
     * 注册Bean
     * @param registry       注册对象
     * @param beanClass      Bean类型
     * @param constructorArg 构造方法参数对象
     * @param primary        是否首选
     */
    protected void registerBean( BeanDefinitionRegistry registry, Class<?> beanClass,
                                 ConstructorArgumentValues constructorArg, boolean primary ) {
        registerBean( registry, beanClass, constructorArg, primary, null );
    }

    /**
     * 注册Bean
     * @param registry       注册对象
     * @param beanClass      Bean类型
     * @param constructorArg 构造方法参数对象
     * @param primary        是否首选
     * @param beanName       beanName
     */
    protected void registerBean( BeanDefinitionRegistry registry, Class<?> beanClass,
                                 ConstructorArgumentValues constructorArg, boolean primary, String beanName ) {
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition( beanClass );
        ScopeMetadata scopeMetadata = METADATA_RESOLVER.resolveScopeMetadata( beanDefinition );
        beanDefinition.setScope( scopeMetadata.getScopeName() );
        beanDefinition.setPrimary( primary );
        beanDefinition.setSynthetic( true );
        beanDefinition.setAutowireCandidate( true );
        String realBeanName = getBeanName( beanName, beanDefinition, registry );
        Optional.ofNullable( constructorArg ).ifPresent( beanDefinition::setConstructorArgumentValues );
        AnnotationConfigUtils.processCommonDefinitionAnnotations( beanDefinition );
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder( beanDefinition, realBeanName );
        BeanDefinitionReaderUtils.registerBeanDefinition( definitionHolder, registry );
    }

    /**
     * 获取beanName
     * @param beanName       beanName
     * @param beanDefinition bean定义信息
     * @param registry       注册对象
     * @return beanName
     */
    protected String getBeanName( String beanName, BeanDefinition beanDefinition, BeanDefinitionRegistry registry ) {
        return Optional.ofNullable( beanName ).filter( Ascii::hasText )
                .orElseGet( () -> BEAN_NAME_GENERATOR.generateBeanName( beanDefinition, registry ) );
    }

    /**
     * 获取注解值
     * @param attributes 注解属性对象
     * @param property   属性
     * @param other      默认值
     * @param <U>        返回值类型
     * @return 值
     */
    protected <U> U getValue( final AnnotationAttributes attributes, String property, U other ) {
        return getValue( Optional.ofNullable( attributes ),
                attribute -> attribute.getOrDefault( property, other ), other );
    }

    /**
     * 获取值
     * @param optional     Optional对象
     * @param function     Lambda Function对象
     * @param defaultValue 默认值
     * @param <T>          泛型类型
     * @param <U>          返回值类型
     * @return 值
     */
    @SuppressWarnings( { "unchecked" } )
    protected <T, U> U getValue( final Optional<T> optional, Function<T, Object> function, U defaultValue ) {
        if ( optional.isPresent() ) {
            Object value = function.apply( optional.get() );
            if ( value != null ) {
                if ( value instanceof String && !Ascii.hasText( value.toString() ) ) {
                    return defaultValue;
                }
                return ( U ) value;
            }
        }
        return defaultValue;
    }

    /**
     * 根据beanName和类型获取容器中的bean
     * @param beanClass bean类型
     * @param beanName beanName
     * @param <T> 泛型类型
     * @return 指定类型的Bean对象
     */
    protected <T> T getBean( Class<T> beanClass, String beanName ) {
        // 检查Bean容器中是否存在配置实例
        if ( beanFactory != null && beanFactory.getBeanNamesForType( beanClass, false, 
                false ).length > 0 ) {
            // 从指定的名称获取bean
            if ( beanFactory.containsBeanDefinition( beanName ) ) {
                try {
                    return beanFactory.getBean( beanName, beanClass );
                } catch ( Exception e ) {
                    // ignore
                }
            }
            // 从指定类型获取bean
            return beanFactory.getBean( beanClass );
        }
        return null;
    }

    @Override
    public void setBeanFactory( BeanFactory beanFactory ) throws BeansException {
        this.beanFactory = ( DefaultListableBeanFactory ) beanFactory;
    }
}
