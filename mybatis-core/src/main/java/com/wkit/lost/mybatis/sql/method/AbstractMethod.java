package com.wkit.lost.mybatis.sql.method;

import com.wkit.lost.mybatis.mapper.MapperSameExecutor;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.sql.mapping.script.AbstractXmlScriptBuilder;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * 抽象注入方法模板
 * @author DT
 */
@Log4j2
public abstract class AbstractMethod implements Method {

    /**
     * 配置对象
     */
    protected Configuration configuration;
    /**
     * 脚本驱动
     */
    protected LanguageDriver languageDriver;
    /**
     * 构建器
     */
    protected MapperBuilderAssistant assistant;

    @Override
    public void inject( MapperBuilderAssistant assistant, Class<?> mapperInterface ) {
        Class<?> entityClass = extractGenericClass( mapperInterface );
        this.assistant = assistant;
        this.configuration = assistant.getConfiguration();
        this.languageDriver = this.configuration.getDefaultScriptingLanguageInstance();
        if ( entityClass != null ) {
            // 获取返回类型
            int index = MapperSameExecutor.class.isAssignableFrom( mapperInterface ) ? 0 : 1;
            Class<?> resultType;
            if ( index == 0 ) {
                resultType = entityClass;
            } else {
                resultType = extractGenericClass( mapperInterface, index );
                if ( resultType == null ) {
                    resultType = entityClass;
                }
            }
            // 解析实体-表映射信息
            Table table = EntityHandler.intercept( assistant, entityClass );
            // 注入
            this.injectMappedStatement( mapperInterface, resultType, table );
        }
    }

    /**
     * 添加查询{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param resultType      返回值类型
     * @param id              {@link MappedStatement}-ID
     * @param sqlSource       SQL源
     * @param table           表映射信息
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addSelectMappedStatement( Class<?> mapperInterface, Class<?> resultType, String id, SqlSource sqlSource, Table table ) {
        return addMappedStatement( mapperInterface, id, sqlSource, SqlCommandType.SELECT, null, null, resultType,
                new NoKeyGenerator(), null, null );
    }

    /**
     * 添加插入{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param parameterType   参数类型
     * @param id              {@link MappedStatement}-ID
     * @param sqlSource       SQL源
     * @param keyGenerator    主键生成器
     * @param keyProperty     主键属性
     * @param keyColumn       主键字段
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addInsertMappedStatement( Class<?> mapperInterface, Class<?> parameterType, String id, SqlSource sqlSource, KeyGenerator keyGenerator, String keyProperty, String keyColumn ) {
        return addMappedStatement( mapperInterface, id, sqlSource, SqlCommandType.INSERT, parameterType, null, Integer.class,
                keyGenerator, keyProperty, keyColumn );
    }

    /**
     * 添加更新{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param parameterType   参数类型
     * @param id              {@link MappedStatement}-ID
     * @param sqlSource       SQL源
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addUpdateMappedStatement( Class<?> mapperInterface, Class<?> parameterType, String id, SqlSource sqlSource ) {
        return addMappedStatement( mapperInterface, id, sqlSource, SqlCommandType.UPDATE, parameterType, null, Integer.class,
                new NoKeyGenerator(), null, null );
    }

    /**
     * 添加删除{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param id              {@link MappedStatement}-ID
     * @param sqlSource       SQL源
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addDeleteMappedStatement( Class<?> mapperInterface, String id, SqlSource sqlSource ) {
        return addMappedStatement( mapperInterface, id, sqlSource, SqlCommandType.DELETE, null, null, Integer.class,
                new NoKeyGenerator(), null, null );
    }

    /**
     * 添加{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param id              {@link MappedStatement}-ID
     * @param sqlSource       SQL源
     * @param sqlCommandType  SQL指令
     * @param parameterType   参数类型
     * @param resultMap       返回结果集
     * @param resultType      返回值类型
     * @param keyGenerator    主键生成器
     * @param keyProperty     主键属性
     * @param keyColumn       主键字段
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addMappedStatement( Class<?> mapperInterface, String id, SqlSource sqlSource, SqlCommandType sqlCommandType,
                                                  Class<?> parameterType, String resultMap, Class<?> resultType,
                                                  KeyGenerator keyGenerator, String keyProperty, String keyColumn ) {
        if ( StringUtil.isBlank( id ) ) {
            id = mappedMethod();
        }
        String statementId = mapperInterface.getName() + "." + id;
        if ( hasMappedStatement( statementId ) ) {
            log.warn( "The `{}` MappedStatement object has been loaded into the container by XML or SqlProvider configuration, " +
                    "and the SQL injection is automatically ignored.", statementId );
            return this.configuration.getMappedStatement( statementId, false );
        }
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        return this.assistant.addMappedStatement( id, sqlSource, StatementType.PREPARED, sqlCommandType, null, null, null,
                parameterType, resultMap, resultType, null, !isSelect, isSelect, false, keyGenerator, keyProperty, keyColumn,
                this.configuration.getDatabaseId(), this.languageDriver, null );
    }

    /**
     * 提取接口泛型类
     * @param mapperInterface 泛型接口
     * @return 泛型类
     */
    protected Class<?> extractGenericClass( final Class<?> mapperInterface ) {
        return extractGenericClass( mapperInterface, 0 );
    }

    /**
     * 创建{@link SqlSource}对象
     * @param scriptBuilder SQL脚本构建器
     * @param parameterType 参数类型
     * @return {@link SqlSource}对象
     */
    protected SqlSource createSqlSource( final AbstractXmlScriptBuilder scriptBuilder, final Class<?> parameterType ) {
        return createSqlSource( scriptBuilder.build(), parameterType );
    }

    /**
     * 创建{@link SqlSource}对象
     * @param script        SQL脚本
     * @param parameterType 参数类型
     * @return {@link SqlSource}对象
     */
    protected SqlSource createSqlSource( final String script, final Class<?> parameterType ) {
        return this.languageDriver.createSqlSource( this.configuration, script, parameterType );
    }

    /**
     * 提取接口泛型类
     * @param mapperInterface 泛型接口
     * @param index           索引
     * @return 泛型类
     */
    protected Class<?> extractGenericClass( final Class<?> mapperInterface, final int index ) {
        Type[] types = mapperInterface.getGenericInterfaces();
        Class<?> target = null;
        for ( Type type : types ) {
            if ( type instanceof ParameterizedType ) {
                Type[] array = ( ( ParameterizedType ) type ).getActualTypeArguments();
                if ( !ArrayUtil.isEmpty( array ) && index > -1 && index < array.length ) {
                    Type temp = array[ index ];
                    if ( accept( temp ) ) {
                        target = ( Class<?> ) temp;
                    }
                    break;
                }
                break;
            }
        }
        return target;
    }

    private boolean accept( final Type type ) {
        return !( type instanceof TypeVariable ) && !( type instanceof WildcardType );
    }

    /**
     * 检查是否存在{@link MappedStatement}对象
     * @param statementName 名称
     * @return true: 存在 | false: 不存在
     */
    private boolean hasMappedStatement( final String statementName ) {
        return this.configuration.hasStatement( statementName, false );
    }

    /**
     * 注入自定义{@link MappedStatement}对象
     * @param mapperInterface 接口
     * @param resultType      返回类型
     * @param table           表映射信息
     * @return {@link MappedStatement}对象
     */
    public abstract MappedStatement injectMappedStatement( final Class<?> mapperInterface, final Class<?> resultType, final Table table );

    /**
     * 映射对应方法
     * @return 方法名
     */
    public abstract String mappedMethod();
}
