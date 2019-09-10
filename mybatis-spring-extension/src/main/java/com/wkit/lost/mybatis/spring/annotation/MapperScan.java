package com.wkit.lost.mybatis.spring.annotation;

import com.wkit.lost.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MyBatis接口扫描注解
 * @author DT
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Inherited
@Import( { MapperScannerRegistrar.class } )
@Repeatable( MapperScans.class )
public @interface MapperScan {

    /**
     * basePackages别名{@link #basePackages()}
     * @return 包名列表
     */
    String[] value() default {};

    /**
     * 接口基础包
     * @return 包名列表
     */
    String[] basePackages() default {};

    /**
     * 基础包类
     * @return 包类列表
     */
    Class<?>[] basePackageClasses() default {};

    /**
     * Bean命名生成器
     * @return 生成器
     */
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    /**
     * 指定注解类
     * @return 指定的接口注解类
     */
    Class<? extends Annotation> annotationClass() default Annotation.class;

    /**
     * 指定基础接口
     * @return 接口
     */
    Class<?> markerInterface() default Class.class;

    /**
     * 指定使用具体的{@code SqlSessionFactory}
     * @return bean名为 {@code SqlSessionFactory}
     */
    String sqlSessionFactoryRef() default "";

    /**
     * 指定使用具体的{@code SqlSessionTemplate}
     * @return bean名为 {@code SqlSessionTemplate}
     */
    String sqlSessionTemplateRef() default "";

    /**
     * 指定工厂类
     * @return 工厂类
     */
    Class<? extends MapperFactoryBean> factoryBean() default MapperFactoryBean.class;

    /**
     * mapper接口映射处理引用类
     * @return 处理类
     */
    String mapperProcessorRef() default "";
}
