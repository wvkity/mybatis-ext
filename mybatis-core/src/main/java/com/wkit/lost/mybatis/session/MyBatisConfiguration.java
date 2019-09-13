package com.wkit.lost.mybatis.session;

import com.wkit.lost.mybatis.binding.MyBatisMapperRegistry;
import com.wkit.lost.mybatis.executor.resultset.MyBatisResultSetHandler;
import com.wkit.lost.mybatis.reflection.wrapper.MyBatisObjectWrapperFactory;
import com.wkit.lost.mybatis.scripting.MyBatisXMLLanguageDriver;
import com.wkit.lost.mybatis.type.handlers.StandardInstantTypeHandler;
import com.wkit.lost.mybatis.type.handlers.StandardJapaneseDateTypeHandler;
import com.wkit.lost.mybatis.type.handlers.StandardLocalDateTimeTypeHandler;
import com.wkit.lost.mybatis.type.handlers.StandardLocalDateTypeHandler;
import com.wkit.lost.mybatis.type.handlers.StandardLocalTimeTypeHandler;
import com.wkit.lost.mybatis.type.handlers.StandardOffsetDateTimeTypeHandler;
import com.wkit.lost.mybatis.type.handlers.StandardOffsetTimeTypeHandler;
import com.wkit.lost.mybatis.type.handlers.StandardZonedDateTimeTypeHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseDate;

/**
 * {@inheritDoc}
 */
@Log4j2
public class MyBatisConfiguration extends Configuration {

    protected final MyBatisMapperRegistry mapperRegistry = new MyBatisMapperRegistry( this );

    public MyBatisConfiguration() {
        super();
        setDefaultScriptingLanguage( MyBatisXMLLanguageDriver.class );
        // registry JDK8+ time api(JSR-310)
        TypeHandlerRegistry typeHandlerRegistry = this.getTypeHandlerRegistry();
        typeHandlerRegistry.register( Instant.class, StandardInstantTypeHandler.class );
        typeHandlerRegistry.register( JapaneseDate.class, StandardJapaneseDateTypeHandler.class );
        typeHandlerRegistry.register( LocalDateTime.class, StandardLocalDateTimeTypeHandler.class );
        typeHandlerRegistry.register( LocalDate.class, StandardLocalDateTypeHandler.class );
        typeHandlerRegistry.register( LocalTime.class, StandardLocalTimeTypeHandler.class );
        typeHandlerRegistry.register( OffsetDateTime.class, StandardOffsetDateTimeTypeHandler.class );
        typeHandlerRegistry.register( OffsetTime.class, StandardOffsetTimeTypeHandler.class );
        typeHandlerRegistry.register( ZonedDateTime.class, StandardZonedDateTimeTypeHandler.class );
        // 设置ObjectWrapperFactory对象
        this.objectWrapperFactory = new MyBatisObjectWrapperFactory();
        // 默认开启驼峰转换
        this.setMapUnderscoreToCamelCase( true );
    }

    @Override
    public void addMappedStatement( MappedStatement ms ) {
        if ( mappedStatements.containsKey( ms.getId() ) ) {
            log.warn( "Mapper `{}` is ignored, because is's exists, maybe from xml file.", ms.getId() );
            return;
        }
        super.addMappedStatement( ms );
    }

    @Override
    public void setDefaultScriptingLanguage( Class<? extends LanguageDriver> driver ) {
        if ( driver == null ) {
            driver = MyBatisXMLLanguageDriver.class;
        }
        super.setDefaultScriptingLanguage( driver );
    }

    @Override
    public MyBatisMapperRegistry getMapperRegistry() {
        return this.mapperRegistry;
    }

    @Override
    public <T> void addMapper( Class<T> type ) {
        this.mapperRegistry.addMapper( type );
    }

    @Override
    public void addMappers( String packageName, Class<?> superType ) {
        this.mapperRegistry.addMappers( packageName, superType );
    }

    @Override
    public void addMappers( String packageName ) {
        this.mapperRegistry.addMappers( packageName );
    }

    @Override
    public <T> T getMapper( Class<T> type, SqlSession sqlSession ) {
        return this.mapperRegistry.getMapper( type, sqlSession );
    }

    @Override
    public boolean hasMapper( Class<?> type ) {
        return this.mapperRegistry.hasMapper( type );
    }

    @Override
    public ResultSetHandler newResultSetHandler( Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql ) {
        ResultSetHandler resultSetHandler = new MyBatisResultSetHandler( executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds );
        resultSetHandler = ( ResultSetHandler ) interceptorChain.pluginAll( resultSetHandler );
        return resultSetHandler;
    }
}
