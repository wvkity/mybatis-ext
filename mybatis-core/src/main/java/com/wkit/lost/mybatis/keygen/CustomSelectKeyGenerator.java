package com.wkit.lost.mybatis.keygen;

import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisConfiguration;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import java.sql.Statement;
import java.util.Optional;

/**
 * 自定义主键生成器
 * @author DT
 */
public class CustomSelectKeyGenerator extends SelectKeyGenerator {

    public static final String SELECT_KEY_SUFFIX = "!selectKey";
    private final boolean executeBefore;
    private final MappedStatement keyStatement;

    /**
     * 构造方法
     * @param keyStatement  {@link MappedStatement}- selectKey映射对象
     * @param executeBefore 执行时机
     */
    public CustomSelectKeyGenerator( MappedStatement keyStatement, boolean executeBefore ) {
        super( keyStatement, executeBefore );
        this.executeBefore = executeBefore;
        this.keyStatement = keyStatement;
    }

    @Override
    public void processBefore( Executor executor, MappedStatement ms, Statement stmt, Object parameter ) {
        if ( executeBefore ) {
            processGeneratedKeys( executor, ms, parameter );
        }
    }

    @Override
    public void processAfter( Executor executor, MappedStatement ms, Statement stmt, Object parameter ) {
        if ( !executeBefore ) {
            processGeneratedKeys( executor, ms, parameter );
        }
    }
    
    public void processGeneratedKeys( Executor executor, MappedStatement ms, Object parameter ) {
        try {
            if ( parameter != null && this.keyStatement != null && this.keyStatement.getKeyProperties() != null ) {
                final String[] keyProperties = ms.getKeyProperties();
                final Configuration configuration = ms.getConfiguration();
                final MetaObject metaParam = configuration.newMetaObject( parameter );
                if ( keyProperties != null ) {
                    // 获取自定义全局配置
                    MyBatisConfiguration customConfiguration = MyBatisConfigCache.getCustomConfiguration( configuration );
                    // guid接口
                    PrimaryKeyGenerator guidGenerator = Optional.ofNullable( customConfiguration ).map( MyBatisConfiguration::getGenerator ).orElse( new GuidGenerator() );
                    if ( customConfiguration != null ) {
                        // 接口为空则填充
                        if ( customConfiguration.getGenerator() == null ) {
                            customConfiguration.setGenerator( guidGenerator );
                        }
                    }
                    // 生成ID
                    String value = guidGenerator.value();
                    // 设置值
                    MetaObject metaResult = configuration.newMetaObject( value );
                    if ( keyProperties.length == 1 ) {
                        if ( metaResult.hasGetter( keyProperties[ 0 ] ) ) {
                            setValue( metaParam, keyProperties[ 0 ], metaResult.getValue( keyProperties[ 0 ] ) );
                        } else {
                            // no getter for the property - maybe just a single value object
                            // so try that
                            setValue( metaParam, keyProperties[ 0 ], value );
                        }
                    } else {
                        handleMultipleProperties( keyProperties, metaParam, metaResult );
                    }
                }
            }
        } catch ( ExecutorException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new ExecutorException( "Error selecting key or setting result to parameter object. Cause: " + e, e );
        }
    }

    private void handleMultipleProperties( String[] keyProperties,
                                           MetaObject metaParam, MetaObject metaResult ) {
        String[] keyColumns = keyStatement.getKeyColumns();

        if ( keyColumns == null || keyColumns.length == 0 ) {
            // no key columns specified, just use the property names
            for ( String keyProperty : keyProperties ) {
                setValue( metaParam, keyProperty, metaResult.getValue( keyProperty ) );
            }
        } else {
            if ( keyColumns.length != keyProperties.length ) {
                throw new ExecutorException( "If SelectKey has key columns, the number must match the number of key properties." );
            }
            for ( int i = 0; i < keyProperties.length; i++ ) {
                setValue( metaParam, keyProperties[ i ], metaResult.getValue( keyColumns[ i ] ) );
            }
        }
    }

    private void setValue( MetaObject metaParam, String property, Object value ) {
        if ( metaParam.hasSetter( property ) ) {
            metaParam.setValue( property, value );
        } else {
            throw new ExecutorException( "No setter found for the keyProperty '" + property + "' in " + metaParam.getOriginalObject().getClass().getName() + "." );
        }
    }
}
