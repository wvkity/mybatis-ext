package com.wkit.lost.mybatis.spring.annotation;

import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.spring.mapper.ClassPathMapperScanner;
import com.wkit.lost.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar,
        ResourceLoaderAware,
        EnvironmentAware {

    private Environment environment;

    private ResourceLoader resourceLoader;

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerBeanDefinitions( @NonNull AnnotationMetadata importingClassMetadata,
                                         @NonNull BeanDefinitionRegistry registry ) {
        AnnotationAttributes mapperScanAttributes = AnnotationAttributes
                .fromMap( importingClassMetadata.getAnnotationAttributes( MapperScan.class.getName() ) );
        if ( mapperScanAttributes != null ) {
            registerBeanDefinitions( mapperScanAttributes, registry );
        }
    }

    /**
     * 注册bean定义信息
     * @param annotationAttributes 注解属性
     * @param registry             bean定义注册器
     */
    void registerBeanDefinitions( @NonNull AnnotationAttributes annotationAttributes,
                                  @NonNull BeanDefinitionRegistry registry ) {
        ClassPathMapperScanner scanner = new ClassPathMapperScanner( registry );
        Optional.ofNullable( this.resourceLoader ).ifPresent( scanner::setResourceLoader );

        Class<? extends Annotation> annotationClass = annotationAttributes.getClass( "annotationClass" );
        if ( !Annotation.class.equals( annotationClass ) ) {
            scanner.setAnnotationClass( annotationClass );
        }

        Class<?> markerInterfaceClass = annotationAttributes.getClass( "markerInterface" );
        if ( !Class.class.equals( markerInterfaceClass ) ) {
            scanner.setMarkerInterface( markerInterfaceClass );
        }

        Class<? extends BeanNameGenerator> generatorClass = annotationAttributes.getClass( "nameGenerator" );
        if ( !BeanNameGenerator.class.equals( generatorClass ) ) {
            scanner.setBeanNameGenerator( BeanUtils.instantiateClass( generatorClass ) );
        }

        Class<? extends MapperFactoryBean> mapperFactoryBeanClass = annotationAttributes.getClass( "factoryBean" );
        if ( !MapperFactoryBean.class.equals( mapperFactoryBeanClass ) ) {
            scanner.setMapperFactoryBeanClass( mapperFactoryBeanClass );
        }

        scanner.setSqlSessionTemplateBeanName( annotationAttributes.getString( "sqlSessionTemplateRef" ) );
        scanner.setSqlSessionFactoryBeanName( annotationAttributes.getString( "sqlSessionFactoryRef" ) );

        List<String> basePackages = new ArrayList<>();
        basePackages.addAll( Arrays.stream( annotationAttributes.getStringArray( "value" ) )
                .filter( StringUtil::hasText ).collect( Collectors.toList() ) );
        basePackages.addAll( Arrays.stream( annotationAttributes.getStringArray( "basePackages" ) )
                .filter( StringUtil::hasText ).collect( Collectors.toList() ) );
        basePackages.addAll( Arrays.stream( annotationAttributes.getClassArray( "basePackageClasses" ) )
                .map( ClassUtils::getPackageName ).collect( Collectors.toList() ) );
        // 扩展处理泛型Mapper映射
        String mapperProcessorRef = annotationAttributes.getString( "mapperProcessorRef" );
        if ( StringUtil.hasText( mapperProcessorRef ) ) {
            scanner.setMapperProcessorBeanName( mapperProcessorRef );
        }
        scanner.registerFilters();
        scanner.doScan( StringUtils.toStringArray( basePackages ) );
    }

    /**
     * {@link MapperScannerRegistrar} for {@link MapperScans}
     */
    static class RepeatingScannerRegistrar extends MapperScannerRegistrar {

        /**
         * {@inheritDoc}
         */
        @Override
        public void registerBeanDefinitions( AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry ) {
            AnnotationAttributes mapperScansAttributes = AnnotationAttributes
                    .fromMap( importingClassMetadata.getAnnotationAttributes( MapperScans.class.getName() ) );
            if ( mapperScansAttributes != null ) {
                Arrays.stream( mapperScansAttributes.getAnnotationArray( "value" ) )
                        .forEach( mapperScanAttributes -> registerBeanDefinitions( mapperScanAttributes, registry ) );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnvironment( @Nullable Environment environment ) {
        this.environment = environment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResourceLoader( @Nullable ResourceLoader resourceLoader ) {
        this.resourceLoader = resourceLoader;
    }
}
