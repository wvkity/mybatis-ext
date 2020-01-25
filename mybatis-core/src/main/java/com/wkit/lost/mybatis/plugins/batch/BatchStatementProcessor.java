package com.wkit.lost.mybatis.plugins.batch;

import com.wkit.lost.mybatis.batch.BatchDataBeanWrapper;
import com.wkit.lost.mybatis.batch.KeyGeneratorType;
import com.wkit.lost.mybatis.plugins.processor.Processor;
import com.wkit.lost.mybatis.scripting.defaults.MyBatisDefaultParameterHandler;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.MetaObjectUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 批量保存处理器
 * @author wvkity
 */
@Log4j2
public class BatchStatementProcessor extends Processor {

    private static final String VARIABLE_DELEGATE = "delegate";
    private static final String VARIABLE_MAPPED_STATEMENT = "mappedStatement";
    private static final String VARIABLE_EXECUTE_BEFORE = "executeBefore";
    private static final String VARIABLE_EXECUTOR = "executor";
    private static final String METHOD_UPDATE = "update";
    private static final String METHOD_BATCH = "batch";

    private static final Set<String> BATCH_METHODS =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( "batchInsert", "batchInsertNotWithAudit" ) ) );

    @SuppressWarnings( { "unchecked" } )
    @Override
    public Object intercept( Invocation invocation ) throws Throwable {
        Object target = invocation.getTarget();
        Method method = invocation.getMethod();
        if ( target instanceof RoutingStatementHandler ) {
            RoutingStatementHandler rsh = ( RoutingStatementHandler ) target;
            MetaObject rshMetadata = MetaObjectUtil.forObject( rsh );
            if ( rshMetadata.hasGetter( VARIABLE_DELEGATE ) ) {
                Object delegateTarget = rshMetadata.getValue( VARIABLE_DELEGATE );
                if ( delegateTarget instanceof PreparedStatementHandler ) {
                    PreparedStatementHandler psh = ( PreparedStatementHandler ) delegateTarget;
                    MetaObject pshMetadata = MetaObjectUtil.forObject( psh );
                    BoundSql boundSql = psh.getBoundSql();
                    Object methodParameterTarget = boundSql.getParameterObject();
                    if ( methodParameterTarget instanceof Map ) {
                        methodParameterTarget = ( ( Map<?, ?> ) methodParameterTarget ).get( Constants.PARAM_BATCH_BEAN_WRAPPER );
                    }
                    if ( methodParameterTarget instanceof BatchDataBeanWrapper ) {
                        if ( pshMetadata.hasGetter( VARIABLE_MAPPED_STATEMENT ) ) {
                            MappedStatement ms =
                                    ( MappedStatement ) pshMetadata.getValue( VARIABLE_MAPPED_STATEMENT );
                            PreparedStatement ps = ( PreparedStatement ) invocation.getArgs()[ 0 ];
                            if ( filter( ms, ps ) ) {
                                Executor executor = ( Executor ) pshMetadata.getValue( VARIABLE_EXECUTOR );
                                if ( canExecBatch( executor, method ) ) {
                                    BatchDataBeanWrapper<Object> wrapper = ( BatchDataBeanWrapper<Object> ) methodParameterTarget;
                                    KeyGeneratorType keyGeneratorType = parseKeyGeneratorType( ms );
                                    // 提前生成主键值
                                    if ( keyGeneratorType == KeyGeneratorType.BEFORE ) {
                                        generatePrimaryKeyValue( ps, psh, pshMetadata, wrapper, ms, keyGeneratorType );
                                    }
                                    executeBatch( ms, wrapper, ps, boundSql, keyGeneratorType );
                                    return wrapper.getAffectedRowCount();
                                }
                            }
                        }
                    }
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public boolean filter( MappedStatement ms, Object parameter ) {
        SqlCommandType exec = ms.getSqlCommandType();
        return exec == SqlCommandType.INSERT && BATCH_METHODS.contains( execMethod( ms ) );
    }

    /**
     * 检查是否可执行批量保存
     * @param executor 执行器
     * @param method   执行方法
     * @return true: 是, false: 否
     */
    private boolean canExecBatch( Executor executor, Method method ) {
        return METHOD_UPDATE.equals( method.getName() )
                || ( executor instanceof BatchExecutor && METHOD_BATCH.equals( method.getName() ) );
    }

    /**
     * 解析主键生成方式
     * @param ms {@link MappedStatement}
     * @return {@link KeyGeneratorType}
     */
    private KeyGeneratorType parseKeyGeneratorType( MappedStatement ms ) {
        KeyGenerator keyGenerator = ms.getKeyGenerator();
        KeyGeneratorType keyGeneratorType = KeyGeneratorType.NONE;
        if ( keyGenerator instanceof SelectKeyGenerator ) {
            MetaObject skgMetadata = MetaObjectUtil.forObject( keyGenerator );
            boolean execBefore = ( boolean ) skgMetadata.getValue( VARIABLE_EXECUTE_BEFORE );
            keyGeneratorType = execBefore ? KeyGeneratorType.BEFORE : KeyGeneratorType.AFTER;
        } else if ( keyGenerator instanceof Jdbc3KeyGenerator ) {
            keyGeneratorType = KeyGeneratorType.AFTER;
        }
        return keyGeneratorType;
    }

    /**
     * 生成主键值
     * @param ps          {@link PreparedStatement}对象
     * @param __          {@link PreparedStatementHandler}对象
     * @param pshMetadata {@link MetaObject}对象
     * @param wrapper     批量保存包装对象
     * @param ms          {@link MappedStatement}对象
     * @param ___         主键生成方式
     */
    protected void generatePrimaryKeyValue( PreparedStatement ps, PreparedStatementHandler __,
                                            MetaObject pshMetadata, BatchDataBeanWrapper<Object> wrapper,
                                            MappedStatement ms, KeyGeneratorType ___ ) {
        Executor executor = ( Executor ) pshMetadata.getValue( VARIABLE_EXECUTOR );
        KeyGenerator keyGenerator = ms.getKeyGenerator();
        for ( Object param : wrapper.getData() ) {
            keyGenerator.processBefore( executor, ms, ps, param );
        }
    }

    /**
     * 执行批量操作
     * @param ms               {@link MappedStatement}对象
     * @param wrapper          数据包装对象
     * @param ps               {@link PreparedStatement}对象
     * @param boundSql         {@link BoundSql}对象
     * @param keyGeneratorType 主键生成方式
     * @return 受影响行数
     * @throws SQLException SQL异常
     */
    protected int executeBatch( MappedStatement ms, BatchDataBeanWrapper<Object> wrapper,
                                PreparedStatement ps, BoundSql boundSql, KeyGeneratorType keyGeneratorType ) throws SQLException {
        int batchSize = wrapper.getBatchSize();
        Collection<Object> data = wrapper.getData();
        List<Object> batchParams = new ArrayList<>( batchSize );
        for ( Object object : data ) {
            Map<String, Object> paramMap = new MapperMethod.ParamMap<>();
            paramMap.put( Constants.PARAM_ENTITY, object );
            DefaultParameterHandler handler = new MyBatisDefaultParameterHandler( ms, paramMap, boundSql );
            handler.setParameters( ps );
            ps.addBatch();
            batchParams.add( object );
            if ( batchParams.size() == batchSize ) {
                executeBatch( ms, ps, wrapper, keyGeneratorType, batchParams );
                batchParams.clear();
            }
        }
        if ( batchParams.size() % batchSize != 0 ) {
            executeBatch( ms, ps, wrapper, keyGeneratorType, batchParams );
        }
        return 0;
    }

    /**
     * 执行批处理
     * @param ms               {@link MappedStatement}对象
     * @param ps               {@link PreparedStatement}对象
     * @param wrapper          数据包装对象
     * @param keyGeneratorType 主键生成方式
     * @param batchParams      待保存对象
     * @throws SQLException SQL异常
     */
    protected void executeBatch( MappedStatement ms, PreparedStatement ps, BatchDataBeanWrapper<Object> wrapper,
                                 KeyGeneratorType keyGeneratorType, List<Object> batchParams ) throws SQLException {
        int[] batchResult = ps.executeBatch();
        wrapper.addRowCounts( batchResult );
        if ( keyGeneratorType == KeyGeneratorType.AFTER ) {
            String[] keyProperties = ms.getKeyProperties();
            if ( keyProperties != null && keyProperties.length > 0 ) {
                String keyProperty = keyProperties[ 0 ];
                ResultSet resultSet = ps.getGeneratedKeys();
                List<Object> keys = getGeneratedKeys( resultSet );
                Configuration configuration = ms.getConfiguration();
                for ( int i = 0, size = keys.size(); i < size; i++ ) {
                    setValue( configuration, batchParams.get( i ), keyProperty, keys.get( i ) );
                }
            }
        }
    }

    /**
     * 获取生成的主键值
     * @param resultSet 结果集对象
     * @return 主键集合
     * @throws SQLException SQL异常
     */
    protected List<Object> getGeneratedKeys( ResultSet resultSet ) throws SQLException {
        List<Object> list = new ArrayList<>();
        while ( resultSet.next() ) {
            list.add( resultSet.getObject( 1 ) );
        }
        return list;
    }

    /**
     * 设置主键值
     * @param configuration      配置对象
     * @param param              实体参数对象
     * @param primaryKeyProperty 主键属性
     * @param value              值
     */
    protected void setValue( Configuration configuration, Object param, String primaryKeyProperty, Object value ) {
        MetaObject metadata = configuration.newMetaObject( param );
        Class<?> primaryKeyType = metadata.getSetterType( primaryKeyProperty );
        if ( primaryKeyType == Integer.class || primaryKeyType == int.class ) {
            metadata.setValue( primaryKeyProperty, ( ( Number ) value ).intValue() );
        } else if ( primaryKeyType == Long.class || primaryKeyType == long.class ) {
            metadata.setValue( primaryKeyProperty, ( ( Number ) value ).longValue() );
        } else {
            metadata.setValue( primaryKeyProperty, value );
        }
    }
}
