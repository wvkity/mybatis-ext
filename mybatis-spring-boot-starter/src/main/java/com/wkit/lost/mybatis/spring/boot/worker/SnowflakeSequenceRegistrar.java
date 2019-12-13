package com.wkit.lost.mybatis.spring.boot.worker;

import com.wkit.lost.mybatis.snowflake.sequence.Level;
import com.wkit.lost.mybatis.snowflake.sequence.Mode;
import com.wkit.lost.mybatis.snowflake.sequence.SnowflakeSequence;
import com.wkit.lost.mybatis.snowflake.worker.SequenceUtil;
import com.wkit.lost.mybatis.spring.boot.registry.AbstractBeanDefinitionRegistry;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.concurrent.TimeUnit;

/**
 * 注册{@link SnowflakeSequence}实例
 * <p>
 *     <ul>
 *         <li>优先从Bean容器中根据名称和类型匹配获取{@link SequenceAutoConfiguration}实例，
 *         如果获取到的对象是空，则从配置文件获取配置实例，此时还是空的话直接new一个实例</li>
 *     </ul>
 * </p>
 * @author wvkity
 */
class SnowflakeSequenceRegistrar extends AbstractBeanDefinitionRegistry implements
        EnvironmentAware, ImportBeanDefinitionRegistrar {

    private SequenceAutoConfiguration configuration;
    private Environment environment;
    private static final Class<SnowflakeSequence> REGISTER_BEAN_CLASS = SnowflakeSequence.class;
    private static final Class<SequenceAutoConfiguration> REGISTER_CONFIG_BEAN_CLASS = SequenceAutoConfiguration.class;
    private static final String REGISTER_CONFIG_BEAN_NAME = "sequenceAutoConfiguration";
    private static final String REGISTER_CONFIG_ENVIRONMENT_KEY = "lost.sequence";
 
    @Override
    public void registerBeanDefinitions( AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry ) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes( EnableSnowflakeSequence.class.getName() ) );
        Level level = getValue( attributes, "level", Level.MILLISECOND );
        Mode mode = getValue( attributes, "mode", Mode.SPECIFIED );
        boolean primary = getValue( attributes, "primary", false );
        String beanName = getValue( attributes, "name", null );
        checkConfiguration( level, mode );
        registerBean( registry, primary, beanName );
    }

    private void checkConfiguration( Level level, Mode mode ) {
        if ( this.configuration == null ) {
            this.configuration = getBean( REGISTER_CONFIG_BEAN_CLASS, REGISTER_CONFIG_BEAN_NAME );
            if ( this.configuration == null ) {
                this.configuration = getConfiguration();
                if ( this.configuration == null ) {
                    this.configuration = new SequenceAutoConfiguration();
                }
            }
        }
        if ( this.configuration.getLevel() == Level.UNKNOWN && level != Level.UNKNOWN ) {
            this.configuration.setLevel( level );
        }
        if ( this.configuration.getMode() == Mode.UNKNOWN && mode != Mode.UNKNOWN ) {
            this.configuration.setMode( mode );
        }
    }

    private void registerBean( BeanDefinitionRegistry registry, boolean primary, String beanName ) {
        // 采用构造方法注入
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        // 启用秒级
        if ( configuration.getLevel() == Level.SECOND ) {
            argumentValues.addIndexedArgumentValue( 0, TimeUnit.SECONDS );
        } else {
            argumentValues.addIndexedArgumentValue( 0, TimeUnit.MILLISECONDS );
        }
        argumentValues.addIndexedArgumentValue( 1, configuration.getEpochTimestamp() );
        // 启用mac地址动态获取
        if ( configuration.getMode() == Mode.MAC ) {
            argumentValues.addIndexedArgumentValue( 2, SequenceUtil.getDefaultWorkerId( 5, 5 ) );
            argumentValues.addIndexedArgumentValue( 3, SequenceUtil.getDefaultDataCenterId( 5 ) );
        } else {
            argumentValues.addIndexedArgumentValue( 2, configuration.getWorkerId() );
            argumentValues.addIndexedArgumentValue( 3, configuration.getDataCenterId() );
        }
        this.registerBean( registry, REGISTER_BEAN_CLASS, argumentValues, primary, beanName );
    }

    @Override
    public void setEnvironment( Environment environment ) {
        this.environment = environment;
    }

    private SequenceAutoConfiguration getConfiguration() {
        try {
            Binder binder = Binder.get( environment );
            return binder.bind( REGISTER_CONFIG_ENVIRONMENT_KEY, REGISTER_CONFIG_BEAN_CLASS ).get();
        } catch ( Exception e ) {
            // ignore
            return null;
        }
    }
}
