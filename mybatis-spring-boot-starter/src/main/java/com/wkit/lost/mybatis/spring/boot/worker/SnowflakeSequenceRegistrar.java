package com.wkit.lost.mybatis.spring.boot.worker;

import com.wkit.lost.mybatis.snowflake.sequence.Level;
import com.wkit.lost.mybatis.snowflake.sequence.Mode;
import com.wkit.lost.mybatis.snowflake.sequence.SnowflakeSequence;
import com.wkit.lost.mybatis.snowflake.worker.SequenceUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

/**
 * 注册{@link SnowflakeSequence}实例
 * <p>
 *     <ul>
 *         <li>优先从Bean容器中根据名称和类型匹配获取{@link SequenceAutoConfiguration}实例，
 *         如果获取到的对象是空，则从配置文件获取配置实例，此时还是空的话直接new一个实例</li>
 *         <li>注册到Bean容器的实例名称为sequence，同时还给该实例起了一个snowflakeSequence别名</li>
 *     </ul>
 * </p>
 * @author wvkity
 */
class SnowflakeSequenceRegistrar implements BeanFactoryAware, EnvironmentAware, ImportBeanDefinitionRegistrar {

    private SequenceAutoConfiguration configuration;
    private DefaultListableBeanFactory beanFactory;
    private Environment environment;

    @Override
    public void registerBeanDefinitions( AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry ) {
        checkConfiguration();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap( 
                importingClassMetadata.getAnnotationAttributes( EnableSnowflakeSequence.class.getName() ) );
        Level level = ( Level ) Optional.ofNullable( attributes ).map( attr -> 
                attr.getEnum( "level" ) ).orElse( Level.MILLISECOND );
        Mode mode = ( Mode ) Optional.ofNullable( attributes ).map( attr -> 
                attr.getEnum( "mode" ) ).orElse( Mode.SPECIFIED );
        boolean primary = Optional.ofNullable( attributes ).map( attr -> 
                attr.getBoolean( "primary" ) ).orElse( false );
        registerBean( registry, level, mode, primary );
    }

    private void checkConfiguration() {
        if ( this.configuration == null ) {
            // 检查Bean容器中是否存在配置实例
            if ( beanFactory.getBeanNamesForType( SequenceAutoConfiguration.class, false, false ).length > 0 ) {
                // 从指定的名称获取bean
                if ( beanFactory.containsBeanDefinition( "sequenceAutoConfiguration" ) ) {
                    try {
                        this.configuration = beanFactory.getBean( "sequenceAutoConfiguration", SequenceAutoConfiguration.class );
                    } catch ( Exception e ) {
                        // ignore
                    }
                }
                // 从指定类型获取bean
                if ( this.configuration == null ) {
                    this.configuration = beanFactory.getBean( SequenceAutoConfiguration.class );
                }
            }
            if ( this.configuration == null ) {
                this.configuration = getConfiguration();
                if ( this.configuration == null ) {
                    this.configuration = new SequenceAutoConfiguration();
                }
            }
        }
    }

    private void registerBean( BeanDefinitionRegistry registry, Level level, Mode mode, boolean primary ) {
        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition( SnowflakeSequence.class );
        GenericBeanDefinition definition = ( GenericBeanDefinition ) definitionBuilder.getRawBeanDefinition();
        definition.setBeanClass( SnowflakeSequence.class );
        definition.setSynthetic( true );
        definition.setScope( SCOPE_SINGLETON );
        definition.setPrimary( primary );
        definition.setAutowireCandidate( true );
        definition.setAutowireMode( AbstractBeanDefinition.AUTOWIRE_BY_TYPE );
        // 采用构造方法注入
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        // 启用秒级
        if ( configuration.getLevel() == Level.SECOND || level == Level.SECOND ) {
            argumentValues.addIndexedArgumentValue( 0, TimeUnit.SECONDS );
        } else {
            argumentValues.addIndexedArgumentValue( 0, TimeUnit.MILLISECONDS );
        }
        argumentValues.addIndexedArgumentValue( 1, configuration.getEpochTimestamp() );
        // 启用mac地址动态获取
        if ( configuration.getMode() == Mode.MAC || mode == Mode.MAC ) {
            argumentValues.addIndexedArgumentValue( 2, SequenceUtil.getDefaultWorkerId( 5, 5 ) );
            argumentValues.addIndexedArgumentValue( 3, SequenceUtil.getDefaultDataCenterId( 5 ) );
        } else {
            argumentValues.addIndexedArgumentValue( 2, configuration.getWorkerId() );
            argumentValues.addIndexedArgumentValue( 3, configuration.getDataCenterId() );
        }
        definition.setConstructorArgumentValues( argumentValues );
        registry.registerBeanDefinition( "sequence", definition );
        registry.registerAlias( "sequence", "snowflakeSequence" );
    }

    @Override
    public void setBeanFactory( BeanFactory beanFactory ) throws BeansException {
        this.beanFactory = ( DefaultListableBeanFactory ) beanFactory;
    }

    @Override
    public void setEnvironment( Environment environment ) {
        this.environment = environment;
    }

    private SequenceAutoConfiguration getConfiguration() {
        try {
            Binder binder = Binder.get( environment );
            return binder.bind( "sequence", SequenceAutoConfiguration.class ).get();
        } catch ( Exception e ) {
            // ignore
            return null;
        }
    }
}
