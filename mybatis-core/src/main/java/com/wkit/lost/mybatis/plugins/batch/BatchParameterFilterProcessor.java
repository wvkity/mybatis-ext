package com.wkit.lost.mybatis.plugins.batch;

import com.wkit.lost.mybatis.batch.BatchDataBeanWrapper;
import com.wkit.lost.mybatis.plugins.processor.Processor;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.MetaObjectUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Log4j2
public class BatchParameterFilterProcessor extends Processor {

    private static final String VARIABLE_CONFIGURATION = "configuration";
    private static final String VARIABLE_MAPPED_STATEMENT = "mappedStatement";
    private static final String VARIABLE_PARAMETER_OBJECT = "parameterObject";
    private static final Set<String> BATCH_METHODS =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( "batchInsert", "batchInsertNotWithAudit" ) ) );


    @Override
    public Object intercept( Invocation invocation ) throws Throwable {
        Object target = invocation.getTarget();
        if ( target instanceof DefaultParameterHandler ) {
            DefaultParameterHandler handler = ( DefaultParameterHandler ) target;
            MetaObject metadata = MetaObjectUtil.forObject( handler );
            MappedStatement ms = ( MappedStatement ) metadata.getValue( VARIABLE_MAPPED_STATEMENT );
            if ( ms.getSqlCommandType() == SqlCommandType.INSERT ) {
                if ( metadata.hasGetter( VARIABLE_PARAMETER_OBJECT ) ) {
                    Object parameterTarget = metadata.getValue( VARIABLE_PARAMETER_OBJECT );
                    if ( parameterTarget instanceof BatchDataBeanWrapper
                            || ( parameterTarget instanceof Map
                            && ( ( Map<?, ?> ) parameterTarget ).getOrDefault( Constants.PARAM_BATCH_BEAN_WRAPPER,
                            null ) instanceof BatchDataBeanWrapper ) ) {
                        if ( metadata.hasGetter( VARIABLE_MAPPED_STATEMENT ) ) {
                            Object msTarget = metadata.getValue( VARIABLE_MAPPED_STATEMENT );
                            if ( msTarget instanceof MappedStatement ) {
                                if ( filter( ms, invocation.getArgs()[ 0 ] ) ) {
                                    return null;
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
}
