package com.wkit.lost.mybatis.core.injector.method;

import com.wkit.lost.mybatis.annotation.extension.Executing;
import com.wkit.lost.mybatis.core.mapping.script.ScriptBuilder;
import com.wkit.lost.mybatis.core.mapping.sql.Provider;
import com.wkit.lost.mybatis.core.mapping.sql.ProviderBuilder;
import com.wkit.lost.mybatis.core.mapping.sql.ProviderCache;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.utils.StringUtil;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;

import java.util.Optional;

/**
 * 抽象通用方法注入
 * @param <T> SQL提供类
 */
public abstract class AbstractGeneralMethod<T extends Provider> extends AbstractMethod implements ProviderBuilder<T> {

    /**
     * 创建主键生成器
     * @param table       表映射信息
     * @param statementId {@link MappedStatement}-ID
     * @return {@link KeyGenerator}
     */
    protected KeyGenerator createKeyGenerator(TableWrapper table, String statementId) {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        KeyGenerator keyGenerator = new NoKeyGenerator();
        // 序列
        String id = statementId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        // 检查是否存在主键
        if (primaryKey != null) {
            if (primaryKey.isIdentity()) {
                // 自增(数据库自动实现)
                keyGenerator = new Jdbc3KeyGenerator();
                this.configuration.setUseGeneratedKeys(true);
            } else if (StringUtil.hasText(primaryKey.getSequenceName())) {
                // 创建selectKey映射
                StatementType statementType = StatementType.PREPARED;
                String keyProperty = primaryKey.getProperty();
                String keyColumn = primaryKey.getColumn();
                SqlSource sqlSource = languageDriver.createSqlSource(this.configuration,
                        ScriptUtil.getSequenceScript(this.configuration, primaryKey), null);
                this.assistant.addMappedStatement(id, sqlSource, statementType, SqlCommandType.SELECT,
                        null, null, null, null, null,
                        primaryKey.getJavaType(), null, false, false,
                        false, new NoKeyGenerator(), keyProperty, keyColumn, null,
                        this.languageDriver, null);
                id = this.assistant.applyCurrentNamespace(id, false);
                MappedStatement ms = this.assistant.getConfiguration().getMappedStatement(id, false);
                // 根据<selectKey>标签获取主键
                keyGenerator = new SelectKeyGenerator(ms, Optional.ofNullable(primaryKey.getExecuting())
                        .map(Executing::value).orElse(false));
                // useGeneratorKeys = true
                this.assistant.getConfiguration().addKeyGenerator(id, keyGenerator);
                this.assistant.getConfiguration().setUseGeneratedKeys(true);
            }
        }
        return keyGenerator;
    }

    /**
     * 添加插入{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param __              返回值类型
     * @param table           表对象
     * @param provider        SQL提供者
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addInsertMappedStatement(Class<?> mapperInterface, Class<?> __,
                                                       TableWrapper table, Provider provider) {
        KeyGenerator keyGenerator = createKeyGenerator(table, (mapperInterface.getName() + "." + applyMethod()));
        ColumnWrapper primary = table.getPrimaryKey();
        boolean hasPrimaryKey = primary != null;
        Class<?> entity = table.getEntity();
        ScriptBuilder scriptBuilder = createScriptBuilder(table, provider);
        return addInsertMappedStatement(mapperInterface, applyMethod(), createSqlSource(scriptBuilder, entity),
                entity, keyGenerator, hasPrimaryKey ? primary.getProperty() : null, hasPrimaryKey ?
                        primary.getColumn() : null);
    }
    
    /**
     * 添加删除{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param __              返回值类型
     * @param table           表对象
     * @param provider        SQL提供者
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addDeleteMappedStatement(Class<?> mapperInterface, Class<?> __,
                                                       TableWrapper table, Provider provider) {
        Class<?> entity = table.getEntity();
        return addDeleteMappedStatement(mapperInterface, applyMethod(),
                createSqlSource(createScriptBuilder(table, provider), entity), null);
    }

    /**
     * 添加更新{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param __              返回值类型
     * @param table           表对象
     * @param provider        SQL提供者
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addUpdateMappedStatement(Class<?> mapperInterface, Class<?> __,
                                                       TableWrapper table, Provider provider) {
        Class<?> entity = table.getEntity();
        return addUpdateMappedStatement(mapperInterface, applyMethod(),
                createSqlSource(createScriptBuilder(table, provider), entity), entity);
    }

    /**
     * 添加查询{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param table           表对象
     * @param resultType      返回值类型
     * @param provider        SQL提供者
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addSelectMappedStatement(Class<?> mapperInterface, Class<?> resultType,
                                                       TableWrapper table, Provider provider) {
        Class<?> entity = table.getEntity();
        return addSelectMappedStatement(mapperInterface, applyMethod(), createSqlSource(
                createScriptBuilder(table, provider), entity), entity, resultType, table);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public T target() {
        return (T) ProviderCache.newInstance(getClass());
    }
}
