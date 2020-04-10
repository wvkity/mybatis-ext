package com.wkit.lost.mybatis.core.injector.method;

import com.wkit.lost.mybatis.core.mapping.script.ScriptBuilder;
import com.wkit.lost.mybatis.core.mapping.script.ScriptBuilderFactory;
import com.wkit.lost.mybatis.core.mapping.sql.Provider;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.utils.StringUtil;
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

/**
 * 抽象方法映射注入器
 * @author wvkity
 */
@Log4j2
public abstract class AbstractMethod implements Method {

    /**
     * 构建器对象
     */
    protected MapperBuilderAssistant assistant;

    /**
     * MyBatis配置对象
     */
    protected Configuration configuration;

    /**
     * 脚本驱动对象
     */
    protected LanguageDriver languageDriver;

    @Override
    public void inject(MapperBuilderAssistant assistant, Class<?> mapperInterface, Class<?> resultType, TableWrapper table) {
        this.assistant = assistant;
        this.configuration = assistant.getConfiguration();
        this.languageDriver = this.configuration.getDefaultScriptingLanguageInstance();
        // 注入
        this.injectMappedStatement(table, mapperInterface, resultType);
    }

    /**
     * 添加{@link MappedStatement}对象到容器中
     * @param mapperInterface 接口
     * @param id              唯一标识
     * @param sqlSource       {@link SqlSource}对象
     * @param commandType     指令类型
     * @param parameterType   参数类型
     * @param resultMap       返回结果集
     * @param resultType      返回值类型
     * @param keyGenerator    主键生成器
     * @param keyProperty     主键属性
     * @param keyColumn       主键列
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addMappedStatement(Class<?> mapperInterface, String id, SqlSource sqlSource,
                                                 SqlCommandType commandType, Class<?> parameterType,
                                                 String resultMap, Class<?> resultType, KeyGenerator keyGenerator,
                                                 String keyProperty, String keyColumn) {
        String realId;
        if (StringUtil.isBlank(id)) {
            realId = applyMethod();
        } else {
            realId = id;
        }
        String statementName = mapperInterface.getName() + "." + realId;
        if (hasMappedStatement(statementName)) {
            log.warn("The `{}` MappedStatement object has been loaded into the container by XML or SqlProvider configuration, " +
                    "and the SQL injection is automatically ignored.", statementName);
            return this.configuration.getMappedStatement(statementName, false);
        }
        boolean isSelect = commandType == SqlCommandType.SELECT;
        return this.assistant.addMappedStatement(realId, sqlSource, StatementType.PREPARED, commandType,
                null, null, null, parameterType, resultMap, resultType,
                null, !isSelect, isSelect, false, keyGenerator, keyProperty,
                keyColumn, this.configuration.getDatabaseId(), this.languageDriver, null);
    }

    /**
     * 添加{@link MappedStatement}对象到容器中(保存操作类型)
     * @param mapperInterface 接口
     * @param id              唯一标识
     * @param sqlSource       {@link SqlSource}对象
     * @param parameterType   参数类型
     * @param keyGenerator    主键生成器
     * @param keyProperty     主键属性
     * @param keyColumn       主键列
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addInsertMappedStatement(Class<?> mapperInterface, String id, SqlSource sqlSource,
                                                       Class<?> parameterType, KeyGenerator keyGenerator,
                                                       String keyProperty, String keyColumn) {
        return addMappedStatement(mapperInterface, id, sqlSource, SqlCommandType.INSERT, parameterType,
                null, Integer.class, keyGenerator, keyProperty, keyColumn);
    }

    /**
     * 添加{@link MappedStatement}对象到容器中(更新操作类型)
     * @param mapperInterface 接口
     * @param id              唯一标识
     * @param sqlSource       {@link SqlSource}对象
     * @param parameterType   参数类型
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addUpdateMappedStatement(Class<?> mapperInterface, String id, SqlSource sqlSource,
                                                       Class<?> parameterType) {
        return addMappedStatement(mapperInterface, id, sqlSource, SqlCommandType.UPDATE, parameterType,
                null, Integer.class, new NoKeyGenerator(), null, null);
    }

    /**
     * 添加{@link MappedStatement}对象到容器中(删除操作类型)
     * @param mapperInterface 接口
     * @param id              唯一标识
     * @param sqlSource       {@link SqlSource}对象
     * @param parameterType   参数类型
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addDeleteMappedStatement(Class<?> mapperInterface, String id, SqlSource sqlSource,
                                                       Class<?> parameterType) {
        return addMappedStatement(mapperInterface, id, sqlSource, SqlCommandType.DELETE, parameterType,
                null, Integer.class, new NoKeyGenerator(), null, null);
    }

    /**
     * 添加{@link MappedStatement}对象到容器中(查询操作类型)
     * @param mapperInterface 接口
     * @param id              唯一标识
     * @param sqlSource       {@link SqlSource}对象
     * @param parameterType   参数类型
     * @param resultType      返回值类型
     * @param __              表映射对象
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addSelectMappedStatement(Class<?> mapperInterface, String id, SqlSource sqlSource,
                                                       Class<?> parameterType, Class<?> resultType, TableWrapper __) {
        return addMappedStatement(mapperInterface, id, sqlSource, SqlCommandType.SELECT, parameterType,
                null, resultType, new NoKeyGenerator(), null, null);
    }

    /**
     * 检查是否存在{@link MappedStatement}对象
     * @param statementName 唯一名称
     * @return true: 存在 | false: 不存在
     */
    protected boolean hasMappedStatement(final String statementName) {
        return this.configuration.hasStatement(statementName, false);
    }

    /**
     * 创建脚本构建器
     * @param table    表对象
     * @param provider SQL提供者
     * @return 脚本构建器
     */
    protected ScriptBuilder createScriptBuilder(TableWrapper table, Provider provider) {
        return createScriptBuilder(null, table, provider);
    }

    /**
     * 创建脚本构建器
     * @param alias    表别名
     * @param table    表对象
     * @param provider SQL提供者
     * @return 脚本构建器
     */
    protected ScriptBuilder createScriptBuilder(String alias, TableWrapper table, Provider provider) {
        return ScriptBuilderFactory.create(provider, table, table.getEntity(), alias);
    }

    /**
     * 创建{@link SqlSource}对象
     * @param builder       SQL脚本构建器
     * @param parameterType 参数类型
     * @return {@link SqlSource}对象
     */
    protected SqlSource createSqlSource(final ScriptBuilder builder, final Class<?> parameterType) {
        return createSqlSource(builder.build(), parameterType);
    }

    /**
     * 创建{@link SqlSource}对象
     * @param script        SQL脚本
     * @param parameterType 参数类型
     * @return {@link SqlSource}对象
     */
    protected SqlSource createSqlSource(final String script, final Class<?> parameterType) {
        return this.languageDriver.createSqlSource(this.configuration, script, parameterType);
    }

    /**
     * 应用对应方法
     * @return 方法名
     */
    public String applyMethod() {
        return StringUtil.getSimpleNameOfFirstLower(this.getClass());
    }

    /**
     * 注入{@link MappedStatement}对象
     * @param table           表对象
     * @param mapperInterface 接口
     * @param resultType      返回值
     * @return {@link MappedStatement}对象
     */
    public abstract MappedStatement injectMappedStatement(final TableWrapper table,
                                                          final Class<?> mapperInterface, final Class<?> resultType);
}
