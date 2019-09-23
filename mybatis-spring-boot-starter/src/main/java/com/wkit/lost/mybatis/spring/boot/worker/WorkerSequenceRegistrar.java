package com.wkit.lost.mybatis.spring.boot.worker;

import com.wkit.lost.mybatis.snowflake.sequence.SnowFlakeSequence;
import com.wkit.lost.mybatis.snowflake.worker.SequenceUtil;
import com.wkit.lost.mybatis.spring.boot.autoconfigure.MyBatisAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@AutoConfigureBefore( { MyBatisAutoConfiguration.class } )
@AutoConfigureAfter( { WorkerAutoConfiguration.class } )
class WorkerSequenceRegistrar implements BeanFactoryAware, EnvironmentAware, ImportBeanDefinitionRegistrar {

    private WorkerAutoConfiguration configuration;
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions( AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry ) {
        if ( this.configuration == null ) {
            if ( registry.containsBeanDefinition( "workerAutoConfiguration" ) ) {
                this.configuration = ( WorkerAutoConfiguration ) beanFactory.getBean( "workerAutoConfiguration" );
            } else {
                this.configuration = new WorkerAutoConfiguration();
            }
        }
        AnnotationAttributes attributes = AnnotationAttributes.fromMap( importingClassMetadata.getAnnotationAttributes( EnableWorkerSequence.class.getName() ) );
        boolean secondEnable = Optional.ofNullable( attributes ).map( attr -> attr.getBoolean( "secondEnable" ) ).orElse( false );
        boolean macEnable = Optional.ofNullable( attributes ).map( attr -> attr.getBoolean( "macEnable" ) ).orElse( false );
        if ( secondEnable ) {
            this.configuration.setSecondEnable( secondEnable );
        }
        if ( macEnable ) {
            this.configuration.setMacEnable( macEnable );
        }
        registerBean( registry );
    }

    private void registerBean( BeanDefinitionRegistry registry ) {
        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition( SnowFlakeSequence.class );
        GenericBeanDefinition definition = ( GenericBeanDefinition ) definitionBuilder.getRawBeanDefinition();
        definition.setBeanClass( SnowFlakeSequence.class );
        definition.setSynthetic( true );
        // 属性
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        // 启用秒级
        if ( configuration.isSecondEnable() ) {
            argumentValues.addIndexedArgumentValue( 0, TimeUnit.SECONDS );
        } else {
            argumentValues.addIndexedArgumentValue( 0, TimeUnit.MILLISECONDS );
        }
        argumentValues.addIndexedArgumentValue( 1, configuration.getEpochTimestamp() );
        // 使用mac地址动态获取
        if ( configuration.isMacEnable() ) {
            argumentValues.addIndexedArgumentValue( 2, SequenceUtil.getDefaultWorkerId( 5, 5 ) );
            argumentValues.addIndexedArgumentValue( 3, SequenceUtil.getDefaultDataCenterId( 5 ) );
        } else {
            argumentValues.addIndexedArgumentValue( 2, configuration.getWorkerId() );
            argumentValues.addIndexedArgumentValue( 3, configuration.getDataCenterId() );
        }
        definition.setConstructorArgumentValues( argumentValues );
        registry.registerBeanDefinition( "workerSequence", definition );
    }

    @Override
    public void setBeanFactory( BeanFactory beanFactory ) throws BeansException {
        this.beanFactory = ( DefaultListableBeanFactory ) beanFactory;
    }

    @Override
    public void setEnvironment( Environment environment ) {
        try {
            Binder binder = Binder.get( environment );
            this.configuration = binder.bind( "sequence", WorkerAutoConfiguration.class ).get();
        } catch ( Exception e ) {
            try {
                if ( beanFactory.containsBeanDefinition( "workerAutoConfiguration" ) ) {
                    this.configuration = ( WorkerAutoConfiguration ) beanFactory.getBean( "workerAutoConfiguration" );
                }
            } catch ( Exception e1 ) {
                // ignore
            }
        }
    }
}
