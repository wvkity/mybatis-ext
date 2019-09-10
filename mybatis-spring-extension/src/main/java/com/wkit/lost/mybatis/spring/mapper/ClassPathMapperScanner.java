package com.wkit.lost.mybatis.spring.mapper;

import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

@Log4j2
public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    private boolean addToConfig = true;

    private SqlSessionFactory sqlSessionFactory;

    private SqlSessionTemplate sqlSessionTemplate;

    private String sqlSessionFactoryBeanName;

    private String sqlSessionTemplateBeanName;

    private Class<? extends Annotation> annotationClass;

    private Class<?> markerInterface;

    //private MapperProcessor mapperProcessor;

    private String mapperProcessorBeanName;

    private Class<? extends MapperFactoryBean> mapperFactoryBeanClass = MapperFactoryBean.class;

    /**
     * 构造方法
     * @param registry 注册器
     */
    public ClassPathMapperScanner( BeanDefinitionRegistry registry ) {
        super( registry, false );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BeanDefinitionHolder> doScan( String... basePackages ) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan( basePackages );
        if ( beanDefinitions.isEmpty() ) {
            log.warn( "No MyBatis mapper was found in '{}' package. Please check your configuration.", Arrays.toString( basePackages ) );
        } else {
            processBeanDefinitions( beanDefinitions );
        }
        return beanDefinitions;
    }

    private void processBeanDefinitions( Set<BeanDefinitionHolder> beanDefinitions ) {
        for ( BeanDefinitionHolder holder : beanDefinitions ) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            log.debug( "Create MapperFactoryBean with name '{}' and '{}' mapperInterface", holder.getBeanName(), beanClassName );
            if ( beanClassName != null ) {
                definition.getConstructorArgumentValues().addGenericArgumentValue( beanClassName );
            }
            // original
            definition.setBeanClass( org.mybatis.spring.mapper.MapperFactoryBean.class );
            // custom
            definition.setBeanClass( this.mapperFactoryBeanClass );
            // 注入mapperProcessor
            if ( StringUtil.hasText( this.mapperProcessorBeanName ) ) {
                definition.getPropertyValues().add( "mapperProcessor", new RuntimeBeanReference( this.mapperProcessorBeanName ) );
            }/* else {
                *//*if ( this.mapperProcessor == null ) {
                    this.mapperProcessor = new MapperProcessor();
                }*//*
                //definition.getPropertyValues().add( "mapperProcessor", this.mapperProcessor );
            }*/
            definition.getPropertyValues().add( "addToConfig", this.addToConfig );
            boolean explicitFactoryUsed = false;
            if ( StringUtil.hasText( this.sqlSessionFactoryBeanName ) ) {
                definition.getPropertyValues().add( "sqlSessionFactory", new RuntimeBeanReference( this.sqlSessionFactoryBeanName ) );
                explicitFactoryUsed = true;
            } else if ( this.sqlSessionFactory != null ) {
                definition.getPropertyValues().add( "sqlSessionFactory", this.sqlSessionFactory );
                explicitFactoryUsed = true;
            }
            if ( StringUtil.hasText( this.sqlSessionTemplateBeanName ) ) {
                if ( explicitFactoryUsed ) {
                    log.warn( "Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSession is ignored." );
                }
                definition.getPropertyValues().add( "sqlSessionTemplate", new RuntimeBeanReference( this.sqlSessionTemplateBeanName ) );
                explicitFactoryUsed = true;
            } else if ( this.sqlSessionTemplate != null ) {
                if ( explicitFactoryUsed ) {
                    log.warn( "Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSession is ignored." );
                }
                definition.getPropertyValues().add( "sqlSessionTemplate", this.sqlSessionTemplate );
                explicitFactoryUsed = true;
            }
            if ( !explicitFactoryUsed ) {
                log.debug( "Enabling autowire by type for MapperFactoryBean with name '{}'.", holder.getBeanName() );
                definition.setAutowireMode( AbstractBeanDefinition.AUTOWIRE_BY_TYPE );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isCandidateComponent( AnnotatedBeanDefinition beanDefinition ) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return metadata.isInterface() && metadata.isIndependent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkCandidate( @NonNull String beanName, @NonNull BeanDefinition beanDefinition ) throws IllegalStateException {
        if ( super.checkCandidate( beanName, beanDefinition ) ) {
            return true;
        } else {
            log.warn( "Skipping MapperFactoryBean with name '{}' and '{}' mapperInterface. Bean already defined with the same name!", beanName, beanDefinition.getBeanClassName() );
            return false;
        }
    }

    public void registerFilters() {
        boolean acceptAllInterfaces = true;
        if ( this.annotationClass != null ) {
            addIncludeFilter( new AnnotationTypeFilter( this.annotationClass ) );
            acceptAllInterfaces = false;
        }
        if ( this.markerInterface != null ) {
            addIncludeFilter( new AssignableTypeFilter( this.markerInterface ) {
                @Override
                protected boolean matchClassName( String className ) {
                    return false;
                }
            } );
            acceptAllInterfaces = false;
        }
        if ( acceptAllInterfaces ) {
            addIncludeFilter( ( metadataReader, metadataReaderFactory ) -> true );
        }
        // 排除package-info.java
        addExcludeFilter( ( metadataReader, metadataReaderFactory ) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith( "package-info" );
        } );
    }

    // TODO 全局配置
    /*public void setMapperConfiguration( MapperConfiguration configuration ) {
        if ( this.mapperProcessor == null ) {
            this.mapperProcessor = new MapperProcessor();
        }
        this.mapperProcessor.setMapperConfiguration( configuration );
    }*/

    public void setAddToConfig( boolean addToConfig ) {
        this.addToConfig = addToConfig;
    }

    public void setSqlSessionFactory( SqlSessionFactory sqlSessionFactory ) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void setSqlSessionTemplate( SqlSessionTemplate sqlSessionTemplate ) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public void setSqlSessionFactoryBeanName( String sqlSessionFactoryBeanName ) {
        this.sqlSessionFactoryBeanName = sqlSessionFactoryBeanName;
    }

    public void setSqlSessionTemplateBeanName( String sqlSessionTemplateBeanName ) {
        this.sqlSessionTemplateBeanName = sqlSessionTemplateBeanName;
    }

    public void setAnnotationClass( Class<? extends Annotation> annotationClass ) {
        this.annotationClass = annotationClass;
    }

    public void setMarkerInterface( Class<?> markerInterface ) {
        this.markerInterface = markerInterface;
    }

    /*public void setMapperProcessor( MapperProcessor mapperProcessor ) {
        this.mapperProcessor = mapperProcessor;
    }*/

    public void setMapperProcessorBeanName( String mapperProcessorBeanName ) {
        this.mapperProcessorBeanName = mapperProcessorBeanName;
    }

    public void setMapperFactoryBeanClass( Class<? extends MapperFactoryBean> mapperFactoryBeanClass ) {
        this.mapperFactoryBeanClass = mapperFactoryBeanClass;
    }
}
