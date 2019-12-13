package com.wkit.lost.mybatis.spring.boot.filling;

import com.wkit.lost.mybatis.filling.DefaultMetaObjectFillingHandler;
import com.wkit.lost.mybatis.filling.MetaObjectFillAuxiliary;
import com.wkit.lost.mybatis.spring.boot.registry.AbstractBeanDefinitionRegistry;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注册自动填充值对象
 * @author wvkity
 */
public class MateObjectAutoFillingRegistrar extends AbstractBeanDefinitionRegistry
        implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

    private static final Class<?> REGISTER_BEAN_CLASS = DefaultMetaObjectFillingHandler.class;
    private static final Class<MetaObjectFillAuxiliary> REGISTER_AUXILIARY_CLASS = MetaObjectFillAuxiliary.class;
    private static final String REGISTER_AUXILIARY_BEAN_NAME = "metaObjectFillAuxiliary";

    @Override
    public void registerBeanDefinitions( AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry ) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes( EnableMetaObjectAutoFilling.class.getName() ) );
        boolean primary = getValue( attributes, "primary", false );
        boolean disableAutoMatching = getValue( attributes, "disableAutoMatching", false );
        boolean disableInsert = getValue( attributes, "disableInsert", false );
        boolean disableUpdate = getValue( attributes, "disableUpdate", false );
        boolean disableDelete = getValue( attributes, "disableDelete", false );
        String beanName = getValue( attributes, "name", null );
        // 采用构造方法注入
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue( 0, disableInsert );
        argumentValues.addIndexedArgumentValue( 1, disableUpdate );
        argumentValues.addIndexedArgumentValue( 2, disableDelete );
        argumentValues.addIndexedArgumentValue( 3, disableAutoMatching );
        argumentValues.addIndexedArgumentValue( 4, getBean( REGISTER_AUXILIARY_CLASS, 
                REGISTER_AUXILIARY_BEAN_NAME ) );
        registerBean( registry, REGISTER_BEAN_CLASS, argumentValues, primary, beanName );
    }
}
